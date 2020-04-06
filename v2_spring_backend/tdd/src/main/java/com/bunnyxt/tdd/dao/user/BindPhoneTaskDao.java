package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.BindPhoneTask;

public interface BindPhoneTaskDao {

    void addBindPhoneTask(Integer added, Long userid, String phone, String bindkey, String code,
                          Integer expired, Byte status);

    BindPhoneTask queryBindPhoneTaskByBindKey(String bindkey);

    void updateBindPhoneTaskStatusByBindKey(String bindkey, Byte status);

    void updateLaterSucceedBindPhoneTaskStatus(Long userid, String phone, Byte status);
}
