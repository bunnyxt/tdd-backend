package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.user.User;

import java.util.List;

public interface UserService {

    User queryUserById(Long id);

    List<User> queryUsers(String username, String email, String phone, Integer enabled, String role,
                          String order_by, Integer desc, Integer pn, Integer ps);

    Integer queryUsersCount(String username, String email, String phone, Integer enabled, String role);
}
