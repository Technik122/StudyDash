package de.gruppe1.studydash.services;

import de.gruppe1.studydash.entities.Note;
import de.gruppe1.studydash.entities.User;
import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.repositories.NoteRepository;
import de.gruppe1.studydash.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public Note updateNote(Long id, Note note) {
        Note existingNote = noteRepository.findById(id).orElse(null);
        if (existingNote != null) {
            existingNote.setTitle(note.getTitle());
            existingNote.setContent(note.getContent());
            existingNote.setDate(note.getDate());
            return noteRepository.save(existingNote);
        } else {
            return null;
        }
    }

    public boolean deleteNoteById(Long id) {
        Note existingNote = noteRepository.findById(id).orElse(null);
        if (existingNote != null) {
            noteRepository.delete(existingNote);
            return true;
        } else {
            return false;
        }
    }

    public List<Note> getNotesByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new AppException("User not found", HttpStatus.NOT_FOUND
                ));
        return noteRepository.findByUser(user);
    }
}
