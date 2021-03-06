package com.wang.hbi.admin.interceptor;

import com.google.common.collect.Sets;
import com.wang.hbi.authority.entrity.SysUserEntrity;
import com.wang.hbi.admin.utils.HbiAdminUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private final static Set<String> ANONYMOUS_URLS = Sets.newHashSet();

    static {
        ANONYMOUS_URLS.add("/hbi/login");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            logger.info(request.getRequestURI());

            if( ANONYMOUS_URLS.contains( request.getRequestURI() ) )  return true;

            SysUserEntrity user = HbiAdminUserUtil.getUserByRequest(request);
            if( user == null || user.getId() == null ){
                response.sendRedirect("/hbi/login");
                return false;
            }

            return true;
        } catch ( Exception e ){
            logger.error("authority interceptor exception:", e);
            return false;
        }
    }
}
