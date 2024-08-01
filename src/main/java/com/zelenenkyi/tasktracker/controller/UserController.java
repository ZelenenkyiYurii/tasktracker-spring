package com.zelenenkyi.tasktracker.controller;

import com.zelenenkyi.tasktracker.model.User;
import com.zelenenkyi.tasktracker.service.UserService;
import com.zelenenkyi.tasktracker.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController{

    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody User item){
        userService.create(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
