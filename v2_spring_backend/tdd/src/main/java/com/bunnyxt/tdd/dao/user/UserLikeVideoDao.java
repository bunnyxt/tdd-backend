package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserLikeVideo;

public interface UserLikeVideoDao {

    void addUserLikeVideo(Integer added, Long userid, Integer aid);

    Integer deleteUserLikeVideo(Long userid, Integer aid);

    UserLikeVideo queryUserLikeVideo(Long userid, Integer aid);

    Integer queryUserLikeVideoCount(Integer aid);
}
