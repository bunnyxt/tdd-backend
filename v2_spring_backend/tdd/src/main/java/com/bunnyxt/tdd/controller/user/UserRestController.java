package com.bunnyxt.tdd.controller.user;

import com.bunnyxt.tdd.auth.TddAuthUtil;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.user.User;
import com.bunnyxt.tdd.service.user.UserService;
import com.bunnyxt.tdd.util.TddParamCheckUtil;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    // user ============================================================================================================

    // check user's profile
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/me", method = RequestMethod.GET)
    public User queryUserByIdMe() {
        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userService.queryUserById(userid);
    }

    // user change personal profile
    // TODO

    // admin ===========================================================================================================

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User queryUserById(@PathVariable Long id) {
        return userService.queryUserById(id);
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> queryUsers(@RequestParam(defaultValue = "") String username,
                                                 @RequestParam(defaultValue = "") String email,
                                                 @RequestParam(defaultValue = "") String phone,
                                                 @RequestParam(defaultValue = "-1") Integer enabled,
                                                 @RequestParam(defaultValue = "") String role,
                                                 @RequestParam(defaultValue = "added") String order_by,
                                                 @RequestParam(defaultValue = "0") Integer desc,
                                                 @RequestParam(defaultValue = "1") Integer pn,
                                                 @RequestParam(defaultValue = "20") Integer ps)
            throws InvalidRequestParameterException {
        // check params
        // TODO username, email, phone
        // enabled
        if (enabled != -1 && enabled != 0 && enabled != 1) {
            throw new InvalidRequestParameterException("enabled", enabled, "enabled should be 0 or 1");
        }
        // role
        List<String> allowedRole = new ArrayList<String>(){{
            add("");
            add("user");
            add("admin");
            add("dba");
        }};
        if (!allowedRole.contains(role)) {
            throw new InvalidRequestParameterException("role", role,
                    "only support role " + allowedRole.toString());
        }
        // order_by
        List<String> allowedOrderBy = new ArrayList<String>(){{
            add("added");
            add("point");
            add("exp");
        }};
        if (!allowedOrderBy.contains(order_by)) {
            throw new InvalidRequestParameterException("order_by", order_by,
                    "only support order by " + allowedOrderBy.toString());
        }
        TddParamCheckUtil.desc(desc);
        TddParamCheckUtil.pn(pn);
        TddParamCheckUtil.ps(ps, 20);

        // get list
        List<User> list = userService.queryUsers(username, email, phone, enabled, role, order_by, desc, pn, ps);

        // get total count
        Integer totalCount = userService.queryUsersCount(username, email, phone, enabled, role);

        return TddResponseUtil.AssembleList(list, totalCount);
    }
}
