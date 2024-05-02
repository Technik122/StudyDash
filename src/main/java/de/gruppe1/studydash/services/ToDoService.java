package de.gruppe1.studydash.services;

import de.gruppe1.studydash.entities.ToDo;
import de.gruppe1.studydash.repositories.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    public ToDo createToDo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public ToDo getToDoById(Long id) {
        return toDoRepository.findById(id).orElse(null);
    }

    public ToDo updateToDo(Long id, ToDo toDo) {
        ToDo existingToDo = toDoRepository.findById(id).orElse(null);
        if (existingToDo != null) {
            existingToDo.setDescription(toDo.getDescription());
            existingToDo.setCompleted(toDo.isCompleted());
            return toDoRepository.save(existingToDo);
        } else {
            return null;
        }
    }

    public boolean deleteToDo(Long id) {
        ToDo existingToDo = toDoRepository.findById(id).orElse(null);
        if (existingToDo != null) {
            toDoRepository.delete(existingToDo);
            return true;
        } else {
            return false;
        }
    }
}
