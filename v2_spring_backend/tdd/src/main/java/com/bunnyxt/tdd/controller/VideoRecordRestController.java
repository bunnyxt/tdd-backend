package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.VideoRecord;
import com.bunnyxt.tdd.service.VideoRecordService;
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
public class VideoRecordRestController {

    @Autowired
    private VideoRecordService videoRecordService;

    @RequestMapping(value = "/video/{aid}/record", method = RequestMethod.GET)
    public ResponseEntity<List<VideoRecord>> queryVideoRecordsByAid(
            @PathVariable Long aid,
            @RequestParam(defaultValue = "0") Integer last_count,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "50000") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.last_count(last_count, 5000);

        return queryVideoRecords(aid, last_count, start_ts, end_ts, pn, ps);
    }

    @RequestMapping(value = "/record", method = RequestMethod.GET)
    public ResponseEntity<List<VideoRecord>> queryVideoRecords(
            @RequestParam(defaultValue = "0") Long aid,
            Integer last_count,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "50000") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        if (aid < 0) {
            // 0 -> not set
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 50000);

        return TddResponseUtil.AssembleList(
                videoRecordService.queryVideoRecords(aid, last_count, start_ts, end_ts, true, pn, ps),
                videoRecordService.queryVideoRecordsCount(aid, start_ts, end_ts)
        );
    }

}
