package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VisitHistoryDao;
import com.bunnyxt.tdd.model.VisitHistoryVideoEx;
import com.bunnyxt.tdd.service.VisitHistoryService;
import com.bunnyxt.tdd.util.CalendarUtil;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitHistoryServiceImpl implements VisitHistoryService {

    @Autowired
    VisitHistoryDao visitHistoryDao;

    @Override
    public void addVisitHistoryVideo(Long userid, String bvid) {
        Integer added = CalendarUtil.getNowTs();
        bvid = bvid.substring(0, 10);  // length of bvid should be 10
        visitHistoryDao.addVisitHistoryVideo(added, userid, bvid);
    }

    @Override
    public List<VisitHistoryVideoEx> getVisitHistoryVideoByUserid(Long userid, Integer start_ts, Integer end_ts, Integer desc, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return visitHistoryDao.getVisitHistoryVideoByUserid(userid, start_ts, end_ts, desc, offset, ps);
    }

    @Override
    public Integer getVisitHistoryVideoCountByUserid(Long userid, Integer start_ts, Integer end_ts) {
        return visitHistoryDao.getVisitHistoryVideoCountByUserid(userid, start_ts, end_ts);
    }
}
