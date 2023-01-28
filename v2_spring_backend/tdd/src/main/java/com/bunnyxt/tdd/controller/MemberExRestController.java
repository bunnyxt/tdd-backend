package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.dao.MemberMidDao;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.MemberEx;
import com.bunnyxt.tdd.service.MemberExService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CrossOrigin
@RestController
public class MemberExRestController {

    @Autowired
    private MemberExService memberExService;

    @Autowired
    private MemberMidDao memberMidDao;

    @RequestMapping(value = "/member/{mid}", method = RequestMethod.GET)
    public MemberEx queryMemberByMid(
            @PathVariable Long mid
    ) {
        return memberExService.queryMemberByMid(mid);
    }

    @RequestMapping(value = "/member", method = RequestMethod.GET)
    public ResponseEntity<List<MemberEx>> queryMembers(
            @RequestParam(defaultValue = "") String sex,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "sr_view") String order_by,
            @RequestParam(defaultValue = "1") Integer desc,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "20") Integer ps
    ) throws InvalidRequestParameterException {
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
        TddParamCheckUtil.desc(desc);
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 20);

        return TddResponseUtil.AssembleList(
                memberExService.queryMembers(sex, name, order_by, desc, pn, ps),
                memberExService.queryMembersCount(sex, name)
        );
    }

    @RequestMapping(value = "/member/random", method = RequestMethod.GET)
    public List<MemberEx> queryVideosRandom(
            @RequestParam(defaultValue = "1") Long count
    ) throws InvalidRequestParameterException {
        // check params
        if (count <= 0 || count > 20) {
            throw new InvalidRequestParameterException("count", count, "count should between 1 and 20");
        }

        // get max id
        Long maxId = memberMidDao.queryMemberMidMaxId();

        // generate random ids
        if (count > maxId) {
            count = maxId;
        }
        List<Long> randomIds = new ArrayList<>();
        Random random = new Random();
        for (long i = 0; i < count; i++) {
            Long randomId = random.nextLong() % maxId + 1;
            if (randomId > 0 && !randomIds.contains(randomId)) {
                randomIds.add(randomId);
            } else {
                i--;
            }
        }

        // get random aids
        List<Long> randomMids = memberMidDao.queryMemberMidsByIds(randomIds);

        // get member ex list
        List<MemberEx> memberExList = new ArrayList<>();
        for (Long mid : randomMids) {
            memberExList.add(memberExService.queryMemberByMid(mid));
        }

        return memberExList;
    }

}
