package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserFavoriteVideo;

public interface UserFavoriteVideoService {

    TddCommonResponse postUserFavoriteVideo(Long userid, Integer aid);

    TddCommonResponse deleteUserFavoriteVideo(Long userid, Integer aid);

    UserFavoriteVideo queryUserFavoriteVideo(Long userid, Integer aid);
}
