package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.MemberDao;
import com.bunnyxt.tdd.model.Member;
import com.bunnyxt.tdd.service.MemberService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member queryMemberByMid(Long mid) {
        return memberDao.queryMemberByMid(mid);
    }

    @Override
    public List<Member> queryMembers(String sex, String name, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return memberDao.queryMembers(sex, name, offset, ps);
    }

    @Override
    public Integer queryMembersCount(String sex, String name) {
        return memberDao.queryMembersCount(sex, name);
    }
}
