package com.windsing.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 私人歌单实体
 */
@Data
@TableName("music_playlist")
public class MusicPlaylist {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String songTitle;

    private String artist;

    private String coverUrl;

    private String sourceUrl;

    private Integer sortOrder;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
