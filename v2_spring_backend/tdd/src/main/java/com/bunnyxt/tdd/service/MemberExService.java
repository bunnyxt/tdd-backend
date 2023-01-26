package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.MemberEx;

import java.util.List;

public interface MemberExService {

    MemberEx queryMemberByMid(Long mid);

    List<MemberEx> queryMembers(String sex, String name, String order_by, Integer desc, Integer pn, Integer ps);

    Integer queryMembersCount(String sex, String name);
}
