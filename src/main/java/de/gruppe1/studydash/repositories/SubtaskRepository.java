package de.gruppe1.studydash.repositories;

import de.gruppe1.studydash.entities.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubtaskRepository extends JpaRepository<Subtask, UUID> {
    List<Subtask> findByParentToDoId(UUID parentToDoId);
    void deleteByUserId(UUID userId);
}
