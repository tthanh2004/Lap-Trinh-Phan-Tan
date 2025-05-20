public class Main {
    public static void main(String[] args) {
        ReaderWriter rw = new ReaderWriter();

        for (int i = 0; i < 3; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    rw.startRead();
                    System.out.println("Reader " + id + " is reading.");
                    Thread.sleep(1000);
                    System.out.println("Reader " + id + " finished reading.");
                    rw.endRead();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        new Thread(() -> {
            try {
                Thread.sleep(500);
                rw.startWrite();
                System.out.println("Writer is writing.");
                Thread.sleep(1500);
                System.out.println("Writer finished writing.");
                rw.endWrite();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
