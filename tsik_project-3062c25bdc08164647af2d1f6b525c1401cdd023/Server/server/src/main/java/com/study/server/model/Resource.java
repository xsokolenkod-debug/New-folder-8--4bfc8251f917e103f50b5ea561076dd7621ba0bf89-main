package com.study.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;

@Entity @Table(name = "resources")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Resource {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false, length = 200)
  private String title;

  @Lob
  private String content; // ссылка, путь, текст — по твоей логике

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private ResourceType type = ResourceType.NOTE;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "uploader_id")
  private User uploader;

  @Column(nullable = false, updatable = false)
  private Instant uploadedAt = Instant.now();
}
