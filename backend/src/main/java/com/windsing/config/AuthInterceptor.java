package com.windsing.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.windsing.common.Result;
import com.windsing.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

/**
 * 登录鉴权拦截器
 * 放行 GET 查询请求，拦截 JSON 修改类请求
 */
public class AuthInterceptor implements HandlerInterceptor {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 放行 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;

        String path = request.getRequestURI();
        String method = request.getMethod().toUpperCase();

        // 登录接口直接放行
        if ("POST".equals(method) && path.equals("/api/user/login")) return true;

        // GET 请求全部放行（查询类操作）
        if ("GET".equals(method)) return true;

        // 其余 POST / PUT / DELETE 需携带 Token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            write401(response, "未登录，请先登录");
            return false;
        }

        String token = authHeader.substring(7);
        Long userId = TokenUtil.validateToken(token);
        if (userId == null) {
            write401(response, "Token无效或已过期");
            return false;
        }

        // 将 userId 存入 request，Controller 可通过 getAttribute 获取
        request.setAttribute("userId", userId);
        return true;
    }

    private void write401(HttpServletResponse response, String message) throws Exception {
        response.setStatus(200); // 业务层返回200，code为401
        response.setContentType("application/json;charset=UTF-8");
        Result<Void> result = Result.error(401, message);
        PrintWriter out = response.getWriter();
        out.write(MAPPER.writeValueAsString(result));
        out.flush();
    }
}
