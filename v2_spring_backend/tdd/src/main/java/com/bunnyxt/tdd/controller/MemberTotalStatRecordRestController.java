package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.MemberTotalStatRecord;
import com.bunnyxt.tdd.service.MemberTotalStatRecordService;
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
public class MemberTotalStatRecordRestController {

    @Autowired
    MemberTotalStatRecordService memberTotalStatRecordService;

    @RequestMapping(value = "/member/{mid}/totalstat", method = RequestMethod.GET)
    public ResponseEntity<List<MemberTotalStatRecord>> queryMemberTotalStatRecordsByAid(
            @PathVariable Integer mid,
            @RequestParam(defaultValue = "0") Integer last_count,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "25000") Integer ps
    ) throws InvalidRequestParameterException {
        return queryMemberTotalStatRecords(mid, last_count, start_ts, end_ts, pn, ps);
    }

    @RequestMapping(value = "/totalstat", method = RequestMethod.GET)
    public ResponseEntity<List<MemberTotalStatRecord>> queryMemberTotalStatRecords(
            @RequestParam(defaultValue = "0") Integer mid,
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
                memberTotalStatRecordService.queryMemberTotalStatRecords(mid, last_count, start_ts, end_ts, pn, ps),
                memberTotalStatRecordService.queryMemberTotalStatRecordsCount(mid, start_ts, end_ts)
        );
    }
}
