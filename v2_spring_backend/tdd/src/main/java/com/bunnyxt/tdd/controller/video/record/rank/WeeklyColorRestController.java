package com.bunnyxt.tdd.controller.video.record.rank;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyCurrentColor;
import com.bunnyxt.tdd.service.video.record.rank.WeeklyColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class WeeklyColorRestController {

    @Autowired
    private WeeklyColorService weeklyColorService;

    @RequestMapping(value = "/video/record/rank/weekly/current/color", method = RequestMethod.GET)
    public Map<String, Map<String, Double>> queryWeeklyCurrentColors()
            throws InvalidRequestParameterException {
        List<WeeklyCurrentColor> colorList = weeklyColorService.queryWeeklyCurrentColors();
        Map<String, Map<String, Double>> colorMap = new HashMap<>();
        for (WeeklyCurrentColor color : colorList) {
            Map<String, Double> valueMap = new HashMap<>();
            valueMap.put("a", color.getA());
            valueMap.put("b", color.getB());
            valueMap.put("c", color.getC());
            valueMap.put("d", color.getD());
            colorMap.put(color.getProperty(), valueMap);
        }
        return colorMap;
    }
}
