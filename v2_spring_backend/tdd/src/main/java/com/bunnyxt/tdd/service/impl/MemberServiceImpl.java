package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.MemberDao;
import com.bunnyxt.tdd.model.Member;
import com.bunnyxt.tdd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    private int modifyPs(int ps) {
        // modify ps to [0, 20]
        if (ps > 20) {
            ps = 20;
        } else if (ps < 0) {
            ps = 0;
        }
        return ps;
    }

    private int modifyPn(int pn) {
        // modify pn
        if (pn <= 0) {
            pn = 1;
        }
        return pn;
    }

    private int calcOffset(int pn, int ps) {
        // calc offset
        return ps * (pn - 1);
    }

    @Override
    public Member queryMemberByMid(int mid) {
        return memberDao.queryMemberByMid(mid);
    }

    @Override
    public List<Member> queryMembers(String sex, String name, int pn, int ps) {
        ps = modifyPs(ps);
        pn = modifyPn(pn);
        int offset = calcOffset(pn, ps);
        return memberDao.queryMembers(sex, name, offset, ps);
    }
}
