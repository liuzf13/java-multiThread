package chap4.ABCPrinterSync;

public class APrinter implements Runnable {
    private ABCPrinter abcPrinter;
    public APrinter(ABCPrinter printer) {
        this.abcPrinter = printer;
    }
    @Override
    public void run() {
        for (int i = 0;i < 10;++i) {
            this.abcPrinter.printA();
        }
    }
}
