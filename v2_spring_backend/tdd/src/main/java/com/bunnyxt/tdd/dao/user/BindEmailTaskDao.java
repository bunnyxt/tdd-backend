package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.BindEmailTask;

public interface BindEmailTaskDao {

    void addBindEmailTask(Integer added, Long userid, String email, String bindkey, String code,
                          Integer expired, Byte status);

    BindEmailTask queryBindEmailTaskByBindKey(String bindkey);

    void updateBindEmailTaskStatusByBindKey(String bindkey, Byte status);

    void updateLaterSucceedBindEmailTaskStatus(Long userid, String email, Byte status);
}
