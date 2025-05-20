class Philosopher implements Runnable {
    int id = 0;
    Resource r = null;

    public Philosopher(int initID, Resource initr) {
        id = initID;
        r = initr;
        new Thread(this).start();
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Philosopher " + id + " is thinking");
                Thread.sleep(30);
                System.out.println("Philosopher " + id + " is hungry");
                r.acquire(id);
                System.out.println("Philosopher " + id + " is eating");
                Thread.sleep(40);
                r.release(id);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
interface Resource {
    void acquire(int i);
    void release(int i);
}