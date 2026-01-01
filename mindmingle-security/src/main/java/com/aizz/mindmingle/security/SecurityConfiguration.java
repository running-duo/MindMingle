package com.aizz.mindmingle.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.ArrayList;
import java.util.List;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

//    private final CorsFilter corsFilter;

    private final TokenConfiguration tokenConfiguration;

    private final TokenService tokenService;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService,
                                  TokenConfiguration tokenConfiguration,
                                  TokenService tokenService) {
        this.userDetailsService = userDetailsService;
        this.tokenConfiguration = tokenConfiguration;
        this.tokenService = tokenService;
    }

    private static final List<String> PERMIT_ALL = new ArrayList<>();

    static {
        PERMIT_ALL.add("/actuator/**");
        PERMIT_ALL.add("/druid/**");
        PERMIT_ALL.add("/doc/**");
        PERMIT_ALL.add("/fom/**");
    }

    private String[] permitAll(){
        PERMIT_ALL.addAll(tokenConfiguration.getIgnoreUrls());
        return PERMIT_ALL.toArray(new String[0]);
    }

    @Bean

    public CorsFilter corsFilter() {

        //1.添加CORS配置信息

        CorsConfiguration config = new CorsConfiguration();

        //放行哪些原始域

        config.addAllowedOrigin("*");

        //是否发送Cookie信息

        config.setAllowCredentials(true);

        //放行哪些原始域(请求方式)

        config.addAllowedMethod("*");

        //放行哪些原始域(头部信息)

        config.addAllowedHeader("*");

        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）

        config.addExposedHeader("*");



        //2.添加映射路径

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();

        configSource.registerCorsConfiguration("/**", config);



        //3.返回新的CorsFilter.

        return new CorsFilter(configSource);

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() {
        SysAuthenticationProvider sysAuthenticationProvider = new SysAuthenticationProvider();
        sysAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        sysAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(sysAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.headers().addHeaderWriter(new XXssProtectionHeaderWriter()).frameOptions().disable();
        httpSecurity.authorizeRequests().antMatchers(permitAll()).permitAll().anyRequest().authenticated();

        TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter(tokenService, permitAll());
        httpSecurity.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(corsFilter(), TokenAuthenticationFilter.class);
        httpSecurity.addFilterBefore(corsFilter(), LogoutFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers(permitAll());
    }
}
