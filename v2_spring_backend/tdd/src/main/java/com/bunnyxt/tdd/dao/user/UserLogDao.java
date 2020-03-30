package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserLog;

public interface UserLogDao {

    void addUserLog(Integer added, Long userid, String action, String detail);

    UserLog queryLastSetNicknameLogViaUserid(Long userid);
}
