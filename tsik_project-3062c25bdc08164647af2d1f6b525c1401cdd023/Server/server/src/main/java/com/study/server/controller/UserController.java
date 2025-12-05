package com.study.server.controller;

import com.study.server.dto.UserDto;
import com.study.server.dto.UserResponse;
import com.study.server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse create(@RequestBody @Valid UserDto dto) { return userService.create(dto); }

  @GetMapping public List<UserResponse> all() { return userService.findAll(); }

  @PutMapping("/{id}") public UserResponse update(@PathVariable Long id, @RequestBody UserDto dto) {
    return userService.update(id, dto);
  }

  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) { userService.delete(id); }
}
