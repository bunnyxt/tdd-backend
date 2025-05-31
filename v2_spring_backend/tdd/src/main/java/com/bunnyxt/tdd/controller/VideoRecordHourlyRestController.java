package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.auth.TddRecaptchaAuthUtil;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.VideoRecord;
import com.bunnyxt.tdd.model.VideoRecordHourly;
import com.bunnyxt.tdd.service.VideoRecordHourlyService;
import com.bunnyxt.tdd.util.TddAbidUtil;
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
public class VideoRecordHourlyRestController {

    @Autowired
    private VideoRecordHourlyService videoRecordHourlyService;

    // full ver ========================================================================================================

    @RequestMapping(value = "/video/{aid}/record/hourly", method = RequestMethod.GET)
    public ResponseEntity<List<VideoRecordHourly>> queryVideoRecordHourlysByAid(
            @PathVariable Long aid,
            @RequestParam(defaultValue = "0") Integer last_count,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "50000") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.last_count(last_count, 5000);

        return queryVideoRecordHourlys(aid, "0000000000", last_count, start_ts, end_ts, pn, ps);
    }

    @RequestMapping(value = "/video/BV{bvid}/record/hourly", method = RequestMethod.GET)
    public ResponseEntity<List<VideoRecordHourly>> queryVideoRecordHourlysByBvid(
            @PathVariable String bvid,
            @RequestParam(defaultValue = "0") Integer last_count,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "50000") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.last_count(last_count, 5000);

        return queryVideoRecordHourlys(0L, bvid, last_count, start_ts, end_ts, pn, ps);
    }

    @RequestMapping(value = "/record/hourly", method = RequestMethod.GET)
    public ResponseEntity<List<VideoRecordHourly>> queryVideoRecordHourlys(
            @RequestParam(defaultValue = "0") Long aid,
            @RequestParam(defaultValue = "0000000000") String bvid,
            Integer last_count,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "50000") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        if (bvid.equals("0000000000")) {
            // bvid not assigned, check aid
            if (aid > 0) {
                // set bvid via aid
                bvid = TddAbidUtil.a2b(aid);
            } else if (aid < 0) {
                throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
            }
        }
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 50000);

        return TddResponseUtil.AssembleList(
                videoRecordHourlyService.queryVideoRecordHourlys(bvid, last_count, start_ts, end_ts, pn, ps),
                videoRecordHourlyService.queryVideoRecordHourlysCount(bvid, start_ts, end_ts)
        );
    }

    // count only ======================================================================================================

    @RequestMapping(value = "/video/{aid}/record/hourly/count", method = RequestMethod.GET)
    public Integer queryVideoRecordHourlysCountByAid(
            @PathVariable Long aid,
            @RequestParam(defaultValue = "0") Integer last_count,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.last_count(last_count, 5000);

        return queryVideoRecordHourlysCount(aid, "0000000000", last_count, start_ts, end_ts);
    }

    @RequestMapping(value = "/video/BV{bvid}/record/hourly/count", method = RequestMethod.GET)
    public Integer queryVideoRecordHourlysCountByBvid(
            @PathVariable String bvid,
            @RequestParam(defaultValue = "0") Integer last_count,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.last_count(last_count, 5000);

        return queryVideoRecordHourlysCount(0L, bvid, last_count, start_ts, end_ts);
    }

    @RequestMapping(value = "/record/hourly/count", method = RequestMethod.GET)
    public Integer queryVideoRecordHourlysCount(
            @RequestParam(defaultValue = "0") Long aid,
            @RequestParam(defaultValue = "0000000000") String bvid,
            Integer last_count,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts
    ) throws InvalidRequestParameterException {
        // check params
        if (bvid.equals("0000000000")) {
            // bvid not assigned, check aid
            if (aid > 0) {
                // set bvid via aid
                bvid = TddAbidUtil.a2b(aid);
            } else if (aid < 0) {
                throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
            }
        }

        return videoRecordHourlyService.queryVideoRecordHourlysCount(bvid, start_ts, end_ts);
    }
}
