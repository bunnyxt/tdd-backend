package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.Member;

import java.util.List;

public interface MemberDao {

    Member queryMemberByMid(int mid);

    List<Member> queryMembers(String sex, String name, int offset, int ps);

}
