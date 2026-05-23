package com.windsing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.windsing.common.Result;
import com.windsing.entity.VideoBookmark;
import com.windsing.service.VideoBookmarkService;
import com.windsing.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 追剧记录 Controller
 */
@RestController
@RequestMapping("/api/video")
public class VideoBookmarkController {

    private final VideoBookmarkService videoBookmarkService;

    public VideoBookmarkController(VideoBookmarkService videoBookmarkService) {
        this.videoBookmarkService = videoBookmarkService;
    }

    /**
     * 从请求头中手动解析 userId（GET 请求不被拦截器保护，需手动提取）
     */
    private Long extractUserId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return TokenUtil.validateToken(authHeader.substring(7));
        }
        return null;
    }

    /**
     * 获取当前用户的追剧列表
     */
    @GetMapping("/bookmarks")
    public Result<List<VideoBookmark>> bookmarks(HttpServletRequest request) {
        Long userId = extractUserId(request);
        if (userId == null) {
            return Result.error(401, "未登录");
        }

        LambdaQueryWrapper<VideoBookmark> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoBookmark::getUserId, userId)
               .orderByDesc(VideoBookmark::getUpdatedAt);
        List<VideoBookmark> list = videoBookmarkService.list(wrapper);
        return Result.ok(list);
    }

    /**
     * 保存 / 更新播放进度（Upsert）
     */
    @PostMapping("/save_progress")
    public Result<VideoBookmark> saveProgress(@RequestBody Map<String, Object> body,
                                              HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            // 兜底：手动从 Token 解析
            userId = extractUserId(request);
        }
        if (userId == null) {
            return Result.error(401, "未登录");
        }

        String videoTitle   = (String) body.get("videoTitle");
        String videoUrl     = (String) body.get("videoUrl");
        Integer episodeIndex = body.get("episodeIndex") != null
                ? ((Number) body.get("episodeIndex")).intValue() : 1;
        String episodeTitle = (String) body.get("episodeTitle");
        Double progressSeconds = body.get("progressSeconds") != null
                ? ((Number) body.get("progressSeconds")).doubleValue() : 0.0;

        VideoBookmark saved = videoBookmarkService.saveOrUpdateProgress(
                userId, videoTitle, videoUrl, episodeIndex, episodeTitle, progressSeconds);
        return Result.ok(saved);
    }
}
