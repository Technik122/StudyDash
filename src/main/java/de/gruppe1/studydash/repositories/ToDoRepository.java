package de.gruppe1.studydash.repositories;

import de.gruppe1.studydash.entities.ToDo;
import de.gruppe1.studydash.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, UUID> {
    List<ToDo> findByUser(User user);
}
