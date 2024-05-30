package com.lin.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock

/**
 * created by zll on 2024/5/11 10:12
 * 问题背景
 * 需执行多线程任务：任务1、任务2并行执行；等全部执行完成后，执行任务3。
 *
 * 实现方式
 * 「多线程同步」。Kotlin实现多线程同步的方式主要包括：（含Java实现方式）
 *
 * 方式1：Thread.join
 * 方式2：线程锁：Synchronized、ReentrantLock、CountDownLatch、CyclicBarrier
 * 方式3：CAS
 * 方式4：Future（CompletableFuture）
 * 方式5：Rxjava
 * 方式6：协程Coroutine、Flow
 * 方式1：Thread.join()
 */
class LockTest {


    @Test
    fun testJoin() {
        lateinit var s1: String
        lateinit var s2: String

        val t1 = Thread { s1 = task1() }
        val t2 = Thread { s2 = task2() }
        t1.start()
        t2.start()

        t1.join()
        t2.join()

        task3(s1, s2)
    }

    @Test
    fun testSynchronized() {
        lateinit var s1: String
        lateinit var s2: String

        Thread {
            synchronized(Unit) {
                s1 = task1()
            }
        }.start()
        s2 = task2()
        synchronized(Unit) {
            task3(s1, s2)
        }
    }

    // ReentrantLock
    // 相对于Synchronized，ReentrantLock的使用则不会出现嵌套 synchrnoized 的问题，
    // 但仍需创建多个 lock 从而管理多个不同的线程任务。
    @Test
    fun testReentrantLock() {
        lateinit var s1: String
        lateinit var s2: String
        val lock = ReentrantLock()
        Thread {
            lock.lock()
            s1 = task1()
            lock.unlock()
        }.start()
        s2 = task2()
        lock.lock()
        task3(s1, s2)
        lock.unlock()
    }

    // 这里需要额外说明的是，阻塞队列BlockingQueue内部是通过ReentrantLock实现的，
    // 所以其也能实现线程同步，但其应用场景是：生产/消费场景中的同步
    @Test
    fun testBlockingQueue() {
        lateinit var s1: String
        lateinit var s2: String
        val queue = SynchronousQueue<Unit>()
        Thread {
            s1 = task1()
            queue.put(Unit)
        }.start()
        s2 = task2()
        queue.take()
        task3(s1, s2)
    }

    // CountDownLatch
    // JUC 中的锁大都基于 AQS 实现的，可以分为独享锁和共享锁。
    // ReentrantLock 就是一种独享锁。相比之下，共享锁更适合本场景，不需为了每个任务都创建单独的锁。
    @Test
    fun countdownLatch() {
        lateinit var s1: String
        lateinit var s2: String
        val cd = CountDownLatch(2)
        Thread {
            s1 = task1()
            cd.countDown()
        }.start()
        Thread {
            s2 = task2()
            cd.countDown()
        }.start()
        cd.await()
        task3(s1, s2)
    }

    // CyclicBarrier
    // 原理：让一组线程到达一个同步点后再一起继续运行，
    // 其中任意一个线程未达到同步点，其他已到达的线程均会被阻塞。
    // 需要特别注意的是：与 CountDownLatch 的区别在于 CountDownLatch 是一次性的，而 CyclicBarrier 可以被重置后循环利用
    @Test
    fun testCyclicBarrier() {
        lateinit var s1: String
        lateinit var s2: String
        val cb = CyclicBarrier(3)
        Thread {
            s1 = task1()
            cb.await()
        }.start()
        Thread {
            s2 = task2()
            cb.await()
        }.start()
        cb.await()
        task3(s1, s2)
    }

    //原理：基于 CAS 的原子类计数
    //应用场景：一些cpu密集型的短任务同步（因为会比较损耗资源）
    @Test
    fun testCAS() {
        lateinit var s1: String
        lateinit var s2: String
        val cas = AtomicInteger(2)
        Thread {
            s1 = task1()
            cas.getAndDecrement()
        }.start()
        Thread {
            s2 = task2()
            cas.getAndDecrement()
        }.start()
        while (cas.get() != 0) {
        }
        task3(s1, s2)
    }


    // 这里需要特别说明的是，看到 CAS 的无锁实现，很多人会想到 volatile：并非线程安全，
    // 因为volatile 能保证可见性，但是不能保证原子性，cnt-- 并非线程安全，需要加锁操作
//    fun test_Volatile() {
//        lateinit var s1: String
//        lateinit var s2: String
//
//        Thread {
//            s1 = task1()
//            cnt--
//        }.start()
//
//        Thread {
//            s2 = task2()
//            cnt--
//        }.start()
//
//        while (cnt != 0) {
//        }
//        task3(s1, s2)
//    }

    // 通过 `future.get()`，可以同步等待结果返回，写起来非常方便
    // Java 1.5 开始提供了一种可以在任务执行结束时返回结果的线程同步方式：Callable 和 Future 。即不需通过定义变量来记录结果了。
//    fun test_future() {
//
//        val future1 = FutureTask(Callable(task1))
//        val future2 = FutureTask(Callable(task2))
//
//        Executors.newCachedThreadPool().execute(future1)
//        Executors.newCachedThreadPool().execute(future2)
//
//        task3(future1.get(), future2.get())
//    }

    // 这里需要特别说明的是，future.get() 虽然方便，但是会阻塞线程。
    // 所以在 Java 8 中引入了 CompletableFuture ：他实现了 Future 接口的同时实现了 CompletionStage 接口，
    // 即可针对多个 CompletionStage 进行逻辑组合、实现复杂的异步编程。以回调的形式避免了线程阻塞
//    fun test_CompletableFuture() {
//        CompletableFuture.supplyAsync(task1)
//            .thenCombine(CompletableFuture.supplyAsync(task2)) { p1, p2 ->
//                task3(p1, p2)
//            }.join()
//    }

    // RxJava 提供线程同步操作符：
    //
    //1.subscribeOn 用来启动异步任务
    //2.zip 操作符可以组合两个 Observable 的结果
//    fun test_Rxjava() {
//
//        Observable.zip(
//            Observable.fromCallable(Callable(task1))
//                .subscribeOn(Schedulers.newThread()),
//            Observable.fromCallable(Callable(task2))
//                .subscribeOn(Schedulers.newThread()),
//            BiFunction(task3)
//        ).test().awaitTerminalEvent()
//    }

    // 方式6协程：Coroutine、Flow
    //Coroutine 是 Kotlin 特有的线程同步方式（前面的方式，其实都是 Java 包本身的线程同步方式。）
    fun test_coroutine() {

        runBlocking {
            val c1 = async(Dispatchers.IO) {
                task1()
            }

            val c2 = async(Dispatchers.IO) {
                task2()
            }

            task3(c1.await(), c2.await())
        }
    }

    // 这里需要特别介绍的是，Kotlin版的 RxJava-协程加强版Flow，使用方式类似RxJava 的操作符，如 zip:
//    fun test_flow() {
//
//        val flow1 = flow<String> { emit(task1()) }
//        val flow2 = flow<String> { emit(task2()) }
//
//        runBlocking {
//            flow1.zip(flow2) { t1, t2 ->
//                task3(t1, t2)
//            }.flowOn(Dispatchers.IO)
//                .collect()
//// flowOn 使得 Task 在异步计算并发射结果。
//        }

//}

private fun task1(): String {
    return "abc"
}

private fun task2(): String {
    return "fff"
}

private fun task3(s1: String, s2: String) {
    val s3 = s1 + s2
    println(s3)
}

}