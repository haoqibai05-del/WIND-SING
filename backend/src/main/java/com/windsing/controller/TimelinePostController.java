package com.windsing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.windsing.common.Result;
import com.windsing.entity.TimelinePost;
import com.windsing.service.TimelinePostService;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * 发布一条新动态（需登录）
     */
    @PostMapping("/add")
    public Result<Void> add(@RequestBody TimelinePost post, HttpServletRequest request) {
        // 从拦截器注入的 userId 覆盖请求体中的值，防止伪造
        Long userId = (Long) request.getAttribute("userId");
        post.setUserId(userId);
        timelinePostService.save(post);
        return Result.ok();
    }
}
