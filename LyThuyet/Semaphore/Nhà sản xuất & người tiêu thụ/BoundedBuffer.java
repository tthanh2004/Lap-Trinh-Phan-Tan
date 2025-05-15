public class BoundedBuffer<T> {
    private final Object[] buffer;
    private int head = 0, tail = 0, count = 0;
    private final CountingSemaphore emptySlots;
    private final CountingSemaphore filledSlots;
    private final BinarySemaphore mutex;

    public BoundedBuffer(int size) {
        buffer = new Object[size];
        emptySlots = new CountingSemaphore(size); 
        filledSlots = new CountingSemaphore(0);   
        mutex = new BinarySemaphore();
        mutex.release();
    }

    public void put(T item) throws InterruptedException {
        emptySlots.acquire();
        mutex.acquire();
        buffer[tail] = item;
        tail = (tail + 1) % buffer.length;
        count++;
        mutex.release();
        filledSlots.release();
    }

    @SuppressWarnings("unchecked")
    public T get() throws InterruptedException {
        filledSlots.acquire();
        mutex.acquire();
        T item = (T) buffer[head];
        head = (head + 1) % buffer.length;
        count--;
        mutex.release();
        emptySlots.release();
        return item;
    }
}
