package com.wang.hbi.authority.dto;

import com.wang.hbi.authority.entrity.SysUserEntrity;
import com.wang.hbi.core.convert.DtoConvert;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户 DTO
 * @author HeJiawang
 * @date   20171031
 */
public class SysUserDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7176351779997451525L;

    /**
     * ID
     */
    private String id;

    /**
     * 登录名
     */
    @NotNull
    private String loginName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 登录密码2
     */
    private String password2;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * IP 地址
     */
    private String currentIp;

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getCurrentIp() {
        return currentIp;
    }

    public void setCurrentIp(String currentIp) {
        this.currentIp = currentIp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * SysUserDto转换为SysUserEntrity
     * @return SysUserEntrity
     */
    public SysUserEntrity convertToUserEntrity(){
        SysUserDtoConvert sysUserDtoConvert = new SysUserDtoConvert();
        SysUserEntrity userEntrity = sysUserDtoConvert.doForward(this);

        return userEntrity;
    }

    /**
     * SysUserEntrity转换为SysUserDto
     * @return SysUserDto
     */
    public SysUserDto convertToUserDto( SysUserEntrity userEntrity ){
        SysUserDtoConvert sysUserDtoConvert = new SysUserDtoConvert();

        return sysUserDtoConvert.doBackward(userEntrity);
    }

    /**
     * SysUserDto 转换
     */
    private static class SysUserDtoConvert implements DtoConvert<SysUserDto, SysUserEntrity> {

        @Override
        public SysUserEntrity doForward(SysUserDto sysUserDto) {
            SysUserEntrity userEntrity = new SysUserEntrity();
            BeanUtils.copyProperties(sysUserDto, userEntrity);

            return userEntrity;
        }

        @Override
        public SysUserDto doBackward(SysUserEntrity sysUserEntrity) {
            SysUserDto userDto = new SysUserDto();
            BeanUtils.copyProperties(sysUserEntrity, userDto);

            return userDto;
        }
    }
}
