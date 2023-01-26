package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserFavoriteMember;

import java.util.List;

public interface UserFavoriteMemberDao {

    void addUserFavoriteMember(Integer added, Long userid, Long mid);

    Integer deleteUserFavoriteMember(Long userid, Long mid);

    UserFavoriteMember queryUserFavoriteMember(Long userid, Long mid);

    Integer queryUserFavoriteMemberCount(Long mid);

    List<UserFavoriteMember> queryUserFavoriteMemberUsers(Long mid, Integer start_ts, Integer end_ts,
                                                          String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryUserFavoriteMemberUsersCount(Long mid, Integer start_ts, Integer end_ts);
}
