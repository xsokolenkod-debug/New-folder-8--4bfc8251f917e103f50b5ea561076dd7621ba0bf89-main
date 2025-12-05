package com.study.server.service;

import com.study.server.dto.TaskDto;
import com.study.server.dto.WebSocketMessage;
import com.study.server.model.*;
import com.study.server.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
  private final TaskRepository taskRepository;
  private final GroupService groupService;
  private final UserService userService;
  private final WebSocketService webSocketService; // ✅ Добавили зависимость

  public TaskDto create(TaskDto dto) {
    if (dto.getDueDate() != null && dto.getDueDate().isBefore(Instant.now())) {
      throw new IllegalArgumentException("dueDate must be in the future");
    }
    Task t = Task.builder()
            .title(dto.getTitle())
            .description(dto.getDescription())
            .group(groupService.getEntity(dto.getGroupId()))
            .assignedTo(dto.getAssignedToId() != null ? userService.getEntity(dto.getAssignedToId()) : null)
            .dueDate(dto.getDueDate())
            .status(dto.getStatus() != null ? dto.getStatus() : TaskStatus.OPEN)
            .build();
    t = taskRepository.save(t);
    return toDto(t);
  }

  @Transactional(readOnly = true)
  public List<TaskDto> findAll() {
    return taskRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional(readOnly = true)
  public List<TaskDto> findByGroup(Long groupId) {
    return taskRepository.findByGroupId(groupId).stream().map(this::toDto).toList();
  }

  public TaskDto update(Long id, TaskDto dto) {
    Task t = taskRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Task not found"));

    TaskStatus oldStatus = t.getStatus(); // сохраним предыдущий статус

    if (dto.getTitle() != null) t.setTitle(dto.getTitle());
    if (dto.getDescription() != null) t.setDescription(dto.getDescription());
    if (dto.getGroupId() != null) t.setGroup(groupService.getEntity(dto.getGroupId()));
    if (dto.getAssignedToId() != null) t.setAssignedTo(userService.getEntity(dto.getAssignedToId()));
    if (dto.getDueDate() != null) {
      if (dto.getDueDate().isBefore(Instant.now())) throw new IllegalArgumentException("dueDate must be in the future");
      t.setDueDate(dto.getDueDate());
    }
    if (dto.getStatus() != null) {
      TaskStatus from = t.getStatus(), to = dto.getStatus();
      boolean ok = switch (from) {
        case OPEN -> (to == TaskStatus.OPEN || to == TaskStatus.IN_PROGRESS);
        case IN_PROGRESS -> (to == TaskStatus.IN_PROGRESS || to == TaskStatus.DONE);
        case DONE -> (to == TaskStatus.DONE);
      };
      if (!ok) throw new IllegalStateException("Illegal status transition: " + from + " -> " + to);
      t.setStatus(to);
    }

    Task saved = taskRepository.save(t);
    TaskDto result = toDto(saved);

    // ✅ Отправляем WebSocket уведомление, если статус изменился
    if (dto.getStatus() != null && !dto.getStatus().equals(oldStatus)) {
      WebSocketMessage wsMessage = WebSocketMessage.builder()
              .type("TASK_UPDATED")
              .content("Задача '" + t.getTitle() + "' изменила статус: " + oldStatus + " → " + t.getStatus())
              .groupId(t.getGroup().getId())
              .userId(t.getAssignedTo() != null ? t.getAssignedTo().getId() : null)
              .data(Map.of(
                      "taskId", t.getId(),
                      "title", t.getTitle(),
                      "status", t.getStatus().toString(),
                      "oldStatus", oldStatus.toString(),
                      "assignedTo", t.getAssignedTo() != null ? t.getAssignedTo().getUsername() : null,
                      "deadline", t.getDueDate() != null ? t.getDueDate().toString() : null
              ))
              .build();

      webSocketService.sendTaskUpdate(t.getGroup().getId(), wsMessage);
    }

    return result;
  }

  public void delete(Long id) {
    taskRepository.deleteById(id);
  }

  private TaskDto toDto(Task t) {
    return TaskDto.builder()
            .id(t.getId())
            .title(t.getTitle())
            .description(t.getDescription())
            .groupId(t.getGroup().getId())
            .assignedToId(t.getAssignedTo() != null ? t.getAssignedTo().getId() : null)
            .dueDate(t.getDueDate())
            .status(t.getStatus())
            .build();
  }
}
