package com.wang.hbi.test;

import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @Author HeJiawang
 * @Date 2017/10/25 21:29
 */
public class TemTest extends TestCase {

    @Test
    public void debugTest(){
        String str1 = "str";
        String str2 = "str";

        assertTrue(StringUtils.equals(str1, str2));
    }
}
