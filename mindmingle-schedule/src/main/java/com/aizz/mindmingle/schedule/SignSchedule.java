package com.aizz.mindmingle.schedule;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@Component
public class SignSchedule {

    private static final Logger log = LoggerFactory.getLogger(SignSchedule.class);

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
        Boolean result = false;
        Integer score = 0;
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
            // {"user_id":1033,"created_at":"2025-08-01T00:41:17.086Z","id":116290,"value":5}
            // TODO:签到失败推送微信公众号
            result = true;
            score = response.getInteger("value");
        } catch (Exception ex) {
            log.error("cpSign error", ex);
            result = false;
            score = 0;
        }
        log.info("签到结束!");
        // bark推送
        barkNotice(result, score);
    }

    private void barkNotice(Boolean result, Integer score) {
        try {
            String url = "https://api.day.app/kcua6Td4HZP2d9pgv73B24";
            StringBuilder body = new StringBuilder();
            if (result) {
                body.append("签到成功!\n时间：");
            } else {
                body.append("签到失败!\n时间：");
            }
            body.append(getCurrentFormattedTime());
            body.append("\n获得积分：");
            body.append(score);
            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            // 构建求体参数
            HashMap<String, String> map = new HashMap<>();
            map.put("body", body.toString());
            map.put("title", "CP小程序打卡结果");
            map.put("badge", "1");
            map.put("sound", "minuet");
            map.put("icon", "https://minio.xiaofeng.show/blog/2025/cowave-log.png");
            map.put("group", "Cowave");
            map.put("url", "");
            // 组合请求头与请求体参数
            HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(map), headers);
            JSONObject response = restTemplate.postForObject(url, requestEntity, JSONObject.class);
            log.info("bark response:{}", response);
        } catch (Exception ex) {
            log.error("barkNotice error", ex);
        }
    }

    //    @Scheduled(cron = "0 0 10 * * ?")
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

    /**
     * 获取当前时间并格式化为 "yyyy-MM-dd HH:mm:ss"
     *
     * @return 格式化后的时间字符串
     */
    private String getCurrentFormattedTime() {
        // 获取当前日期和时间
        LocalDateTime now = LocalDateTime.now();

        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化当前日期和时间
        return now.format(formatter);
    }
}
