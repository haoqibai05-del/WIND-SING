package com.windsing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.windsing.common.Result;
import com.windsing.entity.NovelProgress;
import com.windsing.service.NovelProgressService;
import com.windsing.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 阅读进度 Controller
 */
@RestController
@RequestMapping("/api/novel")
public class NovelProgressController {

    private final NovelProgressService novelProgressService;

    public NovelProgressController(NovelProgressService novelProgressService) {
        this.novelProgressService = novelProgressService;
    }

    private Long extractUserId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return TokenUtil.validateToken(authHeader.substring(7));
        }
        return null;
    }

    /**
     * 获取当前用户书架（所有书籍及阅读进度）
     */
    @GetMapping("/bookshelf")
    public Result<List<NovelProgress>> bookshelf(HttpServletRequest request) {
        Long userId = extractUserId(request);
        if (userId == null) return Result.error(401, "未登录");

        LambdaQueryWrapper<NovelProgress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NovelProgress::getUserId, userId)
               .orderByDesc(NovelProgress::getLastReadAt);
        return Result.ok(novelProgressService.list(wrapper));
    }

    /**
     * 保存阅读进度（Upsert by userId + novelTitle）
     */
    @PostMapping("/save_progress")
    public Result<NovelProgress> saveProgress(@RequestBody Map<String, Object> body,
                                               HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) userId = extractUserId(request);
        if (userId == null) return Result.error(401, "未登录");

        String novelTitle   = (String) body.get("novelTitle");
        String chapterTitle = (String) body.get("chapterTitle");
        Integer chapterIndex = body.get("chapterIndex") != null
                ? ((Number) body.get("chapterIndex")).intValue() : 1;

        NovelProgress saved = novelProgressService.saveOrUpdateProgress(
                userId, novelTitle, chapterTitle, chapterIndex);
        return Result.ok(saved);
    }
}
