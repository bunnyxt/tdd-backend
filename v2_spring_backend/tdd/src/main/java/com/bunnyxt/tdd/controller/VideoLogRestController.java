package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.VideoLog;
import com.bunnyxt.tdd.service.VideoLogService;
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
public class VideoLogRestController {

    @Autowired
    VideoLogService videoLogService;

    @RequestMapping(value = "/video/log", method = RequestMethod.GET)
    public ResponseEntity<List<VideoLog>> queryVideoLogs(
            @RequestParam(defaultValue = "0") Long aid,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "") String attr,
            @RequestParam(defaultValue = "") String oldval,
            @RequestParam(defaultValue = "") String newval,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "20") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        if (aid < 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 20);

        return TddResponseUtil.AssembleList(
                videoLogService.queryVideoLogs(aid, start_ts, end_ts, attr, oldval, newval, pn, ps),
                videoLogService.queryVideoLogsCount(aid, start_ts, end_ts, attr, oldval, newval)
        );
    }

}
