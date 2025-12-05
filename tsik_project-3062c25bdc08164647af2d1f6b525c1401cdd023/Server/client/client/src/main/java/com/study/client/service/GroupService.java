package com.study.client.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.client.model.Group;
import com.study.client.model.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class GroupService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String baseUrl = "http://localhost:8080/api";

    public List<Group> getGroupsForUser(Long userId) throws Exception {
        // На сервере нет отдельного эндпоинта "группы пользователя",
        // поэтому пока просто берем все группы
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/groups"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), new TypeReference<List<Group>>() {});
        }
        return Collections.emptyList();
    }

    public void createGroup(Long creatorId, String name) throws Exception {
        var node = mapper.createObjectNode();
        node.put("name", name);
        node.put("creatorId", creatorId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/groups"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(node)))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<User> getMembers(Long groupId) throws Exception {
        // Если нужно, можно реализовать запрос к /api/groups/{id}/members
        // Пока возвращаем пустой список, чтобы не падало
        return Collections.emptyList();
    }
}
