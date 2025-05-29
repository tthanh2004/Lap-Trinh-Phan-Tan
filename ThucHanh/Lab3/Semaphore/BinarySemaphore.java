package ThucHanh.Lab3.Semaphore;

class Util {
    public static void myWait(Object obj) throws InterruptedException{
        synchronized (obj) {
            obj.wait();
        }
    }
}
public class BinarySemaphore {
    boolean value;
    public BinarySemaphore(boolean initValue) {
        value = initValue;
    }
    public synchronized void P() throws InterruptedException {
        while (value == false)
            Util.myWait(this);
        value = false;
    }
    public synchronized void V() {
        value = true;
        notify();
    }
}