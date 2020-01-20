package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoRecordDao;
import com.bunnyxt.tdd.model.VideoRecord;
import com.bunnyxt.tdd.service.VideoRecordService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoRecordServiceImpl implements VideoRecordService {

    @Autowired
    private VideoRecordDao videoRecordDao;

    @Override
    public List<VideoRecord> queryVideoRecords(Integer aid, Integer start_ts, Integer end_ts, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 25000);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return videoRecordDao.queryVideoRecords(aid, start_ts, end_ts, offset, ps);
    }

    @Override
    public Integer queryVideoRecordsCount(Integer aid, Integer start_ts, Integer end_ts) {
        return videoRecordDao.queryVideoRecordsCount(aid, start_ts, end_ts);
    }
}
