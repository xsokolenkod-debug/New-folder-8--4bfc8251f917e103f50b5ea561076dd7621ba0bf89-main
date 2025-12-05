package com.study.server.mapper;

import com.study.server.dto.UserResponse;
import com.study.server.model.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    public static UserResponse toResponse(User user, String token) {
        return toResponse(user);
    }
}
