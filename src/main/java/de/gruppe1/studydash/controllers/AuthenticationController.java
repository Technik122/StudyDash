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
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements WebMvcConfigurer {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @GetMapping("/messages")
    public ResponseEntity<List<String>> messages() {
        return ResponseEntity.ok(Arrays.asList("ToDo1", "ToDo2", "ToDo3"));
    }

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
}
