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
public class NoteDto {
    private UUID id;
    private String title;
    private String content;
    private Date date;
    private UserDto user;
}
