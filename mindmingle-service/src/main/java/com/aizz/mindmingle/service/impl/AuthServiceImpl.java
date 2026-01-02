package com.aizz.mindmingle.service.impl;

import cn.hutool.http.HttpUtil;
import com.aizz.mindmingle.domain.dos.UserDO;
import com.aizz.mindmingle.domain.dto.LoginDTO;
import com.aizz.mindmingle.domain.vo.LoginVO;
import com.aizz.mindmingle.persistence.mapper.UserMapper;
import com.aizz.mindmingle.security.AccessToken;
import com.aizz.mindmingle.security.TokenConfiguration;
import com.aizz.mindmingle.security.TokenService;
import com.aizz.mindmingle.service.AuthService;
import com.aizz.mindmingle.service.auth.UserDetailsServiceImpl;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 授权接口实现层
 *
 * @author zhangyuliang
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenConfiguration tokenConfiguration;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserMapper userMapper;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Value("${miniProgram.appId}")
    private String appId;

    @Value("${miniProgram.appSecret}")
    private String appSecret;

    private final String wechatServerUrl = "https://api.weixin.qq.com/sns/jscode2session";

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 参数校验
        if (loginDTO == null || !StringUtils.hasText(loginDTO.getUsername()) || !StringUtils.hasText(loginDTO.getPassword())) {
            throw new BadCredentialsException("用户名或密码不能为空");
        }

        log.info("用户登录请求: {}", loginDTO.getUsername());

        try {
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

            // 进行认证
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new BadCredentialsException("用户名或密码错误");
            }

            // 获取认证后的用户信息
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // 查询用户完整信息
            UserDO userDO = userDetailsService.getUserByUsername(username);
            if (userDO == null) {
                throw new BadCredentialsException("用户不存在");
            }

            // 生成访问令牌
            AccessToken accessToken = AccessToken.newToken();
            accessToken.setType(AccessToken.TYPE_USER);
            accessToken.setUserId(userDO.getId());
            accessToken.setUsername(userDO.getUsername());
            accessToken.setUserNick(userDO.getRealName() != null ? userDO.getRealName() : userDO.getUsername());

            // 设置token并生成JWT
            tokenService.setToken(accessToken);
            String token = accessToken.getToken();

            // 更新用户最后登录时间和IP
            updateLoginInfo(userDO.getId());

            // 构建登录响应
            LoginVO loginVO = LoginVO.builder()
                    .accessToken(token)
                    .tokenType("Bearer")
                    .expiresIn((long) tokenConfiguration.getClientExpire())
                    .userId(userDO.getId())
                    .username(userDO.getUsername())
                    .realName(userDO.getRealName())
                    .avatar(userDO.getAvatar())
                    .loginTime(new Date())
                    .build();

            log.info("用户登录成功: {}", username);
            return loginVO;

        } catch (AuthenticationException e) {
            log.warn("用户登录失败: {}, 原因: {}", loginDTO.getUsername(), e.getMessage());
            throw new BadCredentialsException("用户名或密码错误");
        } catch (Exception e) {
            log.error("用户登录异常: {}", loginDTO.getUsername(), e);
            throw new RuntimeException("登录失败，请稍后重试");
        }
    }

    @Override
    public void logout(String token) {
        if (StringUtils.hasText(token)) {
            try {
                // 登出成功（Token服务暂未实现Redis存储，这里仅做日志记录）
                log.info("用户登出成功");
            } catch (Exception e) {
                log.error("用户登出异常", e);
            }
        }
    }

    /**
     * 更新用户登录信息
     */
    private void updateLoginInfo(Long userId) {
        try {
            String ipAddress = getClientIp();

            LambdaUpdateWrapper<UserDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(UserDO::getId, userId)
                    .set(UserDO::getLastLoginTime, new Date())
                    .set(UserDO::getLastLoginIp, ipAddress);

            userMapper.update(null, updateWrapper);
        } catch (Exception e) {
            log.error("更新登录信息失败", e);
        }
    }

    /**
     * 获取客户端IP地址
     */
    private String getClientIp() {
        if (request == null) {
            return "unknown";
        }

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 处理多个IP的情况（取第一个）
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }

    /**
     * 微信小程序登录
     */
    private void miniProgramLogin(String code) {
        String url = wechatServerUrl + "?appId=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        String body = HttpUtil.createGet(url).execute().body();
        // TODO: 处理微信登录逻辑
    }
}
