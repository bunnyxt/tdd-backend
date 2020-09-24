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
    public TddCommonResponse postUserFavoriteVideo(
            @PathVariable Integer aid
    ) throws InvalidRequestParameterException {
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
    public TddCommonResponse deleteUserFavoriteVideo(
            @PathVariable Integer aid
    ) throws InvalidRequestParameterException {
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
    public UserFavoriteVideo queryUserFavoriteVideo(
            @PathVariable Integer aid
    ) throws InvalidRequestParameterException {
        // check params
        if (aid <= 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userFavoriteVideoService.queryUserFavoriteVideo(userid, aid);
    }

    // video favorite count
    @RequestMapping(value = "/video/{aid}/favorite", method = RequestMethod.GET)
    public Integer queryUserFavoriteVideoCount(
            @PathVariable Integer aid
    ) throws InvalidRequestParameterException {
        // check params
        if (aid <= 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }

        return userFavoriteVideoService.queryUserFavoriteVideoCount(aid);
    }

    // admin ===========================================================================================================

    // video favorite count
    @RequestMapping(value = "/video/{aid}/favorite/user", method = RequestMethod.GET)
    public ResponseEntity<List<UserFavoriteVideo>> queryUserFavoriteVideoUsers(
            @PathVariable Integer aid,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "added") String order_by,
            @RequestParam(defaultValue = "1") Integer desc,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "100") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        if (aid <= 0) {
            throw new InvalidRequestParameterException("aid", aid, "aid should be greater than 0");
        }
        TddParamCheckUtil.start_ts(start_ts);
        TddParamCheckUtil.end_ts(end_ts);
        List<String> allowedOrderBy = new ArrayList<String>(){{
            add("added");
        }};
        if (!allowedOrderBy.contains(order_by)) {
            throw new InvalidRequestParameterException("order_by", order_by,
                    "only support order by " + allowedOrderBy.toString());
        }
        TddParamCheckUtil.desc(desc);
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 100);

        return TddResponseUtil.AssembleList(
                userFavoriteVideoService.queryUserFavoriteVideoUsers(aid, start_ts, end_ts, order_by, desc, pn, ps),
                userFavoriteVideoService.queryUserFavoriteVideoUsersCount(aid, start_ts, end_ts)
        );
    }
}
