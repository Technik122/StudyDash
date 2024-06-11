package de.gruppe1.studydash.services;

import de.gruppe1.studydash.dtos.EventDto;
import de.gruppe1.studydash.entities.Event;
import de.gruppe1.studydash.entities.User;
import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.mappers.EventMapper;
import de.gruppe1.studydash.repositories.EventRepository;
import de.gruppe1.studydash.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    public EventDto createEvent(EventDto eventDto) {
        Event event = eventMapper.dtoToEvent(eventDto);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toEventDto(savedEvent);
    }

    public EventDto updateEvent(UUID uuid, EventDto eventDto) {
        Event event = eventMapper.dtoToEvent(eventDto);
        Event existingEvent = eventRepository.findById(uuid).orElse(null);
        if (existingEvent != null) {
            existingEvent.setName(event.getName());
            existingEvent.setDate(event.getDate());
            existingEvent.setColor(event.getColor());
            Event savedEvent = eventRepository.save(existingEvent);
            return eventMapper.toEventDto(savedEvent);
        } else {
            return null;
        }
    }

    public boolean deleteEventById(UUID uuid) {
        Event existingEvent = eventRepository.findById(uuid).orElse(null);
        if (existingEvent != null) {
            eventRepository.delete(existingEvent);
            return true;
        } else {
            return false;
        }
    }

    public List<EventDto> getEventsByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new AppException("User not found", HttpStatus.NOT_FOUND
                ));
        List<Event> events = eventRepository.findByUser(user);
        return eventMapper.toEventDtos(events);
    }
}