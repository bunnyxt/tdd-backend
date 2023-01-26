package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.MemberExDao;
import com.bunnyxt.tdd.dao.VideoExDao;
import com.bunnyxt.tdd.dao.VideoStaffExDao;
import com.bunnyxt.tdd.model.MemberEx;
import com.bunnyxt.tdd.model.fragment.VideoFragment;
import com.bunnyxt.tdd.model.fragment.VideoRecordFragment;
import com.bunnyxt.tdd.service.MemberExService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberExServiceImpl implements MemberExService {

    @Autowired
    MemberExDao memberExDao;

    @Override
    public MemberEx queryMemberByMid(Long mid) {
        return memberExDao.queryMemberByMid(mid);
    }

    @Override
    public List<MemberEx> queryMembers(String sex, String name, String order_by, Integer desc, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return memberExDao.queryMembers(sex, name, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryMembersCount(String sex, String name) {
        return memberExDao.queryMembersCount(sex, name);
    }
}
