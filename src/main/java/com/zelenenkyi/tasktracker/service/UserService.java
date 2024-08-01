package com.zelenenkyi.tasktracker.service;

import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.User;
import org.springframework.stereotype.Service;


public interface UserService {
    void create(User item);
    Long findIdByUsername(String username);

    User findUserByUsername(String username);
}
