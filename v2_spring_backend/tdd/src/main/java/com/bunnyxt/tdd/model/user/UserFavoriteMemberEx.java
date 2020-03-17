package com.bunnyxt.tdd.model.user;

import com.bunnyxt.tdd.model.MemberEx;

public class UserFavoriteMemberEx extends UserFavoriteMember {

    private MemberEx member;

    public MemberEx getMember() {
        return member;
    }

    public void setMember(MemberEx member) {
        this.member = member;
    }
}
