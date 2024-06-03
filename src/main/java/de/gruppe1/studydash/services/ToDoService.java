package de.gruppe1.studydash.services;

import de.gruppe1.studydash.dtos.ToDoDto;
import de.gruppe1.studydash.entities.Subtask;
import de.gruppe1.studydash.entities.ToDo;
import de.gruppe1.studydash.entities.User;
import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.mappers.ToDoMapper;
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
    private final ToDoMapper toDoMapper;

    public ToDoDto createToDo(ToDoDto toDoDto) {
        ToDo toDo = toDoMapper.dtoToToDo(toDoDto);
        User user = userRepository.findById(toDoDto.getUser().getId())
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        for (Subtask subtask : toDo.getSubtasks()) {
            subtask.setParentToDo(toDo);
            subtask.setUser(user);
        }
        ToDo savedToDo = toDoRepository.save(toDo);
        return toDoMapper.toToDoDto(savedToDo);
    }

    public ToDoDto updateToDo(UUID id, ToDoDto toDoDto) {
            ToDo toDo = toDoMapper.dtoToToDo(toDoDto);
            ToDo existingToDo = toDoRepository.findById(id).orElse(null);
            if (existingToDo != null) {
                existingToDo.setDescription(toDo.getDescription());
                existingToDo.setDeadLine(toDo.getDeadLine());
                existingToDo.setPriority(toDo.getPriority());
                existingToDo.setCompleted(toDo.isCompleted());
                ToDo updatedToDo = toDoRepository.save(existingToDo);
                return toDoMapper.toToDoDto(updatedToDo);
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

    public List<ToDoDto> getToDosByUserId(UUID userId) {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new AppException("User not found", HttpStatus.NOT_FOUND
            ));
            List<ToDo> toDos = toDoRepository.findByUser(user);
            return toDoMapper.toToDoDtos(toDos);
    }

    public ToDoDto getToDoById(UUID id) {
        ToDo toDo = toDoRepository.findById(id).orElse(null);
        if (toDo != null) {
            return toDoMapper.toToDoDto(toDo);
        } else {
            return null;
        }
    }
}
