package de.gruppe1.studydash.services;

import de.gruppe1.studydash.dtos.ToDoDto;
import de.gruppe1.studydash.entities.Subtask;
import de.gruppe1.studydash.entities.ToDo;
import de.gruppe1.studydash.entities.User;
import de.gruppe1.studydash.exceptions.AppException;
import de.gruppe1.studydash.mappers.ToDoMapper;
import de.gruppe1.studydash.repositories.SubtaskRepository;
import de.gruppe1.studydash.repositories.ToDoRepository;
import de.gruppe1.studydash.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;
    private final SubtaskRepository subtaskRepository;
    private final ToDoMapper toDoMapper;


    public ToDoDto createToDo(ToDoDto toDoDto) {
        ToDo toDo = toDoMapper.dtoToToDo(toDoDto);
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
            existingToDo.setCourse(toDo.getCourse());
            ToDo updatedToDo = toDoRepository.save(existingToDo);
            return toDoMapper.toToDoDto(updatedToDo);
        } else {
            return null;
        }
    }

    public boolean deleteToDoById(UUID id) {
        ToDo existingToDo = toDoRepository.findById(id).orElse(null);
        if (existingToDo != null) {
            List<Subtask> subtasks = subtaskRepository.findByParentToDoId(id);
            subtaskRepository.deleteAll(subtasks);
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

    public List<ToDoDto> getToDosByCourseId(UUID courseId, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new AppException("User not found", HttpStatus.NOT_FOUND
                ));
        List<ToDo> toDos = toDoRepository.findByCourse(courseId);
        return toDoMapper.toToDoDtos(toDos.stream()
                .filter(toDo -> toDo.getUser().equals(user))
                .collect(Collectors.toList()));
    }
}
