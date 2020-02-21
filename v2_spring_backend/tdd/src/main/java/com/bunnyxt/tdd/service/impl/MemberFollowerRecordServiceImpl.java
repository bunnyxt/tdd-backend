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
    public List<MemberFollowerRecord> queryMemberFollowerRecords(Integer mid, Integer last_count, Integer start_ts, Integer end_ts, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 25000);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return memberFollowerRecordDao.queryMemberFollowerRecords(mid, last_count, start_ts, end_ts, offset, ps);
    }

    @Override
    public Integer queryMemberFollowerRecordsCount(Integer mid, Integer start_ts, Integer end_ts) {
        return memberFollowerRecordDao.queryMemberFollowerRecordsCount(mid, start_ts, end_ts);
    }
}
