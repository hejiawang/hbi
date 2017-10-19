package com.wang.hbi.core.utils.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Cookie工具类包
 * @author HeJiawang
 * @date   20171012
 */
public class CookieHelper {

    private static final Logger logger = LoggerFactory.getLogger(CookieHelper.class);

    /**
     * 获得cookie
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }


    /**
     * get cookie to map
     * @param request request
     * @return cookie map
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 添加cookie
     * @param cookieName cookieName
     * @param value value
     * @param domain domain
     * @param cookiePath cookiePath
     * @param cookieExpiryDate cookieExpiryDate
     * @param response response
     */
    public static void addCookie(String cookieName, String value, String domain, String cookiePath, int cookieExpiryDate, HttpServletResponse response) {
        logger.info("正在写入Cookie.......");
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setDomain(domain);
        cookie.setPath(cookiePath);
        cookie.setMaxAge(cookieExpiryDate);
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     * @param rs HttpServletResponse
     * @param cookieName cookieName
     */
    public static void delCookies(HttpServletResponse rs, String cookieName) {
        Cookie cook = new Cookie(cookieName, null);
        cook.setPath("/");
        cook.setMaxAge(0);
        rs.addCookie(cook);
    }
}
