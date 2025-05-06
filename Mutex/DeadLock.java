package Mutex;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class DeadLock implements Lock{
    int turn = 0; // 0: thread1, 1: thread2
    public void requestCS(int i) {
        while (turn == 1 - i);
    }
    public void releaseCS(int i) {
        turn = 1 - i;
    }

    @Override
    public void lock() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lock'");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lockInterruptibly'");
    }

    @Override
    public boolean tryLock() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tryLock'");
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tryLock'");
    }

    @Override
    public void unlock() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unlock'");
    }

    @Override
    public Condition newCondition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'newCondition'");
    }

}