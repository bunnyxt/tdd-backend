package com.bunnyxt.tdd.auth;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Map;

public class TddSmsUtil {

    public static boolean sendCode(String phone, String code) {
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou", "<access-key-id>", "<access-key-secret>");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "天钿Daily");
        request.putQueryParameter("TemplateCode", "<your-template-code>");
        request.putQueryParameter("TemplateParam", "{\"code\": \"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            Map<String, String> map = (Map<String, String>) JSONObject.parse(data);
            if (!map.get("Message").equals("OK")) {
                System.out.println(data);
                return false;
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
