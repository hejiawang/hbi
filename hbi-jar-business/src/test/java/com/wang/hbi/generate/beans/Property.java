package com.wang.hbi.generate.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 实体类属性
 *
 * @Author HeJiawang
 * @Date 2017/11/19 20:36
 */
@Accessors(chain = true)
@Setter
@Getter
public class Property {

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 是否为主键
     */
    private boolean isId;

    private String columnType;

    private String propertyName;

    private String type;

    public Property setIsId( boolean isId ){
        this.isId = isId;
        return this;
    }

    public boolean getIsId(){
        return this.isId;
    }
}
