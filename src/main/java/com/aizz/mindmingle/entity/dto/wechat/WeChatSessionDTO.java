package com.aizz.mindmingle.entity.dto.wechat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信服务器返回结构体
 *
 * @author zhangyuliang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeChatSessionDTO {

    /**
     * 微信服务器上辨识用户的唯一id
     */
    private String openid;

    /**
     * 身份凭证
     */
    private String session_key;

    /**
     * 错误代码
     */
    private String errcode;

    /**
     * 错误信息
     */
    private String errmsg;
}
