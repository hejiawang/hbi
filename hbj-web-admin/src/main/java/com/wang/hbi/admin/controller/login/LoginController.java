package com.wang.hbi.admin.controller.login;

import com.wang.hbi.admin.controller.BaseController;
import com.wang.hbi.core.result.HttpControllerResult;
import com.wang.hbi.core.utils.web.admin.HbiAdminUserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * 登录get请求方式
     * @return
     */
    @RequestMapping(value = "/login", method= RequestMethod.GET)
    public String loginGet(  ){
        return PAGE_LOGIN;
    }

    /**
     * 登录POST请求方式
     * @param request request
     * @param response response
     * @param loginName 用户登录名
     * @param password 用户登录密码
     * @param captchaCode 验证码
     * @return
     */
    @RequestMapping(value = "/login", method= RequestMethod.POST)
    public String loginPost(HttpServletRequest request, HttpServletResponse response, String loginName, String password, String captchaCode, Model model){
        HttpControllerResult<Void> result = this.validateLogin(loginName, password);
        if( !result.getSuccess() ){
            model.addAttribute("message",result.getMessage());
            return PAGE_LOGIN;
        }

        String sessionId = HbiAdminUserUtil.getSessionId(request);
        if( getLoginErrCount(sessionId) >= HbiAdminUserUtil.ALLOW_ERROR_COUNT ){ //输入用户名密码错误三次后，请输入验证码
            if( StringUtils.isEmpty(captchaCode) ){
                model.addAttribute("validSign", true);
                model.addAttribute("message", "请输入验证码");
                return PAGE_LOGIN;
            }
        }



        return PAGE_MAIN;
    }

    /**
     * 校验登录名、密码、验证码
     * @param loginName
     * @param password
     * @return
     */
    private HttpControllerResult<Void> validateLogin( String loginName, String password ){
        HttpControllerResult<Void> result = new HttpControllerResult<Void>();

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
     * 登录错误次数
     * @param sessionId sessionId
     * @return
     */
    private long getLoginErrCount( String sessionId ){
        return 0;
    }

    private long setLoginErrCount( String sessionId ){
        return 0;
    }
}
