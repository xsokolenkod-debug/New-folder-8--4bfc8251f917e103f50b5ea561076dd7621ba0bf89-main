package com.study.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.client.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Сервис работы с пользователем на стороне JavaFX-клиента.
 *  - login(..) -> /user/login, получает токен и затем /users/me
 *  - getCurrentUser(..) -> /users/me по уже сохранённому токену
 *  - updateUser(..) -> /users/{id}
 */
public class UserService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    // проверь порт backend-а
    private static final String AUTH_BASE_URL = "http://localhost:8080";

    // "Bearer <token>" после логина
    private String authHeader;

    /**
     * Логин: POST /user/login -> токен, потом GET /users/me -> User
     */
    public User login(String username, String password) throws IOException, InterruptedException {
        LoginRequest loginRequest = new LoginRequest(username, password);
        String json = mapper.writeValueAsString(loginRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AUTH_BASE_URL + "/user/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        TokenResponse tokenResponse =
                mapper.readValue(response.body(), TokenResponse.class);

        if (tokenResponse == null || tokenResponse.token == null || tokenResponse.token.isBlank()) {
            return null;
        }

        this.authHeader = "Bearer " + tokenResponse.token;

        return getCurrentUser();
    }

    /**
     * Текущий пользователь по токену.
     */
    public User getCurrentUser() throws IOException, InterruptedException {
        if (authHeader == null || authHeader.isBlank()) {
            return null;
        }

        HttpRequest meRequest = HttpRequest.newBuilder()
                .uri(URI.create(AUTH_BASE_URL + "/users/me"))
                .header("Authorization", authHeader)
                .GET()
                .build();

        HttpResponse<String> meResponse =
                client.send(meRequest, HttpResponse.BodyHandlers.ofString());

        if (meResponse.statusCode() != 200) {
            return null;
        }

        return mapper.readValue(meResponse.body(), User.class);
    }

    /**
     * Обновление пользователя: PUT /users/{id}
     */
    public void updateUser(User user) throws IOException, InterruptedException {
        if (authHeader == null || authHeader.isBlank()) {
            throw new IllegalStateException("User is not authenticated");
        }
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User id is required");
        }

        UpdateUserDto dto = new UpdateUserDto();
        dto.id = user.getId();
        dto.username = user.getUsername();

        dto.name = user.getUsername(); // или user.getName(), если есть поле name
        dto.password = user.getPassword(); // можно поставить null, если не меняешь пароль

        String json = mapper.writeValueAsString(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AUTH_BASE_URL + "/users/" + user.getId()))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Ошибка обновления пользователя: HTTP " + response.statusCode()
                            + " / " + response.body()
            );
        }
    }

    // ===== ВНУТРЕННИЕ DTO =====

    private static class LoginRequest {
        public String username;
        public String password;

        public LoginRequest() {
        }

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    private static class TokenResponse {
        public String token;
    }

    private static class UpdateUserDto {
        public Long id;
        public String username;
        public String email;
        public String password;
        public String name;
    }
}