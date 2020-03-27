package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserSignInOverview;

import java.util.List;

public interface UserSignInOverviewDao {

    UserSignInOverview queryUserSignInOverviewByUserid(Long userid);

    List<UserSignInOverview> queryUserSignInOverviews(Integer start_ts, Integer end_ts,
                                                      String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryUserSignInOverviewsCount(Integer start_ts, Integer end_ts);

    void updateUserSignInOverviewByUseridWhenSignIn(Long userid, Integer last_added, Integer last_added_days);

    void addUserSignInOverview(Long userid);
}
