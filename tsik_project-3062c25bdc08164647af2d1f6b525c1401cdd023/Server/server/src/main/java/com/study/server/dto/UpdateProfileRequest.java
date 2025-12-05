package com.study.server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProfileRequest {
  @NotBlank
  private String name;

  @Email
  @NotBlank
  private String email;

  private String password;
}
