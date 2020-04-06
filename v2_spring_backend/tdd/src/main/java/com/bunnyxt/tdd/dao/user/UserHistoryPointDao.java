package com.bunnyxt.tdd.dao.user;

public interface UserHistoryPointDao {

    void addUserHistoryPoint(Integer added, Long userid, Double diff, String comment);

}
