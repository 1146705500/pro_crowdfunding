package com.gitee.pro.test;

import com.gitee.pro.util.CrowdUtil;
import org.junit.jupiter.api.Test;

public class StringTest {

    @Test
    public void testMD5() {
        String source = "123456";
        String encoded = CrowdUtil.md5(source);
        System.out.println("encoded = " + encoded);
    }

}
