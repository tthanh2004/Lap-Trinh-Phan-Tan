package ThucHanh.Lab2.Bakery;

import java.util.concurrent.locks.Lock;

public class Bakery implements lock {

    static int shared_max = Integer.MIN_VALUE;
    static int shared_min = Integer.MAX_VALUE;
    static int[] array;

    int n;
    boolean[] choosing;
    int[] number;

    public Bakery(int n) {
        this.n = n;
        choosing = new boolean[n];
        number = new int[n];
    }

    @Override
    public void requestCS(int pid) {
        choosing[pid] = true;

        int max = 0;
        for (int i = 0; i < n; i++) {
            if (number[i] > max) {
                max = number[i];
            }
        }
        number[pid] = max + 1;
        choosing[pid] = false;

        for (int j = 0; j < n; j++) {
            if (j == pid) continue;

            while (choosing[j]);

            while (number[j] != 0 && (
                number[j] < number[pid] || 
                (number[j] == number[pid] && j < pid)
            ));
        }
    }

    @Override
    public void releaseCS(int pid) {
       number[pid] = 0;
    }

    public class MyThread extends Thread {
        int tid, startIdx, endIdx;

        public MyThread(int tid, int startIdx, int endIdx) {
            this.tid = tid;
            this.startIdx = startIdx;
            this.endIdx = endIdx;
        }

        @Override
        public void run() {
            int localMax = array[startIdx];
            int localMin = array[startIdx];

            for (int i = startIdx + 1; i < endIdx; i++) {
                localMax = Math.max(localMax, array[i]);
                localMin = Math.min(localMin, array[i]);
            }

            requestCS(tid);
            if (localMax > shared_max) shared_max = localMax;
            if (localMin < shared_min) shared_min = localMin;
            releaseCS(tid);
        }
    }
}
