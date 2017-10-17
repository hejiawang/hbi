package com.wang.hbi.admin.controller.login;

import com.wang.hbi.admin.controller.BaseController;
import com.wang.hbi.core.result.HttpControllerResult;
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

    private static final String PAGE_LOGIN = "login";
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
        HttpControllerResult<Void> result = this.validateLogin(loginName, password, captchaCode);
        if( !result.getSuccess() ){
            model.addAttribute("message",result.getMessage());
            return PAGE_LOGIN;
        }




        return PAGE_MAIN;
    }

    /**
     * 校验登录名、密码、验证码
     * @param loginName
     * @param password
     * @param captchaCode
     * @return
     */
    private HttpControllerResult<Void> validateLogin( String loginName, String password, String captchaCode ){
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
        if( StringUtils.isEmpty(captchaCode) ){
            result.setMessage("请输入验证码");
            return result;
        }

        result.setSuccess(true);
        return result;
    }
}
