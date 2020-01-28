package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.StatDailyDao;
import com.bunnyxt.tdd.model.StatDaily;
import com.bunnyxt.tdd.service.StatDailyService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatDailyServiceImpl implements StatDailyService {

    @Autowired
    StatDailyDao statDailyDao;

    @Override
    public List<StatDaily> queryStatDailys(Integer start_ts, Integer end_ts, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 25000);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return statDailyDao.queryStatDailys(start_ts, end_ts, offset, ps);
    }

    @Override
    public Integer queryStatDailysCount(Integer start_ts, Integer end_ts) {
        return statDailyDao.queryStatDailysCount(start_ts, end_ts);
    }
}
