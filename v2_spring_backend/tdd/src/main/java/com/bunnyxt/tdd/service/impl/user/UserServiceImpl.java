package com.bunnyxt.tdd.service.impl.user;

import com.bunnyxt.tdd.auth.TddMailUtil;
import com.bunnyxt.tdd.auth.TddRecaptchaAuthUtil;
import com.bunnyxt.tdd.auth.TddSmsUtil;
import com.bunnyxt.tdd.dao.user.BindEmailTaskDao;
import com.bunnyxt.tdd.dao.user.BindPhoneTaskDao;
import com.bunnyxt.tdd.dao.user.UserDao;
import com.bunnyxt.tdd.dao.user.UserLogDao;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.BindEmailTask;
import com.bunnyxt.tdd.model.user.BindPhoneTask;
import com.bunnyxt.tdd.model.user.User;
import com.bunnyxt.tdd.service.user.UserService;
import com.bunnyxt.tdd.util.CalendarUtil;
import com.bunnyxt.tdd.util.PageNumModfier;
import com.bunnyxt.tdd.util.TddCodeKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    BindEmailTaskDao bindEmailTaskDao;

    @Autowired
    BindPhoneTaskDao bindPhoneTaskDao;

    @Autowired
    UserLogDao userLogDao;

    @Override
    public User queryUserById(Long id) {
        return userDao.queryUserById(id);
    }

    @Override
    public List<User> queryUsers(String username, String email, String phone, Integer enabled, String role,
                                 String order_by, Integer desc, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return userDao.queryUsers(username, email, phone, enabled, role, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryUsersCount(String username, String email, String phone, Integer enabled, String role) {
        return userDao.queryUsersCount(username, email, phone, enabled, role);
    }

    @Override
    public TddCommonResponse bindEmailRequestCode(Long userid, String email, String recaptcha) {
        // check recaptcha
        TddCommonResponse recaptchaResponse = TddRecaptchaAuthUtil.check(recaptcha);
        if (recaptchaResponse.getStatus().equals("fail")) {
            return recaptchaResponse;
        }

        // check user bind or not
        User user = userDao.queryUserById(userid);
        if (user.getEmail() != null) {
            return new TddCommonResponse("fail", "user have already bind email");
        }

        // check email bind or not
        if (userDao.queryUserByEmail(email) != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("email", email);
            return new TddCommonResponse("fail", "email already used", map);
        }

        Integer added = CalendarUtil.getNowTs();
        Integer expired = added + 5 * 60;  // 5 min later

        // make code and bindkey
        String code = TddCodeKeyGenerator.generateCode();
        String bindkey = TddCodeKeyGenerator.generateKeyViaCode(code);

        // send code via email
        if (!TddMailUtil.sendBindCode(email, code, user.getUsername())) {
            Map<String, Object> map = new HashMap<>();
            map.put("email", email);
            return new TddCommonResponse("fail", "fail to sent bind code to email", map);
        }

        // add to db
        bindEmailTaskDao.addBindEmailTask(added, userid, email, bindkey, code, expired, Byte.valueOf("0"));

        Map<String, Object> map = new HashMap<>();
        map.put("bindkey", bindkey);
        map.put("expired", expired);
        return new TddCommonResponse("success", "bind email task created", map);
    }

    @Override
    public TddCommonResponse bindEmailValidation(Long userid, String bindkey, String code) {
        BindEmailTask task = bindEmailTaskDao.queryBindEmailTaskByBindKey(bindkey);
        if (task == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("bindkey", bindkey);
            return new TddCommonResponse("fail", "no bind mail task found", map);
        }

        if (task.getStatus() != 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("bindkey", bindkey);
            return new TddCommonResponse("fail", "invalid task status", map);
        }

        Integer nowTs = CalendarUtil.getNowTs();
        if (nowTs > task.getExpired()) {
            Map<String, Object> map = new HashMap<>();
            map.put("bindkey", bindkey);
            return new TddCommonResponse("fail", "code expired", map);
        }

        if (!code.equals(task.getCode())) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", code);
            return new TddCommonResponse("fail", "wrong code", map);
        }

        if (!userid.equals(task.getUserid())) {
            return new TddCommonResponse("fail", "task not created by current user");
        }

        // check user bind or not
        User user = userDao.queryUserById(userid);
        if (user.getEmail() != null) {
            return new TddCommonResponse("fail", "user have already bind email");
        }

        // check email bind or not
        if (userDao.queryUserByEmail(task.getEmail()) != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("email", task.getEmail());
            return new TddCommonResponse("fail", "email already used", map);
        }

        // update user email
        userDao.updateUserEmailById(userid, task.getEmail());

        // add user log
        Integer added = CalendarUtil.getNowTs();
        userLogDao.addUserLog(added, userid, "bind email", task.getEmail());

        // update bind email log
        bindEmailTaskDao.updateBindEmailTaskStatusByBindKey(bindkey, Byte.valueOf("1"));

        Map<String, Object> map = new HashMap<>();
        map.put("userid", task.getUserid());
        map.put("email", task.getEmail());
        return new TddCommonResponse("success", "bind email task done", map);
    }

    @Override
    public TddCommonResponse bindEmailUnbind(Long userid) {
        // check user bind or not
        User user = userDao.queryUserById(userid);
        String email = user.getEmail();
        if (email == null) {
            return new TddCommonResponse("fail", "user have not bind email yet");
        }

        // check whether bind phone, since user must bind either phone or email
        String phone = user.getPhone();
        if (phone == null) {
            return new TddCommonResponse("fail", "cannot unbind last only validation");
        }

        // update user email
        userDao.updateUserEmailToNullById(userid);

        // add user log
        Integer added = CalendarUtil.getNowTs();
        userLogDao.addUserLog(added, userid, "unbind email", email);

        return new TddCommonResponse("success", "unbind email done");
    }

    @Override
    public TddCommonResponse bindPhoneRequestCode(Long userid, String phone, String recaptcha) {
        // check recaptcha
        TddCommonResponse recaptchaResponse = TddRecaptchaAuthUtil.check(recaptcha);
        if (recaptchaResponse.getStatus().equals("fail")) {
            return recaptchaResponse;
        }

        // check user bind or not
        User user = userDao.queryUserById(userid);
        if (user.getPhone() != null) {
            return new TddCommonResponse("fail", "user have already bind phone");
        }

        // check email bind or not
        if (userDao.queryUserByPhone(phone) != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("phone", phone);
            return new TddCommonResponse("fail", "phone already used", map);
        }

        Integer added = CalendarUtil.getNowTs();
        Integer expired = added + 5 * 60;  // 5 min later

        // make code and bindkey
        String code = TddCodeKeyGenerator.generateCode();
        String bindkey = TddCodeKeyGenerator.generateKeyViaCode(code);

        // send code via phone
        if (!TddSmsUtil.sendBindCode(phone, code)) {
            Map<String, Object> map = new HashMap<>();
            map.put("phone", phone);
            return new TddCommonResponse("fail", "fail to sent bind code to phone", map);
        }

        // add to db
        bindPhoneTaskDao.addBindPhoneTask(added, userid, phone, bindkey, code, expired, Byte.valueOf("0"));

        Map<String, Object> map = new HashMap<>();
        map.put("bindkey", bindkey);
        map.put("expired", expired);
        return new TddCommonResponse("success", "bind phone task created", map);
    }

    @Override
    public TddCommonResponse bindPhoneValidation(Long userid, String bindkey, String code) {
        BindPhoneTask task = bindPhoneTaskDao.queryBindPhoneTaskByBindKey(bindkey);
        if (task == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("bindkey", bindkey);
            return new TddCommonResponse("fail", "no bind phone task found", map);
        }

        if (task.getStatus() != 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("bindkey", bindkey);
            return new TddCommonResponse("fail", "invalid task status", map);
        }

        Integer nowTs = CalendarUtil.getNowTs();
        if (nowTs > task.getExpired()) {
            Map<String, Object> map = new HashMap<>();
            map.put("bindkey", bindkey);
            return new TddCommonResponse("fail", "code expired", map);
        }

        if (!code.equals(task.getCode())) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", code);
            return new TddCommonResponse("fail", "wrong code", map);
        }

        if (!userid.equals(task.getUserid())) {
            return new TddCommonResponse("fail", "task not created by current user");
        }

        // check user bind or not
        User user = userDao.queryUserById(userid);
        if (user.getPhone() != null) {
            return new TddCommonResponse("fail", "user have already bind phone");
        }

        // check phone bind or not
        if (userDao.queryUserByPhone(task.getPhone()) != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("phone", task.getPhone());
            return new TddCommonResponse("fail", "phone already used", map);
        }

        // update user phone
        userDao.updateUserPhoneById(userid, task.getPhone());

        // add user log
        Integer added = CalendarUtil.getNowTs();
        userLogDao.addUserLog(added, userid, "bind phone", task.getPhone());

        // update bind phone task status
        bindPhoneTaskDao.updateBindPhoneTaskStatusByBindKey(bindkey, Byte.valueOf("1"));

        // set status of task with same userid and phone to 3: later succeed
        bindPhoneTaskDao.updateLaterSucceedBindPhoneTaskStatus(userid, task.getPhone(), Byte.valueOf("3"));

        Map<String, Object> map = new HashMap<>();
        map.put("userid", task.getUserid());
        map.put("phone", task.getPhone());
        return new TddCommonResponse("success", "bind phone task done", map);
    }

    @Override
    public TddCommonResponse bindPhoneUnbind(Long userid) {
        // check user bind or not
        User user = userDao.queryUserById(userid);
        String phone = user.getPhone();
        if (phone == null) {
            return new TddCommonResponse("fail", "user have not bind phone yet");
        }

        // check whether bind phone, since user must bind either phone or email
        String email = user.getEmail();
        if (email == null) {
            return new TddCommonResponse("fail", "cannot unbind last only validation");
        }

        // update user phone
        userDao.updateUserPhoneToNullById(userid);

        // add user log
        Integer added = CalendarUtil.getNowTs();
        userLogDao.addUserLog(added, userid, "unbind phone", phone);

        return new TddCommonResponse("success", "unbind phone done");
    }
}
