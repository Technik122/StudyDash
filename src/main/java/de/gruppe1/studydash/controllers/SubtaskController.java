package de.gruppe1.studydash.controllers;

import de.gruppe1.studydash.configurations.UserAuthProvider;
import de.gruppe1.studydash.dtos.UserDto;
import de.gruppe1.studydash.entities.Subtask;
import de.gruppe1.studydash.services.SubtaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/subtasks")
public class SubtaskController {

    private final SubtaskService subtaskService;
    private final UserAuthProvider userAuthProvider;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteSubtaskById(@PathVariable Long id, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            Subtask subtask = subtaskService.getSubtaskById(id);
            if (subtask != null && subtask.getUser().getId().equals(userDto.getId())) {
                boolean deleted = subtaskService.deleteSubtaskById(id);
                if (deleted) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Subtask> updateSubtask(@PathVariable Long id, @RequestBody Subtask subtask, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            Subtask existingSubtask = subtaskService.getSubtaskById(id);
            if (existingSubtask != null && existingSubtask.getUser().getId().equals(userDto.getId())) {
                existingSubtask.setDescription(subtask.getDescription());
                existingSubtask.setCompleted(subtask.isCompleted());
                Subtask updatedSubtask = subtaskService.updateSubtask(id, existingSubtask);
                return new ResponseEntity<>(updatedSubtask, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}