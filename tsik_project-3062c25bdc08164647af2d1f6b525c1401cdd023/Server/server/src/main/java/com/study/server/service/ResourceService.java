package com.study.server.service;

import com.study.server.dto.ResourceDto;
import com.study.server.model.Resource;
import com.study.server.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class ResourceService {
  private final ResourceRepository resourceRepository;
  private final UserService userService;

  public ResourceDto create(ResourceDto dto) {
    Resource r = Resource.builder()
        .title(dto.getTitle())
        .content(dto.getContent())
        .type(dto.getType())
        .uploader(userService.getEntity(dto.getUploaderId()))
        .build();
    r = resourceRepository.save(r);
    return toDto(r);
  }

  @Transactional(readOnly = true)
  public List<ResourceDto> findAll() {
    return resourceRepository.findAll().stream().map(this::toDto).toList();
  }

  public ResourceDto update(Long id, ResourceDto dto) {
    Resource r = resourceRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Resource not found"));
    if (dto.getTitle() != null) r.setTitle(dto.getTitle());
    if (dto.getContent() != null) r.setContent(dto.getContent());
    if (dto.getType() != null) r.setType(dto.getType());
    if (dto.getUploaderId() != null) r.setUploader(userService.getEntity(dto.getUploaderId()));
    return toDto(r);
  }

  public void delete(Long id) { resourceRepository.deleteById(id); }

  private ResourceDto toDto(Resource r) {
    return ResourceDto.builder()
        .id(r.getId()).title(r.getTitle()).content(r.getContent())
        .type(r.getType()).uploaderId(r.getUploader().getId()).build();
  }
}
