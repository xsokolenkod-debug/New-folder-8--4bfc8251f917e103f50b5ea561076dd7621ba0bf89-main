package com.study.server.controller;

import com.study.server.dto.WebSocketMessage;
import com.study.server.service.WebSocketService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final WebSocketService webSocketService;

    public NotificationController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @PostMapping("/group/{groupId}/chat")
    public String sendGroupChat(@PathVariable Long groupId, @RequestBody WebSocketMessage message) {
        message.setType("CHAT");
        webSocketService.sendChatMessage(groupId, message);
        return "Chat message sent";
    }

    @PostMapping("/group/{groupId}/tasks")
    public String sendTaskUpdate(@PathVariable Long groupId, @RequestBody WebSocketMessage message) {
        message.setType("TASK_UPDATED");
        webSocketService.sendTaskUpdate(groupId, message);
        return "Task update sent";
    }

    @PostMapping("/group/{groupId}/members")
    public String sendMemberUpdate(@PathVariable Long groupId, @RequestBody WebSocketMessage message) {
        message.setType("MEMBER_STATUS");
        webSocketService.sendMemberUpdate(groupId, message);
        return "Member status update sent";
    }

    @PostMapping("/user/{userId}/notifications")
    public String sendUserNotification(@PathVariable Long userId, @RequestBody WebSocketMessage message) {
        message.setType("NOTIFICATION");
        webSocketService.sendUserNotification(userId, message);
        return "User notification sent";
    }
}
