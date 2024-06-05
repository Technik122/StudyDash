package de.gruppe1.studydash.controllers;

import de.gruppe1.studydash.configurations.UserAuthProvider;
import de.gruppe1.studydash.dtos.SignUpDto;
import de.gruppe1.studydash.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import de.gruppe1.studydash.dtos.UserDto;
import de.gruppe1.studydash.dtos.CredentialsDto;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements WebMvcConfigurer {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;


    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto user = userService.login(credentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.created(URI.create("/users" + user.getId())).body(user);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            boolean isValid = userAuthProvider.isTokenValid(authElements[1]);
            return ResponseEntity.ok(isValid);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            String newToken = userAuthProvider.refreshToken(authElements[1]);
            return ResponseEntity.ok(newToken);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
