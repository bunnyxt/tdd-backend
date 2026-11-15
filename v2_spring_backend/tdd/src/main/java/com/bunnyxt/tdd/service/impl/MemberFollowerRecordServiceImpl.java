package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.MemberFollowerRecordDao;
import com.bunnyxt.tdd.model.MemberFollowerRecord;
import com.bunnyxt.tdd.service.MemberFollowerRecordService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberFollowerRecordServiceImpl implements MemberFollowerRecordService {

    @Autowired
    MemberFollowerRecordDao memberFollowerRecordDao;

    @Override
    public List<MemberFollowerRecord> queryMemberFollowerRecords(Long mid, Integer last_count, Long start_ts, Long end_ts, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 50000);
        pn = PageNumModfier.modifyPn(pn);
        Long offset = PageNumModfier.calcOffset(ps, pn);

        return memberFollowerRecordDao.queryMemberFollowerRecords(mid, last_count, start_ts, end_ts, offset, ps);
    }

    @Override
    public Long queryMemberFollowerRecordsCount(Long mid, Long start_ts, Long end_ts) {
        return memberFollowerRecordDao.queryMemberFollowerRecordsCount(mid, start_ts, end_ts);
    }
}
