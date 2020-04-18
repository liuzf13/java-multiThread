package chap4;

public class SyncStack {
    private int index = 0;
    // 资源范围
    private char[] buffer = new char[1];

    // 生产
    public synchronized void push(char c) {
        // 生产区间满了，等待
        while (index == 1) {
            try{
                this.wait();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 进行生产
        buffer[index++] = c;
        System.out.println("Produced: " + c);
        // 完成生产，唤醒等待在该对象上的线程进行消费
        this.notifyAll();
    }

    // 消费
    public synchronized char pop() {
        // 当生产区间为空时，等待
        while (index == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 进行消费
        index -= 1;
        System.out.println("Consumed:" + buffer[index]);

        // 唤醒生产者进行生产
        this.notifyAll();
        return buffer[index];
    }
}
