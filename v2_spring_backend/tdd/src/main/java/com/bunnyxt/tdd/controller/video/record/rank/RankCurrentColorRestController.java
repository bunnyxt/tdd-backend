package com.bunnyxt.tdd.controller.video.record.rank;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.video.record.rank.RankColor;
import com.bunnyxt.tdd.service.video.record.rank.RankCurrentColorService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class RankCurrentColorRestController {

    @Autowired
    private RankCurrentColorService rankCurrentColorService;

    private Map<String, Map<String, Double>> buildRankColorMap(List<RankColor> rankColorList) {
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

    @RequestMapping(value = "/video/record/rank/{rank_name}/current/color", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Map<String, Double>>> queryRankCurrentColors(
            @PathVariable String rank_name
    )
            throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.rank_name(rank_name);

        return TddResponseUtil.SetMaxAge(
                buildRankColorMap(
                        rankCurrentColorService.queryRankCurrentColors(rank_name)
                ),
                300  // 5 min
        );
    }

    @RequestMapping(value = "/video/record/rank/{rank_name}/archive/{arch_id}/color", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Map<String, Double>>> queryRankArchiveColorByArchId(
            @PathVariable String rank_name,
            @PathVariable Long arch_id
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.rank_name(rank_name);
        TddParamCheckUtil.arch_id(arch_id);

        return TddResponseUtil.SetMaxAge(
                buildRankColorMap(
                        rankCurrentColorService.queryRankArchiveColorByArchId(rank_name, arch_id)
                ),
                86400  // 1 day
        );
    }
}
