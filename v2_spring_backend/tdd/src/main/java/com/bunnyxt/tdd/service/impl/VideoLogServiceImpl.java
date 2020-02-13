package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoLogDao;
import com.bunnyxt.tdd.model.VideoLog;
import com.bunnyxt.tdd.service.VideoLogService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoLogServiceImpl implements VideoLogService {

    @Autowired
    VideoLogDao videoLogDao;

    @Override
    public List<VideoLog> queryVideoLogs(Integer aid, Integer start_ts, Integer end_ts, String attr, String oldval, String newval, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return videoLogDao.queryVideoLogs(aid, start_ts, end_ts, attr, oldval, newval, offset, ps);
    }

    @Override
    public Integer queryVideoLogsCount(Integer aid, Integer start_ts, Integer end_ts, String attr, String oldval, String newval) {
        return videoLogDao.queryVideoLogsCount(aid, start_ts, end_ts, attr, oldval, newval);
    }
}
