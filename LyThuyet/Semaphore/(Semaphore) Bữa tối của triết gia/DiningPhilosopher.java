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


public class DiningPhilosopher implements Resource {
    int n = 0;
    BinarySemaphore[] fork = null;

    public DiningPhilosopher(int initN) {
        n = initN;
        fork = new BinarySemaphore[n];
        for (int i = 0; i < n; i++) {
            fork[i] = new BinarySemaphore(true);
        }
    }

    public void acquire(int i){
        try {
            fork[i].acquire();                   
            fork[(i + 1) % n].acquire();           
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void release(int i) {
        fork[i].release();                     
        fork[(i + 1) % n].release();       
    }

    public static void main(String[] args) {
        DiningPhilosopher dp = new DiningPhilosopher(5);
        for (int i = 0; i < 5; i++) {
            new Philosopher(i, dp);
        }
    }
}
