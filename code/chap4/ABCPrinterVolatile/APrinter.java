package chap4.ABCPrinterVolatile;

public class APrinter implements Runnable {
    private ABCPrinter abcPrinter;
    public APrinter(ABCPrinter abcPrinter) {
        this.abcPrinter = abcPrinter;
    }
    @Override
    public void run() {
        for (int i = 0;i < 10;++i) {
            this.abcPrinter.printA();
        }
    }
}
