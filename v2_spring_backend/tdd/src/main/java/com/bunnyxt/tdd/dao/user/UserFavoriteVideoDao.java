package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserFavoriteVideo;

import java.util.List;

public interface UserFavoriteVideoDao {

    void addUserFavoriteVideo(Integer added, Long userid, Long aid);

    Integer deleteUserFavoriteVideo(Long userid, Long aid);

    UserFavoriteVideo queryUserFavoriteVideo(Long userid, Long aid);

    Integer queryUserFavoriteVideoCount(Long aid);

    List<UserFavoriteVideo> queryUserFavoriteVideoUsers(Long aid, Integer start_ts, Integer end_ts,
                                                        String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryUserFavoriteVideoUsersCount(Long aid, Integer start_ts, Integer end_ts);
}
