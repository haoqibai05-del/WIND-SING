package com.windsing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.windsing.entity.NovelProgress;

/**
 * 阅读进度 Service 接口
 */
public interface NovelProgressService extends IService<NovelProgress> {

    /**
     * 保存/更新阅读进度（Upsert by userId + novelTitle）
     */
    NovelProgress saveOrUpdateProgress(Long userId, String novelTitle,
                                       String chapterTitle, Integer chapterIndex);
}
