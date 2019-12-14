package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.Member;

import java.util.List;

public interface MemberService {

    Member queryMemberByMid(int mid);

    List<Member> queryMembers(String sex, String name, int pn, int ps);

    int queryMembersCount(String sex, String name);

}
