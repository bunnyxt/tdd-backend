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
    public List<MemberLog> queryMemberLogs(Integer mid, Integer start_ts, Integer end_ts, String attr, String oldval, String newval, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return memberLogDao.queryMemberLogs(mid, start_ts, end_ts, attr, oldval, newval, offset, ps);
    }

    @Override
    public Integer queryMemberLogsCount(Integer mid, Integer start_ts, Integer end_ts, String attr, String oldval, String newval) {
        return memberLogDao.queryMemberLogsCount(mid, start_ts, end_ts, attr, oldval, newval);
    }
}
