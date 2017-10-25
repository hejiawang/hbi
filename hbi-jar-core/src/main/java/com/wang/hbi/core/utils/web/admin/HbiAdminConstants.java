package com.wang.hbi.core.utils.web.admin;

/**
 * @Author HeJiawang
 * @Date 2017/10/25 20:57
 */
public interface HbiAdminConstants {

    /**
     * 允许用户名密码输入错误次数
     */
    public final static long ALLOW_ERROR_COUNT = 3;

    /**
     * memcached 记录admin工程登录错误次数
     */
    public final static String LOGIN_ERROR_COUNT = "HBI_ADMIN_LOGIN_ERROR_COUNT";

    /**
     * memcached 记录admin工程验证码
     */
    public final static String CAPTCHA = "HBI_ADMIN_CAPTCHA";

    /**
     * session key
     */
    public static final String SESSION_ID_CACHE_KEY = "HBI_SESSION_ID_CACHE_KEY";

    /**
     * cookie key
     */
    public final static String USER_COOKIE_KEY  = "HBI_SOMPLE_ADMIN";

    /**
     * cookie id
     */
    public static final String COOKIE_SESSION_ID = USER_COOKIE_KEY + "_SESSION_ID";

    /**
     * cookie domain
     */
    public static final String COOKIE_DOMAIN = "HBI_COOKIE_DOMAIN";

    /**
     * memcached 记录admin工程session信息
     */
    public final static String NAMESPACE_HBI_WEB_ADMIN     = "NAMESPACE_HBI_WEB_ADMIN";
    public final static String NAMESPACE_HBI_WEB_ADMIN_SESSION = NAMESPACE_HBI_WEB_ADMIN + "_SESSION_";

}
