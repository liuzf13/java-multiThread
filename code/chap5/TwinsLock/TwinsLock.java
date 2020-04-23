package chap5.TwinsLock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock {
    // 自定义同步器，继承 AQS
    private static class Sync extends AbstractQueuedSynchronizer {
        // 构造函数，初始化资源数量
        Sync(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("count must large than zero.");
            }
            // 初始化资源，调用 setState
            setState(count);
        }

        // 我们这里需要用共享式访问，因此需要重写 tryAcquireShared 和 tryReleaseShared
        // 尝试获取资源
        @Override
        protected int tryAcquireShared(int reduceCount) {
            for (;;) {
                // 计算分配后的资源数，并判断是否合法
                int currentCount = getState();
                int newCount = currentCount - reduceCount;

                // 如果剩余资源不合法，返回剩余资源数
                // 如果剩余资源合法，则进行 CAS 更新，成功则返回剩余资源数
                // 用 for 死循环，自旋进行资源判断，直到资源不足或 CAS 成功
                if (newCount < 0 || compareAndSetState(currentCount, newCount)) {
                    return newCount;
                }
            }
        }

        // 释放资源
        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for (;;) {
                // 释放资源，通过 CAS 进行资源数修改，CAS 成功后返回
                int currentCount = getState();
                int newCount = currentCount + returnCount;
                if (compareAndSetState(currentCount, newCount)) {
                    return true;
                }
            }
        }
    }

    // 资源总数定义为 2
    private final Sync sync = new Sync(2);

    // 暴露给用户的接口，加锁
    // 调用 acquireShared 模板方法，acquireShared 会自动执行 tryAcquireShared，
    // 并根据 tryAcquireShared 的结果来判断是否进行 doAcquireShared，维护同步队列
    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    // 暴露给用户的接口，释放锁
    // 调用 releaseShared 模板方法，并自动执行 tryReleaseShared
    // 条件不足则自动进行 doReleaseShared
    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }



    @Override
    public Condition newCondition() {
        return null;
    }
}
