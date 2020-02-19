package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.MemberTotalStatRecordDao;
import com.bunnyxt.tdd.model.MemberTotalStatRecord;
import com.bunnyxt.tdd.service.MemberTotalStatRecordService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberTotalStatRecordServiceImpl implements MemberTotalStatRecordService {

    @Autowired
    MemberTotalStatRecordDao memberTotalStatRecordDao;

    @Override
    public List<MemberTotalStatRecord> queryMemberTotalStatRecords(Integer mid, Integer start_ts, Integer end_ts, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 25000);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return memberTotalStatRecordDao.queryMemberTotalStatRecords(mid, start_ts, end_ts, offset, ps);
    }

    @Override
    public Integer queryMemberTotalStatRecordsCount(Integer mid, Integer start_ts, Integer end_ts) {
        return memberTotalStatRecordDao.queryMemberTotalStatRecordsCount(mid, start_ts, end_ts);
    }
}
