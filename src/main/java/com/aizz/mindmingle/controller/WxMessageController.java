package com.aizz.mindmingle.controller;

import com.aizz.mindmingle.common.Response;
import com.aizz.mindmingle.service.WxMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wxMessage")
public class WxMessageController {

    @Autowired
    private WxMessageService wxMessageService;

    /**
     * 消息推送测试
     *
     * @param openId
     * @return
     */
    @GetMapping("/push")
    public Response<Void> push(@RequestParam("openId") String openId) {
        wxMessageService.push(openId);
        return Response.success();
    }
}
