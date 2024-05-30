package com.lin.test;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * created by zll on 2024/2/1 16:29
 */
public class AtomicTest {

    @Test
    public void testCreateWithOutArguments() {

        AtomicBoolean bool = new AtomicBoolean();
        System.out.println(bool.get());
    }

    @Test
    public void testCreateWithArguments() {

        AtomicBoolean bool = new AtomicBoolean(true);
        System.out.println(bool.get());
    }


    /**
     * 先get 然后set
     */
    @Test
    public void testGetAndSet() {
        AtomicBoolean bool = new AtomicBoolean(true);
        boolean result = bool.getAndSet(false);
        System.out.println(result);
        System.out.println(bool.get());
    }

    /**
     * 就是期望值是true 如果是true会改变成false
     */
    @Test
    public void testCompareAndSet() {
        AtomicBoolean bool = new AtomicBoolean(true);
        boolean result = bool.compareAndSet(true, false);
        System.out.println (result);
        System.out.println(bool.get());
    }


    @Test
    public void testCompareAndSetFailed() {
        AtomicBoolean bool = new AtomicBoolean(true);
        boolean result = bool.compareAndSet(false, true);
        System.out.println(bool.get());
        System.out.println(result);
    }

    /**
     * 和cup 通信
     * 1.数据总线
     * 2.控制总线->告诉cpu的命令
     * 3.地址总线 ->告诉你这东西在内存的中的地址
     */
    @Test
    public void testCreate(){

        AtomicLong atomicLong = new AtomicLong(100);
        /**
         * 判断jvm是不是free lock 如果不支持可能对数据总线尽心加锁
         *  static final boolean VM_SUPPORTS_LONG_CAS = VMSupportsCS8();
         *  比integer 多了一个这个变量
         *  long  64
         *
         *  high 32 俩次传送不能保证原子性
         *  low 32
         */
    }
}
