package chap4;

import java.util.concurrent.TimeUnit;

public class ThreadState {
    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        // 两个 Blocked 线程，一个获得锁，一个被阻塞
        new Thread(new Blocked(), "BlockThread-1").start();
        new Thread(new Blocked(), "BlockThread-2").start();
    }

    public static void doSleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // 该线程不断地进行睡眠
    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                doSleep(100);
            }
        }
    }

    // 该线程在 Waiting.class 上等待
    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 该线程在 Blocked.class 实例上加锁后，不会释放锁
    // 同时创建两个实例，第一个成功获得锁，第二个得不到锁
    static class Blocked implements Runnable {
        @Override
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    doSleep(100);
                }
            }
        }
    }
}
