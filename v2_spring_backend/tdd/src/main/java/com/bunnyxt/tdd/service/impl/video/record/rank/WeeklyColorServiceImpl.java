package com.bunnyxt.tdd.service.impl.video.record.rank;

import com.bunnyxt.tdd.dao.video.record.rank.WeeklyColorDao;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyColor;
import com.bunnyxt.tdd.service.video.record.rank.WeeklyColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeeklyColorServiceImpl implements WeeklyColorService {

    @Autowired
    WeeklyColorDao weeklyColorDao;

    @Override
    public List<WeeklyColor> queryWeeklyCurrentColors() {
        return weeklyColorDao.queryWeeklyCurrentColors();
    }

    @Override
    public List<WeeklyColor> queryWeeklyArchiveColorByArchId(Long arch_id) {
        return weeklyColorDao.queryWeeklyArchiveColorsByArchId(arch_id);
    }
}
