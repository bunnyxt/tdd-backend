package com.bunnyxt.tdd.auth;

import com.alibaba.fastjson.JSON;
import com.bunnyxt.tdd.model.TddCommonResponse;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class TddRecaptchaAuthUtil {

    @Value("${tdd.recaptcha.secret}")
    private static String SECRET; // set your own recaptcha secret here;

    public static TddCommonResponse check(String recaptcha) {
        // check recaptcha
        BufferedReader in = null;
        String result = "";
        try {
            URL url = new URL("https://recaptcha.net/recaptcha/api/siteverify?secret="+SECRET+"&response="+recaptcha);
            URLConnection conn = url.openConnection();
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            Map resultMap = (Map) JSON.parse(result);
            if ((Boolean) resultMap.get("success")) {
                return new TddCommonResponse("success", "recaptcha validation passed");
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("error-codes", resultMap.get("error-codes").toString());
                return new TddCommonResponse("fail", "fail to validate recaptcha", map);
            }
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<>();
            map.put("exception", e.toString());
            return new TddCommonResponse("fail", "fail to validate recaptcha", map);
        }
    }
}
