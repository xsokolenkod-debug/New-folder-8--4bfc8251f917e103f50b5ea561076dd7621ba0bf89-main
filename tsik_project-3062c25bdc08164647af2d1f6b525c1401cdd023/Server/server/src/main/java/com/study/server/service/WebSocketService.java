package com.study.server.service;

import com.study.server.dto.WebSocketMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 1. Чат группы
    public void sendChatMessage(Long groupId, WebSocketMessage message) {
        messagingTemplate.convertAndSend("/topic/group/" + groupId + "/chat", message);
        System.out.println("📨 Sent CHAT → /topic/group/" + groupId + "/chat");
    }

    // 2. Обновление задач
    public void sendTaskUpdate(Long groupId, WebSocketMessage message) {
        messagingTemplate.convertAndSend("/topic/group/" + groupId + "/tasks", message);
        System.out.println("📨 Sent TASK_UPDATED → /topic/group/" + groupId + "/tasks");
    }

    // 3. Вход/выход участника
    public void sendMemberUpdate(Long groupId, WebSocketMessage message) {
        messagingTemplate.convertAndSend("/topic/group/" + groupId + "/members", message);
        System.out.println("📨 Sent MEMBER_STATUS → /topic/group/" + groupId + "/members");
    }

    // 4. Личные уведомления (через обычный топик)
    public void sendUserNotification(Long userId, WebSocketMessage message) {
        messagingTemplate.convertAndSend("/topic/user/" + userId + "/notifications", message);
        System.out.println("📨 Sent NOTIFICATION → /topic/user/" + userId + "/notifications");
    }
}
