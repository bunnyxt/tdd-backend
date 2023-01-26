package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.Member;

import java.util.List;

public interface MemberDao {

    Member queryMemberByMid(Long mid);

    List<Member> queryMembers(String sex, String name, Integer offset, Integer ps);

    Integer queryMembersCount(String sex, String name);

}
