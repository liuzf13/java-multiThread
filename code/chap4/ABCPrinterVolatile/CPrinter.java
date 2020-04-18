package chap4.ABCPrinterVolatile;

public class CPrinter implements Runnable {
    private ABCPrinter abcPrinter;
    public CPrinter(ABCPrinter abcPrinter) {
        this.abcPrinter = abcPrinter;
    }
    @Override
    public void run() {
        for (int i = 0;i < 10;++i) {
            this.abcPrinter.printC();
        }
    }
}
