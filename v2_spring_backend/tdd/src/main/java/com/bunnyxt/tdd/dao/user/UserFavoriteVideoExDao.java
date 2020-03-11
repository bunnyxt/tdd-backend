package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserFavoriteVideoEx;

import java.util.List;

public interface UserFavoriteVideoExDao {

    List<UserFavoriteVideoEx> queryUserFavoriteVideoExsMe(Long userid, String title,
                                                          String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryUserFavoriteVideoExsMeCount(Long userid, String title);
}
