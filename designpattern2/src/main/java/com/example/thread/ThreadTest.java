package com.example.thread;

/**
 * @auther MaxLiu
 * @time 2017/2/23
 */

public class ThreadTest {
    public static void main(String args[]) {

        for(int i = 0; i < 5; i++){
            Thread thread = new MyThread();
            thread.start();
        }
        try {
            Thread.sleep(10000);
            System.out.println("---------------------------------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MyRunnable runnable = new MyRunnable();
        for(int i = 0; i < 5; i++){
            Thread thread = new Thread(runnable);
            thread.start();
        }

    }

    private static class MyThread extends Thread {

        int x = 0;

        @Override
        public void run() {
            System.out.println("线程： " + Thread.currentThread().getName()
                    + " 第 " + (++x) + "次执行此操作 ... ...");
        }
    }

    private static class MyRunnable implements Runnable{

        int x = 0;

        @Override
        public void run() {
            System.out.println("Runnable线程： " + Thread.currentThread().getName()
                    + " 第 " + (++x) + "次执行此操作 ... ...");
        }
    }

}

