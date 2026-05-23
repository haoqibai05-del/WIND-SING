package com.windsing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.windsing.entity.SysUser;

/**
 * 用户 Service 接口
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 登录校验，成功返回 Token，失败返回 null
     */
    String login(String username, String password);
}
