package com.zelenenkyi.tasktracker.service.impl;

import com.zelenenkyi.tasktracker.model.ERole;
import com.zelenenkyi.tasktracker.model.Role;
import com.zelenenkyi.tasktracker.repository.RoleRepository;
import com.zelenenkyi.tasktracker.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public Role findRoleByName(ERole role) {
        return roleRepository.findByName(role).orElseThrow();
    }
}
