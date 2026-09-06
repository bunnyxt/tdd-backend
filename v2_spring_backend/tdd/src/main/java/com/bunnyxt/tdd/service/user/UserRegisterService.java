package com.bunnyxt.tdd.service.user;

import com.bunnyxt.tdd.model.TddCommonResponse;

public interface UserRegisterService {

    TddCommonResponse requestCode(String method, String validation, String username, String password);

    TddCommonResponse goRegister(String regkey, String code);
}
