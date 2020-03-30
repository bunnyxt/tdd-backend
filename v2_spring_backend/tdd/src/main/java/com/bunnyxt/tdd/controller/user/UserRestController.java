package com.bunnyxt.tdd.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.bunnyxt.tdd.auth.TddAuthUtil;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.TddCommonResponse;
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
import java.util.regex.Pattern;

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

    // bind email
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/bind/email/code", method = RequestMethod.POST)
    public TddCommonResponse bindEmailRequestCode(@RequestBody JSONObject jsonObject)
            throws InvalidRequestParameterException {
        // get params
        String email = jsonObject.get("email").toString();
        String recaptcha = jsonObject.getString("recaptcha");

        // check params
        if (email == null) {
            throw new InvalidRequestParameterException("email", null, "email should not be null");
        }
        String pattern = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
        if (!Pattern.matches(pattern, email)) {
            throw new InvalidRequestParameterException("email", email, "invalid email format");
        }
        if (email.length() > 200) {
            throw new InvalidRequestParameterException("email", email, "invalid email format, length of email is too long");
        }
        if (recaptcha == null) {
            throw new InvalidRequestParameterException("recaptcha", null, "recaptcha should not be null");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userService.bindEmailRequestCode(userid, email, recaptcha);
    }

    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/bind/email/validation", method = RequestMethod.POST)
    public TddCommonResponse bindEmailValidation(@RequestBody JSONObject jsonObject)
            throws InvalidRequestParameterException {
        // get params
        String bindkey = jsonObject.get("bindkey").toString();
        String code = jsonObject.get("code").toString();

        // check params
        TddParamCheckUtil.bindkey(bindkey);
        TddParamCheckUtil.code(code);

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userService.bindEmailValidation(userid, bindkey, code);
    }

    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/bind/email", method = RequestMethod.DELETE)
    public TddCommonResponse bindEmailUnbind()
            throws InvalidRequestParameterException {
        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userService.bindEmailUnbind(userid);
    }

    // bind phone
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/bind/phone/code", method = RequestMethod.POST)
    public TddCommonResponse bindPhoneRequestCode(@RequestBody JSONObject jsonObject)
            throws InvalidRequestParameterException {
        // get params
        String phone = jsonObject.get("phone").toString();
        String recaptcha = jsonObject.getString("recaptcha");

        // check params
        if (phone == null) {
            throw new InvalidRequestParameterException("phone", null, "phone should not be null");
        }
        String pattern = "^1[3456789]\\d{9}$";
        if (!Pattern.matches(pattern, phone)) {
            throw new InvalidRequestParameterException("phone", phone, "invalid phone format");
        }
        if (recaptcha == null) {
            throw new InvalidRequestParameterException("recaptcha", null, "recaptcha should not be null");
        }

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userService.bindPhoneRequestCode(userid, phone, recaptcha);
    }

    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/bind/phone/validation", method = RequestMethod.POST)
    public TddCommonResponse bindPhoneValidation(@RequestBody JSONObject jsonObject)
            throws InvalidRequestParameterException {
        // get params
        String bindkey = jsonObject.get("bindkey").toString();
        String code = jsonObject.get("code").toString();

        // check params
        TddParamCheckUtil.bindkey(bindkey);
        TddParamCheckUtil.code(code);

        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userService.bindPhoneValidation(userid, bindkey, code);
    }

    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/bind/phone", method = RequestMethod.DELETE)
    public TddCommonResponse bindPhoneUnbind()
            throws InvalidRequestParameterException {
        // get userid
        Long userid = TddAuthUtil.GetCurrentUser().getId();

        return userService.bindPhoneUnbind(userid);
    }

    // set nickname
    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/user/set/nickname", method = RequestMethod.POST)
    public TddCommonResponse setNickname(@RequestBody JSONObject jsonObject)
            throws InvalidRequestParameterException {
        // get params
        String nickname = jsonObject.getString("nickname");

        // check params
        if (nickname == null) {
            throw new InvalidRequestParameterException("nickname", null, "nickname should not be null");
        }
        if (nickname.length() < 2 || nickname.length() > 18) {
            throw new InvalidRequestParameterException("nickname", nickname, "nickname length should between 2 and 18");
        }
        if (nickname.startsWith("tdduser")) {
            throw new InvalidRequestParameterException("nickname", nickname, "cannot set nickname start with tdduser");
        }
        // TODO check whether sensitive nickname

        // get user
        User user = TddAuthUtil.GetCurrentUser();

        return userService.setNickname(user, nickname);
    }

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
