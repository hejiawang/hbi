package com.wang.hbi.admin.controller.index;

import com.wang.hbi.admin.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Enumeration;

/**
 *
 *
 * @author HeJiawang
 * @date   20171013
 */
@Controller
@RequestMapping("/")
public class Index extends BaseController {

    /**
     * 首页页面请求
     * @return 登录页
     */
    @RequestMapping(value = "", method= RequestMethod.GET)
    public String index(){
        logger.info(" asdfasdfasdfasdf ");
        return "login";
    }

    @RequestMapping(value = "/main", method= RequestMethod.GET)
    public String main(){
        return "main";
    }

    /**
     * 模板页面请求
     * @param module
     * @param view
     * @return
     */
    @RequestMapping(value = "/{project}/{module}/{view}.html", method=RequestMethod.GET)
    public String busView (@PathVariable String project, @PathVariable String module, @PathVariable String view, Model model) {
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            model.addAttribute(name, value);
        }

        return new StringBuilder(project).append("/").append(module).append("/").append(view).toString();
    }
}
