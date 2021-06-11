package com.zhumei.baselib.utils.useing.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 *  线程池 ——管理
 * */
public class ThreadManager {

    //同步的单线程执行的线程池
    private static ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    //异步可并发执行的线程池，线程池大小设置为当前手机处理器核心数
    private static ExecutorService fixedThreadPool;
    private static ThreadManager instance;

    public static ThreadManager getInstance() {
        if (instance == null) {
            synchronized (ThreadManager.class) {
                if (instance == null) {
                    instance = new ThreadManager();
                }
            }

        }
        return instance;
    }

    private ThreadManager() {
    }

    /**
     * 以同步的单线程方式执行任务，文件操作，数据库操作等可使用
     * execute() 参数 Runnable ；submit() 参数 (Runnable) 或 (Runnable 和 结果 T) 或 (Callable)
     * execute() 没有返回值；而 submit() 有返回值
     * submit() 的返回值 Future 调用get方法时，可以捕获处理异常
     * ————————————————
     * 原文链接：https://blog.csdn.net/meism5/article/details/90264191
     */
    public static void executeSingleTask(Runnable runnable) {
        singleThreadPool.execute(runnable);
    }

    /**
     * 以异步的多线程并发的方式执行任务
     */
    public static void executeMultiTasks(Runnable runnable) {
        if (fixedThreadPool==null){
            fixedThreadPool = Executors.newFixedThreadPool(getCoreSize());
        }
        fixedThreadPool.execute(runnable);
    }


    public static int getCoreSize(){

        int threadNum = Runtime.getRuntime().availableProcessors();
        int corePoolSize = Math.max(2, Math.min(threadNum - 1, 4));
        return corePoolSize;
    }
}
