package de.gruppe1.studydash.controllers;

import de.gruppe1.studydash.configurations.UserAuthProvider;
import de.gruppe1.studydash.dtos.SubtaskDto;
import de.gruppe1.studydash.dtos.UserDto;
import de.gruppe1.studydash.services.SubtaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/subtasks")
public class SubtaskController {

    private final SubtaskService subtaskService;
    private final UserAuthProvider userAuthProvider;

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Boolean> deleteSubtaskById(@PathVariable UUID uuid, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            SubtaskDto subtask = subtaskService.getSubtaskById(uuid);
            if (subtask != null && subtask.getUser().getId().equals(userDto.getId())) {
                boolean deleted = subtaskService.deleteSubtaskById(uuid);
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

    @PutMapping("/update/{uuid}")
    public ResponseEntity<SubtaskDto> updateSubtask(@PathVariable UUID uuid, @RequestBody SubtaskDto subtask, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            SubtaskDto existingSubtask = subtaskService.getSubtaskById(uuid);
            if (existingSubtask != null && existingSubtask.getUser().getId().equals(userDto.getId())) {
                existingSubtask.setDescription(subtask.getDescription());
                existingSubtask.setCompleted(subtask.isCompleted());
                SubtaskDto updatedSubtask = subtaskService.updateSubtask(uuid, existingSubtask);
                return new ResponseEntity<>(updatedSubtask, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}