package com.zelenenkyi.tasktracker.dto.websocket;

import lombok.Data;

@Data
public class TaskChangePositionRequest {
    Long sourceId;
    Long destinationId;
    Integer sourceIndex;
    Integer destinationIndex;
}
