package chap4.ABCPrinterVolatile;

public class test {
    public static void main(String[] args) {
        ABCPrinter abcPrinter = new ABCPrinter();
        APrinter aPrinter = new APrinter(abcPrinter);
        BPrinter bPrinter = new BPrinter(abcPrinter);
        CPrinter cPrinter = new CPrinter(abcPrinter);
        Thread aPrinterThread = new Thread(aPrinter, "aPrinterThread");
        Thread bPrinterThread = new Thread(bPrinter, "bPrinterThread");
        Thread cPrinterThread = new Thread(cPrinter, "cPrinterThread");
        cPrinterThread.start();
        bPrinterThread.start();
        aPrinterThread.start();
    }
}
