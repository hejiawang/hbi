package com.wang.hbi.authority.dao;

import com.wang.hbi.authority.entrity.SysUserEntrity;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * 用户信息 dao
 * @Author HeJiawang
 * @Date 2017/10/25 20:14
 */
@RepositoryDefinition(domainClass = SysUserEntrity.class, idClass = String.class)
public interface SysUserDao {

    /**
     * 根据用户登录信息查找用户
     * @param loginName 登录名
     * @param password 密码
     * @return 用户信息
     */
    SysUserEntrity findByLoginNameAndPassword(String loginName, String password);

    /**
     * 保存用户信息
     * @param userEntrity 用户信息
     * @return 用户信息
     */
    SysUserEntrity save(SysUserEntrity userEntrity);
}
