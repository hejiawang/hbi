package com.wang.hbi.admin.interceptor;

import com.wang.hbi.authority.entrity.SysUserEntrity;
import com.wang.hbi.core.utils.web.admin.HbiAdminUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录拦截器
 * @author HeJiawang
 * @date   20171016
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

    /**
     * 免权限列表
     */
    private final static Set<String> ANONYMOUS_URLS = new HashSet<String>();

    static {
        ANONYMOUS_URLS.add("/login");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{

            if( ANONYMOUS_URLS.contains( request.getRequestURI() ) ){
                return true;
            }

            SysUserEntrity user = HbiAdminUserUtil.getUserByRequest(request);
            if( user == null || user.getId() == null ){
                response.sendRedirect("/login");
                return false;
            }

            return true;
        } catch ( Exception e ){
            logger.error("authority interceptor exception:", e);
            return false;
        }
    }
}
