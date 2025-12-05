package com.study.server.controller;

import com.study.server.dto.ResourceDto;
import com.study.server.service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/resources") @RequiredArgsConstructor
public class ResourceController {
  private final ResourceService resourceService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResourceDto create(@RequestBody @Valid ResourceDto dto) { return resourceService.create(dto); }

  @GetMapping public List<ResourceDto> all() { return resourceService.findAll(); }

  @PutMapping("/{id}") public ResourceDto update(@PathVariable Long id, @RequestBody ResourceDto dto) {
    return resourceService.update(id, dto);
  }

  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) { resourceService.delete(id); }
}
