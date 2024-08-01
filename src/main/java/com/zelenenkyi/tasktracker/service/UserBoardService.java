package com.zelenenkyi.tasktracker.service;

import com.zelenenkyi.tasktracker.model.*;

public interface UserBoardService {
    public UserBoardRole create(Board board, String username, ERole role);
}
