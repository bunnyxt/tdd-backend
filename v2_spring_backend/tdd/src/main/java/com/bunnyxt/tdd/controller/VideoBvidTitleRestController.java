package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.VideoBvidTitle;
import com.bunnyxt.tdd.service.VideoBvidTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class VideoBvidTitleRestController {

    @Autowired
    VideoBvidTitleService videoBvidTitleService;

    @RequestMapping(value = "/video/bvidtitle", method = RequestMethod.GET)
    public List<VideoBvidTitle> queryVideoBvidTitle(
            @RequestParam String bvid
    ) throws InvalidRequestParameterException {
        return videoBvidTitleService.queryVideoBvidTitle(bvid);
    }
}
