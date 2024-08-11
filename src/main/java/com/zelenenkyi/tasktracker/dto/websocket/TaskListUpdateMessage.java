package com.zelenenkyi.tasktracker.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskListUpdateMessage {
    Long id;
    String title;
}
