package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserFavoriteMember;

import java.util.List;

public interface UserFavoriteMemberService {

    TddCommonResponse postUserFavoriteMember(Long userid, Long mid);

    TddCommonResponse deleteUserFavoriteMember(Long userid, Long mid);

    UserFavoriteMember queryUserFavoriteMember(Long userid, Long mid);

    Integer queryUserFavoriteMemberCount(Long mid);

    List<UserFavoriteMember> queryUserFavoriteMemberUsers(Long mid, Integer start_ts, Integer end_ts,
                                                          String order_by, Integer desc, Integer pn, Integer ps);

    Integer queryUserFavoriteMemberUsersCount(Long mid, Integer start_ts, Integer end_ts);
}
