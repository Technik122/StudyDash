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
public class EventDto {
    private UUID id;
    private String name;
    private Date date;
    private UserDto user;
    private String color;
}
