package com.wang.hbi.admin.controller.authority;

import com.wang.hbi.admin.controller.BaseController;
import com.wang.hbi.authority.dto.SysUserDto;
import com.wang.hbi.authority.service.SysUserService;
import com.wang.hbi.core.result.HttpControllerResult;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户管理Controller
 * @author HeJiawang
 * @date   20171031
 */
@Controller
@RequestMapping("/user")
public class SysUserController extends BaseController {

    /**
     * 用户管理Service
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 添加用户
     * @param sysUserDto
     * @return HttpControllerResult
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST )
    public HttpControllerResult<Void> saveUser( @Valid SysUserDto sysUserDto, BindingResult bindingResult ){
        checkDTOParams(bindingResult);

        HttpControllerResult<Void> result = new HttpControllerResult<Void>();

        Boolean success = sysUserService.saveUser( sysUserDto );

        result.setSuccess(success);
        String message = success==true?"添加用户成功":"添加用户失败";
        result.setMessage(message);

        return result;
    }

    private void checkDTOParams(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //throw new 带验证码的验证错误异常
        }
    }

}
