package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.model.DonateLog;
import com.bunnyxt.tdd.service.DonateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class DonateLogRestController {

    @Autowired
    DonateLogService donateLogService;

    @RequestMapping(value = "/donatelog", method = RequestMethod.GET)
    public List<DonateLog> queryDonateLogs() {
        return donateLogService.queryDonateLogs();
    }
}
