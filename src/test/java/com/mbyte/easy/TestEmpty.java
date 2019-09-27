package com.mbyte.easy;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @auther 申劭明
 * @date 19-4-11 20:28
 * @deprecated : 测试空工具类
 */
public class TestEmpty extends TestCase {

    @Test
    public static void main(String [] args){
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

    private static class MyThredIteratorThread <T> extends Thread{

        private final Spliterator<T> list;

        private MyThredIteratorThread(Spliterator<T> list) {
            this.list = list;
        }

        @Override
        public void run() {
            list.forEachRemaining(e -> System.out.println(e));

            list.forEachRemaining(new Consumer<T>() {
                @Override
                public void accept(T t) {
                    System.out.println(t);
                }
            });
        }
    }

}
