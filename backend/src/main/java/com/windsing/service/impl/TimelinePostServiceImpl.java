package com.windsing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windsing.entity.TimelinePost;
import com.windsing.mapper.TimelinePostMapper;
import com.windsing.service.TimelinePostService;
import org.springframework.stereotype.Service;

/**
 * 动态帖文 Service 实现
 */
@Service
public class TimelinePostServiceImpl extends ServiceImpl<TimelinePostMapper, TimelinePost>
        implements TimelinePostService {
}
