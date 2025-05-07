import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main {

    static int x = 0;

    public static void main(String[] args) throws InterruptedException {
        Peterson lock = new Peterson();
        List<Thread> threads = new ArrayList<>();

        // Thread 0
        Thread t0 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                lock.requestCS(0);
                x = x + 1;
                lock.releaseCS(0);

            }
        });

        // Thread 1
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                lock.requestCS(1);
                x = x + 1;

                lock.releaseCS(1);
            }
        });

        threads.add(t0);
        threads.add(t1);

        threads.forEach(Thread::start);
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Final value of x: " + x);  // Expected: 2000
    }
}
