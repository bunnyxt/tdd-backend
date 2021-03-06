package com.bunnyxt.tdd.controller.video.record.rank;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyCurrent;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyCurrentEx;
import com.bunnyxt.tdd.service.video.record.rank.WeeklyCurrentService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class WeeklyCurrentRestController {

    @Autowired
    private WeeklyCurrentService weeklyCurrentService;

    @RequestMapping(value = "/video/record/rank/weekly/current", method = RequestMethod.GET)
    public ResponseEntity<List<WeeklyCurrentEx>> queryWeeklyCurrentExs(
            @RequestParam(defaultValue = "rank") String order_rule,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "30") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        List<String> allowedOrderRule = new ArrayList<String>(){{
            add("rank");
            add("incr_view");
            add("incr_danmaku");
            add("incr_reply");
            add("incr_favorite");
            add("incr_coin");
            add("incr_share");
            add("incr_like");
        }};
        if (!allowedOrderRule.contains(order_rule)) {
            throw new InvalidRequestParameterException("order_rule", order_rule,
                    "only support order by " + allowedOrderRule.toString());
        }
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 30);

        // TODO add max age
        return TddResponseUtil.AssembleList(
                weeklyCurrentService.queryWeeklyCurrentExs(order_rule, pn, ps),
                weeklyCurrentService.queryWeeklyCurrentExsCount()
        );
    }

    @RequestMapping(value = "/video/record/rank/weekly/current/BV{bvid}", method = RequestMethod.GET)
    public WeeklyCurrent queryWeeklyCurrentByBvid(
            @PathVariable String bvid
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.bvid(bvid);

        return weeklyCurrentService.queryWeeklyCurrentByBvid(bvid);
    }
}
