package com.study.server.controller;

import com.study.server.dto.*;
import com.study.server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
  private final UserService userService;

  @PostMapping("/auth/register")
  public UserResponse register(@RequestBody @Valid RegisterRequest request) {
    return userService.register(request);
  }

  @PostMapping("/user/login")
  public TokenResponse login(@RequestBody @Valid LoginRequest request) {
    return userService.login(request);
  }

  @GetMapping("/users/me")
  public UserResponse me(@AuthenticationPrincipal UserDetails principal) {
    return userService.getCurrentUser(principal.getUsername());
  }

  @PutMapping("/users/me")
  public UserResponse updateProfile(@AuthenticationPrincipal UserDetails principal,
                                    @RequestBody @Valid UpdateProfileRequest request) {
    return userService.updateProfile(principal.getUsername(), request);
  }
}
