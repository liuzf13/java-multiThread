package chap5.Mutex;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyMutex implements Lock {
    private static class Mutex extends AbstractQueuedSynchronizer {
        Mutex(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("count must large than zero.");
            }
            setState(count);
        }

        // 当资源数为 1 时，可以获得同步状态，并将资源修改为 0
        @Override
        protected boolean tryAcquire(int reduceCount) {
            if (compareAndSetState(1, 0)) {
                // 设置当前线程独占资源
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        // 当资源为 0 时，可以进行释放
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() > 0) {
                throw new IllegalStateException();
            }
            setExclusiveOwnerThread(null);
            setState(1);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return (getState() == 0);
        }
    }

    private final Mutex mutex = new Mutex(1);

    @Override
    public void lock() {
        mutex.acquire(1);

    }

    @Override
    public void unlock() {
        mutex.release(1);
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
