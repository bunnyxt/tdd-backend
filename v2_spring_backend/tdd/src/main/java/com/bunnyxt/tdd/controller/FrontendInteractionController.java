package com.bunnyxt.tdd.controller;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.FrontendInteraction;
import com.bunnyxt.tdd.service.FrontendInteractionService;
import com.bunnyxt.tdd.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class FrontendInteractionController {

    @Autowired
    private FrontendInteractionService frontendInteractionService;

    @RequestMapping(value = "/interaction", method = RequestMethod.POST)
    public void addFrontendInteraction(
            @RequestBody FrontendInteraction frontendInteraction
    ) throws InvalidRequestParameterException {
        frontendInteraction.setAdded(CalendarUtil.getNowTs());
        frontendInteractionService.addFrontendInteraction(frontendInteraction);
    }
}
