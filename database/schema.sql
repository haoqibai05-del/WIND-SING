-- ============================================================
-- 风歌 WIND-SING 数据库初始化脚本
-- 字符集: utf8mb4  引擎: InnoDB
-- ============================================================

CREATE DATABASE IF NOT EXISTS `windsing` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `windsing`;

-- ----------------------------
-- 1. 系统用户表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '用户ID',
  `username`     VARCHAR(50)  NOT NULL                 COMMENT '登录用户名',
  `password`     VARCHAR(255) NOT NULL                 COMMENT '加密密码',
  `nickname`     VARCHAR(50)  DEFAULT NULL             COMMENT '展示昵称',
  `avatar_url`   VARCHAR(500) DEFAULT NULL             COMMENT '头像URL',
  `created_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- 插入默认管理员（密码为 admin123，生产环境请使用 BCrypt 加密）
INSERT INTO `sys_user` (`username`, `password`, `nickname`) VALUES ('admin', 'admin123', '风歌管理员');

-- ----------------------------
-- 2. 动态帖文表
-- ----------------------------
DROP TABLE IF EXISTS `timeline_post`;
CREATE TABLE `timeline_post` (
  `id`            BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '动态ID',
  `user_id`       BIGINT        NOT NULL                 COMMENT '发布者用户ID',
  `content`       TEXT                                   COMMENT '正文内容',
  `images`        VARCHAR(2000) DEFAULT NULL             COMMENT '配图URL列表（JSON数组）',
  `like_count`    INT           NOT NULL DEFAULT 0       COMMENT '点赞数',
  `comment_count` INT           NOT NULL DEFAULT 0       COMMENT '评论数',
  `created_at`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `updated_at`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='动态帖文表';

-- ----------------------------
-- 3. 追剧记录表
-- ----------------------------
DROP TABLE IF EXISTS `video_bookmark`;
CREATE TABLE `video_bookmark` (
  `id`             BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '记录ID',
  `user_id`        BIGINT       NOT NULL                 COMMENT '用户ID',
  `video_title`    VARCHAR(200) NOT NULL                 COMMENT '剧集/视频标题',
  `video_url`      VARCHAR(500) DEFAULT NULL             COMMENT '视频源地址',
  `episode_index`  INT          NOT NULL DEFAULT 1       COMMENT '当前集数',
  `episode_title`     VARCHAR(200) DEFAULT NULL             COMMENT '当前集标题',
  `progress_seconds`  DOUBLE       NOT NULL DEFAULT 0       COMMENT '已播放进度（秒）',
  `created_at`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='追剧记录表';

-- ----------------------------
-- 4. 私人歌单表
-- ----------------------------
DROP TABLE IF EXISTS `music_playlist`;
CREATE TABLE `music_playlist` (
  `id`         BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '歌曲ID',
  `user_id`    BIGINT       NOT NULL                 COMMENT '用户ID',
  `song_title` VARCHAR(200) NOT NULL                 COMMENT '歌曲名',
  `artist`     VARCHAR(100) DEFAULT NULL             COMMENT '歌手名',
  `cover_url`  VARCHAR(500) DEFAULT NULL             COMMENT '封面图URL',
  `source_url` VARCHAR(500) DEFAULT NULL             COMMENT '音源地址',
  `sort_order` INT          NOT NULL DEFAULT 0       COMMENT '排序序号',
  `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='私人歌单表';

-- ----------------------------
-- 5. 阅读进度表
-- ----------------------------
DROP TABLE IF EXISTS `novel_progress`;
CREATE TABLE `novel_progress` (
  `id`             BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '进度ID',
  `user_id`        BIGINT       NOT NULL                 COMMENT '用户ID',
  `novel_title`    VARCHAR(200) NOT NULL                 COMMENT '小说书名',
  `chapter_title`  VARCHAR(200) DEFAULT NULL             COMMENT '当前章节名',
  `chapter_index`  INT          NOT NULL DEFAULT 1       COMMENT '当前章节序号',
  `content`        MEDIUMTEXT                            COMMENT '章节正文（缓存）',
  `last_read_at`   DATETIME     DEFAULT NULL             COMMENT '最近阅读时间',
  `created_at`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_novel` (`user_id`, `novel_title`(50))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='阅读进度表';
