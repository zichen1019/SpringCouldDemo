package com.zc.api;

import com.zc.api.utils.icon.IconUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiApplicationTests {

    @Test
    void contextLoads() {
        System.err.println(123);
    }

    @Test
    public void test() {
//        IconUtil.getIcon("https://www.12306.cn/").forEach(System.err::println);
        IconUtil.getIcon("https://element.eleme.cn/#/zh-CN").forEach(System.err::println);
//        IconUtil.getIcon("https://eslint.org/docs/developer-guide/architecture").forEach(System.err::println);
    }

}
