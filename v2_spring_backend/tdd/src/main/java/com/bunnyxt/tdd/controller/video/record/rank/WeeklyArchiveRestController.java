package com.bunnyxt.tdd.controller.video.record.rank;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchive;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchiveEx;
import com.bunnyxt.tdd.service.video.record.rank.WeeklyArchiveService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class WeeklyArchiveRestController {

    @Autowired
    private WeeklyArchiveService weeklyArchiveService;

    @RequestMapping(value = "/video/record/rank/weekly/archive/{arch_id}", method = RequestMethod.GET)
    public ResponseEntity<List<WeeklyArchiveEx>> queryWeeklyArchiveByArchId(
            @PathVariable Long arch_id,
            @RequestParam(defaultValue = "rank") String order_rule,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "30") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.arch_id(arch_id);
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
                weeklyArchiveService.queryWeeklyArchiveExsByArchId(arch_id, order_rule, pn, ps),
                weeklyArchiveService.queryWeeklyArchiveExsCountByArchId(arch_id)
        );
    }

    @RequestMapping(value = "/video/record/rank/weekly/archive/BV{bvid}", method = RequestMethod.GET)
    public ResponseEntity<List<WeeklyArchive>> queryWeeklyArchivesByBvid(
            @PathVariable String bvid
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.bvid(bvid);

        // TODO add max age
        return TddResponseUtil.AssembleList(
                weeklyArchiveService.queryWeeklyArchivesByBvid(bvid),
                weeklyArchiveService.queryWeeklyArchivesCountByBvid(bvid)
        );
    }
}
