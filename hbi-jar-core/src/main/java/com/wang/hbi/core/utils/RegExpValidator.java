package com.wang.hbi.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证工具类
 * @author HeJiawang
 * @date   20171012
 */
public class RegExpValidator {

    /**
     * @param str 待验证的字符串
     * @return 如果是符合邮箱格式的字符串,返回<b>true</b>,否则为<b>false</b>
     */
    public static boolean isEmail(String str) {
        String regex="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        return match(regex, str);
    }
    
    /**
     * 
     * @param str  待验证的字符串
     * @return     如果是符合手机格式的字符串,返回<b>true</b>,否则为<b>false</b>
     */
    public static boolean isPhoneNum(String str){
        return match("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8}$", str);
    }

    /**
     * 
     * @param str  待验证的字符串
     * @return     如果是符合登录格式的字符串,返回<b>true</b>,否则为<b>false</b>
     * @author renlong 2016-1-27 lren@godaji.com
     */
    public static boolean isLoginNum(String str){
//        return match("^(((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8})|([0|1|2|3]\\d{8})|([0|1|2|3]\\d{4})|([0|1|2|3]\\d{9})$", str);
        return match("^(((1[3|4|5|6|7|8][0-9])\\d{8})|([0-3](\\d{4}|\\d{5}|\\d{8}|\\d{9})))$", str);
    }
    
    /**
     * @param str 待验证的商务平台账号
     * @return 如果是符合登录格式的字符串,返回<b>true</b>,否则为<b>false</b>
     * @author renlong 2016-2-21 lren@godaji.com
     */
    public static boolean isErpUser(String str){
    	return match("^([0-3](\\d{4}|\\d{5}|\\d{8}|\\d{9}))$", str);
    }
    
    /**
     *
     * @param str  待验证的字符串
     * @return     如果是符合IP格式的字符串,返回<b>true</b>,否则为<b>false</b>
     */
    public static boolean isIpAddress(String str){
        return match("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$", str);
    }
    
    /** 
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
