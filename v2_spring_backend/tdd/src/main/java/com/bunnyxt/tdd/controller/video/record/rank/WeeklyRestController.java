package com.bunnyxt.tdd.controller.video.record.rank;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyCurrentEx;
import com.bunnyxt.tdd.service.video.record.rank.WeeklyService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class WeeklyRestController {

    @Autowired
    private WeeklyService weeklyService;

    @RequestMapping(value = "/video/record/rank/weekly/current", method = RequestMethod.GET)
    public ResponseEntity<List<WeeklyCurrentEx>> queryWeeklyCurrents(@RequestParam(defaultValue = "rank") String order_rule,
                                                                     @RequestParam(defaultValue = "1") Integer pn,
                                                                     @RequestParam(defaultValue = "20") Integer ps)
            throws InvalidRequestParameterException {
        // check params
        List<String> allowedOrderRule = new ArrayList<String>(){{
            add("rank");
            add("incr_view");
        }};
        if (!allowedOrderRule.contains(order_rule)) {
            throw new InvalidRequestParameterException("order_rule", order_rule,
                    "only support order by " + allowedOrderRule.toString());
        }
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 20);

        // get list
        List<WeeklyCurrentEx> list = weeklyService.queryWeeklyCurrentExs(order_rule, pn, ps);

        // get total count
        Integer totalCount = weeklyService.queryWeeklyCurrentExsCount();

        return TddResponseUtil.AssembleList(list, totalCount);
    }
}
