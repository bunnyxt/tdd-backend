package com.bunnyxt.tdd.dao.user;

public interface UserHistoryExpDao {

    void addUserHistoryExp(Integer added, Long userid, Double diff, String comment);

}
