package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.domain.dto.LoginDTO;
import com.aizz.mindmingle.domain.dto.UserDTO;
import com.aizz.mindmingle.domain.dto.WxLoginDTO;
import com.aizz.mindmingle.domain.vo.LoginVO;
import com.aizz.mindmingle.domain.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 小风平台认证控制器
 *
 * NOTE: This controller requires AuthService to be updated with the following methods:
 * - Response<LoginVO> login(LoginDTO loginDTO)
 * - Response<LoginVO> wxLogin(WxLoginDTO wxLoginDTO)
 * - Response<UserVO> register(UserDTO userDTO)
 * - Response<Void> logout()
 * - Response<UserVO> getCurrentUser()
 *
 * @author zhangyuliang
 */
@RestController
@RequestMapping("/api/v1/auth")
public class XiaofengAuthController {

    private static final Logger log = LoggerFactory.getLogger(XiaofengAuthController.class);

    // TODO: Uncomment when AuthService is updated with required methods
    // @Autowired
    // private AuthService authService;

    @PostMapping("/login")
    public Response<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        log.info("User login attempt: {}", loginDTO.getUsername());
        // TODO: Implement when AuthService.login(LoginDTO) is available
        // return authService.login(loginDTO);
        return Response.error("AuthService.login(LoginDTO) not yet implemented");
    }

    @PostMapping("/wx-login")
    public Response<LoginVO> wxLogin(@RequestBody WxLoginDTO wxLoginDTO) {
        log.info("WeChat login attempt");
        // TODO: Implement when AuthService.wxLogin(WxLoginDTO) is available
        // return authService.wxLogin(wxLoginDTO);
        return Response.error("AuthService.wxLogin(WxLoginDTO) not yet implemented");
    }

    @PostMapping("/register")
    public Response<UserVO> register(@RequestBody UserDTO userDTO) {
        log.info("User registration: {}", userDTO.getUsername());
        // TODO: Implement when AuthService.register(UserDTO) is available
        // return authService.register(userDTO);
        return Response.error("AuthService.register(UserDTO) not yet implemented");
    }

    @PostMapping("/logout")
    public Response<Void> logout() {
        log.info("User logout");
        // TODO: Implement when AuthService.logout() is available
        // return authService.logout();
        return Response.error("AuthService.logout() not yet implemented");
    }

    @GetMapping("/me")
    public Response<UserVO> getCurrentUser() {
        // TODO: Implement when AuthService.getCurrentUser() is available
        // return authService.getCurrentUser();
        return Response.error("AuthService.getCurrentUser() not yet implemented");
    }
}
