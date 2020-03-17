package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserFavoriteMember;

import java.util.List;

public interface UserFavoriteMemberService {

    TddCommonResponse postUserFavoriteMember(Long userid, Integer mid);

    TddCommonResponse deleteUserFavoriteMember(Long userid, Integer mid);

    UserFavoriteMember queryUserFavoriteMember(Long userid, Integer mid);

    Integer queryUserFavoriteMemberCount(Integer mid);

    List<UserFavoriteMember> queryUserFavoriteMemberUsers(Integer mid, Integer start_ts, Integer end_ts,
                                                          String order_by, Integer desc, Integer pn, Integer ps);

    Integer queryUserFavoriteMemberUsersCount(Integer mid, Integer start_ts, Integer end_ts);
}
