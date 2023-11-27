package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
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

    @PostMapping("/login")
    public Response<Void> login(){
        return Response.success();
    }
}
