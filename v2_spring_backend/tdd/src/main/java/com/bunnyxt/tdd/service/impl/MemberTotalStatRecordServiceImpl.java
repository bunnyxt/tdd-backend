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
    public List<MemberTotalStatRecord> queryMemberTotalStatRecords(Long mid, Integer last_count, Long start_ts, Long end_ts, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 50000);
        pn = PageNumModfier.modifyPn(pn);
        Long offset = PageNumModfier.calcOffset(ps, pn);

        return memberTotalStatRecordDao.queryMemberTotalStatRecords(mid, last_count, start_ts, end_ts, offset, ps);
    }

    @Override
    public Long queryMemberTotalStatRecordsCount(Long mid, Long start_ts, Long end_ts) {
        return memberTotalStatRecordDao.queryMemberTotalStatRecordsCount(mid, start_ts, end_ts);
    }
}
