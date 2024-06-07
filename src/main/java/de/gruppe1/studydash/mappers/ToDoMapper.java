package de.gruppe1.studydash.mappers;

import de.gruppe1.studydash.dtos.ToDoDto;
import de.gruppe1.studydash.entities.ToDo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = SubtaskMapper.class)
public interface ToDoMapper {

    ToDoDto toToDoDto(ToDo toDo);

    ToDo dtoToToDo(ToDoDto toDoDto);

    List<ToDoDto> toToDoDtos(List<ToDo> toDos);

    List<ToDo> dtoToToDos(List<ToDoDto> toDoDtos);

}