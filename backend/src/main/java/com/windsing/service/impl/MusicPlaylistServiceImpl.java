package com.windsing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windsing.entity.MusicPlaylist;
import com.windsing.mapper.MusicPlaylistMapper;
import com.windsing.service.MusicPlaylistService;
import org.springframework.stereotype.Service;

/**
 * 私人歌单 Service 实现
 */
@Service
public class MusicPlaylistServiceImpl extends ServiceImpl<MusicPlaylistMapper, MusicPlaylist>
        implements MusicPlaylistService {

    @Override
    public boolean removeIfOwner(Long id, Long userId) {
        MusicPlaylist item = this.getById(id);
        if (item == null || !item.getUserId().equals(userId)) return false;
        return this.removeById(id);
    }
}
