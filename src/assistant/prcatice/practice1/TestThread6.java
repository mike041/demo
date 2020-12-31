package practice1;

import java.util.*;

public class TestThread6 {
    public static void main(String[] args) {

        Hero6 gareen = new Hero6();
        gareen.name = "盖伦";
        gareen.hp = 10000;
        gareen.damage = 50;
        System.out.printf("盖伦的初始血量是 %.0f%n", gareen.hp);
        int n = 10000;

        Hero6.getName(gareen);


        Thread[] addThreads = new Thread[n];
        Thread[] reduceThreads = new Thread[n];
        for (int i = 0; i < n; i++) {
            Thread t = new Thread() {
                public void run() {
                    synchronized (gareen) {
                        gareen.recover();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            };
            t.start();
            addThreads[i] = t;

        }

        for (int i = 0; i < n; i++) {
            Thread t = new Thread() {
                public void run() {
                    gareen.hurt();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            t.start();
            reduceThreads[i] = t;
        }

        //等待所有增加线程结束
        for (Thread t : addThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //等待所有减少线程结束
        for (Thread t : reduceThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.printf("%d个增加线程和%d个减少线程结束后%n盖伦的血量是 %.0f%n", n, n, gareen.hp);
        ArrayList arrayList=new  ArrayList();
        List list=Collections.synchronizedList(arrayList);
    }


}
