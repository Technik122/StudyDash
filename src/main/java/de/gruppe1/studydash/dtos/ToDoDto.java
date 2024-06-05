package de.gruppe1.studydash.dtos;

import de.gruppe1.studydash.entities.Priority;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ToDoDto {
    private UUID id;
    private String title;
    private String description;
    private boolean completed;
    private Date deadLine;
    private UserDto user;
    private Priority priority;

    //private List<SubtaskDto> subtasks;
}
