package de.gruppe1.studydash.services;

import de.gruppe1.studydash.entities.Subtask;
import de.gruppe1.studydash.repositories.SubtaskRepository;
import de.gruppe1.studydash.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final UserRepository userRepository;

    public Subtask createSubtask(Subtask subtask) {
        return subtaskRepository.save(subtask);
    }

    public Subtask updateSubtask(Long id, Subtask subtask) {
        Subtask existingSubtask = subtaskRepository.findById(id).orElse(null);
        if (existingSubtask != null) {
            existingSubtask.setDescription(subtask.getDescription());
            existingSubtask.setCompleted(subtask.isCompleted());
            return subtaskRepository.save(existingSubtask);
        } else {
            return null;
        }
    }

    public boolean deleteSubtaskById(Long id) {
        Subtask existingSubtask = subtaskRepository.findById(id).orElse(null);
        if (existingSubtask != null) {
            subtaskRepository.delete(existingSubtask);
            return true;
        } else {
            return false;
        }
    }

    public Subtask getSubtaskById(Long id) {
        return subtaskRepository.findById(id).orElse(null);
    }
}