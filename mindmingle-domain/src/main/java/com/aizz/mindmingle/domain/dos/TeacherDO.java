package com.aizz.mindmingle.domain.dos;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 教师表
 *
 * @author zhangyuliang
 */
@TableName("t_teacher")
public class TeacherDO {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    public Long id;

    /**
     * 教师名称
     */
    @TableField(value = "name")
    public String name;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    public String phone;

    /**
     * 微信id
     */
    @TableField(value = "wx_id")
    public String wxId;

    /**
     * 备注
     */
    @TableField(value = "memo")
    public String memo;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    public Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    public Date updateTime;

    // Constructors
    public TeacherDO() {
    }

    public TeacherDO(Long id, String name, String phone, String wxId, String memo, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.wxId = wxId;
        this.memo = memo;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getWxId() {
        return wxId;
    }

    public String getMemo() {
        return memo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    // Builder
    public static TeacherDOBuilder builder() {
        return new TeacherDOBuilder();
    }

    public static class TeacherDOBuilder {
        private Long id;
        private String name;
        private String phone;
        private String wxId;
        private String memo;
        private Date createTime;
        private Date updateTime;

        TeacherDOBuilder() {
        }

        public TeacherDOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TeacherDOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TeacherDOBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public TeacherDOBuilder wxId(String wxId) {
            this.wxId = wxId;
            return this;
        }

        public TeacherDOBuilder memo(String memo) {
            this.memo = memo;
            return this;
        }

        public TeacherDOBuilder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public TeacherDOBuilder updateTime(Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public TeacherDO build() {
            return new TeacherDO(id, name, phone, wxId, memo, createTime, updateTime);
        }
    }
}
