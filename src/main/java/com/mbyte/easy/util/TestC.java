package com.mbyte.easy.util;

import java.util.Scanner;
import java.util.TreeMap;


public class TestC {
    public static void main(String[] args) {

        Scanner input =new Scanner(System.in);
        //获取批量数
        int count=input.nextInt();
        int time;
        int max;
        //创建TreeMap
        TreeMap<Integer,Integer> map=new TreeMap<>();
        int t;
        int c;
        for (int i = 0; i <count ; i++) {
            t=input.nextInt();
            c=input.nextInt();
            map.put(t,c);
        }

        long start=System.currentTimeMillis();
        int lastKey=map.lastKey();
        int firstKey=map.firstKey();
        int a=lastKey-firstKey;
        int sum=0;
        for (int key:map.keySet()){
            sum+=map.get(key);
        };
        sum=sum-map.lastEntry().getValue();
        if(a>=sum){
            max=map.lastEntry().getValue();
            time=map.lastKey()+max;

        }else{
           int lastValue= map.lastEntry().getValue();
            int temp=sum-a;
            time=map.lastKey()+temp+lastValue;
            max=temp+lastValue;
        }
        System.out.println(time  +"   "+max);
        System.out.println(System.currentTimeMillis()-start);

//        Scanner input = new Scanner(System.in);
//        int n = input.nextInt();
//        int[] t = new int[n];
//        int[] c = new int[n];
//        int sum = 0;
//        int queue = 0;
//        int max = 0;
//
//        for (int i = 0; i < n; i++) {
//            t[i] = input.nextInt();
//            c[i] = input.nextInt();
//            sum = sum + c[i];
//        }
//        int time = 0;
//        while (sum > 0) {
//            for (int k = 0; k < n; k++) {
//                if (t[k] == time) {
//                    queue = queue + c[k];
//                    if (max < queue) {
//                        max = queue;
//                    }
//                }
//            }
//            if (queue > 0) {
//                queue--;
//                sum--;
//            }
//            time++;
//        }
//        System.out.println(time + " " + max);
//        Scanner input = new Scanner(System.in);
//        //n个游戏
//        int n = input.nextInt();
//        //预算
//        int x = input.nextInt();
//        //原价
//        int[] a = new int[n];
//        //现在
//        int[] b = new int[n];
//
//        int[] bud = new int[n];
//        //快乐值
//        int[] w = new int[n];
//
//        for (int i = 0; i < n; i++) {
//            a[i] = input.nextInt();
//            b[i] = input.nextInt();
//            w[i] = input.nextInt();
//            bud[i] = a[i] - b[i];
//        }
//        int[][] ints = DP_01bag(x, n, bud, w);

    }

    /**
     * @param m : 表示背包的最大容量
     * @param n : 表示商品的个数
     * @param w : 表示商品重量数组
     * @param p : 表示商品价值数组
     */
    public static int[][] DP_01bag(int m, int n, int w[], int p[]) {
        //c[i][m] 表示前i件物品恰好放入重量为m的背包时的最大价值
        int c[][] = new int[n + 1][m + 1];

        for (int i = 0; i < n + 1; i++) {
            c[i][0] = 0;
        }
        for (int j = 0; j < m + 1; j++) {
            c[0][j] = 0;
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                //当物品为i件重量为j时，如果第i件的重量(w[i-1])小于重量j时，c[i][j]为下列两种情况之一：
                //(1)物品i不放入背包中，所以c[i][j]为c[i-1][j]的值
                //(2)物品i放入背包中，则背包剩余重量为j-w[i-1],所以c[i][j]为c[i-1][j-w[i-1]]的值加上当前物品i的价值
                if (w[i - 1] <= j) {
                    if (c[i - 1][j] < c[i - 1][j - w[i - 1]] + p[i - 1]) {
                        c[i][j] = c[i - 1][j - w[i - 1]] + p[i - 1];
                    } else {
                        c[i][j] = c[i - 1][j];
                    }
                } else {
                    c[i][j] = c[i - 1][j];
                }
            }
        }
        return c;
    }
}
