package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserLikeVideo;

public interface UserLikeVideoService {

    TddCommonResponse postUserLikeVideo(Long userid, Integer aid);

    TddCommonResponse deleteUserLikeVideo(Long userid, Integer aid);

    UserLikeVideo queryUserLikeVideo(Long userid, Integer aid);

    Integer queryUserLikeVideoCount(Integer aid);
}
