package com.demo.kafka.test;

import java.text.SimpleDateFormat;
import java.util.Date;

// https://medium.com/javarevisited/synchronized-this-vs-synchronized-class-in-java-d521556b1f
public class SynchronizedTest {

    public static void main(String[] args) {
        SyncThread syncThread = new SyncThread();
        Thread F_thread1 = new Thread(new SyncThread(), "F_thread1");
        Thread F_thread2 = new Thread(new SyncThread(), "F_thread2");
        Thread F_thread3 = new Thread(syncThread, "F_thread3");
        Thread F_thread4 = new Thread(syncThread, "F_thread4");
        F_thread1.start();
        F_thread2.start();
        F_thread3.start();
        F_thread4.start();
    }
}

// 1. object lock
//class SyncThread implements Runnable {
//
//    @Override
//    public void run() {
//        System.out.println(Thread.currentThread().getName() + "_Sync: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//        synchronized (new SyncThread()) {
//            try {
//                System.out.println(Thread.currentThread().getName() + "_Sync_Start: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//                Thread.sleep(2000);
//                System.out.println(Thread.currentThread().getName() + "_Sync_End: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

// 2. class lock
//class SyncThread implements Runnable {
//
//    @Override
//    public void run() {
//        System.out.println(Thread.currentThread().getName() + "_Sync: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//        synchronized (SyncThread.class) {
//            try {
//                System.out.println(Thread.currentThread().getName() + "_Sync_Start: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//                Thread.sleep(2000);
//                System.out.println(Thread.currentThread().getName() + "_Sync_End: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

// 3. this lock
//class SyncThread implements Runnable {
//
//    @Override
//    public void run() {
//        System.out.println(Thread.currentThread().getName() + "_Sync: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//        synchronized (this) {
//            try {
//                System.out.println(Thread.currentThread().getName() + "_Sync_Start: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//                Thread.sleep(2000);
//                System.out.println(Thread.currentThread().getName() + "_Sync_End: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

// 4. non-static method lock -> this lock
//class SyncThread implements Runnable {
//
//    @Override
//    public void run() {
//        sync();
//    }
//    public synchronized void sync() {
//        System.out.println(Thread.currentThread().getName() + "_Sync: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//        try {
//            System.out.println(Thread.currentThread().getName() + "_Sync_Start: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//            Thread.sleep(2000);
//            System.out.println(Thread.currentThread().getName() + "_Sync_End: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}

// 5. static method lock -> class lock
class SyncThread implements Runnable {

    @Override
    public void run() {
        sync();
    }
    public synchronized static void sync() {
        System.out.println(Thread.currentThread().getName() + "_Sync: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        try {
            System.out.println(Thread.currentThread().getName() + "_Sync_Start: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "_Sync_End: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}