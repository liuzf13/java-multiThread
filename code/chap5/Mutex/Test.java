package chap5.Mutex;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;


public class Test {
    public static void main(String args[]) throws InterruptedException {
        Lock lock = new MyMutex();
        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName());
                    } finally {
                        lock.unlock();
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {}
                    }
                }
            }
        }

        // 启动 10 个线程进行测试
        // 理论上，应该每秒输出一次，因为最多只有一个线程能获得同步状态
        for (int i = 0;i < 10;++i) {
            Worker w = new Worker();
            w.start();
        }

    }
}
