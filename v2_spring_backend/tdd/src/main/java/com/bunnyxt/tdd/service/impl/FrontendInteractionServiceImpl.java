package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.FrontendInteractionDao;
import com.bunnyxt.tdd.model.FrontendInteraction;
import com.bunnyxt.tdd.service.FrontendInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrontendInteractionServiceImpl implements FrontendInteractionService {

    @Autowired
    private FrontendInteractionDao frontendInteractionDao;

    @Override
    public void addFrontendInteraction(FrontendInteraction frontendInteraction) {
        frontendInteractionDao.addFrontendInteraction(frontendInteraction);
    }
}
