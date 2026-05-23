package com.windsing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windsing.entity.VideoBookmark;
import com.windsing.mapper.VideoBookmarkMapper;
import com.windsing.service.VideoBookmarkService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 追剧记录 Service 实现
 */
@Service
public class VideoBookmarkServiceImpl extends ServiceImpl<VideoBookmarkMapper, VideoBookmark>
        implements VideoBookmarkService {

    @Override
    public VideoBookmark saveOrUpdateProgress(Long userId, String videoTitle, String videoUrl,
                                               Integer episodeIndex, String episodeTitle,
                                               Double progressSeconds) {
        // 根据 userId + videoUrl 查找已有记录
        LambdaQueryWrapper<VideoBookmark> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoBookmark::getUserId, userId)
               .eq(VideoBookmark::getVideoUrl, videoUrl);
        VideoBookmark existing = this.getOne(wrapper);

        if (existing != null) {
            // UPDATE：更新进度和最后观看时间
            existing.setProgressSeconds(progressSeconds);
            existing.setEpisodeIndex(episodeIndex != null ? episodeIndex : existing.getEpisodeIndex());
            if (episodeTitle != null) existing.setEpisodeTitle(episodeTitle);
            existing.setUpdatedAt(LocalDateTime.now());
            this.updateById(existing);
            return existing;
        } else {
            // INSERT：新建追剧记录
            VideoBookmark bookmark = new VideoBookmark();
            bookmark.setUserId(userId);
            bookmark.setVideoTitle(videoTitle);
            bookmark.setVideoUrl(videoUrl);
            bookmark.setEpisodeIndex(episodeIndex != null ? episodeIndex : 1);
            bookmark.setEpisodeTitle(episodeTitle);
            bookmark.setProgressSeconds(progressSeconds);
            this.save(bookmark);
            return bookmark;
        }
    }
}
