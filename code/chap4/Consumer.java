package chap4;

import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private SyncStack stack;
    public Consumer(SyncStack stack) {
        this.stack = stack;
    }
    @Override
    public void run() {
        char c;
        // 消费者，调用 pop 进行消费
        for (int i = 0;i < 20;i++) {
            c = this.stack.pop();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
