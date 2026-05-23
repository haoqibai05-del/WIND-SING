package com.windsing.controller;

import com.windsing.common.Result;
import com.windsing.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户 Controller
 */
@RestController
@RequestMapping("/api/user")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 登录 - 返回 Token
     */
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            return Result.error(400, "账号和密码不能为空");
        }

        String token = sysUserService.login(username.trim(), password);
        if (token == null) {
            return Result.error(401, "账号或密码错误");
        }

        return Result.ok(Map.of("token", token));
    }
}
