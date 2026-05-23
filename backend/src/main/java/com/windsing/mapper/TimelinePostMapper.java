package com.windsing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windsing.entity.TimelinePost;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动态帖文 Mapper
 */
@Mapper
public interface TimelinePostMapper extends BaseMapper<TimelinePost> {
}
