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

    private static String ACCESS_KEY_ID = "<access-key-id>";
    private static String ACCESS_KEY_SECRET = "<access-key-secret>";

    private static boolean sendTddCode(String phone, String code, String TemplateCode) {
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "天钿Daily");
        request.putQueryParameter("TemplateCode", TemplateCode);
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

    public static boolean sendRegCode(String phone, String code) {
        return sendTddCode(phone, code, "SMS_xxxxxxxxx");
    }

    public static boolean sendBindCode(String phone, String code) {
        return sendTddCode(phone, code, "SMS_xxxxxxxxx");
    }
}
