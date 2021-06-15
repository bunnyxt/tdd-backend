package com.bunnyxt.tdd.util;

import com.bunnyxt.tdd.model.video.record.rank.RankColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TddVideoRecordRankUtil {

    public static Map<String, Map<String, Double>> buildRankColorMap(List<RankColor> rankColorList) {
        Map<String, Map<String, Double>> rankColorMap = new HashMap<>();
        for (RankColor color : rankColorList) {
            Map<String, Double> valueMap = new HashMap<>();
            valueMap.put("a", color.getA());
            valueMap.put("b", color.getB());
            valueMap.put("c", color.getC());
            valueMap.put("d", color.getD());
            rankColorMap.put(color.getProperty(), valueMap);
        }
        return rankColorMap;
    }
}
