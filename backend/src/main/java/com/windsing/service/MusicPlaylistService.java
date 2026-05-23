package com.windsing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.windsing.entity.MusicPlaylist;

/**
 * 私人歌单 Service 接口
 */
public interface MusicPlaylistService extends IService<MusicPlaylist> {

    /**
     * 删除用户自己的歌曲（带权限校验）
     * @return true=删除成功, false=无权或不存在
     */
    boolean removeIfOwner(Long id, Long userId);
}
