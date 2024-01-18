package com.aizz.mindmingle.schedule;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class SignSchedule {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${sign.url}")
    private String url;
    @Value("${sign.token}")
    private String token;

    @Scheduled(cron = "0 0 7 * * ?")
    private void cpSign() {
        log.info("准备签到...");
        try {
            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            headers.set("Content-Type", "application/json");
            // 构建求体参数
            HashMap<String, String> map = new HashMap<>();
            // 组合请求头与请求体参数
            HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(map), headers);
            // sleep随机数，两小时内
            Random rand = new Random();
            int value = rand.nextInt(7200000);
            log.info("sleep[{}]millis...", value);
            Thread.sleep(value);
            log.info("发起签到...");
            JSONObject response = restTemplate.postForObject(url, requestEntity, JSONObject.class);
            log.info("签到响应:{}", response);
        } catch (Exception ex) {
            log.error("cpSign error", ex);
        }
        log.info("签到结束!");
    }
}
