package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.VideoAidTitle;
import com.bunnyxt.tdd.service.VideoAidTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class VideoAidTitleRestController {

    @Autowired
    VideoAidTitleService videoAidTitleService;

    @RequestMapping(value = "/video/aidtitle", method = RequestMethod.GET)
    public List<VideoAidTitle> queryVideoAidTitle(
            @RequestParam Long aid
    ) throws InvalidRequestParameterException {
        // check param
        if (aid < 1000) {
            throw new InvalidRequestParameterException("aid", aid, "aid should bigger than 1000");
        }
        return videoAidTitleService.queryVideoAidTitle(aid);
    }

}
