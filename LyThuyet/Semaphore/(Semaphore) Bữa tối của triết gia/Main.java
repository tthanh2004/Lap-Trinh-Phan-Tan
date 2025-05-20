public class Main {
    public static void main(String[] args) {
        int numPhilosophers = 5;
        DiningPhilosopher dp = new DiningPhilosopher(numPhilosophers);

        for (int i = 0; i < numPhilosophers; i++) {
            new Philosopher(i, dp);
        }
    }
}
