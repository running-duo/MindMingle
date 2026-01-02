package com.aizz.mindmingle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 临时测试控制器 - 用于生成BCrypt密码哈希
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 生成BCrypt密码哈希
     */
    @GetMapping("/generatePassword")
    public Map<String, Object> generatePassword(@RequestParam(defaultValue = "admin123") String password) {
        Map<String, Object> result = new HashMap<>();

        String oldHash = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi";

        result.put("plainPassword", password);
        result.put("oldHash", oldHash);
        result.put("oldHashMatches", passwordEncoder.matches(password, oldHash));

        // 生成新哈希
        String newHash1 = passwordEncoder.encode(password);
        String newHash2 = passwordEncoder.encode(password);
        String newHash3 = passwordEncoder.encode(password);

        result.put("newHash1", newHash1);
        result.put("newHash1Matches", passwordEncoder.matches(password, newHash1));
        result.put("newHash2", newHash2);
        result.put("newHash2Matches", passwordEncoder.matches(password, newHash2));
        result.put("newHash3", newHash3);
        result.put("newHash3Matches", passwordEncoder.matches(password, newHash3));

        return result;
    }
}
