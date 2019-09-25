package com.mbyte.easy.util;

/**
 * @Author: 申劭明
 * @Date: 2019/9/11 21:53
 */
public class TestThread {

    public static void main(String[] args) {
        // t1 t2同时共享同一变量trainCount
        ThreadTrain threadTrain = new ThreadTrain();
        Thread t1 = new Thread(threadTrain, "窗口1");
        Thread t2 = new Thread(threadTrain, "窗口2");
        Thread t3 = new Thread(threadTrain, "窗口3");
        Thread t4 = new Thread(threadTrain, "窗口4");
        Thread t5 = new Thread(threadTrain, "窗口5");
        Thread t6 = new Thread(threadTrain, "窗口6");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}

// 售票窗口
class ThreadTrain implements Runnable {
    // 总共有100张火车票
    private int trainCount = 100;

    @Override
    public void run() {
        while (trainCount > 0) {
            try {
                // 休眠50秒
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 出售火车票
            sale();
        }
    }

    // 卖票方法
    public synchronized void sale() {
//        if (trainCount > 0) {
            System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - trainCount + 1) + "张票");

            trainCount--;
//        }

    }
}

