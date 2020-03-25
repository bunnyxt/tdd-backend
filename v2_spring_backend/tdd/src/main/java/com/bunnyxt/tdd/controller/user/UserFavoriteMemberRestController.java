package com.bunnyxt.tdd.controller.user;

import com.bunnyxt.tdd.auth.TddAuthUtil;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserFavoriteMember;
import com.bunnyxt.tdd.service.user.UserFavoriteMemberService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class UserFavoriteMemberRestController {

    @Autowired
    UserFavoriteMemberService userFavoriteMemberService;

    // user ============================================================================================================

    // user post favorite member
    @PreAuthorize(value = "hasRole('user')")
    @RequestMapping(value = "/user/favorite/member/{mid}", method = RequestMethod.POST)
    public TddCommonResponse postUserFavoriteMember(@PathVariable Integer mid)
            throws InvalidRequestParameterException {
        // check params
        if (mid <= 0) {
            throw new InvalidRequestParameterException("mid", mid, "mid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userFavoriteMemberService.postUserFavoriteMember(userid, mid);
    }

    // user delete favorite member
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/favorite/member/{mid}", method = RequestMethod.DELETE)
    public TddCommonResponse deleteUserFavoriteMember(@PathVariable Integer mid)
            throws InvalidRequestParameterException {
        // check params
        if (mid <= 0) {
            throw new InvalidRequestParameterException("mid", mid, "mid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userFavoriteMemberService.deleteUserFavoriteMember(userid, mid);
    }

    // user favorite member status
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/favorite/member/{mid}", method = RequestMethod.GET)
    public UserFavoriteMember queryUserFavoriteMember(@PathVariable Integer mid)
            throws InvalidRequestParameterException {
        // check params
        if (mid <= 0) {
            throw new InvalidRequestParameterException("mid", mid, "mid should be greater than 0");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userFavoriteMemberService.queryUserFavoriteMember(userid, mid);
    }

    // member favorite count
    @RequestMapping(value = "/member/{mid}/favorite", method = RequestMethod.GET)
    public Integer queryUserFavoriteMemberCount(@PathVariable Integer mid)
            throws InvalidRequestParameterException {
        // check params
        if (mid <= 0) {
            throw new InvalidRequestParameterException("mid", mid, "mid should be greater than 0");
        }

        return userFavoriteMemberService.queryUserFavoriteMemberCount(mid);
    }

    // admin ===========================================================================================================

    // video favorite count
    @RequestMapping(value = "/member/{mid}/favorite/user", method = RequestMethod.GET)
    public ResponseEntity<List<UserFavoriteMember>> queryUserFavoriteVideoUsers(@PathVariable Integer mid,
                                                                                @RequestParam(defaultValue = "0") Integer start_ts,
                                                                                @RequestParam(defaultValue = "0") Integer end_ts,
                                                                                @RequestParam(defaultValue = "added") String order_by,
                                                                                @RequestParam(defaultValue = "1") Integer desc,
                                                                                @RequestParam(defaultValue = "1") Integer pn,
                                                                                @RequestParam(defaultValue = "100") Integer ps)
            throws InvalidRequestParameterException {
        // check params
        if (mid <= 0) {
            throw new InvalidRequestParameterException("mid", mid, "aid should be greater than 0");
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

        // get list
        List<UserFavoriteMember> list = userFavoriteMemberService.queryUserFavoriteMemberUsers(mid, start_ts, end_ts, order_by, desc, pn, ps);

        // get total count
        Integer totalCount = userFavoriteMemberService.queryUserFavoriteMemberUsersCount(mid, start_ts, end_ts);

        return TddResponseUtil.AssembleList(list, totalCount);
    }
}
