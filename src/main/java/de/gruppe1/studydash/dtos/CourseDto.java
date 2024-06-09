package de.gruppe1.studydash.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDto {
    private UUID id;
    private String name;
    private int semester;
    private UserDto user;
    private String exam;
    private Date examDate;
    private Double grade;
    private boolean completed;
    private String color;
}
