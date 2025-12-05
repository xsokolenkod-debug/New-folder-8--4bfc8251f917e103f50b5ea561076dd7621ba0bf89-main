package com.study.server.dto;

import com.study.server.model.ResourceType;
import lombok.*;
import jakarta.validation.constraints.*;
import org.antlr.v4.runtime.misc.NotNull;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ResourceDto {
  private Long id;
  @NotBlank private String title;
  private String content;
  @NotNull
  private ResourceType type;
  @NotNull private Long uploaderId;
}
