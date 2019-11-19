package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.model.Member;
import com.bunnyxt.tdd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Member> queryMembers(@RequestParam(defaultValue = "") String sex,
                                     @RequestParam(defaultValue = "") String name,
                                     @RequestParam(defaultValue = "1") int pn,
                                     @RequestParam(defaultValue = "20") int ps) {
        return memberService.queryMembers(sex, name, pn, ps);
    }
}
