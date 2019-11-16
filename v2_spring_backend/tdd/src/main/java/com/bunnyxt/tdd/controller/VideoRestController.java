package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.model.Video;
import com.bunnyxt.tdd.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class VideoRestController {

    @Autowired
    private VideoService videoService;

    @RequestMapping(value = "/video/aid/{aid}", method = RequestMethod.GET)
    public Video queryVideoByAid(@PathVariable int aid) {
        return videoService.queryVideoByAid(aid);
    }

    @RequestMapping(value = "/videos/tid/{tid}", method = RequestMethod.GET)
    public List<Video> queryVideosByTid(@PathVariable int tid,
                                        @RequestParam(defaultValue = "1") int pn,
                                        @RequestParam(defaultValue = "50") int ps) {
        return videoService.queryVideosByTid(tid, pn, ps);
    }

    @RequestMapping(value = "/videos/vc", method = RequestMethod.GET)
    public List<Video> queryVideosVc(@RequestParam(defaultValue = "1") int pn,
                                     @RequestParam(defaultValue = "50") int ps) {
        return videoService.queryVideosVc(pn, ps);
    }


}
