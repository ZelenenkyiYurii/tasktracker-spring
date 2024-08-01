package com.zelenenkyi.tasktracker.service;

import com.zelenenkyi.tasktracker.model.ERole;
import com.zelenenkyi.tasktracker.model.Role;

public interface RoleService {
    public Role findRoleByName(ERole role);
}
