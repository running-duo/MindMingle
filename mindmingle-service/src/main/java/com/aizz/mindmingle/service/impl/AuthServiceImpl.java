package com.aizz.mindmingle.service.impl;

import cn.hutool.http.HttpUtil;
import com.aizz.mindmingle.service.AuthService;
import org.jasypt.util.text.AES256TextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 授权接口实现层
 *
 * @author zhangyuliang
 */
@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Value("${miniProgram.appId}")
    private String appId;
    @Value("${miniProgram.appSecret}")
    private String appSecret;
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    private final String wechatServerUrl = "https://api.weixin.qq.com/sns/jscode2session";

    @Override
    public void login() {
        log.info(dataSourceUrl);
//        miniProgramLogin("");
    }

    private void miniProgramLogin(String code) {
        String url = wechatServerUrl + "?appId=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        String body = HttpUtil.createGet(url).execute().body();

    }
}
