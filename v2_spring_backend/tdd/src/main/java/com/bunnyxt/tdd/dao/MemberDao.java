package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.Member;

import java.util.List;

public interface MemberDao {

    Member queryMemberByMid(Long mid);

    List<Member> queryMembers(String sex, String name, Long offset, Integer ps);

    Long queryMembersCount(String sex, String name);

}
