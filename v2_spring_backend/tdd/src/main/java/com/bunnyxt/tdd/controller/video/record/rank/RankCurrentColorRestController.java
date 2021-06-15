package com.bunnyxt.tdd.controller.video.record.rank;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.video.record.rank.RankColor;
import com.bunnyxt.tdd.service.video.record.rank.RankCurrentColorService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import com.bunnyxt.tdd.util.TddVideoRecordRankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class RankCurrentColorRestController {

    @Autowired
    private RankCurrentColorService rankCurrentColorService;

    @RequestMapping(value = "/video/record/rank/{rank_name}/current/color", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Map<String, Double>>> queryRankCurrentColors(
            @PathVariable String rank_name
    )
            throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.rank_name(rank_name);

        return TddResponseUtil.SetMaxAge(
                TddVideoRecordRankUtil.buildRankColorMap(
                        rankCurrentColorService.queryRankCurrentColors(rank_name)
                ),
                300  // 5 min
        );
    }
}
