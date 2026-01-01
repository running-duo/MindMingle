package com.aizz.mindmingle.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.Arrays;

@RestController
@RequestMapping("/wxAuth")
public class WxAuthController {

    private static final Logger log = LoggerFactory.getLogger(WxAuthController.class);

    /**
     * 微信小程序认证
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce 随机码
     * @param echostr 加密/解密字符串
     * @param params
     * @return
     */
    @GetMapping(value = "/verification")
    public String verification(@RequestParam(required = false) String signature,
                               @RequestParam(required = false) String timestamp,
                               @RequestParam(required = false) String nonce,
                               @RequestParam(required = false) String echostr,
                               @RequestBody(required = false) String params) {

        String token = "aizz";
        try {
            log.info("signature:", signature);
            log.info("timestamp:", timestamp);
            log.info("nonce:", nonce);
            log.info("echostr:", echostr);
            log.info("wx+object:" + params);
            if (params != null) {

            }
            StringBuffer str = new StringBuffer();
            str.append(token);
            str.append(timestamp);
            str.append(nonce);
            String shaStc = getSHA1(token, timestamp, nonce);
            if (shaStc.equals(signature)) {
                log.info("成功");
                return echostr;
            } else {
                log.info("失败");
                return "false";
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("error", e);
            return "false";
        }
    }

    /**
     * 获取SHA1签名
     * @param token 令牌
     * @param timestamp 时间戳
     * @param nonce 随机码
     * @return SHA1签名
     */
    public String getSHA1(String token, String timestamp, String nonce) {
        try {
            String[] array = new String[]{token, timestamp, nonce};
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < 3; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}
