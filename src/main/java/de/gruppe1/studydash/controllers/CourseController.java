package de.gruppe1.studydash.controllers;


import de.gruppe1.studydash.configurations.UserAuthProvider;
import de.gruppe1.studydash.dtos.CourseDto;
import de.gruppe1.studydash.dtos.UserDto;
import de.gruppe1.studydash.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final UserAuthProvider userAuthProvider;

    @GetMapping("/get")
    public ResponseEntity<List<CourseDto>> getCoursesByUser(@RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto user = (UserDto) auth.getPrincipal();
            List<CourseDto> courses = courseService.getCoursesByUserId(user.getId());
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            courseDto.setUser(userDto);
            CourseDto createdCourse = courseService.createCourse(courseDto);
            return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Boolean> deleteCourseById(@PathVariable UUID uuid) {
        boolean deleted = courseService.deleteCourseById(uuid);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<CourseDto> updateToDo(@PathVariable UUID uuid, @RequestBody CourseDto courseDto) {
        CourseDto updatedCourse = courseService.updateCourse(uuid, courseDto);
        if (updatedCourse != null) {
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
