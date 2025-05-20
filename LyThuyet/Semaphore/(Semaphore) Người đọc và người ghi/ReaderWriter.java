class BinarySemaphore {
    private boolean signal = false;

    public BinarySemaphore() {
        this.signal = false;
    }

    public BinarySemaphore(boolean initial) {
        this.signal = initial;
    }

    public synchronized void acquire() throws InterruptedException {
        while (!signal) {
            wait();
        }
        signal = false;
    }

    public synchronized void release() {
        signal = true;
        notify(); 
    }
}

public class ReaderWriter {
    int numReaders = 0;
    BinarySemaphore mutex = new BinarySemaphore(true);
    BinarySemaphore wlock = new BinarySemaphore(true);

    public void startRead() throws InterruptedException {
        mutex.acquire();
        numReaders++;
        if (numReaders == 1) {
            wlock.acquire();
        }
        mutex.release();
    }

    public void endRead() throws InterruptedException {
        mutex.acquire();
        numReaders--;
        if (numReaders == 0) {
            wlock.release();
        }
        mutex.release();
    }

    public void startWrite() throws InterruptedException {
        wlock.acquire();
    }

    public void endWrite() {
        wlock.release();
    }
}
