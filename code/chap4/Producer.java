package chap4;

import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {
    SyncStack stack;
    public Producer(SyncStack stack) {
        this.stack = stack;
    }
    @Override
    public void run() {
        // 生产者，调用 push
        char c;
        for (int i = 0;i < 20;i++) {
            c = (char)(Math.random() * 26 + 'A');
            stack.push(c);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
