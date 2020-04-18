package chap4.ABCPrinterVolatile;

public class BPrinter implements Runnable {
    private ABCPrinter abcPrinter;
    public BPrinter(ABCPrinter abcPrinter) {
        this.abcPrinter = abcPrinter;
    }
    @Override
    public void run() {
        for (int i = 0;i < 10;++i) {
            this.abcPrinter.printB();
        }
    }
}
