package Mutex;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class openDoor implements Lock {
    private volatile boolean openDoor = true; // Ban đầu cửa mở

    @Override
    public void lock() {
        while (!openDoor);
        openDoor = false; // Khi chiếm được thì đóng cửa
    }

    @Override
    public void unlock() {
        openDoor = true; // Mở cửa ra
    }

    @Override
    public boolean tryLock() {
        if (openDoor) {
            openDoor = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
