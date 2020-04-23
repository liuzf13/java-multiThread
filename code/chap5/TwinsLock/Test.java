package chap5.TwinsLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Test {
    public static void main(String args[]) throws InterruptedException {
        Lock lock = new TwinsLock();
        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        // 启动 10 个线程进行测试
        // 理论上，应该成对输出，因为最多只有两个线程能获得同步状态
        for (int i = 0;i < 10;++i) {
            Worker w = new Worker();
            w.start();
        }

    }
}
