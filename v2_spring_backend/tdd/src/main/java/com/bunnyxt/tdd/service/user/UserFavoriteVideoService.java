package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserFavoriteVideo;

import java.util.List;

public interface UserFavoriteVideoService {

    TddCommonResponse postUserFavoriteVideo(Long userid, Long aid);

    TddCommonResponse deleteUserFavoriteVideo(Long userid, Long aid);

    UserFavoriteVideo queryUserFavoriteVideo(Long userid, Long aid);

    Integer queryUserFavoriteVideoCount(Long aid);

    List<UserFavoriteVideo> queryUserFavoriteVideoUsers(Long aid, Integer start_ts, Integer end_ts,
                                                        String order_by, Integer desc, Integer pn, Integer ps);

    Integer queryUserFavoriteVideoUsersCount(Long aid, Integer start_ts, Integer end_ts);
}
