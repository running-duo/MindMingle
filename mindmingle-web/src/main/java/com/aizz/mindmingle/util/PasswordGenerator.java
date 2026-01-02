package com.aizz.mindmingle.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCrypt 密码生成工具
 */
public class PasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "admin123";

        System.out.println("===============================================");
        System.out.println("BCrypt 密码生成工具");
        System.out.println("===============================================");
        System.out.println("原始密码: " + password);
        System.out.println();

        // 生成新密码
        String newPassword = encoder.encode(password);
        System.out.println("新生成的 BCrypt 密码:");
        System.out.println(newPassword);
        System.out.println();

        // 验证旧密码
        String oldPassword = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi";
        boolean oldMatches = encoder.matches(password, oldPassword);
        System.out.println("验证旧密码哈希:");
        System.out.println("旧哈希: " + oldPassword);
        System.out.println("是否匹配 '" + password + "': " + oldMatches);
        System.out.println();

        // 验证新密码
        boolean newMatches = encoder.matches(password, newPassword);
        System.out.println("验证新密码哈希:");
        System.out.println("新哈希: " + newPassword);
        System.out.println("是否匹配 '" + password + "': " + newMatches);
        System.out.println();

        // 生成多个供选择
        System.out.println("额外生成的密码（可选）:");
        for (int i = 1; i <= 3; i++) {
            System.out.println(i + ". " + encoder.encode(password));
        }
        System.out.println("===============================================");
    }
}
