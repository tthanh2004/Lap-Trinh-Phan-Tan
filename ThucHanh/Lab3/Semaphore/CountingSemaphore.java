package ThucHanh.Lab3.Semaphore;


class URL {
    public static void myWait(Object obj) throws InterruptedException {
        synchronized (obj) {
            obj.wait();
        }
    }
}

public class CountingSemaphore {
    int value;
    public CountingSemaphore(int initValue) {
        value = initValue;
    }
    public synchronized void P() throws InterruptedException {
        value--;
        if (value < 0) URL.myWait(this);
    }
    public synchronized void V() {
        value++;
        if (value <= 0) notify();
    }
}
