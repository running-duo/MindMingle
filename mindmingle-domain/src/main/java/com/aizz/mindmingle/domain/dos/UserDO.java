package com.aizz.mindmingle.domain.dos;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户表
 *
 * @author zhangyuliang
 */
@Data
@TableName("t_user")
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码（BCrypt加密）
     */
    @TableField(value = "password")
    private String password;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 头像URL
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 状态：0-禁用，1-启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 最后登录时间
     */
    @TableField(value = "last_login_time")
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    @TableField(value = "last_login_ip")
    private String lastLoginIp;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
}
