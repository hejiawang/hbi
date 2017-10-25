package com.wang.hbi.authority.service.impl;

import com.wang.hbi.authority.dao.SysUserDao;
import com.wang.hbi.authority.entrity.SysUserEntrity;
import com.wang.hbi.authority.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author HeJiawang
 * @Date 2017/10/25 20:11
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    /**
     * 根据用户登录信息查找用户
     * @param loginName 登录名
     * @param password 密码
     * @return 用户信息
     */
    @Override
    public SysUserEntrity checkUser(String loginName, String password) {
        return sysUserDao.findByLoginNameAndPassword(loginName, password);
    }
}
