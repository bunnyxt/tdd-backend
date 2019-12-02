package com.bunnyxt.tdd.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
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
        detail.put("value", ex.getValue());
        detail.put("prompt", ex.getPrompt());
        map.put("detail", detail);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

}
