package ThucHanh.Lab3.Monitor;

import java.util.Random;

public class Main {

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

    public static void main(String[] args) {
        BoundedBufferMonitor buffer = new BoundedBufferMonitor();

        Thread t1 = new Thread(() -> {
            Random rand = new Random();
            while (true) {
                double num = 10 + rand.nextInt(191);
                try {
                    buffer.deposit(num);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                try {
                    double num = buffer.fetch();
                    if (isPrime((int) num)) {
                        System.out.println((int) num + " - Nguyên tố");
                    } else if (isPerfectSquare((int) num)) {
                        System.out.println((int) num + " - Chính phương");
                    }
                    else {
                        System.out.println((int) num + " - Không phải nguyên tố và không phải chính phương");
                    }
                } catch (InterruptedException e) {
                }
            }
        });

        t1.start();
        t2.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
        t2.interrupt();
    }
}