package de.gruppe1.studydash.controllers;

import de.gruppe1.studydash.configurations.UserAuthProvider;
import de.gruppe1.studydash.dtos.SubtaskDto;
import de.gruppe1.studydash.dtos.ToDoDto;
import de.gruppe1.studydash.dtos.UserDto;
import de.gruppe1.studydash.services.SubtaskService;
import de.gruppe1.studydash.services.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class ToDoController {

    private final ToDoService toDoService;
    private final SubtaskService subtaskService;
    private final UserAuthProvider userAuthProvider;

    @GetMapping("/user")
    public ResponseEntity<List<ToDoDto>> getToDosByUser(@RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto user = (UserDto) auth.getPrincipal();
            List<ToDoDto> toDos = toDoService.getToDosByUserId(user.getId());
            return new ResponseEntity<>(toDos, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ToDoDto> createToDo(@RequestBody ToDoDto toDoDto, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            toDoDto.setUser(userDto);
            ToDoDto createdToDoDto = toDoService.createToDo(toDoDto);
            return new ResponseEntity<>(createdToDoDto, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Boolean> deleteToDoById(@PathVariable UUID uuid, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            ToDoDto toDoDto = toDoService.getToDoById(uuid);
            if (toDoDto != null && toDoDto.getUser().getId().equals(userDto.getId())) {
                boolean deleted = toDoService.deleteToDoById(uuid);
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
    public ResponseEntity<ToDoDto> updateToDo(@PathVariable UUID uuid, @RequestBody ToDoDto toDoDto, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            ToDoDto existingToDo = toDoService.getToDoById(uuid);
            if (existingToDo != null && existingToDo.getUser().getId().equals(userDto.getId())) {
                ToDoDto updatedToDo = toDoService.updateToDo(uuid, toDoDto);
                if (updatedToDo != null) {
                    return new ResponseEntity<>(updatedToDo, HttpStatus.OK);
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

    @GetMapping("/{uuid}/subtasks/get")
    public ResponseEntity<List<SubtaskDto>> getSubtasksByToDo(@PathVariable UUID uuid, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            ToDoDto toDo = toDoService.getToDoById(uuid);
            if (toDo != null && toDo.getUser().getId().equals(userDto.getId())) {
                List<SubtaskDto> subtasks = subtaskService.getSubtasksByParentToDoId(toDo.getId());
                if (subtasks != null) {
                    return new ResponseEntity<>(subtasks, HttpStatus.OK);
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

    @PostMapping("/{uuid}/subtasks/add")
    public ResponseEntity<SubtaskDto> createSubtask(@PathVariable UUID uuid, @RequestBody SubtaskDto subtask, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            ToDoDto toDo = toDoService.getToDoById(uuid);
            if (toDo != null && toDo.getUser().getId().equals(userDto.getId())) {
                subtask.setUser(userDto);
                SubtaskDto createdSubtask = subtaskService.createSubtask(toDo.getId(), subtask);
                return new ResponseEntity<>(createdSubtask, HttpStatus.CREATED);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ToDoDto>> getToDosByCourse(@PathVariable String courseId, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            UUID courseUuid = UUID.fromString(courseId);
            List<ToDoDto> toDos = toDoService.getToDosByCourseId(courseUuid, userDto.getId());
            if (toDos != null) {
                return new ResponseEntity<>(toDos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}