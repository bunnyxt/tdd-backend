package com.bunnyxt.tdd.service.impl.video.record.rank;

import com.bunnyxt.tdd.dao.video.record.rank.WeeklyCurrentColorDao;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyCurrentColor;
import com.bunnyxt.tdd.service.video.record.rank.WeeklyCurrentColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeeklyCurrentColorServiceImpl implements WeeklyCurrentColorService {

    @Autowired
    WeeklyCurrentColorDao weeklyCurrentColorDao;

    @Override
    public List<WeeklyCurrentColor> queryWeeklyCurrentColors() {
        return weeklyCurrentColorDao.queryWeeklyCurrentColors();
    }
}
