package com.windsing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.windsing.entity.MusicPlaylist;
import org.apache.ibatis.annotations.Mapper;

/**
 * 私人歌单 Mapper
 */
@Mapper
public interface MusicPlaylistMapper extends BaseMapper<MusicPlaylist> {
}
