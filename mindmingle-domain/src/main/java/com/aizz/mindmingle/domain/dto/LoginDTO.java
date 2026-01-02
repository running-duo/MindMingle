package com.aizz.mindmingle.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录请求 DTO
 *
 * @author zhangyuliang
 */
@Data
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码（可选）
     */
    private String captcha;

    /**
     * 验证码Key（可选）
     */
    private String captchaKey;
}
