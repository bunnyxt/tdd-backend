package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.UpdateLog;
import com.bunnyxt.tdd.service.UpdateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UpdateLogRestController {

    @Autowired
    UpdateLogService updateLogService;

    @RequestMapping(value = "/updatelog", method = RequestMethod.GET)
    public List<UpdateLog> queryStatDailys(@RequestParam(defaultValue = "5") Integer last_count)
            throws InvalidRequestParameterException {
        // check params
        if (last_count < 0) {
            throw new InvalidRequestParameterException("last_count", last_count, "last_count should not be negative");
        }

        return updateLogService.queryUpdateLogs(last_count);
    }
}
