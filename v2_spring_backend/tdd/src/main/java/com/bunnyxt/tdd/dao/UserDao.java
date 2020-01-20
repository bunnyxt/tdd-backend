package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.User;

public interface UserDao {

    User queryUserByUsername(String username);

}
