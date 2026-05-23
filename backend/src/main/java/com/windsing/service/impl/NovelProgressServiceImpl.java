package com.windsing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windsing.entity.NovelProgress;
import com.windsing.mapper.NovelProgressMapper;
import com.windsing.service.NovelProgressService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 阅读进度 Service 实现
 */
@Service
public class NovelProgressServiceImpl extends ServiceImpl<NovelProgressMapper, NovelProgress>
        implements NovelProgressService {

    @Override
    public NovelProgress saveOrUpdateProgress(Long userId, String novelTitle,
                                               String chapterTitle, Integer chapterIndex) {
        LambdaQueryWrapper<NovelProgress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NovelProgress::getUserId, userId)
               .eq(NovelProgress::getNovelTitle, novelTitle);
        NovelProgress existing = this.getOne(wrapper);

        if (existing != null) {
            existing.setChapterTitle(chapterTitle);
            existing.setChapterIndex(chapterIndex);
            existing.setLastReadAt(LocalDateTime.now());
            existing.setUpdatedAt(LocalDateTime.now());
            this.updateById(existing);
            return existing;
        } else {
            NovelProgress np = new NovelProgress();
            np.setUserId(userId);
            np.setNovelTitle(novelTitle);
            np.setChapterTitle(chapterTitle);
            np.setChapterIndex(chapterIndex);
            np.setLastReadAt(LocalDateTime.now());
            this.save(np);
            return np;
        }
    }
}
