package com.aizz.mindmingle.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author zhangyuliang
 */
public class SysAuthenticationProvider extends DaoAuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(SysAuthenticationProvider.class);

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        log.info("开始密码验证 - 用户名: {}", userDetails.getUsername());
        log.info("前端传入的密码: {}", authentication.getCredentials());
        log.info("数据库密码哈希: {}", userDetails.getPassword());
        log.info("密码编码器: {}", getPasswordEncoder());

        try {
            super.additionalAuthenticationChecks(userDetails, authentication);
            log.info("密码验证成功！");
        } catch (AuthenticationException e) {
            log.error("密码验证失败！原因: {}", e.getMessage());
            throw e;
        }
        // 可以对userDetails进一步校验
    }

    @Override
    protected Authentication createSuccessAuthentication(
            Object principal, Authentication auth, UserDetails user) {
        Authentication authentication = super.createSuccessAuthentication(principal, auth, user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
