package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.MemberLog;
import com.bunnyxt.tdd.service.MemberLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class MemberLogRestController {

    @Autowired
    MemberLogService memberLogService;

    @RequestMapping(value = "/member/log", method = RequestMethod.GET)
    public ResponseEntity<List<MemberLog>> queryMemberLogs(@RequestParam(defaultValue = "0") Integer mid,
                                                           @RequestParam(defaultValue = "0") Integer start_ts,
                                                           @RequestParam(defaultValue = "0") Integer end_ts,
                                                           @RequestParam(defaultValue = "") String attr,
                                                           @RequestParam(defaultValue = "") String oldval,
                                                           @RequestParam(defaultValue = "") String newval,
                                                           @RequestParam(defaultValue = "1") Integer pn,
                                                           @RequestParam(defaultValue = "20") Integer ps)
            throws InvalidRequestParameterException {
        // check params
        if (mid < 0) {
            throw new InvalidRequestParameterException("mid", mid, "mid should be greater than 0");
        }
        if (pn <= 0) {
            throw new InvalidRequestParameterException("pn", pn, "pn should be greater than 0");
        }
        if (ps <= 0 || ps > 20) {
            throw new InvalidRequestParameterException("ps", ps, "ps should between 1 and 20");
        }

        // get list
        List<MemberLog> list = memberLogService.queryMemberLogs(mid, start_ts, end_ts, attr, oldval, newval, pn, ps);

        // get total count
        Integer totalCount = memberLogService.queryMemberLogsCount(mid, start_ts, end_ts, attr, oldval, newval);

        // add headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(totalCount));
        headers.add("Access-Control-Allow-Headers", "x-total-count");
        headers.add("Access-Control-Expose-Headers", "x-total-count");
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }

}
