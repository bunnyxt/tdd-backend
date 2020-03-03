package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserFavoriteVideo;

public interface UserFavoriteVideoDao {

    void addUserFavoriteVideo(Integer added, Long userid, Integer aid);

    Integer deleteUserFavoriteVideo(Long userid, Integer aid);

    UserFavoriteVideo queryUserFavoriteVideo(Long userid, Integer aid);
}
