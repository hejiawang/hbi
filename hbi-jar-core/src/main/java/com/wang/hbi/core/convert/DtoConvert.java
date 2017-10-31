package com.wang.hbi.core.convert;

/**
 * 对象转换接口
 * @author HeJiawang
 * @date   20171031
 */
public interface DtoConvert<S, T> {

    /**
     * 将资源对象转换为目标对象
     * @param s sourec
     * @return target
     */
    T convert(S s);
}
