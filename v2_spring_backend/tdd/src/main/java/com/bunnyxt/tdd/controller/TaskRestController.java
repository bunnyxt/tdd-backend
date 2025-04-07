package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class TaskRestController {

    @Autowired
    TaskService taskService;

    // deprecated
    @RequestMapping(value = "/task/visit/video/{aid}/record", method = RequestMethod.POST)
    public void addVisitVideoRecord(@PathVariable Long aid) {
//        Long userid = 3L;  // TODO set user role only and change to real userid
//        taskService.addVisitVideoRecord(aid, userid);
    }
}
