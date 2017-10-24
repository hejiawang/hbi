package com.wang.hbi.core.utils.web.admin;

import com.wang.hbi.authority.entrity.SysUserEntrity;
import com.wang.hbi.core.memcached.XMemcachedClientForSession;
import com.wang.hbi.core.memcached.XMemcachedConstants;
import com.wang.hbi.core.utils.password.Md5;
import com.wang.hbi.core.utils.password.SaltUtil;
import com.wang.hbi.core.utils.web.CookieHelper;
import com.wang.hbi.core.utils.web.ip.ClientIPUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Hbi Admin 工程，用户缓存工具类
 *
 * @author HeJiawang
 * @date   20171019
 */
public class HbiAdminUserUtil {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(HbiAdminUserUtil.class);

    /**
     * 允许用户名密码输入错误次数
     */
    public final static long   ALLOW_ERROR_COUNT = 3;

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
    private static final String SESSION_ID_CACHE_KEY = "HBI_SESSION_ID_CACHE_KEY";

    /**
     * cookie key
     */
    public final static String USER_COOKIE_KEY  = "HBI_SOMPLE_ADMIN";

    /**
     * cookie id
     */
    private static final String COOKIE_SESSION_ID = USER_COOKIE_KEY + "_SESSION_ID";

    /**
     * cookie domain
     */
    private static final String COOKIE_DOMAIN = "HBI_COOKIE_DOMAIN";

    /**
     * memcached 记录admin工程session信息
     */
    public final static String NAMESPACE_HBI_WEB_ADMIN     = "NAMESPACE_HBI_WEB_ADMIN";
    public final static String NAMESPACE_HBI_WEB_ADMIN_SESSION = NAMESPACE_HBI_WEB_ADMIN + "_SESSION_";

    /**
     * 获取请求的sessionId
     * @param request 请求
     * @return sessionId
     */
    public static String getSessionId( HttpServletRequest request ){
        if (request == null) return null;

        String sessionId = (String) request.getAttribute(SESSION_ID_CACHE_KEY);
        if (sessionId != null)  return sessionId;

        Cookie cookie = CookieHelper.getCookieByName(request, COOKIE_SESSION_ID);
        if( cookie != null ) sessionId = cookie.getValue();

        request.setAttribute(SESSION_ID_CACHE_KEY, sessionId);
        return sessionId;
    }

    /**
     * 生成sessionId写入cookie
     */
    public static String getOrCreateSessionId(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = getSessionId(request);
        if (StringUtils.isEmpty(sessionId)) {
            sessionId = genSessionId(ClientIPUtils.getIpAddr(request));
            CookieHelper.addCookie(
                    COOKIE_SESSION_ID,
                    sessionId,
                    COOKIE_DOMAIN,
                    "/",
                    -1, //session有效时间为关闭浏览器失效
                    response
            );
        }
        request.setAttribute(SESSION_ID_CACHE_KEY, sessionId);
        return sessionId;
    }

    /**
     * 生成sessionId
     *
     * @param ip 客户端IP
     * @return 生成的 sessionId 应用:在用户访问页面里，如果没有sessionId时，调用生成。
     */
    public static String genSessionId(String ip) {
        return Md5.getMd5String(ip + System.currentTimeMillis() + SaltUtil.generateWord(4));
    }

    /**
     * 将用户信息存至Memcached
     * @param request request
     * @param user 用户信息
     * @return 用户信息
     */
    public static SysUserEntrity WriteUserToMemcached(HttpServletRequest request, SysUserEntrity user){
        try {
            if (request == null || user == null) return new SysUserEntrity();

            user.setCurrentIp(ClientIPUtils.getIpAddr(request));
            WriteUserToMemcached(getSessionId(request), user);
        } catch (Exception e) {
            logger.error("用户信息写入memcached中异常", e);
        }
        return user;
    }

    /**
     * 缓存用户信息至memcached,缓存时间为7天
     * @param sessionId sessionId
     * @param user 用户信息
     */
    public static void WriteUserToMemcached(String sessionId, SysUserEntrity user){
        if (user == null || sessionId == null)  return ;

        try {
            final String key = NAMESPACE_HBI_WEB_ADMIN_SESSION + sessionId;
            XMemcachedClientForSession.set(key, XMemcachedConstants.TIME_OUT_ONE_HOUR, user);
        } catch (Exception e) {
            logger.error("用户信息写入memcached中异常", e);
        }
    }

    /**
     * 从memcached中获取用户信息
     * @param request request
     * @return 用户信息
     */
    public static SysUserEntrity getUserByRequest(HttpServletRequest request) {
        return getUserBySessionId(getSessionId(request));
    }

    /**
     * 从memcached中获取用户信息
     * @param sessionId sessionId
     * @return 用户信息
     */
    public static SysUserEntrity getUserBySessionId(String sessionId) {
        try {
            String key = NAMESPACE_HBI_WEB_ADMIN_SESSION  + sessionId;

            SysUserEntrity user = (SysUserEntrity) XMemcachedClientForSession.get(key);
            if (user == null) return new SysUserEntrity();

            return  user;
        } catch (Exception e) {
            logger.error("获取从memcached中frontendUser异常", e);
            return new SysUserEntrity();
        }
    }
}
