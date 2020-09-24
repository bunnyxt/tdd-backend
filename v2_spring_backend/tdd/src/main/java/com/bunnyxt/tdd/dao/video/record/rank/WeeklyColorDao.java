package com.bunnyxt.tdd.dao.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.WeeklyColor;

import java.util.List;

public interface WeeklyColorDao {

    List<WeeklyColor> queryWeeklyCurrentColors();

    List<WeeklyColor> queryWeeklyArchiveColorsById(Long id);
}
