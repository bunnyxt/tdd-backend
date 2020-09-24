package com.bunnyxt.tdd.controller.user;

import com.bunnyxt.tdd.auth.TddAuthUtil;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.user.UserFavoriteVideoEx;
import com.bunnyxt.tdd.service.user.UserFavoriteVideoExService;
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
public class UserFavoriteVideoExRestController {

    @Autowired
    UserFavoriteVideoExService userFavoriteVideoExService;

    // user ============================================================================================================

    // user favorite video list
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/favorite/video/me", method = RequestMethod.GET)
    public ResponseEntity<List<UserFavoriteVideoEx>> queryUserFavoriteVideosMe(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "added") String order_by,
            @RequestParam(defaultValue = "1") Integer desc,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "20") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        List<String> allowedOrderBy = new ArrayList<String>(){{
            add("added");
            add("pubdate");
            add("view");
            add("danmaku");
            add("reply");
            add("favorite");
            add("coin");
            add("share");
            add("like");
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

        return TddResponseUtil.AssembleList(
                userFavoriteVideoExService.queryUserFavoriteVideoExsMe(userid, title, order_by, desc, pn, ps),
                userFavoriteVideoExService.queryUserFavoriteVideoExsMeCount(userid, title)
        );
    }

    // admin ===========================================================================================================

    // check one user's favorite video
    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/user/favorite/video/tdduser{userid}", method = RequestMethod.GET)
    public ResponseEntity<List<UserFavoriteVideoEx>> queryUserFavoriteVideos(
            @PathVariable Long userid,
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "added") String order_by,
            @RequestParam(defaultValue = "1") Integer desc,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "100") Integer ps
    ) throws InvalidRequestParameterException {
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

        return TddResponseUtil.AssembleList(
                userFavoriteVideoExService.queryUserFavoriteVideoExs(userid, start_ts, end_ts, order_by, desc, pn, ps),
                userFavoriteVideoExService.queryUserFavoriteVideoExsCount(userid, start_ts, end_ts)
        );
    }
}
