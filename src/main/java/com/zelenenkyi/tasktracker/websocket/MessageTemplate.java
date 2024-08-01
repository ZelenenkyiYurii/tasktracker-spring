package com.zelenenkyi.tasktracker.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageTemplate<T> {
    private ETypeObject type;
    private EAction action;
    private T object;
}
