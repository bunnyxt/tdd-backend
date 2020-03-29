package com.bunnyxt.tdd.dao.user;

public interface UserLogDao {

    void addUserLog(Integer added, Long userid, String action, String detail);
}
