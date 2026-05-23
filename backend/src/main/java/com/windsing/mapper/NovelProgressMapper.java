package com.windsing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windsing.entity.NovelProgress;
import org.apache.ibatis.annotations.Mapper;

/**
 * 阅读进度 Mapper
 */
@Mapper
public interface NovelProgressMapper extends BaseMapper<NovelProgress> {
}
