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
                CORE_POOL_SIZE,// �����߳��� ��С
                MAX_POOL_SIZE,// ���ִ���߳���
                ALIVE_POOL_SIZE,// �����̳߳�ʱ
                TimeUnit.SECONDS,// ��ʱʱ�䵥λ
                // ���̳߳شﵽcorePoolSizeʱ�����ύ���񽫱�����workQueue�У�
                // �ȴ��̳߳����������ִ��
                new ArrayBlockingQueue<Runnable>(BLOCK_POOL_SIZE),// �������д�С
                // �̹߳�����Ϊ�̳߳��ṩ�������̵߳Ĺ��ܣ�����һ���ӿڣ�
                // ֻ��һ��������Thread newThread(Runnable r)
                Executors.defaultThreadFactory(),
                // �̳߳ضԾܾ�����Ĵ�����ԡ�һ���Ƕ������������޷��ɹ�ִ������
                // ��ʱThreadPoolExecutor�����handler��rejectedExecution
                // ������֪ͨ������
                new ThreadPoolExecutor.AbortPolicy()
        );
        executor.allowCoreThreadTimeOut(true);
        /*
         * ThreadPoolExecutorĬ�����ĸ��ܾ����ԣ�
         *
         * 1��ThreadPoolExecutor.AbortPolicy()   ֱ���׳��쳣RejectedExecutionException
         * 2��ThreadPoolExecutor.CallerRunsPolicy()    ֱ�ӵ���run������������ִ��
         * 3��ThreadPoolExecutor.DiscardPolicy()   ֱ�Ӷ�������������
         * 4��ThreadPoolExecutor.DiscardOldestPolicy()  �����ڶ����ж��׵�����
         */

        for (int i = 0; i < 10; i++) {
            try {
                executor.execute(new WorkerThread("�߳� --> " + i));
                LOG();
            } catch (Exception e) {
                System.out.println("AbortPolicy...");
            }
        }
        executor.shutdown();

        // ��������ִ����Ϻ��ٴδ�ӡ��־
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("\n\n---------ִ�����---------\n");
                    LOG();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * ��ӡ Log ��Ϣ
     */
    private static void LOG() {
        System.out.println(" ==============�̳߳�===============\n"
                + "   �̳߳����߳��� : " + executor.getPoolSize()
                + "   �ȴ�ִ���߳��� : " + executor.getQueue().size()
                + "   ���е������� : " + executor.getTaskCount()
                + "   ִ��������߳��� : " + executor.getActiveCount()
                + "   ִ����ϵ������� : " + executor.getCompletedTaskCount()

        );
    }

    // ģ���ʱ����
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
