package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.User;
import com.bunnyxt.tdd.model.user.UserRegisterTask;

public interface UserRegisterDao {

    void addUserRegisterTask(Integer added, Byte method, String phone, String email, String username, String password,
                             String regkey, String code, Integer expired, Byte status);

    UserRegisterTask queryUserRegisterTaskByRegKey(String regkey);

    void updateUserRegisterTaskStatus(String regkey, Byte status);
}
