package com.bunnyxt.tdd.controller.video.record.rank;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.video.record.rank.RankArchiveOverview;
import com.bunnyxt.tdd.service.video.record.rank.RankArchiveOverviewService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RankArchiveOverviewRestController {

    @Autowired
    RankArchiveOverviewService rankArchiveOverviewService;

    @RequestMapping(value = "/video/record/rank/{rank_name}/archive/overview", method = RequestMethod.GET)
    public ResponseEntity<List<RankArchiveOverview>> queryRankArchiveOverviews(
            @PathVariable String rank_name
    ) {
        // check params
        TddParamCheckUtil.rank_name(rank_name);

        return TddResponseUtil.SetMaxAge(
                rankArchiveOverviewService.queryRankArchiveOverviews(rank_name),
                300  // 5 min
        );
    }

    @RequestMapping(value = "/video/record/rank/{rank_name}/archive/{arch_id}/overview", method = RequestMethod.GET)
    public ResponseEntity<RankArchiveOverview> queryRankArchiveOverviewByArchId(
            @PathVariable String rank_name,
            @PathVariable Long arch_id
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.rank_name(rank_name);
        TddParamCheckUtil.arch_id(arch_id);

        return TddResponseUtil.SetMaxAge(
                rankArchiveOverviewService.queryRankArchiveOverviewByArchId(rank_name, arch_id),
                86400  // 1 day
        );
    }
}
