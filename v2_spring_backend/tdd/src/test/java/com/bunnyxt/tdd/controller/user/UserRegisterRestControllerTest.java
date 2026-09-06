package com.bunnyxt.tdd.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.service.user.UserRegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRegisterRestControllerTest {

    @Mock
    private UserRegisterService userRegisterService;

    @Test
    public void requestCodeRejectsPhoneRegistration() {
        UserRegisterRestController controller = new UserRegisterRestController();
        controller.userRegisterService = userRegisterService;

        JSONObject request = validRegistrationRequest("phone", "13800138000");

        try {
            controller.requestCode(request);
        } catch (InvalidRequestParameterException exception) {
            assertEquals("method", exception.getParameter());
            return;
        }
        throw new AssertionError("phone registration should be rejected");
    }

    @Test
    public void requestCodeStillDelegatesEmailRegistration() {
        UserRegisterRestController controller = new UserRegisterRestController();
        controller.userRegisterService = userRegisterService;
        JSONObject request = validRegistrationRequest("email", "reader@example.com");
        TddCommonResponse expected = new TddCommonResponse("success", "register task created");
        when(userRegisterService.requestCode(
                "email", "reader@example.com", "reader1", "Password1!", "captcha"))
                .thenReturn(expected);

        TddCommonResponse actual = controller.requestCode(request);

        assertSame(expected, actual);
        verify(userRegisterService).requestCode(
                "email", "reader@example.com", "reader1", "Password1!", "captcha");
    }

    @Test
    public void userControllerHasNoPhoneBindingRoutes() {
        for (Method method : UserRestController.class.getDeclaredMethods()) {
            RequestMapping mapping = method.getAnnotation(RequestMapping.class);
            if (mapping != null) {
                assertFalse(Arrays.stream(mapping.value())
                        .anyMatch(path -> path.startsWith("/user/bind/phone")));
            }
        }
    }

    private JSONObject validRegistrationRequest(String method, String validation) {
        JSONObject request = new JSONObject();
        request.put("method", method);
        request.put("validation", validation);
        request.put("username", "reader1");
        request.put("password", "Password1!");
        request.put("recaptcha", "captcha");
        return request;
    }
}
