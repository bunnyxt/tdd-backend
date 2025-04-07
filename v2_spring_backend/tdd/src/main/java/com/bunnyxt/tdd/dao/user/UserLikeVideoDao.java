package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserLikeVideo;

public interface UserLikeVideoDao {

    void addUserLikeVideo(Integer added, Long userid, Long aid);

    Integer deleteUserLikeVideo(Long userid, Long aid);

    UserLikeVideo queryUserLikeVideo(Long userid, Long aid);

    Integer queryUserLikeVideoCount(Long aid);
}
