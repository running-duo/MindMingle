package com.aizz.mindmingle.service;

import com.aizz.mindmingle.domain.dto.LoginDTO;
import com.aizz.mindmingle.domain.vo.LoginVO;

/**
 * 授权接口层
 *
 * @author zhangyuliang
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param loginDTO 登录请求参数
     * @return 登录响应（包含token）
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 用户登出
     *
     * @param token 访问令牌
     */
    void logout(String token);
}
