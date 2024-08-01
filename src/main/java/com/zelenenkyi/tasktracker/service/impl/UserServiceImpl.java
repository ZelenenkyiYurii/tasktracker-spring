package com.zelenenkyi.tasktracker.service.impl;

import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.User;
import com.zelenenkyi.tasktracker.repository.UserRepository;
import com.zelenenkyi.tasktracker.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;


    @Override
    public void create(User item) {
        repository.save(item);
    }

    @Override
    public Long findIdByUsername(String username) {
        return repository.findIdByUsername(username);
    }

    @Override
    public User findUserByUsername(String username) {
        return repository.findByUsername(username).orElseThrow();
    }
}
