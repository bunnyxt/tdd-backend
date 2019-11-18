package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.model.Video;
import com.bunnyxt.tdd.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
public class VideoRestController {

    @Autowired
    private VideoService videoService;

}
