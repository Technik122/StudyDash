package de.gruppe1.studydash.mappers;

import de.gruppe1.studydash.dtos.EventDto;
import de.gruppe1.studydash.entities.Event;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDto toEventDto(Event event);

    Event dtoToEvent(EventDto eventDto);

    List<EventDto> toEventDtos(List<Event> events);
}
