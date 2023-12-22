package com.aizz.mindmingle.service.impl;

import com.aizz.mindmingle.service.WxMessageService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WxMessageServiceImpl implements WxMessageService {

    @Value("${miniProgram.appId}")
    private String appId;
    @Value("${miniProgram.appSecret}")
    private String appSecret;

    @Override
    public void push(String openId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            //这里简单起见我们每次都获取最新的access_token（时间开发中，应该在access_token快过期时再重新获取）
            String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + getAccessToken();
            //拼接推送的模版
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("to_user", openId);
            jsonObject.put("template_id", "9kIY32HYU_IO4aA2kl8XHFvtm9BZsJ1kXF-sZ6KBaxc");
            jsonObject.put("page", "pages/index/index");
            JSONObject data = new JSONObject();
            data.put("time1", new Date());
            data.put("thing2", "软笔书法");
            data.put("thing3", "张三");
            data.put("thing6", "三江学院");
            data.put("thing7", "测试");
            jsonObject.put("data", data);
            log.info(jsonObject.toString());
            ResponseEntity<String> responseEntity =
                    restTemplate.postForEntity(url, jsonObject, String.class);
            String body = responseEntity.getBody();
            log.info(body);
        } catch (Exception ex) {
            log.error("push error", ex);
        }
    }

    private String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("APPID", appId);  //
        params.put("APPSECRET", appSecret);  //
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}", String.class, params);
        String body = responseEntity.getBody();
        JSONObject object = JSON.parseObject(body);
        String Access_Token = object.getString("access_token");
        String expires_in = object.getString("expires_in");
        log.info("有效时长expires_in：" + expires_in);
        return Access_Token;
    }
}
