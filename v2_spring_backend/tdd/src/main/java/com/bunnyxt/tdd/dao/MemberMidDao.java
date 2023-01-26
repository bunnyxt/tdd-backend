package com.bunnyxt.tdd.dao;

import java.util.List;

public interface MemberMidDao {

    Long queryMemberMidMaxId();

    Long queryMemberMidById(Long id);

    List<Long> queryMemberMidsByIds(List<Long> ids);
}
