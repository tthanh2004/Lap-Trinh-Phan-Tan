public class CountingSemaphore {
    private int permits;

    public CountingSemaphore(int initial) {
        if (initial < 0) throw new IllegalArgumentException("Giá trị khởi tạo phải >= 0");
        this.permits = initial;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits == 0) {
            wait();
        }
        permits--; 
    }

    public synchronized void release() {
        permits++;
        notify();
    }
}
