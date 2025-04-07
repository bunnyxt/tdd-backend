package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserLikeVideo;

public interface UserLikeVideoService {

    TddCommonResponse postUserLikeVideo(Long userid, Long aid);

    TddCommonResponse deleteUserLikeVideo(Long userid, Long aid);

    UserLikeVideo queryUserLikeVideo(Long userid, Long aid);

    Integer queryUserLikeVideoCount(Long aid);
}
