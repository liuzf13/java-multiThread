package chap4;

import java.util.concurrent.TimeUnit;

public class DaemonThreadTest {
    public static void main(String args[]) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunnerThread");
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("DaemonRunner end");
            }
        }
    }
}
