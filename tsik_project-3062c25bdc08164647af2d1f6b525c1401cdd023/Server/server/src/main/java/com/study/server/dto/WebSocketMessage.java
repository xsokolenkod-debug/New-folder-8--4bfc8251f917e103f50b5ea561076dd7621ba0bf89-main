package com.study.server.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebSocketMessage {
    private String type;     // "CHAT", "TASK_UPDATED", "NOTIFICATION"
    private String content;  // Текст сообщения
    private Long groupId;
    private Long userId;
    private String username;
    private Object data;
    // Дополнительные данные
}

