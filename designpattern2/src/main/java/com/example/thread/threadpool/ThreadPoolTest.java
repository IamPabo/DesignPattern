package com.example.thread.threadpool;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @auther MaxLiu
 * @time 2017/2/23
 */

public class ThreadPoolTest {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int BLOCK_POOL_SIZE = 2;
    private static final int ALIVE_POOL_SIZE = 2;
    private static ThreadPoolExecutor executor;

    public static void main(String args[]) {
        executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,// 核心线程数 最小
                MAX_POOL_SIZE,// 最大执行线程数
                ALIVE_POOL_SIZE,// 空闲线程超时
                TimeUnit.SECONDS,// 超时时间单位
                // 当线程池达到corePoolSize时，新提交任务将被放入workQueue中，
                // 等待线程池中任务调度执行
                new ArrayBlockingQueue<Runnable>(BLOCK_POOL_SIZE),// 阻塞队列大小
                // 线程工厂，为线程池提供创建新线程的功能，它是一个接口，
                // 只有一个方法：Thread newThread(Runnable r)
                Executors.defaultThreadFactory(),
                // 线程池对拒绝任务的处理策略。一般是队列已满或者无法成功执行任务，
                // 这时ThreadPoolExecutor会调用handler的rejectedExecution
                // 方法来通知调用者
                new ThreadPoolExecutor.AbortPolicy()
        );
        executor.allowCoreThreadTimeOut(true);
        /*
         * ThreadPoolExecutor默认有四个拒绝策略：
         *
         * 1、ThreadPoolExecutor.AbortPolicy()   直接抛出异常RejectedExecutionException
         * 2、ThreadPoolExecutor.CallerRunsPolicy()    直接调用run方法并且阻塞执行
         * 3、ThreadPoolExecutor.DiscardPolicy()   直接丢弃后来的任务
         * 4、ThreadPoolExecutor.DiscardOldestPolicy()  丢弃在队列中队首的任务
         */

        for (int i = 0; i < 10; i++) {
            try {
                executor.execute(new WorkerThread("线程 --> " + i));
                LOG();
            } catch (Exception e) {
                System.out.println("AbortPolicy...");
            }
        }
        executor.shutdown();

        // 所有任务执行完毕后再次打印日志
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("\n\n---------执行完毕---------\n");
                    LOG();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 打印 Log 信息
     */
    private static void LOG() {
        System.out.println(" ==============线程池===============\n"
                + "   线程池中线程数 : " + executor.getPoolSize()
                + "   等待执行线程数 : " + executor.getQueue().size()
                + "   所有的任务数 : " + executor.getTaskCount()
                + "   执行任务的线程数 : " + executor.getActiveCount()
                + "   执行完毕的任务数 : " + executor.getCompletedTaskCount()

        );
    }

    // 模拟耗时任务
    public static class WorkerThread implements Runnable {
        private String threadName;

        public WorkerThread(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public synchronized void run() {

            int i = 0;
            boolean flag = true;
            try {
                while (flag) {
                    i++;
                    if (i > 2) flag = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String getThreadName() {
            return threadName;
        }
    }

}
