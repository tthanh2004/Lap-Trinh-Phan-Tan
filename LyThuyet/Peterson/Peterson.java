public class Peterson implements lock {
    private volatile boolean[] flag = new boolean[2];
    private volatile int turn;

    public Peterson() {
        flag[0] = false;
        flag[1] = false;
        turn = 0;
    }

    public void requestCS(int pid) {
        int other = 1 - pid;
        flag[pid] = true;
        turn = other;
        while (flag[other] && turn == other) {
            // busy wait
        }
    }

    public void releaseCS(int pid) {
        flag[pid] = false;
    }
}
