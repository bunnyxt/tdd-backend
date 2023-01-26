package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserLikeMember;

public interface UserLikeMemberDao {

    void addUserLikeMember(Integer added, Long userid, Long mid);

    Integer deleteUserLikeMember(Long userid, Long mid);

    UserLikeMember queryUserLikeMember(Long userid, Long mid);

    Integer queryUserLikeMemberCount(Long mid);
}
