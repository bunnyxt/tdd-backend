package com.bunnyxt.tdd.controller.video.record.rank;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyColor;
import com.bunnyxt.tdd.service.video.record.rank.WeeklyColorService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class WeeklyColorRestController {

    @Autowired
    private WeeklyColorService weeklyColorService;

    private Map<String, Map<String, Double>> buildWeeklyColorMap(List<WeeklyColor> weeklyColorList) {
        Map<String, Map<String, Double>> weeklyColorMap = new HashMap<>();
        for (WeeklyColor color : weeklyColorList) {
            Map<String, Double> valueMap = new HashMap<>();
            valueMap.put("a", color.getA());
            valueMap.put("b", color.getB());
            valueMap.put("c", color.getC());
            valueMap.put("d", color.getD());
            weeklyColorMap.put(color.getProperty(), valueMap);
        }
        return weeklyColorMap;
    }

    @RequestMapping(value = "/video/record/rank/weekly/current/color", method = RequestMethod.GET)
    public Map<String, Map<String, Double>> queryWeeklyCurrentColors()
            throws InvalidRequestParameterException {
        return buildWeeklyColorMap(
                weeklyColorService.queryWeeklyCurrentColors()
        );
    }

    @RequestMapping(value = "/video/record/rank/weekly/archive/{arch_id}/color", method = RequestMethod.GET)
    public Map<String, Map<String, Double>> queryWeeklyCurrentColorByArchId(
            @PathVariable Long arch_id
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.arch_id(arch_id);

        return buildWeeklyColorMap(
                weeklyColorService.queryWeeklyArchiveColorByArchId(arch_id)
        );
    }
}
