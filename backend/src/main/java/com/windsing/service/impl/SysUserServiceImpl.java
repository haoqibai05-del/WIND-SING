package com.windsing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windsing.entity.SysUser;
import com.windsing.mapper.SysUserMapper;
import com.windsing.service.SysUserService;
import com.windsing.util.TokenUtil;
import org.springframework.stereotype.Service;

/**
 * 用户 Service 实现
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {

    @Override
    public String login(String username, String password) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        SysUser user = this.getOne(wrapper);

        if (user == null) return null;
        // 生产环境应使用 BCryptPasswordEncoder 比对加密密码
        if (!password.equals(user.getPassword())) return null;

        return TokenUtil.generateToken(user.getId(), user.getUsername());
    }
}
