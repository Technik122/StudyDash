package de.gruppe1.studydash.mappers;

import de.gruppe1.studydash.dtos.SubtaskDto;
import de.gruppe1.studydash.entities.Subtask;
import de.gruppe1.studydash.entities.ToDo;
import de.gruppe1.studydash.repositories.ToDoRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SubtaskMapper {

    SubtaskDto toSubtaskDto(Subtask subtask);

    Subtask dtoToSubtask(SubtaskDto subtaskDto, @Context ToDoRepository toDoRepository);

    List<SubtaskDto> toSubtaskDtos(List<Subtask> subtasks);

    //List<Subtask> dtoToSubtasks(List<SubtaskDto> subtaskDtos, @Context ToDoRepository toDoRepository);

    default UUID map(ToDo value) {
        return value.getId();
    }

    default ToDo map(UUID id, @Context ToDoRepository toDoRepository) {
        return toDoRepository.findById(id).orElse(null);
    }
}
