package com.wang.hbi.generate.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 配置生成那些内容
 *
 * @Author HeJiawang
 * @Date 2017/11/19 20:31
 */
@Accessors(chain = true)
@Setter
@Getter
public class GenerConfig {

    /**
     * 业务包名
     */
    private String businessName;

    /**
     * 所在包
     */
    private String packageName;

    /**
     * 需要引入的类
     */
    private List<String> importList;

    /**
     * 表明
     */
    private String tableName;

    /**
     * 实体名
     */
    private String modelName;

    /**
     * dao 名
     */
    private String daoName;

    /**
     * service 名
     */
    private String serviceName;

    /**
     * service impl 名
     */
    private String serviceImplName;

    /**
     * mapper名
     */
    private String mapperName;

    /**
     * 类型
     */
    private Type type;

    /**
     * 包含字段
     */
    private List<Property> properties;
}
