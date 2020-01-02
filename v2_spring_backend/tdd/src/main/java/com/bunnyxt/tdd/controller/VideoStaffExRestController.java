package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.VideoStaffEx;
import com.bunnyxt.tdd.service.VideoStaffExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.InvalidPropertiesFormatException;
import java.util.List;

@CrossOrigin
@RestController
public class VideoStaffExRestController {

    @Autowired
    private VideoStaffExService videoStaffExService;

    @RequestMapping(value = "/video/{aid}/staff", method = RequestMethod.GET)
    public List<VideoStaffEx> queryVideoStaffsByAid(@PathVariable int aid)
            throws InvalidPropertiesFormatException {
        // check params
        if (aid < 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }

        return videoStaffExService.queryVideoStaffsByAid(aid);
    }
}
