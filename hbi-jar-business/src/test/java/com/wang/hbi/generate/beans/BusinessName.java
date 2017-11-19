package com.wang.hbi.generate.beans;

/**
 * 业务包名
 * @Author HeJiawang
 * @Date 2017/11/19 20:06
 */
public enum BusinessName {


    AUTHORITY("authority"), BUSINESS("business");

    private String name;

    BusinessName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
