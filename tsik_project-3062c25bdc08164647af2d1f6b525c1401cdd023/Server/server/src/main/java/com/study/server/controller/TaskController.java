package com.study.server.controller;

import com.study.server.dto.TaskDto;
import com.study.server.dto.WebSocketMessage;
import com.study.server.service.TaskService;
import com.study.server.service.WebSocketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;
  private final WebSocketService webSocketService; // ✅ добавляем зависимость

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TaskDto create(@RequestBody @Valid TaskDto dto) {
    return taskService.create(dto);
  }

  @GetMapping
  public List<TaskDto> all() {
    return taskService.findAll();
  }

  @GetMapping("/group/{groupId}")
  public List<TaskDto> byGroup(@PathVariable Long groupId) {
    return taskService.findByGroup(groupId);
  }

  @PutMapping("/{id}")
  public TaskDto update(@PathVariable Long id, @RequestBody TaskDto dto) {
    // обновляем задачу
    TaskDto updated = taskService.update(id, dto);

    // если изменился статус — уведомляем группу
    if (dto.getStatus() != null) {
      WebSocketMessage ws = WebSocketMessage.builder()
              .type("TASK_UPDATED")
              .groupId(updated.getGroupId())
              .content("Статус задачи '" + updated.getTitle() + "' изменён на " + updated.getStatus())
              .data(updated)
              .build();

      webSocketService.sendTaskUpdate(updated.getGroupId(), ws);
    }

    return updated;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    taskService.delete(id);
  }
}
