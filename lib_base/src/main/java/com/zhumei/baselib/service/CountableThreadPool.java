package com.zhumei.baselib.service;


import com.blankj.utilcode.util.LogUtils;
import com.zhumei.baselib.BuildConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CountableThreadPool {

    // 固定线程数量 ; 获取可用线程即可.
    private int corePoolSize;
//    private int corePoolSize;
    // 用于线程计数
    private AtomicInteger threadAlive = new AtomicInteger();

    //创建锁 ：true 公平锁 | 非公平锁
    private ReentrantLock reentrantLock = new ReentrantLock(true);

    /**
     * condition可以通俗的理解为条件队列。当一个线程在调用了await方法以后，
     * 直到线程等待的某个条件为真的时候才会被唤醒。
     * 这种方式为线程提供了更加简单的等待/通知模式。
     * Condition必须要配合锁一起使用，
     * 因为对共享状态变量的访问发生在多线程环境下
     */
    private Condition condition = reentrantLock.newCondition();

    //核心线程数量大小
//    private static final int corePoolSize = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    public CountableThreadPool(int threadNum) {
        this.corePoolSize = Math.max(2, Math.min(threadNum - 1, 4));
        this.executorService = Executors.newFixedThreadPool(corePoolSize);
    }

    public CountableThreadPool(int threadNum, ExecutorService executorService) {
        this.corePoolSize = Math.max(2, Math.min(threadNum - 1, 4));
        this.executorService = executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public int getThreadAlive() {
        return threadAlive.get();
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    private ExecutorService executorService;

    public void execute(final Runnable runnable) {

        LogUtils.d("execute.....", threadAlive.get() + " ---" + corePoolSize);
        if (threadAlive.get() >= corePoolSize) {
            try {
                if (BuildConfig.DEBUG) {
                    LogUtils.d("reentrantLock lock().....");
                }
                reentrantLock.lock();
                while (threadAlive.get() >= corePoolSize) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                    }
                }
            } finally {
                reentrantLock.unlock();
                if (BuildConfig.DEBUG) {
                    LogUtils.d("reentrantLock unlock().....");
                }
            }



        }
        threadAlive.incrementAndGet();
//
//        15:00:49.462
//        15:02:18.516
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (BuildConfig.DEBUG) {
                        LogUtils.e("reentrantLock lock.....");
                    }

                    reentrantLock.lock();
                    runnable.run();
                    threadAlive.decrementAndGet();
                    condition.signal();


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                    if (BuildConfig.DEBUG) {
                        LogUtils.e("reentrantLock unlock.....");
                    }
                }


            }
        });
    }

    public boolean isShutdown() {
        return executorService.isShutdown();
    }

    public void shutdown() {
        executorService.shutdown();
    }


}

