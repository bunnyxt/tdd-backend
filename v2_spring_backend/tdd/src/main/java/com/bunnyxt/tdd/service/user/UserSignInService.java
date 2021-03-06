package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserSignIn;

import java.util.List;

public interface UserSignInService {

    List<UserSignIn> queryUserSignIns(Long userid, Integer last_count, Integer start_ts, Integer end_ts,
                                      Integer pn, Integer ps);

    Integer queryUserSignInsCount(Long userid, Integer start_ts, Integer end_ts);

    TddCommonResponse postUserSignIn(Long userid);
}
