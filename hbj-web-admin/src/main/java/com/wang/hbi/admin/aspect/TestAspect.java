package com.wang.hbi.admin.aspect;

import com.google.common.collect.Maps;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * aspect test
 * @author HeJiawang
 * @date   20171031
 */
@Aspect
@Component
public class TestAspect {

    /**
     * 过滤
     */
    private static Map<String, String> map = null;

    {
        map = Maps.newHashMap();
    }

    /**
     * 切入controller方法执行前执行
     */
    @Before("execution(* com.wang.hbi.admin.controller..*.*(..))")
    public void before(){
        System.out.println("切入controller方法执行前执行.....");
    }
}
