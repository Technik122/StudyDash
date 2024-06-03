package de.gruppe1.studydash.mappers;

import de.gruppe1.studydash.dtos.SubtaskDto;
import de.gruppe1.studydash.entities.Subtask;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubtaskMapper {

    SubtaskDto toSubtaskDto(Subtask subtask);

    Subtask dtoToSubtask(SubtaskDto subtaskDto);

    List<SubtaskDto> toSubtaskDtos(List<Subtask> subtasks);

    List<Subtask> dtoToSubtasks(List<SubtaskDto> subtaskDtos);
}
