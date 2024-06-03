package de.gruppe1.studydash.mappers;

import de.gruppe1.studydash.dtos.NoteDto;
import de.gruppe1.studydash.entities.Note;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteDto toNoteDto(Note note);

    Note dtoToNoteDto(NoteDto noteDto);

    List<NoteDto> toNoteDtos(List<Note> notes);
}
