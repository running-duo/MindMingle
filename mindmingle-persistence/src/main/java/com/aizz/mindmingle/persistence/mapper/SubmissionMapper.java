package com.aizz.mindmingle.persistence.mapper;

import com.aizz.mindmingle.domain.dos.SubmissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工具提交数据Mapper
 */
@Mapper
public interface SubmissionMapper extends BaseMapper<SubmissionDO> {
}
