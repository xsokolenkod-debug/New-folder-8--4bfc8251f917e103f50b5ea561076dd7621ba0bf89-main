package com.study.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {

    private Long id;
    private String title;
    private String content;
    // Тип ресурса храним как строку: "LINK", "FILE", "NOTE"
    private String type;
    private Long uploaderId;

    public Resource() {
    }

    public Resource(Long id, String title, String content, String type, Long uploaderId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.uploaderId = uploaderId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Long uploaderId) {
        this.uploaderId = uploaderId;
    }

    @Override
    public String toString() {
        return title != null ? title : ("Материал #" + id);
    }
}
