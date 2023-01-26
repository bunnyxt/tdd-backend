package com.bunnyxt.tdd.controller.user;

import com.bunnyxt.tdd.auth.TddAuthUtil;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserLikeMember;
import com.bunnyxt.tdd.service.user.UserLikeMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserLikeMemberRestController {

    @Autowired
    UserLikeMemberService userLikeMemberService;

    // user ============================================================================================================

    // user post like member
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/like/member/{mid}", method = RequestMethod.POST)
    public TddCommonResponse postUserLikeMember(
            @PathVariable Long mid
    ) throws InvalidRequestParameterException {
        // check params
        if (mid <= 0) {
            throw new InvalidRequestParameterException("mid", mid, "mid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userLikeMemberService.postUserLikeMember(userid, mid);
    }

    // user delete like member
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/like/member/{mid}", method = RequestMethod.DELETE)
    public TddCommonResponse deleteUserLikeMember(
            @PathVariable Long mid
    ) throws InvalidRequestParameterException {
        // check params
        if (mid <= 0) {
            throw new InvalidRequestParameterException("mid", mid, "mid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userLikeMemberService.deleteUserLikeMember(userid, mid);
    }

    // user like member status
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/like/member/{mid}", method = RequestMethod.GET)
    public UserLikeMember queryUserLikeMember(
            @PathVariable Long mid
    ) throws InvalidRequestParameterException {
        // check params
        if (mid <= 0) {
            throw new InvalidRequestParameterException("mid", mid, "mid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userLikeMemberService.queryUserLikeMember(userid, mid);
    }

    // member like count
    @RequestMapping(value = "/member/{mid}/like", method = RequestMethod.GET)
    public Integer queryUserLikeMemberCount(
            @PathVariable Long mid
    ) throws InvalidRequestParameterException {
        // check params
        if (mid <= 0) {
            throw new InvalidRequestParameterException("mid", mid, "mid should be greater than 0");
        }

        return userLikeMemberService.queryUserLikeMemberCount(mid);
    }

    // admin ===========================================================================================================
}
