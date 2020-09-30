package com.bunnyxt.tdd.controller.video.record.rank;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchiveOverview;
import com.bunnyxt.tdd.service.video.record.rank.WeeklyArchiveOverviewService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class WeeklyArchiveOverviewRestController {

    @Autowired
    WeeklyArchiveOverviewService weeklyArchiveOverviewService;

    @RequestMapping(value = "/video/record/rank/weekly/archive/overview", method = RequestMethod.GET)
    public ResponseEntity<List<WeeklyArchiveOverview>> queryWeeklyArchiveOverviews() {
        return TddResponseUtil.SetMaxAge(
                weeklyArchiveOverviewService.queryWeeklyArchiveOverviews(),
                300  // 5 min
        );
    }

    @RequestMapping(value = "/video/record/rank/weekly/archive/{arch_id}/overview", method = RequestMethod.GET)
    public ResponseEntity<WeeklyArchiveOverview> queryWeeklyArchiveOverviewByArchId(
            @PathVariable Long arch_id
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.arch_id(arch_id);

        return TddResponseUtil.SetMaxAge(
                weeklyArchiveOverviewService.queryWeeklyArchiveOverviewByArchId(arch_id),
                86400  // 1 day
        );
    }
}
