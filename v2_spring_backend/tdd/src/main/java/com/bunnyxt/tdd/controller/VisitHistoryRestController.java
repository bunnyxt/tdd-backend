package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.auth.TddAuthUtil;
import com.bunnyxt.tdd.model.user.User;
import com.bunnyxt.tdd.service.VisitHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class VisitHistoryRestController {

    @Autowired
    VisitHistoryService visitHistoryService;

    @RequestMapping(value = "/visit/history/video/BV{bvid}", method = RequestMethod.POST)
    public void addVisitHistoryVideo(@PathVariable String bvid) {
        User currentUser = TddAuthUtil.GetCurrentUser();
        Long userid = 3L;
        if (currentUser != null) {
            userid = currentUser.getId();
        }
        visitHistoryService.addVisitHistoryVideo(userid, bvid);
    }
}
