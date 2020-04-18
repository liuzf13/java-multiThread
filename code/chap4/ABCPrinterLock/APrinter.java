package chap4.ABCPrinterLock;

public class APrinter implements Runnable {
    private ABCPrinter printer;
    public APrinter(ABCPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void run() {
        for (int i = 0;i < 10;++i) {
            this.printer.printA();
        }
    }
}
