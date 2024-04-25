package de.gruppe1.studydash.repositories;

import de.gruppe1.studydash.entities.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
