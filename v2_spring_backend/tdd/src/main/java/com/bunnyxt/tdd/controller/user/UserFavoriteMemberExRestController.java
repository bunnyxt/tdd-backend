package com.bunnyxt.tdd.controller.user;

import com.bunnyxt.tdd.auth.TddAuthUtil;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.user.UserFavoriteMemberEx;
import com.bunnyxt.tdd.service.user.UserFavoriteMemberExService;
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
public class UserFavoriteMemberExRestController {

    @Autowired
    UserFavoriteMemberExService userFavoriteMemberExService;

    // user ============================================================================================================

    // user favorite video list
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/favorite/member/me", method = RequestMethod.GET)
    public ResponseEntity<List<UserFavoriteMemberEx>> queryUserFavoriteMembersMe(@RequestParam(defaultValue = "") String name,
                                                                                 @RequestParam(defaultValue = "") String sex,
                                                                                 @RequestParam(defaultValue = "added") String order_by,
                                                                                 @RequestParam(defaultValue = "1") Integer desc,
                                                                                 @RequestParam(defaultValue = "1") Integer pn,
                                                                                 @RequestParam(defaultValue = "20") Integer ps)
            throws InvalidRequestParameterException {
        // check params
        List<String> allowedSex = new ArrayList<String>(){{
            add("男");
            add("女");
            add("保密");
        }};
        if (!sex.equals("")) {
            if (!allowedSex.contains(sex)) {
                throw new InvalidRequestParameterException("sex", sex, "sex should in " + allowedSex.toString());
            }
        }
        List<String> allowedOrderBy = new ArrayList<String>(){{
            add("added");
            add("mid");
            add("video_count");
            add("v_pubdate");
            add("sr_view");
            add("sr_danmaku");
            add("sr_reply");
            add("sr_favorite");
            add("sr_coin");
            add("sr_share");
            add("sr_like");
            add("fr_follower");
        }};
        if (!allowedOrderBy.contains(order_by)) {
            throw new InvalidRequestParameterException("order_by", order_by,
                    "only support order by " + allowedOrderBy.toString());
        }
        TddParamCheckUtil.desc(desc);
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 20);

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        // get list
        List<UserFavoriteMemberEx> list = userFavoriteMemberExService.queryUserFavoriteMemberExsMe(userid, name, sex, order_by, desc, pn, ps);

        // get total count
        Integer totalCount = userFavoriteMemberExService.queryUserFavoriteMemberExsMeCount(userid, name, sex);

        return TddResponseUtil.AssembleList(list, totalCount);
    }

    // admin ===========================================================================================================

    // check one user's favorite video
    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/user/favorite/member/tdduser{userid}", method = RequestMethod.GET)
    public ResponseEntity<List<UserFavoriteMemberEx>> queryUserFavoriteVideos(@PathVariable Long userid,
                                                                             @RequestParam(defaultValue = "0") Integer start_ts,
                                                                             @RequestParam(defaultValue = "0") Integer end_ts,
                                                                             @RequestParam(defaultValue = "added") String order_by,
                                                                             @RequestParam(defaultValue = "1") Integer desc,
                                                                             @RequestParam(defaultValue = "1") Integer pn,
                                                                             @RequestParam(defaultValue = "100") Integer ps)
            throws InvalidRequestParameterException {
        // check params
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
        List<UserFavoriteMemberEx> list = userFavoriteMemberExService.queryUserFavoriteMemberExs(userid, start_ts, end_ts, order_by, desc, pn, ps);

        // get total count
        Integer totalCount = userFavoriteMemberExService.queryUserFavoriteMemberExsCount(userid, start_ts, end_ts);

        return TddResponseUtil.AssembleList(list, totalCount);
    }
}
