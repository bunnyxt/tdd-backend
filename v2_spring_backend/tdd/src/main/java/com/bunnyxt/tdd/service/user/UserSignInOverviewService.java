package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.user.UserSignInOverview;

import java.util.List;

public interface UserSignInOverviewService {

    UserSignInOverview queryUserSignInOverviewByUserid(Long userid);

    List<UserSignInOverview> queryUserSignInOverviews(Integer start_ts, Integer end_ts,
                                                      String order_by, Integer desc, Integer pn, Integer ps);

    Integer queryUserSignInOverviewsCount(Integer start_ts, Integer end_ts);
}
