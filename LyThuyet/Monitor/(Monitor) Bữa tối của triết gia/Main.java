import java.util.Random;

public class Main {
    static final int N = 5; 
    static final String[] state = new String[N]; 
    static final Object lock = new Object();

    public static void main(String[] args) {
        for (int i = 0; i < N; i++) {
            state[i] = "thinking";
        }

        for (int i = 0; i < N; i++) {
            final int id = i;
            new Thread(() -> {
                while (true) {
                    think(id);
                    acquire(id);
                    eat(id);
                    release(id);
                }
            }, "Philosopher-" + id).start();
        }
    }

    static void acquire(int i) {
        synchronized (lock) {
            state[i] = "hungry";
            checkStartEating(i);
            while (!state[i].equals("eating")) {
                myWait(lock);
            }
        }
    }

    static void release(int i) {
        synchronized (lock) {
            state[i] = "thinking";
            checkStartEating(left(i));
            checkStartEating(right(i));
        }
    }

    static void checkStartEating(int i) {
        if (!state[left(i)].equals("eating") &&
            !state[right(i)].equals("eating") &&
            state[i].equals("hungry")) {
            state[i] = "eating";
            lock.notifyAll();
        }
    }

    static int left(int i) {
        return (i + N - 1) % N;
    }

    static int right(int i) {
        return (i + 1) % N;
    }

    static void think(int i) {
        System.out.println(Thread.currentThread().getName() + " is thinking.");
        sleepRandom();
    }

    static void eat(int i) {
        System.out.println(Thread.currentThread().getName() + " is eating.");
        sleepRandom();
    }

    static void myWait(Object obj) {
        try {
            obj.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static void sleepRandom() {
        try {
            Thread.sleep(500 + new Random().nextInt(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
