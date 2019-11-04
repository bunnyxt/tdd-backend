package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.model.VideoRecord;
import com.bunnyxt.tdd.service.VideoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VideoRecordRestController {

    @Autowired
    private VideoRecordService videoRecordService;

    @RequestMapping(value = "/video/records/aid/{aid}", method = RequestMethod.GET)
    public List<VideoRecord> queryVideoByAid(@PathVariable int aid) {
        return videoRecordService.queryVideoRecordsByAid(aid);
    }

}
