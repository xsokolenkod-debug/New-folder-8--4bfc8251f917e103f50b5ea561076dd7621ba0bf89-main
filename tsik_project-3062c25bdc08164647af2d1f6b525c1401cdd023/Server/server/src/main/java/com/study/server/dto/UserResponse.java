package com.study.server.dto;

import com.study.server.model.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponse {
  Long id;
  String username;
  String email;
  String name;
  Role role;
}
