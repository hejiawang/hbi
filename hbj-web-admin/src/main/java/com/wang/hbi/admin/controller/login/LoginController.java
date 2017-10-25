package com.wang.hbi.admin.controller.login;

import com.wang.hbi.admin.controller.BaseController;
import com.wang.hbi.authority.entrity.SysUserEntrity;
import com.wang.hbi.authority.service.SysUserService;
import com.wang.hbi.core.memcached.XMemcachedClient;
import com.wang.hbi.core.memcached.XMemcachedConstants;
import com.wang.hbi.core.result.HttpControllerResult;
import com.wang.hbi.core.utils.web.KaptchaDefine;
import com.wang.hbi.admin.utils.HbiAdminUserUtil;
import com.wang.hbi.core.utils.web.admin.HbiAdminConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登录 controller
 * @author HeJiawang
 * @date   20171013
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

    /**
     * 登录页地址
     */
    private static final String PAGE_LOGIN = "login";

    /**
     * 主页地址
     */
    private static final String PAGE_MAIN = "main";

    @Resource
    private SysUserService sysUserService;

    /**
     * 登录get请求方式
     * @return 登录页
     */
    @RequestMapping(value = "/login", method= RequestMethod.GET)
    public String loginGet(  ){
        return PAGE_LOGIN;
    }

    /**
     * 登录POST请求方式
     * @param request request
     * @param loginName 用户登录名
     * @param password 用户登录密码
     * @param captchaCode 验证码
     * @return
     */
    @RequestMapping(value = "/login", method= RequestMethod.POST)
    public String loginPost(HttpServletRequest request, String loginName, String password, String captchaCode, Model model){
        HttpControllerResult<Void> result = this.validateLogin(loginName, password);
        if( !result.getSuccess() ){
            model.addAttribute("message",result.getMessage());
            return PAGE_LOGIN;
        }

        String sessionId = HbiAdminUserUtil.getSessionId(request);
        if( this.getLoginErrCount(sessionId) >= HbiAdminConstants.ALLOW_ERROR_COUNT ){ //输入用户名密码错误三次后，请输入验证码
            if( StringUtils.isEmpty(captchaCode) ){
                model.addAttribute("validSign", true);
                model.addAttribute("message", "请输入验证码");
                return PAGE_LOGIN;
            }

            String captchaText = KaptchaDefine.getTextFromMemcached( sessionId );
            if( !captchaCode.equalsIgnoreCase(captchaText) ){   //equalsIgnoreCase()可忽略大小写
                model.addAttribute("validSign", true);
                model.addAttribute("message", "验证码输入错误");
                return PAGE_LOGIN;
            } else {
                KaptchaDefine.deleteTextToMemcached(sessionId);
            }
        }

        SysUserEntrity user = sysUserService.checkUser( loginName, password);
        if( user == null ){ //用户名或密码错误
            this.setLoginErrCount(sessionId);
            model.addAttribute("message", "用户名或密码错误");
            return PAGE_LOGIN;
        }

        HbiAdminUserUtil.WriteUserToMemcached(request, user);
        this.deleteLoginErrCount(sessionId);

        //加载信息
        //记录登录日志

        return PAGE_MAIN;
    }

    /**
     * 校验登录名、密码
     * @param loginName 登录名
     * @param password 密码
     * @return HttpControllerResult
     */
    private HttpControllerResult<Void> validateLogin( String loginName, String password ){
        HttpControllerResult<Void> result = new HttpControllerResult<>();

        result.setSuccess(false);
        if( StringUtils.isEmpty(loginName) ){
            result.setMessage("请输入登录名");
            return result;
        }
        if( StringUtils.isEmpty(password) ){
            result.setMessage("请输入密码");
            return result;
        }

        result.setSuccess(true);
        return result;
    }

    /**
     * 获取登录错误次数
     * @param sessionId sessionId
     * @return 登录错误次数
     */
    private long getLoginErrCount( String sessionId ){
        Long count = null;
        try{
            count = XMemcachedClient.getCounter(HbiAdminConstants.LOGIN_ERROR_COUNT + sessionId);
        } catch (Exception e) {
            logger.error("memcached get errorCount error!", e);
        }
        return count;
    }

    /**
     * 设置登录错误次数加1
     * @param sessionId sessionId
     * @return 登录错误次数
     */
    private long setLoginErrCount( String sessionId ){
        final String errorCountKey = HbiAdminConstants.LOGIN_ERROR_COUNT + sessionId;
        Long count = null;
        try {
            count = XMemcachedClient.incr(errorCountKey, 1, 0, XMemcachedConstants.TIME_OUT_ONE_HOUR);
        } catch (Exception e) {
            logger.error("memcached incr errorCount error!", e);
        }
        return count == null ? 0 : count;
    }

    /**
     * 清空登录错误次数
     * @param sessionId sessionId
     */
    private void deleteLoginErrCount( String sessionId) {
        try {
            XMemcachedClient.delete(HbiAdminConstants.LOGIN_ERROR_COUNT + sessionId);
        } catch (Exception e) {
            logger.error("memcached delete errorCount error!");
        }
    }
}
