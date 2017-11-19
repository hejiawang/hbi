package com.wang.hbi.test;

import com.wang.hbi.authority.entrity.SysUserEntrity;
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
        SysUserEntrity user = new SysUserEntrity();
        user.getId();
    }
}
