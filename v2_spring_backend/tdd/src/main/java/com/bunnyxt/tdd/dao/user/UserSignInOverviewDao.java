package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserSignInOverview;

public interface UserSignInOverviewDao {

    UserSignInOverview queryUserSignInOverviewByUserid(Long userid);

    void updateUserSignInOverviewByUseridWhenSignIn(Long userid, Integer last_added, Integer last_added_days);
}
