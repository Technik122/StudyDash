package de.gruppe1.studydash.controllers;

import de.gruppe1.studydash.configurations.UserAuthProvider;
import de.gruppe1.studydash.dtos.UserDto;
import de.gruppe1.studydash.entities.ToDo;
import de.gruppe1.studydash.services.ToDoService;
import de.gruppe1.studydash.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class ToDoController {

    private final ToDoService toDoService;
    private final UserAuthProvider userAuthProvider;

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

  @RequestMapping("/resource")
  public Map<String, Object> home() {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "Hello World");
    return model;
  }

    @PostMapping
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        ToDo createdToDo = toDoService.createToDo(toDo);
        return new ResponseEntity<>(createdToDo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable Long id) {
        ToDo toDo = toDoService.getToDoById(id);
        if (toDo != null) {
            return new ResponseEntity<>(toDo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo toDo) {
        ToDo updatedToDo = toDoService.updateToDo(id, toDo);
        if (updatedToDo != null) {
            return new ResponseEntity<>(updatedToDo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable Long id) {
        boolean deleted = toDoService.deleteToDo(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
