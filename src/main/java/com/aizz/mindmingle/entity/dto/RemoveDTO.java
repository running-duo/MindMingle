package com.aizz.mindmingle.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangyuliang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemoveDTO {

    List<Long> ids;
}
