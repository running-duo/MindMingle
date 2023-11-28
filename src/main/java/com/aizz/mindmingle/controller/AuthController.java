package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  授权
 *
 * @author zhangyuliang
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Response<Void> login(){
        authService.login();
        return Response.success();
    }
}
