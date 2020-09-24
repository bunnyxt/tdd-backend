package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.auth.TddAuthUtil;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.VisitHistoryVideoEx;
import com.bunnyxt.tdd.model.user.User;
import com.bunnyxt.tdd.service.VisitHistoryService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class VisitHistoryRestController {

    @Autowired
    VisitHistoryService visitHistoryService;

    // video ===========================================================================================================

    @RequestMapping(value = "/visit/history/video/BV{bvid}", method = RequestMethod.POST)
    public void addVisitHistoryVideo(
            @PathVariable String bvid
    ) {
        User currentUser = TddAuthUtil.GetCurrentUser();
        Long userid = 3L;
        if (currentUser != null) {
            userid = currentUser.getId();
        }
        visitHistoryService.addVisitHistoryVideo(userid, bvid);
    }

    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/visit/history/video/me", method = RequestMethod.GET)
    public ResponseEntity<List<VisitHistoryVideoEx>> getVisitHistoryVideoMe(
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "1") Integer desc,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "20") Integer ps
    ) throws InvalidRequestParameterException {
        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return getVisitHistoryVideo(userid, start_ts, end_ts, desc, pn, ps);
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/visit/history/video/tdduser{userid}", method = RequestMethod.GET)
    public ResponseEntity<List<VisitHistoryVideoEx>> getVisitHistoryVideo(
            @PathVariable Long userid,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "1") Integer desc,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "20") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.start_ts(start_ts);
        TddParamCheckUtil.end_ts(end_ts);
        TddParamCheckUtil.desc(desc);
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 20);

        return TddResponseUtil.AssembleList(
                visitHistoryService.getVisitHistoryVideoByUserid(userid, start_ts, end_ts, desc, pn, ps),
                visitHistoryService.getVisitHistoryVideoCountByUserid(userid, start_ts, end_ts)
        );
    }
}
