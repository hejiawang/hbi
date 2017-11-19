package com.wang.hbi.generate.beans;

/**
 * 包类型
 * @Author HeJiawang
 * @Date 2017/11/19 20:24
 */
public enum Type {

    MODEL("model"), SERVICE("service"), SERVICEIMPL("serviceImpl"), DAO("dao"), MAPPER("mapper");

    private String type;

    Type (String type) {
        this.type = type;
    }

    public String toString() {
        return this.type;
    }
}
