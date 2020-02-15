package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.MemberEx;
import com.bunnyxt.tdd.service.MemberExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class MemberExRestController {

    @Autowired
    private MemberExService memberExService;

    @RequestMapping(value = "/member/{mid}", method = RequestMethod.GET)
    public MemberEx queryMemberByMid(@PathVariable Integer mid) {
        return memberExService.queryMemberByMid(mid);
    }

    @RequestMapping(value = "/member", method = RequestMethod.GET)
    public ResponseEntity<List<MemberEx>> queryMembers(@RequestParam(defaultValue = "") String sex,
                                                       @RequestParam(defaultValue = "") String name,
                                                       @RequestParam(defaultValue = "1") Integer pn,
                                                       @RequestParam(defaultValue = "20") Integer ps)
            throws InvalidRequestParameterException {
        // check params
        List<String> allowedSex = new ArrayList<String>(){{
            add("男");
            add("女");
            add("保密");
        }};
        if (!sex.equals("")) {
            if (!allowedSex.contains(sex)) {
                throw new InvalidRequestParameterException("sex", sex, "sex should in " + allowedSex.toString());
            }
        }
        if (pn <= 0) {
            throw new InvalidRequestParameterException("pn", pn, "pn should be greater than 0");
        }
        if (ps <= 0 || ps > 20) {
            throw new InvalidRequestParameterException("ps", ps, "ps should between 1 and 20");
        }

        // get list
        List<MemberEx> list = memberExService.queryMembers(sex, name, pn, ps);

        // get total count
        Integer totalCount = memberExService.queryMembersCount(sex, name);

        // add headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(totalCount));
        headers.add("Access-Control-Allow-Headers", "x-total-count");
        headers.add("Access-Control-Expose-Headers", "x-total-count");
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }

}
