package com.study.client.controller;

import com.study.client.model.User;
import com.study.client.service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProfileController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button saveButton;

    @FXML
    private Label statusLabel;

    private User currentUser;
    private final UserService userService = new UserService();

    public void setCurrentUser(User user) {
        this.currentUser = user;
        usernameField.setText(user.getUsername());
    }

    @FXML
    private void onSaveClicked() {
        String newUsername = usernameField.getText().trim();
        String newPassword = passwordField.getText().trim();

        if(newUsername.isEmpty()) {
            statusLabel.setText("Имя пользователя не может быть пустым");
            return;
        }

        new Thread(() -> {
            try {
                currentUser.setUsername(newUsername);
                if(!newPassword.isBlank()) currentUser.setPassword(newPassword);
                userService.updateUser(currentUser);
                Platform.runLater(() -> statusLabel.setText("Профиль обновлен"));
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> statusLabel.setText("Ошибка при сохранении"));
            }
        }).start();
    }
}
