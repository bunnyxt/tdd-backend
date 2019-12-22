package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.VideoStaff;
import com.bunnyxt.tdd.service.VideoStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.InvalidPropertiesFormatException;
import java.util.List;

@CrossOrigin
@RestController
public class VideoStaffRestController {

    @Autowired
    private VideoStaffService videoStaffService;

    @RequestMapping(value = "/video/{aid}/staff", method = RequestMethod.GET)
    public List<VideoStaff> queryVideoStaffsByAid(@PathVariable int aid)
            throws InvalidPropertiesFormatException {
        // check params
        if (aid < 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }

        return videoStaffService.queryVideoStaffsByAid(aid);
    }

}
