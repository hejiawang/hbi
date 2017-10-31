package com.wang.hbi.authority.service.impl;

import com.wang.hbi.authority.dao.SysUserDao;
import com.wang.hbi.authority.dto.SysUserDto;
import com.wang.hbi.authority.entrity.SysUserEntrity;
import com.wang.hbi.authority.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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

    /**
     * 添加用户
     * @param sysUserDto 用户信息
     * @return 是否添加用户成功
     */
    @Override
    public Boolean saveUser(SysUserDto sysUserDto) {
        if( !StringUtils.equals(sysUserDto.getPassword2(), sysUserDto.getPassword()) ) return false;

        SysUserEntrity userEntrity = sysUserDto.convertToUserEntrity();
        userEntrity = sysUserDao.save( userEntrity );
        return userEntrity!=null;
    }

}
