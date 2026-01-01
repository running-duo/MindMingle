package com.aizz.mindmingle.domain.dos;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 学生表
 *
 * @author zhangyuliang
 */
@TableName("t_student")
public class StudentDO {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    public Long id;

    /**
     * 学生名称
     */
    @TableField(value = "name")
    public String name;

    /**
     * 家长电话
     */
    @TableField(value = "phone")
    public String phone;

    /**
     * 微信id
     */
    @TableField(value = "wx_id")
    public String wxId;

    /**
     * 总课时
     */
    @TableField(value = "total_course")
    public Integer totalCourse;

    /**
     * 剩余课时
     */
    @TableField(value = "remain_course")
    public Integer remainCourse;

    /**
     * 课程有效期
     */
    @TableField(value = "course_valid_date")
    public Date courseValidDate;

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
    public StudentDO() {
    }

    public StudentDO(Long id, String name, String phone, String wxId, Integer totalCourse, Integer remainCourse, Date courseValidDate, String memo, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.wxId = wxId;
        this.totalCourse = totalCourse;
        this.remainCourse = remainCourse;
        this.courseValidDate = courseValidDate;
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

    public Integer getTotalCourse() {
        return totalCourse;
    }

    public Integer getRemainCourse() {
        return remainCourse;
    }

    public Date getCourseValidDate() {
        return courseValidDate;
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

    public void setTotalCourse(Integer totalCourse) {
        this.totalCourse = totalCourse;
    }

    public void setRemainCourse(Integer remainCourse) {
        this.remainCourse = remainCourse;
    }

    public void setCourseValidDate(Date courseValidDate) {
        this.courseValidDate = courseValidDate;
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
    public static StudentDOBuilder builder() {
        return new StudentDOBuilder();
    }

    public static class StudentDOBuilder {
        private Long id;
        private String name;
        private String phone;
        private String wxId;
        private Integer totalCourse;
        private Integer remainCourse;
        private Date courseValidDate;
        private String memo;
        private Date createTime;
        private Date updateTime;

        StudentDOBuilder() {
        }

        public StudentDOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public StudentDOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StudentDOBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public StudentDOBuilder wxId(String wxId) {
            this.wxId = wxId;
            return this;
        }

        public StudentDOBuilder totalCourse(Integer totalCourse) {
            this.totalCourse = totalCourse;
            return this;
        }

        public StudentDOBuilder remainCourse(Integer remainCourse) {
            this.remainCourse = remainCourse;
            return this;
        }

        public StudentDOBuilder courseValidDate(Date courseValidDate) {
            this.courseValidDate = courseValidDate;
            return this;
        }

        public StudentDOBuilder memo(String memo) {
            this.memo = memo;
            return this;
        }

        public StudentDOBuilder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public StudentDOBuilder updateTime(Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public StudentDO build() {
            return new StudentDO(id, name, phone, wxId, totalCourse, remainCourse, courseValidDate, memo, createTime, updateTime);
        }
    }
}
