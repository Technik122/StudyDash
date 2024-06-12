package de.gruppe1.studydash.configurations;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.exceptions.JwtAuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] authElements = header.split(" ");

            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                try {
                    SecurityContextHolder.getContext()
                            .setAuthentication(userAuthProvider.validateToken(authElements[1]));
                } catch (TokenExpiredException e) {
                    AppException appException = new AppException("Anmeldetoken ist abgelaufen. Bitte erneut einloggen.", HttpStatus.UNAUTHORIZED);
                    response.setStatus(appException.getHttpStatus().value());
                    response.getWriter().write(objectMapper.writeValueAsString(appException));
                    return;
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw new JwtAuthException(e);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}