package de.gruppe1.studydash.controllers;

import de.gruppe1.studydash.configurations.UserAuthProvider;
import de.gruppe1.studydash.dtos.UserDto;
import de.gruppe1.studydash.entities.Subtask;
import de.gruppe1.studydash.entities.ToDo;
import de.gruppe1.studydash.entities.User;
import de.gruppe1.studydash.mappers.UserMapper;
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
    private final UserMapper userMapper;

    @GetMapping("/user")
    public ResponseEntity<List<ToDo>> getToDosByUser(@RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto user = (UserDto) auth.getPrincipal();
            List<ToDo> toDos = toDoService.getToDosByUserId(user.getId());
            return new ResponseEntity<>(toDos, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            User user = userMapper.dtoToUser(userDto);
            toDo.setUser(user);
            ToDo createdToDo = toDoService.createToDo(toDo);
            return new ResponseEntity<>(createdToDo, HttpStatus.CREATED);
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
            ToDo toDo = toDoService.getToDoById(uuid);
            if (toDo != null && toDo.getUser().getId().equals(userDto.getId())) {
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
    public ResponseEntity<ToDo> updateToDo(@PathVariable UUID uuid, @RequestBody ToDo toDo, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            ToDo existingToDo = toDoService.getToDoById(uuid);
            if (existingToDo != null && existingToDo.getUser().getId().equals(userDto.getId())) {
                ToDo updatedToDo = toDoService.updateToDo(uuid, toDo);
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
    public ResponseEntity<List<Subtask>> getSubtasksByToDo(@PathVariable UUID uuid, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            ToDo toDo = toDoService.getToDoById(uuid);
            if (toDo != null && toDo.getUser().getId().equals(userDto.getId())) {
                List<Subtask> subtasks = toDo.getSubTasks();
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
    public ResponseEntity<Subtask> createSubtask(@PathVariable UUID uuid, @RequestBody Subtask subtask, @RequestHeader(value = "Authorization") String header) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            Authentication auth = userAuthProvider.validateToken(authElements[1]);
            UserDto userDto = (UserDto) auth.getPrincipal();
            ToDo toDo = toDoService.getToDoById(uuid);
            if (toDo != null && toDo.getUser().getId().equals(userDto.getId())) {
                User user = userMapper.dtoToUser(userDto);
                subtask.setUser(user);
                subtask.setParentToDo(toDo);
                Subtask createdSubtask = subtaskService.createSubtask(subtask);
                return new ResponseEntity<>(createdSubtask, HttpStatus.CREATED);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}