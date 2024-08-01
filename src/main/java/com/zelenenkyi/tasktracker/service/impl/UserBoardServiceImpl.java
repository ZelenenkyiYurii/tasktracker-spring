package com.zelenenkyi.tasktracker.service.impl;

import com.zelenenkyi.tasktracker.model.*;
import com.zelenenkyi.tasktracker.repository.RoleRepository;
import com.zelenenkyi.tasktracker.service.RoleService;
import com.zelenenkyi.tasktracker.service.UserBoardService;
import com.zelenenkyi.tasktracker.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserBoardServiceImpl implements UserBoardService {
    private final UserService userService;
    private final RoleService roleService;
    @Override
    public UserBoardRole create(Board board, String username, ERole role) {
        User user=userService.findUserByUsername(username);
        UserBoardRole userBoardRole=new UserBoardRole(board,user,roleService.findRoleByName(role));
        return userBoardRole;
    }
}
