package de.gruppe1.studydash.repositories;

import de.gruppe1.studydash.entities.Subtask;
import de.gruppe1.studydash.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
    List<Subtask> findByUser(User user);
}
