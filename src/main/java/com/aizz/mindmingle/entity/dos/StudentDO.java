package com.aizz.mindmingle.entity.dos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Long id;

    private String name;


}
