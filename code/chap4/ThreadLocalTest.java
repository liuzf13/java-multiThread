package chap4;

public class ThreadLocalTest {
    static ThreadLocal<String> localVar = new ThreadLocal<>();
    public static void main(String[] args) {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                localVar.set("threadA");
                System.out.println("threadA localVale: " + localVar.get());
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                localVar.set("threadB");
                System.out.println("threadB localVale: " + localVar.get());
            }
        });

        threadA.start();
        threadB.start();
    }

    // 首先，要给当前对象加锁
    public synchronized Object get(long mills) throws InterruptedException {
        long future = System.currentTimeMillis() + mills;
        long remaining = mills;
        String result = null;

        // 当剩余等待时间 > 0 且未得到计算结果时，等待
        while (result == null && remaining > 0) {
            wait(remaining);
            remaining = future - System.currentTimeMillis();
        }
        return result;
    }
}
