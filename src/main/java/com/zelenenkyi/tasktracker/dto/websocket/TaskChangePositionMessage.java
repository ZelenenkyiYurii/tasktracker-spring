package com.zelenenkyi.tasktracker.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;


@Data
@AllArgsConstructor
public class TaskChangePositionMessage {
    HashMap<Long,Integer> mapSourceIdPosition;
    HashMap<Long,Integer> mapDestinationIdPosition;
    Long sourceTaskListId;
    Long destinationTaskListId;


}
