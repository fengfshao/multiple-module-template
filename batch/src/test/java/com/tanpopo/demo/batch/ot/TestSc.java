package com.tanpopo.demo.batch.ot;

import com.tanpopo.demo.commons.util.ArgumentsUtil;
import org.junit.Test;

/**
 * 测试java中对scala单测的调用
 *
 * @author fengfshao
 * @since 2023/9/4
 */


public class TestSc {

    @Test
    public void testArguments() {
        ArgumentsUtil arg = new ArgumentsUtil(new String[]{"aa=a1", "bb=b2"});
        System.out.println(arg.argsMap());
    }
}
