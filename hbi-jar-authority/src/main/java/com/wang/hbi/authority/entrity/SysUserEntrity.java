package com.wang.hbi.authority.entrity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户
 * @author HeJiawang
 * @date   20171016
 */
@Accessors(chain = true)
@Setter
@Getter
@Entity
@Table(name = "sys_user")
public class SysUserEntrity implements Serializable {

    private static final long serialVersionUID = 7398272058924381572L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID")
    private String id;

    /**
     * 登录名
     */
    @Column(name = "LOGIN_NAME")
    private String loginName;

    /**
     * 登录密码
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 真实姓名
     */
    @Column(name = "REAL_NAME")
    private String realName;

    /**
     * 联系电话
     */
    @Column(name = "TELEPHONE")
    private String telephone;

    /**
     * IP 地址
     */
    @Transient
    private String currentIp;

}
