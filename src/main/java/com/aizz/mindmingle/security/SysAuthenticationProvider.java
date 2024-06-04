package com.aizz.mindmingle.security;

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

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
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
