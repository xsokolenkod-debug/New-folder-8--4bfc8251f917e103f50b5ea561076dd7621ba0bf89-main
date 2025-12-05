package com.study.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {

  private Long id;
  private String name;
  private Long creatorId;

  public Group() {}

  public Group(Long id, String name, Long creatorId) {
    this.id = id;
    this.name = name;
    this.creatorId = creatorId;
  }

  // Геттеры и сеттеры
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(Long creatorId) {
    this.creatorId = creatorId;
  }

  @Override
  public String toString() {
    return name; // Для отображения в ListView
  }
}
