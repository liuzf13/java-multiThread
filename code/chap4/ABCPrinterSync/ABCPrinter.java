package chap4.ABCPrinterSync;

public class ABCPrinter {
    private int status = 1;

    public synchronized void printA() {
        while (status != 1) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print("A");
        status = 2;
        this.notifyAll();
    }

    public synchronized void printB() {
        while (status != 2) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print("B");
        status = 3;
        this.notifyAll();
    }

    public synchronized void printC() {
        while (status != 3) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print("C");
        status = 1;
        this.notifyAll();
    }
}
