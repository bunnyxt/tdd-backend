package com.bunnyxt.tdd.service.impl.video.record.rank;

import com.bunnyxt.tdd.dao.video.record.rank.WeeklyColorDao;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyCurrentColor;
import com.bunnyxt.tdd.service.video.record.rank.WeeklyColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeeklyColorServiceImpl implements WeeklyColorService {

    @Autowired
    WeeklyColorDao weeklyColorDao;

    @Override
    public List<WeeklyCurrentColor> queryWeeklyCurrentColors() {
        return weeklyColorDao.queryWeeklyCurrentColors();
    }
}
