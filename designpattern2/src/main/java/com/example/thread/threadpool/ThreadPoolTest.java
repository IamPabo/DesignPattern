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
    private static final int CORE_POOL_SIZE = 1;
    private static final int MAX_POOL_SIZE = 3;
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

        for (int i = 0; i < 6; i++) {
            try {
                LOG();
                executor.execute(new WorkerThread("线程 --> " + i));
            }catch (Exception e){
                System.out.println("AbortPolicy...");
            }
        }
        // 10s后，所有任务已经执行完毕，我们在监听一下相关数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    LOG();
                } catch (Exception e) {

                }
            }
        }).start();
    }
    private static void LOG(){
        System.out.println(" ==============线程池===============\n"
                + "   核心线程 : " + executor.getCorePoolSize()
                + "   现有线程 : " + executor.getPoolSize()
                + "   最大执行线程 : " + executor.getMaximumPoolSize()
                + "   阻塞线程 : " + executor.getActiveCount()
                + "   任务数量 :" + executor.getTaskCount()

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
                    Thread.sleep(1000);
                    i++;
                    System.out.println("工作线程 " + threadName + "  " + i);
                    if (i > 2) flag = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getThreadName() {
            return threadName;
        }
    }

}
