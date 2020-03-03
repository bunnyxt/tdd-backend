package com.bunnyxt.tdd.controller.user;

import com.bunnyxt.tdd.auth.TddAuthUtil;
import com.bunnyxt.tdd.dao.user.UserFavoriteVideoDao;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserFavoriteVideo;
import com.bunnyxt.tdd.model.user.UserFavoriteVideoEx;
import com.bunnyxt.tdd.service.user.UserFavoriteVideoService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

@CrossOrigin
@RestController
public class UserFavoriteVideoRestController {

    @Autowired
    UserFavoriteVideoService userFavoriteVideoService;

    // user ============================================================================================================

    // user post favorite video
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/favorite/video/{aid}", method = RequestMethod.POST)
    public TddCommonResponse postUserFavoriteVideo(@PathVariable Integer aid)
            throws InvalidRequestParameterException {
        // check params
        if (aid <= 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userFavoriteVideoService.postUserFavoriteVideo(userid, aid);
    }

    // user delete favorite video
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/favorite/video/{aid}", method = RequestMethod.DELETE)
    public TddCommonResponse deleteUserFavoriteVideo(@PathVariable Integer aid)
            throws InvalidRequestParameterException {
        // check params
        if (aid <= 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userFavoriteVideoService.deleteUserFavoriteVideo(userid, aid);
    }

    // user favorite video status
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/favorite/video/{aid}", method = RequestMethod.GET)
    public UserFavoriteVideo queryUserFavoriteVideo(@PathVariable Integer aid)
            throws InvalidRequestParameterException {
        // check params
        if (aid <= 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userFavoriteVideoService.queryUserFavoriteVideo(userid, aid);
    }

    // admin ===========================================================================================================
}
