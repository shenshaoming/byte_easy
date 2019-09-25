package com.mbyte.easy.util;

import java.util.*;
import java.util.function.Consumer;

/**
 * @Author: 申劭明
 * @Date: 2019/9/8 20:36
 */
public class Test {

    private static class MyThredIteratorThread <T> extends Thread{
        private final Spliterator<T> list;

        private MyThredIteratorThread(Spliterator<T> list) {
            this.list = list;
        }

        @Override
        public void run() {
            list.forEachRemaining(e -> System.out.println(e));
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(1000);
        for (int i = 0; i < 20000; i++) {
            list.add("bery" + i);
        }
        //0~20000
        Spliterator<String> spliterator1 = list.spliterator();
        //spliterator1:0~10000 spliterator2:10001~20000
        Spliterator<String> spliterator2 = spliterator1.trySplit();
        //spliterator1:0~5000 spliterator2:10001~20000 spliterator3:5001~10000
        Spliterator<String> spliterator3 = spliterator1.trySplit();
        //spliterator1:0~5000 spliterator2:10001~15000 spliterator3:5001~10000 spliterator4:15001~20000
        Spliterator<String> spliterator4 = spliterator2.trySplit();

        MyThredIteratorThread<String> thread1 = new MyThredIteratorThread<>(spliterator1);
        MyThredIteratorThread<String> thread2 = new MyThredIteratorThread<>(spliterator2);
        MyThredIteratorThread<String> thread3 = new MyThredIteratorThread<>(spliterator3);
        MyThredIteratorThread<String> thread4 = new MyThredIteratorThread<>(spliterator4);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

//        while(true){
//            if (thread1.isAlive() || thread2.isAlive() || thread3.isAlive() || thread4.isAlive()){
//                try {
//                    //如果线程没有结束,主线程就睡眠300ms,当所有线程都执行完成才会结束主程序
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }else{
//                break;
//            }
//        }

        System.err.println("test END");
    }
}
