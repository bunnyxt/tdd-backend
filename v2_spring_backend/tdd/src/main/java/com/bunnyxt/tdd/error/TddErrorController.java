package com.bunnyxt.tdd.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TddErrorController extends BasicErrorController {

    @Autowired
    public TddErrorController(ErrorAttributes errorAttributes,
                              ServerProperties serverProperties,
                              List<ErrorViewResolver> errorViewResolverList) {
        super(errorAttributes, serverProperties.getError(), errorViewResolverList);
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        // TODO html request also give json string reply
        return super.errorHtml(request, response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", body.get("status"));
        map.put("message", body.get("error"));
        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("timestamp", body.get("timestamp"));
        detail.put("message", body.get("message"));
        detail.put("path", body.get("path"));
        map.put("detail", detail);
        return new ResponseEntity<>(map, status);
    }
}
