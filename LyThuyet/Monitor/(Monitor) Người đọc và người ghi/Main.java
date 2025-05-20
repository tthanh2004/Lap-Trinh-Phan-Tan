public class Main {
    public static void main(String[] args) {
        ReaderWriterMonitor monitor = new ReaderWriterMonitor();
        
        for (int i = 0; i < 5; i++) {
            final int id = i;
            new Thread(() -> {
                while (true) {
                    monitor.readDB(id);
                    sleepRandom();
                }
            }, "Reader-" + id).start();
        }

       
        for (int i = 0; i < 2; i++) {
            final int id = i;
            new Thread(() -> {
                while (true) {
                    monitor.writeDB(id);
                    sleepRandom();
                }
            }, "Writer-" + id).start();
        }
    }

    static void sleepRandom() {
        try {
            Thread.sleep(1000 + (int) (Math.random() * 2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class ReaderWriterMonitor {
    private int numReader = 0;
    private int numWriter = 0;
    private final Object lock = new Object();

    public void readDB(int id) {
        synchronized (lock) {
            while (numWriter > 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            numReader++;
        }


        System.out.println(Thread.currentThread().getName() + " is reading...");
        simulateAction();

        synchronized (lock) {
            numReader--;
            lock.notifyAll();
        }
    }

    public void writeDB(int id) {
        synchronized (lock) {
            while (numReader > 0 || numWriter > 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            numWriter = 1;
        }

        System.out.println(Thread.currentThread().getName() + " is writing...");
        simulateAction();

        synchronized (lock) {
            numWriter = 0;
            lock.notifyAll();
        }
    }

    private void simulateAction() {
        try {
            Thread.sleep(1000 + (int)(Math.random() * 2000)); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
