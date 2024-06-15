package com.aizz.mindmingle.schedule;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Random;

@Slf4j
@Component
public class SignSchedule {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
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
            // TODO:签到失败推送微信公众号
        } catch (Exception ex) {
            log.error("cpSign error", ex);
        }
        log.info("签到结束!");
    }

    @Scheduled(cron = "0 0 10 * * ?")
    private void nfVideoSign() {
        log.info("准备nf.video签到...");
        try {
            String signUrl = "https://nf.video/8081/api/applets/market/sign/post";
            Object cookie1 = redisTemplate.opsForValue().get("nf:video:cookie1");
            Object cookie2 = redisTemplate.opsForValue().get("nf:video:cookie2");
            // sleep随机数
            Random rand = new Random();
            int value = rand.nextInt(3600000);
            log.info("sleep[{}]millis...", value);
            Thread.sleep(value);
            nfVideoSignCore(cookie1.toString(), signUrl);
            // sleep随机数
            rand = new Random();
            value = rand.nextInt(3600000);
            log.info("sleep[{}]millis...", value);
            Thread.sleep(value);
            nfVideoSignCore(cookie2.toString(), signUrl);

            // TODO:签到失败推送微信公众号
        } catch (Exception ex) {
            log.error("nfVideoSign error", ex);
        }
        log.info("nf.video签到结束!");
    }

    private void nfVideoSignCore(String cookie, String signUrl) {
        try {
            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cookie", cookie);
            headers.set("Content-Type", "application/json");
            // 构建求体参数
            HashMap<String, String> map = new HashMap<>();
            // 组合请求头与请求体参数
            HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(map), headers);
            JSONObject response = restTemplate.postForObject(signUrl, requestEntity, JSONObject.class);
            log.info("nf.video response:{}", response);
        } catch (Exception ex) {
            log.error("nfVideoSignCore error", ex);
        }
    }
}
