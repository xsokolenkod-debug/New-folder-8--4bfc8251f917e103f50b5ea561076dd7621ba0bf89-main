package com.study.server.dto;

import com.study.server.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
  private Long id;
  @NotBlank private String username;
  @Email @NotBlank private String email;
  @NotBlank(groups = Create.class) private String password;
  @NotBlank private String name;
  private Role role;

  public interface Create {}
  public interface Update {}
}
