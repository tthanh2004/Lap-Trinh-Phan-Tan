package DaLuong;

import java.util.Random;
import java.util.Scanner;

public class Main {

    static int[] A;
    static int N = 1000000;

    public static void main(String[] args) throws InterruptedException {
        A = new int[N];
        Random rand = new Random();

        for (int i = 0; i < N; i++) {
            A[i] = rand.nextInt(1000);
        }


        System.out.print("Array: ");
        for (int i = 0; i < N; i++) {
            System.out.print(A[i] + " ");
        }
        System.out.println();
        
        //chạy với 2 luồng
        System.out.println("Running with 2 threads...");
        long startTime = System.currentTimeMillis();
        int[] count = new int[2];

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < N / 2; i++) {
                if (isPerfectSquare(A[i])) {
                    //System.out.println("T1 found perfect square: " + A[i]);
                    count[0]++;
                }
                //System.out.println("Thread 1 found " + count[0] + " perfect squares.");
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = N / 2; i < N; i++) {
                if (isPerfectSquare(A[i])) {
                    //System.out.println("T2 found perfect square: " + A[i]);
                    count[1]++;
                }
                //System.out.println("Thread 2 found " + count[1] + " perfect squares.");
            }
        });

        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        long endTime = System.currentTimeMillis();
        System.out.println("Total perfect squares found by 2 threads: " + (count[0] + count[1]));
        System.out.println("Time taken with 2 threads: " + (endTime - startTime) + " ms");

        //chạy với 1 luồng
        System.out.println("Running with 1 thread...");
        startTime = System.currentTimeMillis();
        int countSingleThread = 0;
        for (int i = 0; i < N; i++) {
            if (isPerfectSquare(A[i])) {
                countSingleThread++;
                //System.out.println("Found perfect square: " + A[i]);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("Total perfect squares found by 1 thread: " + countSingleThread);
        System.out.println("Time taken with 1 thread: " + (endTime - startTime) + " ms");

        //chạy với k luồng
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of threads : ");
        int k = scanner.nextInt();
        if (k < 1 || k > N) {
            System.out.println("Invalid number of threads. Please enter a value between 1 and " + N);
            return;
        }
        System.out.println("Running with " + k + " threads...");
        startTime = System.currentTimeMillis();
        int[] countKThreads = new int[k];
        Thread[] threads = new Thread[k];

        for (int i = 0; i < k; i++) {
            int threadIndex = i;
            threads[i] = new Thread(() -> {
                int start = (N / k) * threadIndex;
                int end = (threadIndex == k - 1) ? N : (N / k) * (threadIndex + 1);
                for (int j = start; j < end; j++) {
                    if (isPerfectSquare(A[j])) {
                        //System.out.println("Thread " + threadIndex + " found perfect square: " + A[j]);
                        countKThreads[threadIndex]++;
                    }
                }
                //System.out.println("Thread " + threadIndex + " found " + countKThreads[threadIndex] + " perfect squares.");
            });
            threads[i].start();
            threads[i].join();
        }

        int totalPerfectSquaresK = 0;
        for (int threadCount : countKThreads) {
            totalPerfectSquaresK += threadCount;
        }
        endTime = System.currentTimeMillis();
        System.out.println("Total perfect squares found by " + k + " threads: " + totalPerfectSquaresK);
        System.out.println("Time taken with " + k + " threads: " + (endTime - startTime) + " ms");
    }

    // Hàm kiểm tra số chính phương
    static boolean isPerfectSquare(int n) {
        int sqrt = (int) Math.sqrt(n);
        return sqrt * sqrt == n;
    }
}
