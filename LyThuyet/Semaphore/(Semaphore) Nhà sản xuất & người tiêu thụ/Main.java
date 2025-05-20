public class Main {
    public static void main(String[] args) {
        BoundedBuffer<Integer> buffer = new BoundedBuffer<>(5);

        Runnable producer = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("Sản xuất: " + i);
                    buffer.put(i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable consumer = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    int value = buffer.get();
                    System.out.println("Tiêu thụ: " + value);
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
