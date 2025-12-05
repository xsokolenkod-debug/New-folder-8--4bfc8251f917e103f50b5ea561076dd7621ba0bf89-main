package com.study.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    private Long id;
    private String title;
    private String description;
    private Long groupId;
    private Long assignedToId;
    private Instant dueDate;
    // Статус храним как строку: "OPEN", "IN_PROGRESS", "DONE"
    private String status;

    public Task() {
    }

    public Task(Long id, String title, String description, Long groupId,
                Long assignedToId, Instant dueDate, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.groupId = groupId;
        this.assignedToId = assignedToId;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return title != null ? title : ("Задача #" + id);
    }
}
