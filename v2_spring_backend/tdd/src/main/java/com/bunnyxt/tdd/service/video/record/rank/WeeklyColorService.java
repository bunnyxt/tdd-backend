package com.bunnyxt.tdd.service.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.WeeklyColor;

import java.util.List;

public interface WeeklyColorService {

    List<WeeklyColor> queryWeeklyCurrentColors();

    List<WeeklyColor> queryWeeklyArchiveColorByArchId(Long arch_id);
}
