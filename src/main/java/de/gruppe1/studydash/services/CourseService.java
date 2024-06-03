package de.gruppe1.studydash.services;


import de.gruppe1.studydash.dtos.CourseDto;
import de.gruppe1.studydash.entities.Course;
import de.gruppe1.studydash.entities.User;
import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.mappers.CourseMapper;
import de.gruppe1.studydash.repositories.CourseRepository;
import de.gruppe1.studydash.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;

    public CourseDto createCourse(CourseDto courseDto) {
        Course course = courseMapper.dtoToCourse(courseDto);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toCourseDto(savedCourse);
    }

    public CourseDto updateCourse(UUID uuid, CourseDto courseDto) {
        Course course = courseMapper.dtoToCourse(courseDto);
        Course existingCourse = courseRepository.findById(uuid).orElse(null);
        if (existingCourse != null) {
            existingCourse.setName(course.getName());
            Course savedCourse = courseRepository.save(existingCourse);
            return courseMapper.toCourseDto(savedCourse);
        } else {
            return null;
        }
    }

    public boolean deleteCourseById(UUID uuid) {
        Course existingCourse = courseRepository.findById(uuid).orElse(null);
        if (existingCourse != null) {
            courseRepository.delete(existingCourse);
            return true;
        } else {
            return false;
        }
    }

    public List<CourseDto> getCoursesByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new AppException("User not found", HttpStatus.NOT_FOUND
                ));
        List<Course> courses = courseRepository.findByUser(user);
        return courseMapper.toCourseDtos(courses);
    }
}
