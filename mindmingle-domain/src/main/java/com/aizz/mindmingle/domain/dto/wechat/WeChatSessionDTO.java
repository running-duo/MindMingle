package com.aizz.mindmingle.domain.dto.wechat;

/**
 * 微信服务器返回结构体
 *
 * @author zhangyuliang
 */
public class WeChatSessionDTO {

    /**
     * 微信服务器上辨识用户的唯一id
     */
    public String openid;

    /**
     * 身份凭证
     */
    public String session_key;

    /**
     * 错误代码
     */
    public String errcode;

    /**
     * 错误信息
     */
    public String errmsg;

    // Constructors
    public WeChatSessionDTO() {
    }

    public WeChatSessionDTO(String openid, String session_key, String errcode, String errmsg) {
        this.openid = openid;
        this.session_key = session_key;
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    // Getters
    public String getOpenid() {
        return openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public String getErrcode() {
        return errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    // Setters
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    // Builder
    public static WeChatSessionDTOBuilder builder() {
        return new WeChatSessionDTOBuilder();
    }

    public static class WeChatSessionDTOBuilder {
        private String openid;
        private String session_key;
        private String errcode;
        private String errmsg;

        WeChatSessionDTOBuilder() {
        }

        public WeChatSessionDTOBuilder openid(String openid) {
            this.openid = openid;
            return this;
        }

        public WeChatSessionDTOBuilder session_key(String session_key) {
            this.session_key = session_key;
            return this;
        }

        public WeChatSessionDTOBuilder errcode(String errcode) {
            this.errcode = errcode;
            return this;
        }

        public WeChatSessionDTOBuilder errmsg(String errmsg) {
            this.errmsg = errmsg;
            return this;
        }

        public WeChatSessionDTO build() {
            return new WeChatSessionDTO(openid, session_key, errcode, errmsg);
        }
    }
}
