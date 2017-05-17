package com.lorelib.snowflake;

import org.junit.Test;

/**
 * @author listening
 * @description IdGeneratorTest:
 * @create 2017 05 16 13:25.
 */
public class IdGeneratorTest {
    @Test
    public void testNextId() {
        long num = 10000L;
        for (long i = 0; i < num; i++) {
            System.out.println("id: " + IdGenerator.nextId());
        }
    }

    /**
     * 环境：
     *  jdk1.8.0_101
     *  Intel Core i3-4170  3.7GHz  8GB RAM  64位
     * 测试数据：
     * 生成1000000000个ID耗用时间: 245007毫秒       10亿
     * 生成1000000个ID耗用时间: 264毫秒             100万
     * 生成10000个ID耗用时间: 4毫秒                 1万
     */
    @Test
    public void testPerformance() {
        long start = System.currentTimeMillis();
        long num = 10000L;
        for (long i = 0; i < num; i++) {
            long id = IdGenerator.nextId();
            System.out.println("id: " + id + ", len: " + Long.toString(id).length());
        }
        long end = System.currentTimeMillis();
        System.out.println("生成" + num + "个ID耗用时间: " + (end - start) + "毫秒");
    }
}
