package com.wang.hbi.authority.service;

import com.wang.hbi.authority.entrity.SysUserEntrity;

/**
 * 用户 service
 * @Author HeJiawang
 * @Date 2017/10/25 20:10
 */
public interface SysUserService {

    /**
     * 根据用户登录信息查找用户
     * @param loginName 登录名
     * @param password 密码
     * @return 用户信息
     */
    SysUserEntrity checkUser(String loginName, String password);
}
