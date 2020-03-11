package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserFavoriteVideo;

import java.util.List;

public interface UserFavoriteVideoDao {

    void addUserFavoriteVideo(Integer added, Long userid, Integer aid);

    Integer deleteUserFavoriteVideo(Long userid, Integer aid);

    UserFavoriteVideo queryUserFavoriteVideo(Long userid, Integer aid);

    Integer queryUserFavoriteVideoCount(Integer aid);

    List<UserFavoriteVideo> queryUserFavoriteVideoUsers(Integer aid, Integer start_ts, Integer end_ts,
                                                        String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryUserFavoriteVideoUsersCount(Integer aid, Integer start_ts, Integer end_ts);
}
