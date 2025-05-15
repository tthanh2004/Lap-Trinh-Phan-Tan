import java.util.Random;

public class main {
    static int x = 0;

    public static void main(String[] args) throws InterruptedException {
        final int N = 5;
        Bakery lock = new Bakery(N);
        Thread[] threads = new Thread[N];

        for (int i = 0; i < N; i++) {
            int pid = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    lock.requestCS(pid);
                    x++;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.releaseCS(pid);
                }
            });
        }

        for (int i = 0; i < N; i++) threads[i].start();
        for (int i = 0; i < N; i++) threads[i].join();

        System.out.println("Final value of x: " + x);  // Expected: N * 1000
    }
}
