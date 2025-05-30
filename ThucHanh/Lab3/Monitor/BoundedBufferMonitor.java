package ThucHanh.Lab3.Monitor;

class Util {
    public static void myWait(Object obj) throws InterruptedException{
        synchronized (obj) {
            obj.wait();
        }
    }
}

class BoundedBufferMonitor {
    final int sizeBuf = 10;
    double[] buffer = new double[sizeBuf];
    int inBuf = 0, outBuf = 0, count = 0;
    public synchronized void deposit(double value) throws InterruptedException {
        while (count == sizeBuf) // buffer full
            Util.myWait(this);
        buffer[inBuf] = value;
        inBuf = (inBuf + 1) % sizeBuf;
        count++;
        if (count == 1) // items available for fetch
            notify();
    }
    public synchronized double fetch() throws InterruptedException {
        double value;
        while (count == 0) // buffer empty
            Util.myWait(this);
        value = buffer[outBuf];
        outBuf = (outBuf + 1) % sizeBuf;
        count--;
        if (count == sizeBuf - 1) // empty slots available
            notify();
        return value;
    }
}
