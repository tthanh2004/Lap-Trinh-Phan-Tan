public class Bakery implements lock {
    private final int n;
    private volatile boolean[] choosing;
    private volatile int[] number;

    public Bakery(int n) {
        this.n = n;
        choosing = new boolean[n];
        number = new int[n];
        for (int i = 0; i < n; i++) {
            choosing[i] = false;
            number[i] = 0;
        }
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
            )) {
                // busy wait
            }
        }
    }

    @Override
    public void releaseCS(int pid) {
       number[pid] = 0;
    }
}
