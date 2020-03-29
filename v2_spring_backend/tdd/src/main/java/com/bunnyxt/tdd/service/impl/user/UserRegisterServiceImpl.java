package com.bunnyxt.tdd.service.impl.user;

import com.alibaba.fastjson.JSON;
import com.bunnyxt.tdd.auth.TddMailUtil;
import com.bunnyxt.tdd.auth.TddRecaptchaAuthUtil;
import com.bunnyxt.tdd.auth.TddSmsUtil;
import com.bunnyxt.tdd.dao.RoleDao;
import com.bunnyxt.tdd.dao.user.UserDao;
import com.bunnyxt.tdd.dao.user.UserRegisterDao;
import com.bunnyxt.tdd.dao.user.UserSignInOverviewDao;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.User;
import com.bunnyxt.tdd.model.user.UserRegisterTask;
import com.bunnyxt.tdd.service.user.UserRegisterService;
import com.bunnyxt.tdd.util.CalendarUtil;
import com.bunnyxt.tdd.util.TddCodeKeyGenerator;
import com.bunnyxt.tdd.util.TddResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.util.DigestUtils;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    UserRegisterDao userRegisterDao;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    UserSignInOverviewDao userSignInOverviewDao;

    @Override
    public TddCommonResponse requestCode(String method, String validation, String username, String password, String recaptcha) {
        // check recaptcha
        TddCommonResponse recaptchaResponse = TddRecaptchaAuthUtil.check(recaptcha);
        if (recaptchaResponse.getStatus().equals("fail")) {
            return recaptchaResponse;
        }

        // check username
        User user;
        user = userDao.queryUserByUsername(username);
        if (user != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("username", username);
            return new TddCommonResponse("fail", "username already used", map);
        }

        // check email or phone
        if (method.equals("email")) {
            user = userDao.queryUserByEmail(validation);
            if (user != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("email", validation);
                return new TddCommonResponse("fail", "email already used", map);
            }
        } else if (method.equals("phone")) {
            user = userDao.queryUserByPhone(validation);
            if (user != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("phone", validation);
                return new TddCommonResponse("fail", "phone already used", map);
            }
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("method", method);
            return new TddCommonResponse("fail", "validation method not support", map);
        }

        Integer added = CalendarUtil.getNowTs();
        Integer expired = added + 5 * 60;  // 5 min later

        // make code and regkey
        String code = TddCodeKeyGenerator.generateCode();
        String regkey = TddCodeKeyGenerator.generateKeyViaCode(code);

        // send code via email or phone
        if (method.equals("email")) {
            if (!TddMailUtil.sendCode(validation, code)) {
                return new TddCommonResponse("fail", "fail to sent validation code to email " + validation);
            }
        } else if (method.equals("phone")) {
            if (!TddSmsUtil.sendCode(validation, code)) {
                return new TddCommonResponse("fail", "fail to sent validation code to phone " + validation);
            }
        }

        // add to db
        Byte methodCode = -1;
        String phone = null;
        String email = null;
        if (method.equals("email")) {
            methodCode = 0;
            email = validation;
        } else if (method.equals("phone")) {
            methodCode = 1;
            phone = validation;
        }
        password = new BCryptPasswordEncoder().encode(password);  // encrypt here
        userRegisterDao.addUserRegisterTask(added, methodCode, phone, email, username, password, regkey, code, expired, Byte.valueOf("0"));

        Map<String, Object> map = new HashMap<>();
        map.put("regkey", regkey);
        map.put("expired", expired);
        return new TddCommonResponse("success", "register task created", map);
    }

    @Override
    public TddCommonResponse goRegister(String regkey, String code) {
        UserRegisterTask task = userRegisterDao.queryUserRegisterTaskByRegKey(regkey);
        if (task == null) {
            return new TddCommonResponse("fail", "no register task found with regkey " + regkey);
        }

        if (task.getStatus() != 0) {
            return new TddCommonResponse("fail", "invalid status of regkey " + regkey);
        }

        Integer nowTs = CalendarUtil.getNowTs();
        if (nowTs > task.getExpired()) {
            return new TddCommonResponse("fail", "code expired of regkey " + regkey);
        }

        if (!code.equals(task.getCode())) {
            return new TddCommonResponse("fail", "wrong code " + code);
        }

        User user;
        user = userDao.queryUserByUsername(task.getUsername());
        if (user != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("username", task.getUsername());
            return new TddCommonResponse("fail", "username already used", map);
        }

        // check email or phone
        if (task.getMethod() == 0) {
            user = userDao.queryUserByEmail(task.getEmail());
            if (user != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("email", task.getEmail());
                return new TddCommonResponse("fail", "email already used", map);
            }
        } else if (task.getMethod() == 1) {
            user = userDao.queryUserByPhone(task.getPhone());
            if (user != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("phone", task.getPhone());
                return new TddCommonResponse("fail", "phone already used", map);
            }
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("method", task.getMethod());
            return new TddCommonResponse("fail", "validation method not support", map);
        }

        // insert user
        userDao.addUser(nowTs, task.getUsername(), task.getPassword(), task.getEmail(), task.getPhone());

        // get new user
        user = userDao.queryUserByUsername(task.getUsername());

        // set nickname
        userDao.updateUserNicknameById(user.getId(), "tdduser"+user.getId());

        // insert role
        roleDao.addUserRole(user.getId(),3L);  // 3 is common user

        // insert sign in overview
        userSignInOverviewDao.addUserSignInOverview(user.getId());

        // set task status to 1(success)
        userRegisterDao.updateUserRegisterTaskStatus(task.getRegkey(), Byte.valueOf("1"));

        return new TddCommonResponse("success", "register task done");
    }
}
