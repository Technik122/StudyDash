package de.gruppe1.studydash.controllers;

import de.gruppe1.studydash.configurations.UserAuthProvider;
import de.gruppe1.studydash.dtos.EventDto;
import de.gruppe1.studydash.dtos.UserDto;
import de.gruppe1.studydash.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final UserAuthProvider userAuthProvider;

    @GetMapping("/get")
    public ResponseEntity<List<EventDto>> getEventsByUser(@RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto user = (UserDto) auth.getPrincipal();
            List<EventDto> events = eventService.getEventsByUserId(user.getId());
            return new ResponseEntity<>(events, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            eventDto.setUser(userDto);
            EventDto createdEvent = eventService.createEvent(eventDto);
            return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Boolean> deleteEventById(@PathVariable UUID uuid) {
        boolean deleted = eventService.deleteEventById(uuid);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable UUID uuid, @RequestBody EventDto eventDto) {
        EventDto updatedEvent = eventService.updateEvent(uuid, eventDto);
        if (updatedEvent != null) {
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}