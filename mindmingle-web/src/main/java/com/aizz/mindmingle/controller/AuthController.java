package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.LoginDTO;
import com.aizz.mindmingle.domain.vo.LoginVO;
import com.aizz.mindmingle.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权控制器
 *
 * @author zhangyuliang
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    /**
     * 用户登录
     *
     * @param loginDTO 登录请求参数
     * @return 登录响应（包含token）
     */
    @PostMapping("/login")
    public Response<LoginVO> login(@Validated @RequestBody LoginDTO loginDTO) {
        try {
            LoginVO loginVO = authService.login(loginDTO);
            return Response.success("登录成功", loginVO);
        } catch (Exception e) {
            log.error("登录失败", e);
            return Response.error(e.getMessage());
        }
    }

    /**
     * 用户登出
     *
     * @param request HTTP请求
     * @return 响应结果
     */
    @PostMapping("/logout")
    public Response<Void> logout(HttpServletRequest request) {
        try {
            // 从请求头获取token
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            authService.logout(token);
            return Response.success("登出成功", null);
        } catch (Exception e) {
            log.error("登出失败", e);
            return Response.error(e.getMessage());
        }
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/current")
    public Response<LoginVO> getCurrentUser() {
        try {
            // TODO: 从TokenService获取当前用户信息
            return Response.success();
        } catch (Exception e) {
            log.error("获取当前用户信息失败", e);
            return Response.error(e.getMessage());
        }
    }
}
