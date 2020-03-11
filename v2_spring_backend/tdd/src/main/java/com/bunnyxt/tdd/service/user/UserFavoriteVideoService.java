package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserFavoriteVideo;

import java.util.List;

public interface UserFavoriteVideoService {

    TddCommonResponse postUserFavoriteVideo(Long userid, Integer aid);

    TddCommonResponse deleteUserFavoriteVideo(Long userid, Integer aid);

    UserFavoriteVideo queryUserFavoriteVideo(Long userid, Integer aid);

    Integer queryUserFavoriteVideoCount(Integer aid);

    List<UserFavoriteVideo> queryUserFavoriteVideoUsers(Integer aid, Integer start_ts, Integer end_ts,
                                                        String order_by, Integer desc, Integer pn, Integer ps);

    Integer queryUserFavoriteVideoUsersCount(Integer aid, Integer start_ts, Integer end_ts);
}
