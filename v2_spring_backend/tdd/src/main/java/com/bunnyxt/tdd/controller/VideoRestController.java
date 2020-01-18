package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.Video;
import com.bunnyxt.tdd.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
public class VideoRestController {

    @Autowired
    private VideoService videoService;

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/video/{aid}", method = RequestMethod.PUT)
    public Video updateVideoByAid(@PathVariable int aid, @RequestBody Video video)
            throws InvalidRequestParameterException {
        // when use PUT method, you must provide all parameters of video, since all params will be updated
        // if you only want to change one param, use PATCH method instead

        // check params
        if (aid != video.getAid()) {
            throw new InvalidRequestParameterException("aid", aid,
                    "aid should be same as video.aid provided in request body, which is " + video.getAid());
        }

        videoService.updateVideoByAid(aid, video);

        return videoService.queryVideoByAid(aid);
    }

}
