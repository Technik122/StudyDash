package de.gruppe1.studydash.configurations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.gruppe1.studydash.dtos.UserDto;
import de.gruppe1.studydash.entities.User;
import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.mappers.UserMapper;
import de.gruppe1.studydash.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto) {
        Date dateNow = new Date();
        // 1h Gültigkeit
        Date validityDate = new Date(dateNow.getTime() + 3_600_600);

        return JWT.create()
                .withIssuer(userDto.getUsername())
                .withIssuedAt(dateNow)
                .withExpiresAt(validityDate)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);

        // strengere Überprüfung, ob User existiert
        User userEntity = userRepository.findByUsername(decoded.getIssuer())
                .orElseThrow(() -> new AppException("Username konnte nicht gefunden werden", HttpStatus.NOT_FOUND));

        return new UsernamePasswordAuthenticationToken(userMapper.toUserDto(userEntity), null, Collections.emptyList());
    }

    public boolean isTokenValid(String token) {
        try {
            validateToken(token);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public String refreshToken(String expiredToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decoded = verifier.verify(expiredToken);
            if (decoded.getExpiresAt().before(new Date())) {
                User userEntity = userRepository.findByUsername(decoded.getIssuer())
                        .orElseThrow(() -> new AppException("Username konnte nicht gefunden werden", HttpStatus.NOT_FOUND));
                return createToken(userMapper.toUserDto(userEntity));
            } else {
                throw new AppException("Anmeldetoken ist abgelaufen. Bitte erneut einloggen.", HttpStatus.BAD_REQUEST);
            }
        } catch (JWTVerificationException e) {
            throw new AppException("Anmeldetoken ist fehlerhaft. Bitte einloggen.", HttpStatus.UNAUTHORIZED);
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getIssuer();
        } catch (JWTVerificationException exception) {
            throw new AppException("Anmeldetoken ist fehlerhaft. Bitte erneut einloggen.", HttpStatus.UNAUTHORIZED);
        }
    }
}
