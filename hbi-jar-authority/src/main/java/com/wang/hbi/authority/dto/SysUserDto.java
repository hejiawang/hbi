package com.wang.hbi.authority.dto;

import com.wang.hbi.authority.entrity.SysUserEntrity;
import com.wang.hbi.core.convert.DtoConvert;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户 DTO
 * @author HeJiawang
 * @date   20171031
 */
@Accessors(chain = true)
@Setter
@Getter
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
