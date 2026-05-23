package com.windsing.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 追剧记录实体
 */
@Data
@TableName("video_bookmark")
public class VideoBookmark {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String videoTitle;

    private String videoUrl;

    private Integer episodeIndex;

    private String episodeTitle;

    /** 已播放进度（秒） */
    private Double progressSeconds;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
