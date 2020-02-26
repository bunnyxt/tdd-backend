package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.User;

import java.util.List;

public interface UserDao {

    // for UserDetailService in Spring Security
    User queryUserByUsername(String username);

    // for UserService, tdd usage
    User queryUserById(Long id);

    List<User> queryUsers(String username, String email, String phone, Integer enabled, String role,
                          String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryUsersCount(String username, String email, String phone, Integer enabled, String role);

    void updateUserPointById(Double point, Long id);

    void updateUserExpById(Double exp, Long id);

}
