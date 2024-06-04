package com.aizz.mindmingle.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class AccessUser {

    /**
     * 用户id
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private Long accessUserId;

    /**
     * 用户编码
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String accessUserCode;

    /**
     * 用户账号
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String accessUserAccount;

    /**
     * 用户名称
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String accessUserName;

    /**
     * 访问时间
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private Date accessTime = new Date();

    /**
     * 部门id
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private Long accessDeptId;

    /**
     * 部门编码
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String accessDeptCode;

    /**
     * 部门名称
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private String accessDeptName;

    /**
     * 开始时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 结束时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
