package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserSignIn;

import java.util.List;

public interface UserSignInDao {

    List<UserSignIn> queryUserSignIns(Long userid, Integer last_count, Integer start_ts, Integer end_ts,
                                      Integer offset, Integer ps);

    Integer queryUserSignInsCount(Long userid, Integer start_ts, Integer end_ts);

    UserSignIn queryUserSignInOneDay(Long userid, Integer start_ts, Integer end_ts);

    void addUserSignIn(Integer added, Long userid);

    Integer queryUserSignInRankInOneDay(Long userid, Integer start_ts, Integer end_ts);

    void updateUserSignInRank(Integer added, Long userid, Integer rank);
}
