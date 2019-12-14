package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.Member;
import com.bunnyxt.tdd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class MemberRestController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/member/{mid}", method = RequestMethod.GET)
    public Member queryMemberByMid(@PathVariable int mid) {
        return memberService.queryMemberByMid(mid);
    }

    @RequestMapping(value = "/member", method = RequestMethod.GET)
    public ResponseEntity<List<Member>> queryMembers(@RequestParam(defaultValue = "") String sex,
                                                     @RequestParam(defaultValue = "") String name,
                                                     @RequestParam(defaultValue = "1") int pn,
                                                     @RequestParam(defaultValue = "20") int ps)
            throws InvalidRequestParameterException {
        // check params
        List<String> allowedSex = new ArrayList<>();
        allowedSex.add("男");
        allowedSex.add("女");
        allowedSex.add("保密");
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
        List<Member> list = memberService.queryMembers(sex, name, pn, ps);

        // get total count
        int totalCount = memberService.queryMembersCount(sex, name);

        // add headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(totalCount));
        headers.add("Access-Control-Allow-Headers", "x-total-count");
        headers.add("Access-Control-Expose-Headers", "x-total-count");
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }
}
