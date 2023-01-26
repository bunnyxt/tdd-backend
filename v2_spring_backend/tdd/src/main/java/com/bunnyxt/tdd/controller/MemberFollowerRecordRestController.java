package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.MemberFollowerRecord;
import com.bunnyxt.tdd.service.MemberFollowerRecordService;
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
public class MemberFollowerRecordRestController {

    @Autowired
    MemberFollowerRecordService memberFollowerRecordService;

    @RequestMapping(value = "/member/{mid}/follower", method = RequestMethod.GET)
    public ResponseEntity<List<MemberFollowerRecord>> queryMemberFollowerRecordsByAid(
            @PathVariable Long mid,
            @RequestParam(defaultValue = "0") Integer last_count,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "25000") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.last_count(last_count, 5000);

        return queryMemberFollowerRecords(mid, last_count, start_ts, end_ts, pn, ps);
    }

    @RequestMapping(value = "/follower", method = RequestMethod.GET)
    public ResponseEntity<List<MemberFollowerRecord>> queryMemberFollowerRecords(
            @RequestParam(defaultValue = "0") Long mid,
            Integer last_count,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "25000") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        if (mid < 0) {
            throw new InvalidRequestParameterException("mid", mid, "mid should be greater than 0");
        }
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 25000);

        return TddResponseUtil.AssembleList(
                memberFollowerRecordService.queryMemberFollowerRecords(mid, last_count, start_ts, end_ts, pn, ps),
                memberFollowerRecordService.queryMemberFollowerRecordsCount(mid, start_ts, end_ts)
        );
    }
}
