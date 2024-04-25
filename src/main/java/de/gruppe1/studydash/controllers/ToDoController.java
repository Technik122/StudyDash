package de.gruppe1.studydash.controllers;

import de.gruppe1.studydash.entities.ToDo;
import de.gruppe1.studydash.repositories.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.expression.ExpressionException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping
    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }

    @PostMapping
    public ToDo createToDo(@RequestBody ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    @PutMapping("/{id}")
    public ToDo updateToDo(@PathVariable Long id, @RequestBody ToDo toDo) {
        ToDo existingToDo = toDoRepository.findById(id).orElseThrow();
        existingToDo.setTitle(toDo.getTitle());
        existingToDo.setDescription(toDo.getDescription());
        existingToDo.setCompleted(toDo.isCompleted());
        return toDoRepository.save(existingToDo);
    }

    @DeleteMapping("/{id}")
    public void deleteToDo(@PathVariable Long id) {
        toDoRepository.deleteById(id);
    }
}
