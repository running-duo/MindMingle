package com.aizz.mindmingle.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration(proxyBeanMethods = false)
@ConfigurationProperties("spring.application.token")
public class TokenConfiguration {

    /**
     * header名称
     */
    public String header = "Authorization";

    /**
     * 秘钥
     */
    public String salt = "aizz";

    /**
     * 是否检查冲突
     */
    public boolean conflict = false;

    /**
     * 应用令牌认证超时[单位秒]
     */
    public int appExpire = 60;

    /**
     * 客户端超时[单位秒]
     */
    public int clientExpire = 3600;

    /**
     * 服务端超时[单位秒]
     */
    public int serverExpire = 36000;

    /**
     * 忽略鉴权的url
     */
    public List<String> ignoreUrls = new ArrayList<>();

    // Getter methods for ConfigurationProperties
    public String getHeader() { return header; }
    public String getSalt() { return salt; }
    public boolean isConflict() { return conflict; }
    public int getAppExpire() { return appExpire; }
    public int getClientExpire() { return clientExpire; }
    public int getServerExpire() { return serverExpire; }
    public List<String> getIgnoreUrls() { return ignoreUrls; }

    // Setter methods for ConfigurationProperties
    public void setHeader(String header) { this.header = header; }
    public void setSalt(String salt) { this.salt = salt; }
    public void setConflict(boolean conflict) { this.conflict = conflict; }
    public void setAppExpire(int appExpire) { this.appExpire = appExpire; }
    public void setClientExpire(int clientExpire) { this.clientExpire = clientExpire; }
    public void setServerExpire(int serverExpire) { this.serverExpire = serverExpire; }
    public void setIgnoreUrls(List<String> ignoreUrls) { this.ignoreUrls = ignoreUrls; }

}
