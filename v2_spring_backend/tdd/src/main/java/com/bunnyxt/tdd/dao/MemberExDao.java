package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.MemberEx;

import java.util.List;

public interface MemberExDao {

    MemberEx queryMemberByMid(Long mid);

    List<MemberEx> queryMembers(String sex, String name,
                                String order_by, Integer desc, Long offset, Integer ps);

    Long queryMembersCount(String sex, String name);
}
