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
    public ResponseEntity<List<UserFavoriteVideoEx>> queryUserFavoriteVideos(@RequestParam(defaultValue = "") String title,
                                                                             @RequestParam(defaultValue = "added") String order_by,
                                                                             @RequestParam(defaultValue = "1") Integer desc,
                                                                             @RequestParam(defaultValue = "1") Integer pn,
                                                                             @RequestParam(defaultValue = "20") Integer ps)
            throws InvalidRequestParameterException {
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

        // get list
        List<UserFavoriteVideoEx> list = userFavoriteVideoExService.queryUserFavoriteVideoExs(userid, title, order_by, desc, pn, ps);

        // get total count
        Integer totalCount = userFavoriteVideoExService.queryUserFavoriteVideoExsCount(userid, title);

        return TddResponseUtil.AssembleList(list, totalCount);
    }
}
