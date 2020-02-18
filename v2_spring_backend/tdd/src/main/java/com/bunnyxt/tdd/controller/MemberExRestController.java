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
                                                       @RequestParam(defaultValue = "sr_view") String order_by,
                                                       @RequestParam(defaultValue = "1") Integer desc,
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
        List<String> allowedOrderBy = new ArrayList<String>(){{
            add("mid");
            add("video_count");
            add("v_pubdate");
//            add("r_view");
//            add("r_danmaku");
//            add("r_reply");
//            add("r_favorite");
//            add("r_coin");
//            add("r_share");
//            add("r_like");
            add("sr_view");
            add("sr_danmaku");
            add("sr_reply");
            add("sr_favorite");
            add("sr_coin");
            add("sr_share");
            add("sr_like");
            add("fr_follower");
        }};
        if (!allowedOrderBy.contains(order_by)) {
            throw new InvalidRequestParameterException("order_by", order_by,
                    "only support order by " + allowedOrderBy.toString());
        }
        if (desc != 0 && desc != 1) {
            throw new InvalidRequestParameterException("desc", desc, "desc should be 0 or 1");
        }
        if (pn <= 0) {
            throw new InvalidRequestParameterException("pn", pn, "pn should be greater than 0");
        }
        if (ps <= 0 || ps > 20) {
            throw new InvalidRequestParameterException("ps", ps, "ps should between 1 and 20");
        }

        // get list
        List<MemberEx> list = memberExService.queryMembers(sex, name, order_by, desc, pn, ps);

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
