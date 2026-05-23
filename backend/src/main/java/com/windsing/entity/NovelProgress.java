package com.windsing.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 阅读进度实体
 */
@Data
@TableName("novel_progress")
public class NovelProgress {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String novelTitle;

    private String chapterTitle;

    private Integer chapterIndex;

    /** 章节正文缓存 */
    private String content;

    private LocalDateTime lastReadAt;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
