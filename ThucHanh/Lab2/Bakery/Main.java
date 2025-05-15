package ThucHanh.Lab2.Bakery;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static int x = 0;

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Input = \n");
        int n = sc.nextInt();

        Bakery.array = new int[n];
        for (int i = 0; i < n; i++) {
            Bakery.array[i] = sc.nextInt();
        }

        int k = sc.nextInt();

        Bakery bakery = new Bakery(k);
        Thread[] threads = new Thread[k];

        int chunkSize = n / k;
        int remainder = n % k;

        int start = 0;
        for (int i = 0; i < k; i++) {
            int end = start + chunkSize + (i < remainder ? 1 : 0);
            threads[i] = bakery.new MyThread(i, start, end);
            start = end;
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("Output = \n");
        System.out.println("Gia tri lon nhat: " + Bakery.shared_max);
        System.out.println("Gia tri nho nhat: " + Bakery.shared_min);
    }
}
