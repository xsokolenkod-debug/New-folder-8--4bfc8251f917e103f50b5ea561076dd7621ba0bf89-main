package com.study.client.controller;

import com.study.client.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    // кнопки слева (нужны, если будешь их использовать в коде)
    @FXML
    private Button tasksButton;
    @FXML
    private Button messagesButton;
    @FXML
    private Button groupsButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button statsButton;

    // контейнер в центре
    @FXML
    private StackPane contentArea;

    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Добро пожаловать, " + user.getUsername());

        // по умолчанию открываем, например, группы
        openView("/fxml/dashGroup.fxml");
    }

    // ===== обработчики кнопок меню =====

    @FXML
    private void onTasksClicked() {
        openView("/fxml/tasks.fxml");
    }

    @FXML
    private void onMessagesClicked() {
        openView("/fxml/notification.fxml");
    }

    @FXML
    private void onGroupsClicked() {
        openView("/fxml/dashGroup.fxml"); // или /fxml/group.fxml — как у тебя задумано
    }

    @FXML
    private void onProfileClicked() {
        openView("/fxml/profile.fxml");
    }

    @FXML
    private void onStatsClicked() {
        openView("/fxml/statistika.fxml");
    }

    // ===== общий метод загрузки любого FXML в центр =====

    private void openView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();

            Object controller = loader.getController();
            if (controller != null && currentUser != null) {
                try {
                    controller.getClass()
                            .getMethod("setCurrentUser", User.class)
                            .invoke(controller, currentUser);
                } catch (NoSuchMethodException ignored) {
                    // у контроллера нет setCurrentUser(User) — ничего страшного
                } catch (Exception e) {
                    // IllegalAccessException, InvocationTargetException и т.п.
                    e.printStackTrace();
                }
            }

            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}