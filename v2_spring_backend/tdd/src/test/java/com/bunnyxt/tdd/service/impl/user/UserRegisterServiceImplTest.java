package com.bunnyxt.tdd.service.impl.user;

import com.bunnyxt.tdd.auth.TddMailUtil;
import com.bunnyxt.tdd.auth.TddRecaptchaAuthUtil;
import com.bunnyxt.tdd.dao.RoleDao;
import com.bunnyxt.tdd.dao.user.UserDao;
import com.bunnyxt.tdd.dao.user.UserRegisterDao;
import com.bunnyxt.tdd.dao.user.UserSignInOverviewDao;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserRegisterTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRegisterServiceImplTest {

    @Mock
    private UserRegisterDao userRegisterDao;
    @Mock
    private UserDao userDao;
    @Mock
    private RoleDao roleDao;
    @Mock
    private UserSignInOverviewDao userSignInOverviewDao;
    @Mock
    private TddMailUtil tddMailUtil;
    @Mock
    private TddRecaptchaAuthUtil tddRecaptchaAuthUtil;

    @InjectMocks
    private UserRegisterServiceImpl service;

    @Test
    public void emailRegistrationStillCreatesAnEmailOnlyTask() {
        when(tddRecaptchaAuthUtil.check("captcha"))
                .thenReturn(new TddCommonResponse("success", "ok"));
        when(tddMailUtil.sendRegCode(eq("reader@example.com"), anyString())).thenReturn(true);

        TddCommonResponse response = service.requestCode(
                "email", "reader@example.com", "reader1", "Password1!", "captcha");

        assertEquals("success", response.getStatus());
        verify(userRegisterDao).addUserRegisterTask(
                anyInt(), eq((byte) 0), isNull(), eq("reader@example.com"), eq("reader1"),
                anyString(), anyString(), anyString(), anyInt(), eq((byte) 0));
    }

    @Test
    public void existingPhoneRegistrationTaskCannotCreateAUser() {
        UserRegisterTask task = new UserRegisterTask();
        task.setMethod((byte) 1);
        task.setStatus((byte) 0);
        task.setExpired(Integer.MAX_VALUE);
        task.setCode("123456");
        task.setUsername("legacyPhoneUser");
        task.setPhone("13800138000");
        when(userRegisterDao.queryUserRegisterTaskByRegKey("legacy-key")).thenReturn(task);

        TddCommonResponse response = service.goRegister("legacy-key", "123456");

        assertEquals("fail", response.getStatus());
        assertEquals("validation method not support", response.getMessage());
        verify(userDao, never()).addUser(
                anyInt(), anyString(), anyString(), anyString(), anyString());
    }
}
