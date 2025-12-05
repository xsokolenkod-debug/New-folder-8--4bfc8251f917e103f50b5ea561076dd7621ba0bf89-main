package com.study.client.controller;

import com.study.client.model.Group;
import com.study.client.model.User;
import com.study.client.service.GroupService;
import com.study.client.service.MaterialService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class GroupController {

    @FXML
    private ListView<User> membersListView;

    @FXML
    private ListView<String> materialsListView;

    @FXML
    private TextField newMaterialField;

    @FXML
    private Button addMaterialButton;

    private Group currentGroup;
    private final GroupService groupService = new GroupService();
    private final MaterialService materialService = new MaterialService();

    public void setCurrentGroup(Group group) {
        this.currentGroup = group;
        loadMembers();
        loadMaterials();
    }

    private void loadMembers() {
        new Thread(() -> {
            try {
                List<User> members = groupService.getMembers(currentGroup.getId());
                Platform.runLater(() -> membersListView.setItems(FXCollections.observableArrayList(members)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loadMaterials() {
        new Thread(() -> {
            try {
                List<String> materials = materialService.getMaterials(currentGroup.getId());
                Platform.runLater(() -> materialsListView.setItems(FXCollections.observableArrayList(materials)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void onAddMaterialClicked() {
        String name = newMaterialField.getText().trim();
        if(name.isEmpty()) return;

        new Thread(() -> {
            try {
                materialService.addMaterial(currentGroup.getId(), name);
                loadMaterials();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
