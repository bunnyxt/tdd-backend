package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserLikeMember;

public interface UserLikeMemberService {

    TddCommonResponse postUserLikeMember(Long userid, Long mid);

    TddCommonResponse deleteUserLikeMember(Long userid, Long mid);

    UserLikeMember queryUserLikeMember(Long userid, Long mid);

    Integer queryUserLikeMemberCount(Long mid);
}
