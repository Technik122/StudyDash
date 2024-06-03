package de.gruppe1.studydash.services;

import de.gruppe1.studydash.dtos.NoteDto;
import de.gruppe1.studydash.entities.Note;
import de.gruppe1.studydash.entities.User;
import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.mappers.NoteMapper;
import de.gruppe1.studydash.repositories.NoteRepository;
import de.gruppe1.studydash.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteMapper noteMapper;

    public NoteDto createNote(NoteDto noteDto) {
        Note note = noteMapper.dtoToNoteDto(noteDto);
        Note savedNote = noteRepository.save(note);
        return noteMapper.toNoteDto(savedNote);
    }

    public NoteDto updateNote(UUID uuid, NoteDto noteDto) {
        Note note = noteMapper.dtoToNoteDto(noteDto);
        Note existingNote = noteRepository.findById(uuid).orElse(null);
        if (existingNote != null) {
            existingNote.setTitle(note.getTitle());
            existingNote.setContent(note.getContent());
            existingNote.setDate(note.getDate());
            Note savedNote = noteRepository.save(existingNote);
            return noteMapper.toNoteDto(savedNote);
        } else {
            return null;
        }
    }

    public boolean deleteNoteById(UUID uuid) {
        Note existingNote = noteRepository.findById(uuid).orElse(null);
        if (existingNote != null) {
            noteRepository.delete(existingNote);
            return true;
        } else {
            return false;
        }
    }

    public List<NoteDto> getNotesByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new AppException("User not found", HttpStatus.NOT_FOUND
                ));
        List<Note> notes = noteRepository.findByUser(user);
        return noteMapper.toNoteDtos(notes);
    }
}
