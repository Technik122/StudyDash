package de.gruppe1.studydash.repositories;

import de.gruppe1.studydash.entities.Event;
import de.gruppe1.studydash.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByUser(User user);
    void deleteByUserId(UUID userId);
}
