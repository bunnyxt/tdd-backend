package com.bunnyxt.tdd.controller.video.record.rank;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.service.video.record.rank.RankArchiveColorService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import com.bunnyxt.tdd.util.TddVideoRecordRankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
public class RankArchiveColorRestController {

    @Autowired
    private RankArchiveColorService rankArchiveColorService;

    @RequestMapping(value = "/video/record/rank/{rank_name}/archive/{arch_id}/color", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Map<String, Double>>> queryRankArchiveColorByArchId(
            @PathVariable String rank_name,
            @PathVariable Long arch_id
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.rank_name(rank_name);
        TddParamCheckUtil.arch_id(arch_id);

        return TddResponseUtil.SetMaxAge(
                TddVideoRecordRankUtil.buildRankColorMap(
                        rankArchiveColorService.queryRankArchiveColorByArchId(rank_name, arch_id)
                ),
                86400  // 1 day
        );
    }
}
