package de.gruppe1.studydash.repositories;

import de.gruppe1.studydash.entities.Course;
import de.gruppe1.studydash.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByUser(User user);
}
