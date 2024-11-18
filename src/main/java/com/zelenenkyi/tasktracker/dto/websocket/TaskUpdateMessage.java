package com.zelenenkyi.tasktracker.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskUpdateMessage {
    Long id;
    String title;
    String description;
    Long taskListId;

}
