package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoRecordHourlyDao;
import com.bunnyxt.tdd.model.VideoRecordHourly;
import com.bunnyxt.tdd.service.VideoRecordHourlyService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoRecordHourlyServiceImpl implements VideoRecordHourlyService {

    @Autowired
    VideoRecordHourlyDao videoRecordHourlyDao;

    @Override
    public List<VideoRecordHourly> queryVideoRecordHourlys(String bvid, Integer last_count,
                                                           Integer start_ts, Integer end_ts, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 25000);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return videoRecordHourlyDao.queryVideoRecordHourlys(bvid, last_count, start_ts, end_ts, offset, ps);
    }

    @Override
    public Integer queryVideoRecordHourlysCount(String bvid, Integer start_ts, Integer end_ts) {
        return videoRecordHourlyDao.queryVideoRecordHourlysCount(bvid, start_ts, end_ts);
    }
}
