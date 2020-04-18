package chap4.ABCPrinterSync;

public class test {
    public static void main(String args[]) {
        ABCPrinter abcPrinter = new ABCPrinter();
        APrinter aPrinter = new APrinter(abcPrinter);
        BPrinter bPrinter = new BPrinter(abcPrinter);
        CPrinter cPrinter = new CPrinter(abcPrinter);
        Thread aPrinterThread = new Thread(aPrinter, "aPirnterThread");
        Thread bPrinterThread = new Thread(bPrinter, "bPirnterThread");
        Thread cPrinterThread = new Thread(cPrinter, "cPirnterThread");
        cPrinterThread.start();
        bPrinterThread.start();
        aPrinterThread.start();
    }
}
