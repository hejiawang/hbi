package com.wang.hbi.core.memcached;

/**
 *  Memcached 常量
 *
 * @author HeJiawang
 * @date 20171019
 */
public class XMemcachedConstants {

    /**
     * memcached半分钟失效
     */
    public final static int TIME_OUT_HALF_MINUTES = 30;

    /**
     * memcached一分钟失效
     */
    public final static int TIME_OUT_ONE_MINUTES  = 60;

    /**
     * memcached五分钟失效
     */
    public final static int TIME_OUT_FIVE_MINUTES = 5 * 60;

    /**
     * memcached十分钟失效
     */
    public final static int TIME_OUT_TEN_MINUTES = 10 * 60;

    /**
     * memcached半小时失效
     */
    public final static int TIME_OUT_HALF_HOURS = 30 * 60;

    /**
     * memcached一小时失效
     */
    public final static int TIME_OUT_ONE_HOUR = 60 * 60;

    /**
     * memcached一天失效
     */
    public final static int TIME_OUT_DAY = 24 * 60 * 60;

    /**
     * memcached一周失效
     */
    public final static int TIME_OUT_WEEK = 7 * 24 * 60 * 60;

    /**
     * memcached一月失效
     */
    public final static int TIME_OUT_MONTH = 30 * 24 * 60 * 60;
}
