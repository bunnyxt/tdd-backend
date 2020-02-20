package com.bunnyxt.tdd.dao;

import java.util.List;

public interface MemberMidDao {

    Integer queryMemberMidMaxId();

    Integer queryMemberMidById(Integer id);

    List<Integer> queryMemberMidsByIds(List<Integer> ids);
}
