package chap4;

public class ProducerConsumerTest {
    public static void main(String args[]) {
        // 生产者、消费者线程要同时访问 SyncStack 对象
        SyncStack stack = new SyncStack();
        Consumer consumer = new Consumer(stack);
        Producer producer = new Producer(stack);
        Thread consumerThread = new Thread(consumer, "consumerThread");
        Thread producerThread = new Thread(producer, "producerThread");
        consumerThread.start();
        producerThread.start();
    }
}
