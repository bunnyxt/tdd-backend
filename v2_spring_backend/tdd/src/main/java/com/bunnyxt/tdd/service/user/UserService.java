package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.User;

import java.util.List;

public interface UserService {

    User queryUserById(Long id);

    List<User> queryUsers(String username, String email, String phone, Integer enabled, String role,
                          String order_by, Integer desc, Integer pn, Integer ps);

    Integer queryUsersCount(String username, String email, String phone, Integer enabled, String role);

    TddCommonResponse bindEmailRequestCode(Long userid, String email, String recaptcha);

    TddCommonResponse bindEmailValidation(Long userid, String bindkey, String code);

    TddCommonResponse bindEmailUnbind(Long userid);

    TddCommonResponse bindPhoneRequestCode(Long userid, String phone, String recaptcha);

    TddCommonResponse bindPhoneValidation(Long userid, String bindkey, String code);

    TddCommonResponse bindPhoneUnbind(Long userid);

    TddCommonResponse setNickname(User user, String nickname);

    TddCommonResponse changePassword(User user, String password);
}
