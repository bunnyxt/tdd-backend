package com.bunnyxt.tdd.controller.video.record.rank;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.video.record.rank.RankArchive;
import com.bunnyxt.tdd.model.video.record.rank.RankArchiveEx;
import com.bunnyxt.tdd.service.video.record.rank.RankArchiveService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class RankArchiveRestController {

    @Autowired
    private RankArchiveService rankArchiveService;

    @RequestMapping(value = "/video/record/rank/{rank_name}/archive/{arch_id}", method = RequestMethod.GET)
    public ResponseEntity<List<RankArchiveEx>> queryRankArchiveByArchId(
            @PathVariable String rank_name,
            @PathVariable Long arch_id,
            @RequestParam(defaultValue = "rank") String order_rule,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "30") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.rank_name(rank_name);
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
                rankArchiveService.queryRankArchiveExsByArchId(rank_name, arch_id, order_rule, pn, ps),
                rankArchiveService.queryRankArchiveExsCountByArchId(rank_name, arch_id)
        );
    }

    @RequestMapping(value = "/video/record/rank/{rank_name}/archive/BV{bvid}", method = RequestMethod.GET)
    public ResponseEntity<List<RankArchive>> queryRankArchivesByBvid(
            @PathVariable String rank_name,
            @PathVariable String bvid
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.rank_name(rank_name);
        TddParamCheckUtil.bvid(bvid);

        // TODO add max age
        return TddResponseUtil.AssembleList(
                rankArchiveService.queryRankArchivesByBvid(rank_name, bvid),
                rankArchiveService.queryRankArchivesCountByBvid(rank_name, bvid)
        );
    }
}
