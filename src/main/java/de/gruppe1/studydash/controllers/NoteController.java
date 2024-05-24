package de.gruppe1.studydash.controllers;

import de.gruppe1.studydash.configurations.UserAuthProvider;
import de.gruppe1.studydash.dtos.UserDto;
import de.gruppe1.studydash.entities.Note;
import de.gruppe1.studydash.entities.User;
import de.gruppe1.studydash.mappers.UserMapper;
import de.gruppe1.studydash.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserAuthProvider userAuthProvider;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/get")
    public ResponseEntity<List<Note>> getNotesByUser(@RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto user = (UserDto) auth.getPrincipal();
            List<Note> notes = noteService.getNotesByUserId(user.getId());
            return new ResponseEntity<>(notes, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Note> createNote(@RequestBody Note note, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            User user = userMapper.dtoToUser(userDto);
            note.setUser(user);
            Note createdNote = noteService.createNote(note);
            return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteToDoById(@PathVariable Long id) {
        boolean deleted = noteService.deleteNoteById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        Note updatedNote = noteService.updateNote(id, note);
        if (updatedNote != null) {
            return new ResponseEntity<>(updatedNote, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
