package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.VideoEx;
import com.bunnyxt.tdd.service.VideoExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
                                                     @RequestParam(defaultValue = "pubdate") String order_by,
                                                     @RequestParam(defaultValue = "1") int desc,
                                                     @RequestParam(defaultValue = "1") int pn,
                                                     @RequestParam(defaultValue = "20") int ps)
            throws InvalidRequestParameterException {
        // check params
        if (vc != 0 && vc != 1) {
            throw new InvalidRequestParameterException("vc", vc, "vc should be 0 or 1");
        }
        if (desc != 0 && desc != 1) {
            throw new InvalidRequestParameterException("desc", desc, "desc should be 0 or 1");
        }
        List<String> allowedOrderBy = new ArrayList<>();
        allowedOrderBy.add("pubdate");
        allowedOrderBy.add("view");
        allowedOrderBy.add("danmaku");
        allowedOrderBy.add("reply");
        allowedOrderBy.add("favorite");
        allowedOrderBy.add("coin");
        allowedOrderBy.add("share");
        allowedOrderBy.add("like");
        if (!allowedOrderBy.contains(order_by)) {
            throw new InvalidRequestParameterException("order_by", order_by,
                    "only support order by " + allowedOrderBy.toString());
        }
        if (pn <= 0) {
            throw new InvalidRequestParameterException("pn", pn, "pn should be greater than 0");
        }
        if (ps <= 0 || ps > 20) {
            throw new InvalidRequestParameterException("ps", ps, "ps should between 1 and 20");
        }

        // get list
        List<VideoEx> list = videoExService.queryVideos(vc, start_ts, end_ts, title, up, order_by, desc, pn, ps);

        // get total count
        int totalCount = videoExService.queryVideosCount(vc, start_ts, end_ts, title, up);

        // add headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", String.valueOf(totalCount));
        headers.add("Access-Control-Allow-Headers", "x-total-count");
        headers.add("Access-Control-Expose-Headers", "x-total-count");
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }
}
