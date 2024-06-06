package de.gruppe1.studydash.services;

import de.gruppe1.studydash.dtos.CredentialsDto;
import de.gruppe1.studydash.dtos.SignUpDto;
import de.gruppe1.studydash.dtos.UserDto;
import de.gruppe1.studydash.entities.User;
import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.mappers.UserMapper;
import de.gruppe1.studydash.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppException("Username konnte nicht gefunden werden", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()),
                user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Falsches Passwort", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto signUpDto) {
        Optional<User> oUser = userRepository.findByUsername(signUpDto.username());

        if (oUser.isPresent()) {
            throw new AppException("Username existiert bereits", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(signUpDto);

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }
}
