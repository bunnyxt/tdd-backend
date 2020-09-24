package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.StatDaily;
import com.bunnyxt.tdd.service.StatDailyService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class StatDailyRestController {

    @Autowired
    StatDailyService statDailyService;

    @RequestMapping(value = "/statdaily", method = RequestMethod.GET)
    public ResponseEntity<List<StatDaily>> queryStatDailys(
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "25000") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 25000);

        return TddResponseUtil.AssembleList(
                statDailyService.queryStatDailys(start_ts, end_ts, pn, ps),
                statDailyService.queryStatDailysCount(start_ts, end_ts)
        );
    }
}
