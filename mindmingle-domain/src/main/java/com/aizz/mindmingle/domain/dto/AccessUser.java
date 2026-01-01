package com.aizz.mindmingle.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class AccessUser {

    /**
     * 用户id
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public Long accessUserId;

    /**
     * 用户编码
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public String accessUserCode;

    /**
     * 用户账号
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public String accessUserAccount;

    /**
     * 用户名称
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public String accessUserName;

    /**
     * 访问时间
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public Date accessTime = new Date();

    /**
     * 部门id
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public Long accessDeptId;

    /**
     * 部门编码
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public String accessDeptCode;

    /**
     * 部门名称
     */
    @JsonIgnore
    @JSONField(serialize = false)
    public String accessDeptName;

    /**
     * 开始时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date beginTime;

    /**
     * 结束时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date endTime;

    // Getter methods
    public Long getAccessUserId() { return accessUserId; }
    public String getAccessUserCode() { return accessUserCode; }
    public String getAccessUserAccount() { return accessUserAccount; }
    public String getAccessUserName() { return accessUserName; }
    public Date getAccessTime() { return accessTime; }
    public Long getAccessDeptId() { return accessDeptId; }
    public String getAccessDeptCode() { return accessDeptCode; }
    public String getAccessDeptName() { return accessDeptName; }
    public Date getBeginTime() { return beginTime; }
    public Date getEndTime() { return endTime; }

    // Setter methods
    public void setAccessUserId(Long accessUserId) { this.accessUserId = accessUserId; }
    public void setAccessUserCode(String accessUserCode) { this.accessUserCode = accessUserCode; }
    public void setAccessUserAccount(String accessUserAccount) { this.accessUserAccount = accessUserAccount; }
    public void setAccessUserName(String accessUserName) { this.accessUserName = accessUserName; }
    public void setAccessTime(Date accessTime) { this.accessTime = accessTime; }
    public void setAccessDeptId(Long accessDeptId) { this.accessDeptId = accessDeptId; }
    public void setAccessDeptCode(String accessDeptCode) { this.accessDeptCode = accessDeptCode; }
    public void setAccessDeptName(String accessDeptName) { this.accessDeptName = accessDeptName; }
    public void setBeginTime(Date beginTime) { this.beginTime = beginTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
}
