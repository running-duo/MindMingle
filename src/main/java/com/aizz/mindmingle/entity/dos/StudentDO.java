package com.aizz.mindmingle.entity.dos;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 学生表
 *
 * @author zhangyuliang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDO {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 家长电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 微信id
     */
    @TableField(value = "wx_id")
    private String wxId;

    /**
     * 总课时
     */
    @TableField(value = "total_course")
    private Integer totalCourse;

    /**
     * 剩余课时
     */
    @TableField(value = "remain_course")
    private Integer remainCourse;

    /**
     * 课程有效期
     */
    @TableField(value = "course_valid_date")
    private Date courseValidDate;

    /**
     * 备注
     */
    @TableField(value = "memo")
    private String memo;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date updateTime;
}
