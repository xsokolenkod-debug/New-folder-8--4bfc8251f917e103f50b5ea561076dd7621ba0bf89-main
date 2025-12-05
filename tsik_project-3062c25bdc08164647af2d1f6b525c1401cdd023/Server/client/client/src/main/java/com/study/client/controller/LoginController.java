package com.study.client.controller;

import javafx.event.ActionEvent;
import com.study.client.model.User;
import com.study.client.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.application.Platform;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label errorLabel;

    private UserService userService = new UserService();

    @FXML
    private void initialize() {
        errorLabel.setText("");
    }

    @FXML
    private void onLoginClicked(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Введите логин и пароль");
            return;
        }

        new Thread(() -> {
            try {
                User user = userService.login(username, password);
                if (user != null) {
                    Platform.runLater(() -> switchToDashboard(event, user));
                } else {
                    Platform.runLater(() -> errorLabel.setText("Неверный логин или пароль"));
                }
            } catch (Exception e) {
                Platform.runLater(() -> errorLabel.setText("Ошибка подключения к серверу"));
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void onRegisterClicked(ActionEvent event) {
        errorLabel.setText("Регистрация пока не реализована");
    }

    private void switchToDashboard(ActionEvent event, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setCurrentUser(user);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Не удалось открыть дашборд");
        }
    }
}