package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserLikeMember;

public interface UserLikeMemberService {

    TddCommonResponse postUserLikeMember(Long userid, Integer mid);

    TddCommonResponse deleteUserLikeMember(Long userid, Integer mid);

    UserLikeMember queryUserLikeMember(Long userid, Integer mid);

    Integer queryUserLikeMemberCount(Integer mid);
}
