package com.bunnyxt.tdd.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
public class TddRestExceptionController {

    @ExceptionHandler(InvalidRequestParameterException.class)
    @ResponseBody
    public final ResponseEntity<Map<String, Object>> invalidRequestParameterExceptionHandler(
            InvalidRequestParameterException ex, WebRequest request) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", 40001);
        map.put("message", "invalid request parameter");
        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("parameter", ex.getParameter());
        detail.put("value", isSensitiveParameter(ex.getParameter()) ? null : ex.getValue());
        detail.put("prompt", ex.getPrompt());
        map.put("detail", detail);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    /**
     * Registration and password change hand the submitted password to
     * InvalidRequestParameterException as the rejected value, so echoing it back in the
     * response body puts the user's cleartext password into their own client. Filtering here
     * rather than at the call sites keeps every existing throw site unchanged. The match is a
     * substring so it also covers parameter names added later, such as "newPassword"; no other
     * parameter in use contains "password", so their responses are untouched.
     */
    private static boolean isSensitiveParameter(String parameter) {
        return parameter != null && parameter.toLowerCase(Locale.ROOT).contains("password");
    }

}
