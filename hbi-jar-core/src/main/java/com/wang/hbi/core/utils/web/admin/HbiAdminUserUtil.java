package com.wang.hbi.core.utils.web.admin;

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
                    DomainUrlUtil.COOKIE_DOMAIN,
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

}
