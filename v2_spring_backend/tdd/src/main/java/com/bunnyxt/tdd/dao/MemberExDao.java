package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.MemberEx;

import java.util.List;

public interface MemberExDao {

    MemberEx queryMemberByMid(Integer mid);

    List<MemberEx> queryMembers(String sex, String name,
                                String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryMembersCount(String sex, String name);
}
