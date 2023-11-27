package com.aizz.mindmingle.service.impl;

import com.aizz.mindmingle.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 授权接口实现层
 *
 * @author zhangyuliang
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${}")
    private String appId;
    @Value("${}")
    private String appSecret;

    private final String wechatServerUrl = "https://api.weixin.qq.com/sns/jscode2session";

    @Override
    public void login() {

    }

    private void miniProgramLogin(String code){
        String url = wechatServerUrl + "?appId="+appId+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
//        restTemplate.exchange()
    }
}
