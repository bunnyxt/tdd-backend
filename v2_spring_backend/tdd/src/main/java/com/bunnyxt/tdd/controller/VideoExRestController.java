package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.model.VideoEx;
import com.bunnyxt.tdd.service.VideoExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class VideoExRestController {

    @Autowired
    private VideoExService videoExService;

    @RequestMapping(value = "/video/{aid}", method = RequestMethod.GET)
    public VideoEx queryVideoByAid(@PathVariable int aid) {
        return videoExService.queryVideoByAid(aid);
    }

    @RequestMapping(value = "/video", method = RequestMethod.GET)
    public ResponseEntity<List<VideoEx>> queryVideos(@RequestParam(defaultValue = "0") int vc,
                                              @RequestParam(defaultValue = "0") int start_ts,
                                              @RequestParam(defaultValue = "0") int end_ts,
                                              @RequestParam(defaultValue = "") String title,
                                              @RequestParam(defaultValue = "") String up,
                                              @RequestParam(defaultValue = "1") int pn,
                                              @RequestParam(defaultValue = "20") int ps) {
        List<VideoEx> list = videoExService.queryVideos(vc, start_ts, end_ts, title, up, pn, ps);
        int totalCount = videoExService.queryVideosCount(vc, start_ts, end_ts, title, up);
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(totalCount));
        headers.add("Access-Control-Allow-Headers", "x-total-count");
        headers.add("Access-Control-Expose-Headers", "x-total-count");
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }
}
