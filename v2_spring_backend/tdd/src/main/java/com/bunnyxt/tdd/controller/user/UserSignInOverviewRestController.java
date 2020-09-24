package com.bunnyxt.tdd.controller.user;

import com.bunnyxt.tdd.auth.TddAuthUtil;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.user.UserSignIn;
import com.bunnyxt.tdd.model.user.UserSignInOverview;
import com.bunnyxt.tdd.service.user.UserSignInOverviewService;
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
public class UserSignInOverviewRestController {

    @Autowired
    UserSignInOverviewService userSignInOverviewService;

    // user ============================================================================================================

    // check user's sign in overview
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/signin/overview/me", method = RequestMethod.GET)
    public UserSignInOverview queryUserSignInOverviewMe()
            throws InvalidRequestParameterException {
        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return queryUserSignInsMe(userid);
    }

    // admin ===========================================================================================================

    // check one user's sign in overview
    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/user/signin/overview/tdduser{userid}", method = RequestMethod.GET)
    public UserSignInOverview queryUserSignInsMe(
            @PathVariable Long userid
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.userid(userid);

        return userSignInOverviewService.queryUserSignInOverviewByUserid(userid);
    }

    // overall query
    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/user/signin/overview", method = RequestMethod.GET)
    public ResponseEntity<List<UserSignInOverview>> queryUserSignIns(
            @RequestParam(defaultValue = "0") Integer start_ts,
            @RequestParam(defaultValue = "0") Integer end_ts,
            @RequestParam(defaultValue = "total") String order_by,
            @RequestParam(defaultValue = "1") Integer desc,
            @RequestParam(defaultValue = "1") Integer pn,
            @RequestParam(defaultValue = "100") Integer ps
    ) throws InvalidRequestParameterException {
        // check params
        TddParamCheckUtil.start_ts(start_ts);
        TddParamCheckUtil.end_ts(end_ts);
        List<String> allowedOrderBy = new ArrayList<String>(){{
            add("userid");
            add("total");
            add("last_added");
            add("last_added_days");
        }};
        if (!allowedOrderBy.contains(order_by)) {
            throw new InvalidRequestParameterException("order_by", order_by,
                    "only support order by " + allowedOrderBy.toString());
        }
        TddParamCheckUtil.desc(desc);
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 100);

        return TddResponseUtil.AssembleList(
                userSignInOverviewService.queryUserSignInOverviews(start_ts, end_ts, order_by, desc, pn, ps),
                userSignInOverviewService.queryUserSignInOverviewsCount(start_ts, end_ts)
        );
    }
}
