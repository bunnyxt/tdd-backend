package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserFavoriteVideoEx;

import java.util.List;

public interface UserFavoriteVideoExDao {

    List<UserFavoriteVideoEx> queryUserFavoriteVideoExsMe(Long userid, String title,
                                                          String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryUserFavoriteVideoExsMeCount(Long userid, String title);

    List<UserFavoriteVideoEx> queryUserFavoriteVideoExs(Long userid, Integer start_ts, Integer end_ts,
                                                        String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryUserFavoriteVideoExsCount(Long userid, Integer start_ts, Integer end_ts);
}
