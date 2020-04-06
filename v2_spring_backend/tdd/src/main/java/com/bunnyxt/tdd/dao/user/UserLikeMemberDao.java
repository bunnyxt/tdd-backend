package com.bunnyxt.tdd.dao.user;

import com.bunnyxt.tdd.model.user.UserLikeMember;

public interface UserLikeMemberDao {

    void addUserLikeMember(Integer added, Long userid, Integer mid);

    Integer deleteUserLikeMember(Long userid, Integer mid);

    UserLikeMember queryUserLikeMember(Long userid, Integer mid);

    Integer queryUserLikeMemberCount(Integer mid);
}
