package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserFavoriteMember;

import java.util.List;

public interface UserFavoriteMemberDao {

    void addUserFavoriteMember(Integer added, Long userid, Integer mid);

    Integer deleteUserFavoriteMember(Long userid, Integer mid);

    UserFavoriteMember queryUserFavoriteMember(Long userid, Integer mid);

    Integer queryUserFavoriteMemberCount(Integer mid);

    List<UserFavoriteMember> queryUserFavoriteMemberUsers(Integer mid, Integer start_ts, Integer end_ts,
                                                          String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryUserFavoriteMemberUsersCount(Integer mid, Integer start_ts, Integer end_ts);
}
