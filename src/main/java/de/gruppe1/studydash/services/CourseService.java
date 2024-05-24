package de.gruppe1.studydash.services;


import de.gruppe1.studydash.entities.Course;
import de.gruppe1.studydash.entities.User;
import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.repositories.CourseRepository;
import de.gruppe1.studydash.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course course) {
        Course existingCourse = courseRepository.findById(id).orElse(null);
        if (existingCourse != null) {
            existingCourse.setName(course.getName());
            return courseRepository.save(existingCourse);
        } else {
            return null;
        }
    }

    public boolean deleteCourseById(Long id) {
        Course existingCourse = courseRepository.findById(id).orElse(null);
        if (existingCourse != null) {
            courseRepository.delete(existingCourse);
            return true;
        } else {
            return false;
        }
    }

    public List<Course> getCoursesByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new AppException("User not found", HttpStatus.NOT_FOUND
                ));
        return courseRepository.findByUser(user);
    }
}
