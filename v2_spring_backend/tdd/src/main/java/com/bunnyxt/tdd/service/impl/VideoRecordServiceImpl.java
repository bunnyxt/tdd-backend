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
    public List<VideoRecord> queryVideoRecords(Integer aid, Integer last_count, Integer start_ts, Integer end_ts,
                                               Boolean limit, Integer pn, Integer ps) {
        Integer offset = null;
        if (limit) {
            // pn, ps -> offset, ps
            ps = PageNumModfier.modifyPs(ps, 25000);
            pn = PageNumModfier.modifyPn(pn);
            offset = PageNumModfier.calcOffset(ps, pn);
        } else {
            offset = 0;
            ps = 0;
        }

        return videoRecordDao.queryVideoRecords(aid, last_count, start_ts, end_ts, limit, offset, ps);
    }

    @Override
    public Integer queryVideoRecordsCount(Integer aid, Integer start_ts, Integer end_ts) {
        return videoRecordDao.queryVideoRecordsCount(aid, start_ts, end_ts);
    }
}
