package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.Video;
import com.bunnyxt.tdd.service.VideoService;
import com.bunnyxt.tdd.util.ObjUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin
@RestController
public class VideoRestController {

    @Autowired
    private VideoService videoService;

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/video/{aid}", method = RequestMethod.PUT)
    public Video updateVideoByAid(
            @PathVariable Long aid,
            @RequestBody Video video
    ) throws InvalidRequestParameterException, IllegalAccessException {
        // when use PUT method, you could provide multiple parameters of video,
        // then all provided params will be updated, if they are allowed to be updated

//        // check params
//        if (!aid.equals(video.getAid())) {
//            throw new InvalidRequestParameterException("aid", aid,
//                    "aid should be same as video.aid provided in request body, which is " + video.getAid());
//        }

//        System.out.println(ObjUtil.objToMap(video));

        videoService.updateVideoByAid(aid, video);

        return videoService.queryVideoByAid(aid);
    }

}
