package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.user.UserFavoriteMemberEx;

import java.util.List;

public interface UserFavoriteMemberExService {

    List<UserFavoriteMemberEx> queryUserFavoriteMemberExsMe(Long userid, String name, String sex,
                                                            String order_by, Integer desc, Integer pn, Integer ps);

    Integer queryUserFavoriteMemberExsMeCount(Long userid, String name, String sex);

    List<UserFavoriteMemberEx> queryUserFavoriteMemberExs(Long userid, Integer start_ts, Integer end_ts,
                                                          String order_by, Integer desc, Integer pn, Integer ps);

    Integer queryUserFavoriteMemberExsCount(Long userid,  Integer start_ts, Integer end_ts);
}
