package de.gruppe1.studydash.services;

import de.gruppe1.studydash.entities.ToDo;
import de.gruppe1.studydash.entities.User;
import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.repositories.ToDoRepository;
import de.gruppe1.studydash.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;

    public ToDo createToDo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public ToDo updateToDo(UUID id, ToDo toDo) {
            ToDo existingToDo = toDoRepository.findById(id).orElse(null);
            if (existingToDo != null) {
                existingToDo.setDescription(toDo.getDescription());
                existingToDo.setDeadLine(toDo.getDeadLine());
                existingToDo.setPriority(toDo.getPriority());
                existingToDo.setCompleted(toDo.isCompleted());
                return toDoRepository.save(existingToDo);
            } else {
                return null;
            }
    }

        public boolean deleteToDoById(UUID id) {
            ToDo existingToDo = toDoRepository.findById(id).orElse(null);
            if (existingToDo != null) {
                toDoRepository.delete(existingToDo);
                return true;
            } else {
                return false;
            }
    }

    public List<ToDo> getToDosByUserId(Long userId) {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new AppException("User not found", HttpStatus.NOT_FOUND
            ));
            return toDoRepository.findByUser(user);
    }

    public ToDo getToDoById(UUID id) {
        return toDoRepository.findById(id).orElse(null);
    }
}
