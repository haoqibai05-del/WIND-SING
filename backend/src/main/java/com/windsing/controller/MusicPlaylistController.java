package com.windsing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.windsing.common.Result;
import com.windsing.entity.MusicPlaylist;
import com.windsing.service.MusicPlaylistService;
import com.windsing.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 私人歌单 Controller
 */
@RestController
@RequestMapping("/api/music")
public class MusicPlaylistController {

    private final MusicPlaylistService musicPlaylistService;

    public MusicPlaylistController(MusicPlaylistService musicPlaylistService) {
        this.musicPlaylistService = musicPlaylistService;
    }

    /** 手动从 Authorization 头解析 userId */
    private Long extractUserId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return TokenUtil.validateToken(authHeader.substring(7));
        }
        return null;
    }

    /**
     * 获取当前用户的歌单列表
     */
    @GetMapping("/list")
    public Result<List<MusicPlaylist>> list(HttpServletRequest request) {
        Long userId = extractUserId(request);
        if (userId == null) {
            return Result.error(401, "未登录");
        }

        LambdaQueryWrapper<MusicPlaylist> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MusicPlaylist::getUserId, userId)
               .orderByAsc(MusicPlaylist::getSortOrder)
               .orderByDesc(MusicPlaylist::getCreatedAt);
        return Result.ok(musicPlaylistService.list(wrapper));
    }

    /**
     * 添加歌曲到歌单（需登录）
     */
    @PostMapping("/add")
    public Result<MusicPlaylist> add(@RequestBody MusicPlaylist song, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        song.setUserId(userId);
        musicPlaylistService.save(song);
        return Result.ok(song);
    }

    /**
     * 删除歌单中的歌曲（只能删自己的）
     */
    @DeleteMapping("/remove/{id}")
    public Result<Void> remove(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "未登录");
        }

        boolean ok = musicPlaylistService.removeIfOwner(id, userId);
        if (!ok) {
            return Result.error(403, "无权删除或歌曲不存在");
        }
        return Result.ok();
    }
}
