package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.MemberEx;

import java.util.List;

public interface MemberExService {

    MemberEx queryMemberByMid(Integer mid);

    List<MemberEx> queryMembers(String sex, String name, Integer pn, Integer ps);

    Integer queryMembersCount(String sex, String name);
}
