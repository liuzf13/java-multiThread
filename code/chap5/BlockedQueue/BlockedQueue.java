package chap5.BlockedQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockedQueue {
    private int[] items;
    private int addIndex, removeIndex, count;

    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public BlockedQueue(int size) {
        this.items = new int[size];
    }

    // 添加一个元素
    // 如果队列满，则不允许添加，线程阻塞在 notFull 上，加入到 notFull 的等待队列
    // 线程从 notFull.await() 返回，说明队列非满，可以继续添加
    // 添加完后，可以唤醒 notEmpty 等待队列中的线程，进行消费(因为此时队列必然有元素)
    public void add(int value) throws InterruptedException {
        lock.lock();
        try{
            // 队列满，阻塞添加线程
            while (count == items.length) {
                notFull.await();
            }

            // 从 notFull.await() 返回后，队列非满，可以添加元素
            items[addIndex] = value;
            if (++addIndex == items.length) {
                addIndex = 0;
            }
            ++count;

            // 此时，队列必不为空，可以唤醒阻塞在 notEmpty 上的线程进行消费
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    // 消费一个元素
    // 如果队列空，则不允许消费，线程阻塞在 notEmpty 上，加入到 notEmpty 的阻塞队列
    // 线程从 notEmpty.await() 返回，说明队列非空，可以继续消费
    // 消费完后，队列肯定不是满的，可以唤醒 notFull 等待队列中的线程进行添加
    public int remove() throws InterruptedException {
        lock.lock();
        try {
            // 队列空，阻塞消费线程
            while (count == 0) {
                notEmpty.await();
            }

            // 从 notEmpty.await() 返回后，队列非空，可以进行消费
            int result = items[removeIndex];
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            --count;

            // 消费完后，队列非满，可以唤醒 notFull 上阻塞的线程进行添加
            notFull.signal();
            return result;
        } finally {
            lock.unlock();
        }
    }
}
