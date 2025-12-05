package com.study.client.controller;

import com.study.client.model.Task;
import com.study.client.service.TaskService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class TasksController {

    @FXML
    private ListView<Task> openListView;

    @FXML
    private ListView<Task> inProgressListView;

    @FXML
    private ListView<Task> doneListView;

    @FXML
    private Button addTaskButton;

    @FXML
    private TextField newTaskField;

    private Long groupId;
    private final TaskService taskService = new TaskService();

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
        loadTasks();
    }

    private void loadTasks() {
        new Thread(() -> {
            try {
                List<Task> tasks = taskService.getTasks(groupId);
                Platform.runLater(() -> {
                    openListView.setItems(FXCollections.observableArrayList(tasks.stream().filter(t -> t.getStatus().equals("OPEN")).toList()));
                    inProgressListView.setItems(FXCollections.observableArrayList(tasks.stream().filter(t -> t.getStatus().equals("IN_PROGRESS")).toList()));
                    doneListView.setItems(FXCollections.observableArrayList(tasks.stream().filter(t -> t.getStatus().equals("DONE")).toList()));
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void onAddTaskClicked() {
        String title = newTaskField.getText().trim();
        if(title.isEmpty()) return;

        new Thread(() -> {
            try {
                taskService.createTask(groupId, title);
                loadTasks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
