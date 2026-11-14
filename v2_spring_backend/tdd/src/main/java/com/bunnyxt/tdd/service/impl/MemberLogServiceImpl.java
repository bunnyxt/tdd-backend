package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.MemberLogDao;
import com.bunnyxt.tdd.model.MemberLog;
import com.bunnyxt.tdd.service.MemberLogService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberLogServiceImpl implements MemberLogService {

    @Autowired
    MemberLogDao memberLogDao;

    @Override
    public List<MemberLog> queryMemberLogs(Long mid, Long start_ts, Long end_ts, String attr, String oldval, String newval, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Long offset = PageNumModfier.calcOffset(ps, pn);

        return memberLogDao.queryMemberLogs(mid, start_ts, end_ts, attr, oldval, newval, offset, ps);
    }

    @Override
    public Long queryMemberLogsCount(Long mid, Long start_ts, Long end_ts, String attr, String oldval, String newval) {
        return memberLogDao.queryMemberLogsCount(mid, start_ts, end_ts, attr, oldval, newval);
    }
}
