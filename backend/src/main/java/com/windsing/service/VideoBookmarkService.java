package com.windsing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.windsing.entity.VideoBookmark;

/**
 * 追剧记录 Service 接口
 */
public interface VideoBookmarkService extends IService<VideoBookmark> {

    /**
     * 保存或更新播放进度（Upsert）
     * @return 更新后的记录
     */
    VideoBookmark saveOrUpdateProgress(Long userId, String videoTitle, String videoUrl,
                                       Integer episodeIndex, String episodeTitle, Double progressSeconds);
}
