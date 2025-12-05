package com.study.server.repository;

import com.study.server.model.Task;
import com.study.server.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByGroupId(Long groupId);
  List<Task> findByAssignedToId(Long userId);
  List<Task> findByStatus(TaskStatus status);
  List<Task> findByDueDateBefore(Instant before);
}
