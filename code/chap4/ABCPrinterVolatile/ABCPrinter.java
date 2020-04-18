package chap4.ABCPrinterVolatile;

public class ABCPrinter {
    private volatile int status = 1;
    public void printA() {
        while (status != 1) {

        }
        System.out.print("A");
        status = 2;
    }

    public void printB() {
        while (status != 2) {

        }
        System.out.print("B");
        status = 3;
    }

    public void printC() {
        while (status != 3) {

        }
        System.out.print("C");
        status = 1;
    }
}
