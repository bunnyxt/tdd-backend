package com.bunnyxt.tdd.controller.video.record.rank;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.video.record.rank.RankCurrent;
import com.bunnyxt.tdd.model.video.record.rank.RankCurrentEx;
import com.bunnyxt.tdd.service.video.record.rank.RankCurrentService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class RankCurrentRestController {

    @Autowired
    private RankCurrentService rankCurrentService;

    @RequestMapping(value = "/video/record/rank/{rank_name}/current", method = RequestMethod.GET)
    public ResponseEntity<List<RankCurrentEx>> queryRankCurrentExs(
            @PathVariable String rank_name,
            @RequestParam(defaultValue = "rank") String order_rule,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "30") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        List<String> allowedRankName = new ArrayList<String>(){{
            add("weekly");
            add("monthly");
        }};
        if (!allowedRankName.contains(rank_name)) {
            throw new InvalidRequestParameterException("rank_name", rank_name,
                    "only support rank name " + allowedRankName.toString());
        }
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
                rankCurrentService.queryRankCurrentExs(rank_name, order_rule, pn, ps),
                rankCurrentService.queryRankCurrentExsCount(rank_name)
        );
    }

    @RequestMapping(value = "/video/record/rank/{rank_name}/current/BV{bvid}", method = RequestMethod.GET)
    public RankCurrent queryRankCurrentByBvid(
            @PathVariable String rank_name,
            @PathVariable String bvid
    ) throws InvalidRequestParameterException {
        // check params
        List<String> allowedRankName = new ArrayList<String>(){{
            add("weekly");
            add("monthly");
        }};
        if (!allowedRankName.contains(rank_name)) {
            throw new InvalidRequestParameterException("rank_name", rank_name,
                    "only support rank name " + allowedRankName.toString());
        }
        TddParamCheckUtil.bvid(bvid);

        return rankCurrentService.queryRankCurrentByBvid(rank_name, bvid);
    }
}
