public class Main {

    static class BoundedBuffer {
        private final int sizeBuf = 10;
        private final double[] buffer = new double[sizeBuf];
        private int inBuf = 0, outBuf = 0, count = 0;

        public synchronized void deposit(double value) {
            while (count == sizeBuf) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            buffer[inBuf] = value;
            inBuf = (inBuf + 1) % sizeBuf;
            count++;

            if (count == 1) {
                notify();
            }
        }

        public synchronized double fetch() {
            while (count == 0) {
                try {
                    wait(); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            double value = buffer[outBuf];
            outBuf = (outBuf + 1) % sizeBuf;
            count--;

            if (count == sizeBuf - 1) {
                notify(); 
            }

            return value;
        }
    }

    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer();

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                double value = Math.random() * 100;
                buffer.deposit(value);
                System.out.printf("Sản xuất: %.2f\n", value);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Thread Consumer
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                double value = buffer.fetch();
                System.out.printf("Tiêu thụ: %.2f\n", value);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Bắt đầu chạy
        producer.start();
        consumer.start();
    }
}
