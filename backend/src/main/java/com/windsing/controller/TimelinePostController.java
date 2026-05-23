package com.windsing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.windsing.common.Result;
import com.windsing.entity.TimelinePost;
import com.windsing.service.TimelinePostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 动态帖文 Controller
 */
@RestController
@RequestMapping("/api/timeline")
public class TimelinePostController {

    private final TimelinePostService timelinePostService;

    public TimelinePostController(TimelinePostService timelinePostService) {
        this.timelinePostService = timelinePostService;
    }

    /**
     * 查询所有动态，按发布时间倒序
     */
    @GetMapping("/list")
    public Result<List<TimelinePost>> list() {
        LambdaQueryWrapper<TimelinePost> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TimelinePost::getCreatedAt);
        List<TimelinePost> list = timelinePostService.list(wrapper);
        return Result.ok(list);
    }
}
