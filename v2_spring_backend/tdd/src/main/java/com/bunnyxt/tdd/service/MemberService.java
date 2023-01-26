package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.Member;

import java.util.List;

public interface MemberService {

    Member queryMemberByMid(Long mid);

    List<Member> queryMembers(String sex, String name, Integer pn, Integer ps);

    Integer queryMembersCount(String sex, String name);

}
