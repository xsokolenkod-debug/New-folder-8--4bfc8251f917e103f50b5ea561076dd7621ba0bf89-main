package com.study.server.dto;

import com.study.server.model.TaskStatus;
import lombok.*;
import jakarta.validation.constraints.*;

import java.time.Instant;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TaskDto {
  private Long id;
  @NotBlank private String title;
  private String description;
  @NotNull private Long groupId;
  private Long assignedToId;
  private Instant dueDate;
  private TaskStatus status;
}
