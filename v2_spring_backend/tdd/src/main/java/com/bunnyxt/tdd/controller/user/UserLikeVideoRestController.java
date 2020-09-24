package com.bunnyxt.tdd.controller.user;

import com.bunnyxt.tdd.auth.TddAuthUtil;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserFavoriteVideo;
import com.bunnyxt.tdd.model.user.UserLikeVideo;
import com.bunnyxt.tdd.service.user.UserLikeVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserLikeVideoRestController {

    @Autowired
    UserLikeVideoService userLikeVideoService;

    // user ============================================================================================================

    // user post like video
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/like/video/{aid}", method = RequestMethod.POST)
    public TddCommonResponse postUserLikeVideo(
            @PathVariable Integer aid
    ) throws InvalidRequestParameterException {
        // check params
        if (aid <= 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userLikeVideoService.postUserLikeVideo(userid, aid);
    }

    // user delete like video
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/like/video/{aid}", method = RequestMethod.DELETE)
    public TddCommonResponse deleteUserLikeVideo(
            @PathVariable Integer aid
    ) throws InvalidRequestParameterException {
        // check params
        if (aid <= 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userLikeVideoService.deleteUserLikeVideo(userid, aid);
    }

    // user like video status
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/like/video/{aid}", method = RequestMethod.GET)
    public UserLikeVideo queryUserLikeVideo(
            @PathVariable Integer aid
    ) throws InvalidRequestParameterException {
        // check params
        if (aid <= 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userLikeVideoService.queryUserLikeVideo(userid, aid);
    }

    // video like count
    @RequestMapping(value = "/video/{aid}/like", method = RequestMethod.GET)
    public Integer queryUserLikeVideoCount(
            @PathVariable Integer aid
    ) throws InvalidRequestParameterException {
        // check params
        if (aid <= 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }

        return userLikeVideoService.queryUserLikeVideoCount(aid);
    }

    // admin ===========================================================================================================
}
