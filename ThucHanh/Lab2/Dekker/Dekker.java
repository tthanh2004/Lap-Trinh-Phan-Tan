package ThucHanh.Lab2.Dekker;

public class Dekker implements Lock {

    static int shared_max = Integer.MIN_VALUE;
    static int shared_min = Integer.MAX_VALUE;
    static int[] array;

    int turn = 0;
    boolean[] wantCS = {false, false};

    @Override
    public void requestCS(int i) {
        wantCS[i] = true;
        while (wantCS[1 - i]) {
            if (turn != i) {
                wantCS[i] = false;
                while (turn != i);
                wantCS[i] = true;
            }
        }
    }

    @Override
    public void releaseCS(int i) {
        turn = 1 - i;
        wantCS[i] = false;
    }

    public class myThread extends Thread {
        Lock lock;
        int tid;
        int startIdx;
        int endIdx;

        public myThread(int tid) {
            this.lock = Dekker.this;
            this.tid = tid;
            int mid = array.length / 2;
            this.startIdx = tid * mid;
            this.endIdx = (tid == 0) ? mid : array.length;
        }

        void CS(int localMax, int localMin) {
            if (localMax > shared_max) shared_max = localMax;
            if (localMin < shared_min) shared_min = localMin;
        }

        @Override
        public void run() {
            int localMax = array[startIdx];
            int localMin = array[startIdx];

            for (int i = startIdx + 1; i < endIdx; i++) {
                if (array[i] > localMax) localMax = array[i];
                if (array[i] < localMin) localMin = array[i];
            }

            lock.requestCS(tid);
            CS(localMax, localMin);
            lock.releaseCS(tid);
        }
    }
}
