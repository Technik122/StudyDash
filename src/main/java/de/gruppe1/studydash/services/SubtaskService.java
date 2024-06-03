package de.gruppe1.studydash.services;

import de.gruppe1.studydash.dtos.SubtaskDto;
import de.gruppe1.studydash.entities.Subtask;
import de.gruppe1.studydash.mappers.SubtaskMapper;
import de.gruppe1.studydash.repositories.SubtaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final SubtaskMapper subtaskMapper;

    public SubtaskDto createSubtask(SubtaskDto subtaskDto) {
        Subtask subtask = subtaskMapper.dtoToSubtask(subtaskDto);
        Subtask savedSubtask = subtaskRepository.save(subtask);
        return subtaskMapper.toSubtaskDto(savedSubtask);
    }

    public SubtaskDto updateSubtask(UUID id, SubtaskDto subtaskDto) {
        Subtask subtask = subtaskMapper.dtoToSubtask(subtaskDto);
        Subtask existingSubtask = subtaskRepository.findById(id).orElse(null);
        if (existingSubtask != null) {
            existingSubtask.setDescription(subtask.getDescription());
            existingSubtask.setCompleted(subtask.isCompleted());
            Subtask savedSubtask = subtaskRepository.save(existingSubtask);
            return subtaskMapper.toSubtaskDto(savedSubtask);
        } else {
            return null;
        }
    }

    public boolean deleteSubtaskById(UUID id) {
        Subtask existingSubtask = subtaskRepository.findById(id).orElse(null);
        if (existingSubtask != null) {
            subtaskRepository.delete(existingSubtask);
            return true;
        } else {
            return false;
        }
    }

    public SubtaskDto getSubtaskById(UUID id) {
        Subtask subtask = subtaskRepository.findById(id).orElse(null);
        if (subtask != null) {
            return subtaskMapper.toSubtaskDto(subtask);
        } else {
            return null;
        }
    }
}