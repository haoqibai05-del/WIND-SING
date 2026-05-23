package com.windsing.util;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简易内存 Token 工具类（生产环境应使用 Redis + JWT）
 */
public class TokenUtil {

    /** Token 有效期 24 小时 */
    private static final long TOKEN_EXPIRE_MS = 24 * 60 * 60 * 1000;
    private static final ConcurrentHashMap<String, TokenInfo> STORE = new ConcurrentHashMap<>();

    /**
     * 生成 Token 并存入内存
     */
    public static String generateToken(Long userId, String username) {
        String token = UUID.randomUUID().toString().replace("-", "");
        STORE.put(token, new TokenInfo(userId, username, System.currentTimeMillis() + TOKEN_EXPIRE_MS));
        return token;
    }

    /**
     * 校验 Token，有效则返回 userId，否则返回 null
     */
    public static Long validateToken(String token) {
        TokenInfo info = STORE.get(token);
        if (info == null) return null;
        if (info.expireTime < System.currentTimeMillis()) {
            STORE.remove(token);
            return null;
        }
        return info.userId;
    }

    /**
     * 移除 Token（登出用）
     */
    public static void removeToken(String token) {
        STORE.remove(token);
    }

    private static class TokenInfo {
        final Long userId;
        final String username;
        final long expireTime;

        TokenInfo(Long userId, String username, long expireTime) {
            this.userId = userId;
            this.username = username;
            this.expireTime = expireTime;
        }
    }
}
