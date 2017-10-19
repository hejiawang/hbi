package com.wang.hbi.core.utils.web.ip;

import javax.servlet.http.HttpServletRequest;

import com.wang.hbi.core.utils.RegExpValidator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * IP工具类
 * @author HeJiawang
 * @date   20171019
 */
public class ClientIPUtils {
	
	/**
	 * 反向代理软件就不能获取到客户端的真实IP地址了。
	 * 但是在转发请求的HTTP头信息中，增加了X－FORWARDED－FOR信息。用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址。
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))  ip = request.getHeader("Proxy-Client-IP");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))  ip = request.getHeader("WL-Proxy-Client-IP");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))  ip = request.getRemoteAddr();
        if(StringUtils.isNotBlank(ip)) ip = ip.split(",")[0];

        return ip;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        // 如果客户端经过多级反向代理，则X-Forwarded-For的值并不止一个，而是一串IP值，
        // 取X-Forwarded-For中第一个非unknown的有效IP字符串即为用户真实IP
        String ipResult = getIp(ip);
        if (ipResult != null)  return ipResult;

        ip = request.getHeader("Proxy-Client-IP");
        ipResult = getIp(ip);
        if (ipResult != null)  return ipResult;

        ip = request.getHeader("WL-Proxy-Client-IP");
        ipResult = getIp(ip);
        if (ipResult != null) return ipResult;

        ip = request.getRemoteAddr();
        ipResult = getIp(ip);
        if (ipResult != null)  return ipResult;

        return "";
    }

    private static String getIp(String ips) {
        if (StringUtils.isEmpty(ips))  return null;

        String[] tokens = ips.split(",");
        for (String s : tokens) {
            if (RegExpValidator.isIpAddress(s.trim()))  return s.trim();
        }

        return null;
    }

}
