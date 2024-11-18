package com.zelenenkyi.tasktracker.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class TaskListUpdatePositionMessage {
    HashMap<Long,Integer> mapIdPosition;

}
