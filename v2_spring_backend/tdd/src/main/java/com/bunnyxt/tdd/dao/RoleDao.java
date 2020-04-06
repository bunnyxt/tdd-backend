package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.Role;

import java.util.List;

public interface RoleDao {

    List<Role> queryRolesByUserId(Long userId);

    void addUserRole(Long userId, Long roleId);

}
