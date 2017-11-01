package com.wang.hbi.admin.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * base controller
 * @author HeJiawang
 * @date   20171013
 */
public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger( this.getClass() );

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    /**
     * 集合转换
     * @param sources sources
     * @param targetClass targetClass
     * @param <E> object
     * @return object
     * @throws Exception exception
     */
    protected <E> List<E> convertList(List<Object> sources, Class<E> targetClass) throws Exception {
        List<E> targetList = new ArrayList<>();
        sources.forEach(source ->targetList.add(convert(source, targetClass)));

        return targetList;
    }

    /**
     * 对象转换
     * @param source source
     * @param clazz clazz
     * @param <T> object
     * @return object
     */
    protected <T> T convert(Object source, Class<T> clazz) {
        T target = null;
        try {
            target = clazz.newInstance();
            for (Class<?> c = source.getClass(); !Object.class.equals(c); c = c.getSuperclass()) {
                for (Field sourceField : c.getDeclaredFields()) {
                    Class<?> targetClass = clazz;
                    sourceField.setAccessible(true);
                    for (Object o = new Object(); o != null;) {
                        try {
                            o = null;
                            Field targetField = targetClass.getDeclaredField(sourceField.getName());
                            targetField.setAccessible(true);
                            if (sourceField.getType().isEnum() && targetField.getType().isEnum()) {
                                Object sourceEnumObject = sourceField.get(source);
                                if (sourceEnumObject != null) {
                                    Object convertEnum = convertEnum(sourceEnumObject, targetField.getType());
                                    targetField.set(target, convertEnum);
                                }
                            } else {
                                Object sourceObject = sourceField.get(source);
                                if (sourceObject != null) {
                                    targetField.set(target, sourceObject);
                                }
                            }
                        } catch (NoSuchFieldException e) {
                            o = e;
                            targetClass = targetClass.getSuperclass();
                            if (Object.class.equals(targetClass)) {
                                o = null;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * 枚举转换
     * @param sourceEnumObject sourceEnumObject
     * @param targetEnumClass targetEnumClass
     * @return Object
     * @throws Exception exception
     */
    protected Object convertEnum(Object sourceEnumObject, Class<?> targetEnumClass) throws Exception {
        if (sourceEnumObject == null) return null;

        String sourceEnumValue = sourceEnumObject.toString();
        try {
            Field targetEnumField = targetEnumClass.getField(sourceEnumValue);
            return targetEnumField.get(null);
        } catch (NoSuchFieldException e) {}
        return null;
    }
}
