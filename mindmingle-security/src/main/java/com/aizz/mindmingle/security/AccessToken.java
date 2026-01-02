package com.aizz.mindmingle.security;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class AccessToken implements UserDetails {

    @Serial
    private static final long serialVersionUID = -3928832861296252415L;

    public static final String KEY = "Token:";

    public static final String TYPE_APP = "app";

    public static final String TYPE_USER = "user";

    public static final String TYPE_LDAP = "ldap";

    public static final String TYPE_OAUTH = "oauth";

    /**
     * Token
     */
    public String token;

    /**
     * Token id
     */
    public String id;

    /**
     * Token type
     */
    public String type;

    /**
     * 登录iP
     */
    public String loginIp;

    /**
     * 登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date loginTime;

    /**
     * 访问ip
     */
    public String accessIp;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date accessTime;

    /**
     * 用户id
     */
    public Long userId;

    /**
     * 租户id
     */
    public Long tenantId;

    /**
     * 用户code
     */
    public String userCode;

    /**
     * 用户账号
     */
    public String username;

    /**
     * 用户昵称
     */
    public String userNick;

    /**
     * 用户密码
     */
    @JsonIgnore
    public String userPasswd;

    /**
     * 用户Roles
     */
    public List<String> roles;

    /**
     * 用户Permissions
     */
    public List<String> permissions;

    /**
     * 部门id
     */
    public Long deptId;

    /**
     * 部门code
     */
    public String deptCode;

    /**
     * 部门名称
     */
    public String deptName;

    /**
     * 集群id
     */
    public Integer clusterId;

    /**
     * 集群level
     */
    public Integer clusterLevel;

    /**
     * 集群name
     */
    public String clusterName;

    /**
     * 校验码
     */
    public int validCode;

    /**
     * 校验描述
     */
    public String validDesc;

    /**
     * 权限
     */
    public List<? extends GrantedAuthority> authorities;

    public AccessToken(){

    }

    public AccessToken(int validCode, String validDesc) {
        this.validCode = validCode;
        this.validDesc = validDesc;
    }

    public AccessToken(int validCode, String validDesc, String token) {
        this.validCode = validCode;
        this.validDesc = validDesc;
        this.token = token;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return userPasswd;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities != null){
            return authorities;
        }
        if(!CollectionUtils.isEmpty(roles)){
            List<SimpleGrantedAuthority> list = new ArrayList<>(roles.size());
            for(String role : roles){
                list.add(new SimpleGrantedAuthority(role));
            }
            return list;
        }
        return new ArrayList<>();
    }

    public int getExpire(TokenConfiguration configuration){
        if(TYPE_USER.equals(type) || TYPE_LDAP.equals(type) || TYPE_OAUTH.equals(type)){
            return configuration.getClientExpire();
        }else if(TYPE_APP.equals(type)){
            return configuration.getAppExpire();
        }else{
            return 0;
        }
    }

    public static AccessToken newToken(){
        AccessToken accessToken = new AccessToken();
        accessToken.loginIp = Access.ip();
        accessToken.loginTime = Access.time();
        accessToken.accessIp = Access.ip();
        accessToken.accessTime = Access.time();
        accessToken.id = IdUtil.fastSimpleUUID();
        return accessToken;
    }

    // Getter methods for commonly used fields
    public String getToken() { return token; }
    public String getId() { return id; }
    public String getType() { return type; }
    public String getLoginIp() { return loginIp; }
    public Date getLoginTime() { return loginTime; }
    public String getAccessIp() { return accessIp; }
    public Date getAccessTime() { return accessTime; }
    public Long getUserId() { return userId; }
    public String getUserCode() { return userCode; }
    public String getUserNick() { return userNick; }
    public String getUserPasswd() { return userPasswd; }
    public List<String> getRoles() { return roles; }
    public List<String> getPermissions() { return permissions; }
    public Long getDeptId() { return deptId; }
    public String getDeptCode() { return deptCode; }
    public String getDeptName() { return deptName; }
    public Integer getClusterId() { return clusterId; }
    public Integer getClusterLevel() { return clusterLevel; }
    public String getClusterName() { return clusterName; }
    public int getValidCode() { return validCode; }
    public String getValidDesc() { return validDesc; }

    // Setter methods
    public void setToken(String token) { this.token = token; }
    public void setId(String id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setLoginIp(String loginIp) { this.loginIp = loginIp; }
    public void setLoginTime(Date loginTime) { this.loginTime = loginTime; }
    public void setAccessIp(String accessIp) { this.accessIp = accessIp; }
    public void setAccessTime(Date accessTime) { this.accessTime = accessTime; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setUserCode(String userCode) { this.userCode = userCode; }
    public void setUsername(String username) { this.username = username; }
    public void setUserNick(String userNick) { this.userNick = userNick; }
    public void setUserPasswd(String userPasswd) { this.userPasswd = userPasswd; }
    public void setRoles(List<String> roles) { this.roles = roles; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
    public void setDeptId(Long deptId) { this.deptId = deptId; }
    public void setDeptCode(String deptCode) { this.deptCode = deptCode; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    public void setClusterId(Integer clusterId) { this.clusterId = clusterId; }
    public void setClusterLevel(Integer clusterLevel) { this.clusterLevel = clusterLevel; }
    public void setClusterName(String clusterName) { this.clusterName = clusterName; }
    public void setValidCode(int validCode) { this.validCode = validCode; }
    public void setValidDesc(String validDesc) { this.validDesc = validDesc; }
    public void setAuthorities(List<? extends GrantedAuthority> authorities) { this.authorities = authorities; }
}
