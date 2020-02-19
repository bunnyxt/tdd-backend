package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.MemberFollowerRecord;
import com.bunnyxt.tdd.service.MemberFollowerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class MemberFollowerRecordController {

    @Autowired
    MemberFollowerRecordService memberFollowerRecordService;

    @RequestMapping(value = "/member/{mid}/follower", method = RequestMethod.GET)
    public ResponseEntity<List<MemberFollowerRecord>> queryMemberFollowerRecordsByAid(@PathVariable Integer mid,
                                                                                      @RequestParam(defaultValue = "0") Integer start_ts,
                                                                                      @RequestParam(defaultValue = "0") Integer end_ts,
                                                                                      @RequestParam(defaultValue = "1") Integer pn,
                                                                                      @RequestParam(defaultValue = "25000") Integer ps)
            throws InvalidRequestParameterException {
        return queryMemberFollowerRecords(mid, start_ts, end_ts, pn, ps);
    }

    @RequestMapping(value = "/follower", method = RequestMethod.GET)
    public ResponseEntity<List<MemberFollowerRecord>> queryMemberFollowerRecords(@RequestParam(defaultValue = "0") Integer mid,
                                                                                 @RequestParam(defaultValue = "0") Integer start_ts,
                                                                                 @RequestParam(defaultValue = "0") Integer end_ts,
                                                                                 @RequestParam(defaultValue = "1") Integer pn,
                                                                                 @RequestParam(defaultValue = "25000") Integer ps)
            throws InvalidRequestParameterException {
        // check params
        if (mid < 0) {
            throw new InvalidRequestParameterException("mid", mid, "aid should be greater than 0");
        }
        if (pn <= 0) {
            throw new InvalidRequestParameterException("pn", pn, "pn should be greater than 0");
        }
        if (ps <= 0 || ps > 25000) {
            throw new InvalidRequestParameterException("ps", ps, "ps should between 1 and 25000");
        }

        // get list
        List<MemberFollowerRecord> list = memberFollowerRecordService.queryMemberFollowerRecords(mid, start_ts, end_ts, pn, ps);

        // get total count
        Integer totalCount = memberFollowerRecordService.queryMemberFollowerRecordsCount(mid, start_ts, end_ts);

        // add headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(totalCount));
        headers.add("Access-Control-Allow-Headers", "x-total-count");
        headers.add("Access-Control-Expose-Headers", "x-total-count");
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }
}
