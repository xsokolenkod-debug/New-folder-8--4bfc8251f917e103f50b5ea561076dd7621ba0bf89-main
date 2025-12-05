package com.study.server.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GroupDto {
  private Long id;
  @NotBlank private String name;
  @NotNull private Long creatorId;
}
