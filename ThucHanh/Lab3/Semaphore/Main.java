package ThucHanh.Lab3.Semaphore;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    static BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    static boolean isPerfectSquare(int n) {
        int sqrt = (int) Math.sqrt(n);
        return (sqrt * sqrt == n);
    }

    static class MainCountingSemaphore {
        CountingSemaphore t1Counting = new CountingSemaphore(1);
        CountingSemaphore t2Counting = new CountingSemaphore(0);
        Thread t1, t2;

        MainCountingSemaphore() {
            t1 = new Thread(() -> {
                Random random = new Random();
                while (true) {
                    try {
                        t1Counting.P();
                        int n = 10 + random.nextInt(191);
                        queue.put(n);
                        t2Counting.V();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            t2 = new Thread(() -> {
                while (true) {
                    try {
                        t2Counting.P();
                        int n = queue.take();
                        if (isPrime(n)) {
                            System.out.println(n + " Nguyên tố");
                        } else if (isPerfectSquare(n)) {
                            System.out.println(n + " Chính phương");
                        } else {
                            System.out.println(n + " Không phải số nguyên tố hay chính phương");
                        }
                        t1Counting.V();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    static class MainBinarySemaphore {
        BinarySemaphore t1Binary = new BinarySemaphore(true);
        BinarySemaphore t2Binary = new BinarySemaphore(false);
        Thread t1, t2;

        MainBinarySemaphore() {
            t1 = new Thread(() -> {
                Random random = new Random();
                while (true) {
                    try {
                        t1Binary.P();
                        int n = random.nextInt(200);
                        queue.put(n);
                        t2Binary.V();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            t2 = new Thread(() -> {
                while (true) {
                    try {
                        t2Binary.P();
                        int n = queue.take();
                        if (isPrime(n)) {
                            System.out.println(n + " Nguyên tố");
                        } else if (isPerfectSquare(n)) {
                            System.out.println(n + " Chính phương");
                        } else {
                            System.out.println(n + " Không phải số nguyên tố hay chính phương");
                        }
                        t1Binary.V();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MainCountingSemaphore counting = new MainCountingSemaphore();
        counting.t1.start();
        counting.t2.start();
    }
}