package com.study.server.controller;

import com.study.server.dto.GroupDto;
import com.study.server.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/groups") @RequiredArgsConstructor
public class GroupController {
  private final GroupService groupService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public GroupDto create(@RequestBody @Valid GroupDto dto) { return groupService.create(dto); }

  @GetMapping public List<GroupDto> all() { return groupService.findAll(); }

  @PutMapping("/{id}") public GroupDto update(@PathVariable Long id, @RequestBody GroupDto dto) {
    return groupService.update(id, dto);
  }

  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) { groupService.delete(id); }

  @PostMapping("/{groupId}/members/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void addMember(@PathVariable Long groupId, @PathVariable Long userId) {
    groupService.addMember(groupId, userId);
  }

  @DeleteMapping("/{groupId}/members/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeMember(@PathVariable Long groupId, @PathVariable Long userId) {
    groupService.removeMember(groupId, userId);
  }
}
