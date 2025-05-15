package ThucHanh.Lab2.Dekker;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("input = \n");
        int n = sc.nextInt();
        Dekker.array = new int[n];

        for (int i = 0; i < n; i++) {
            Dekker.array[i] = sc.nextInt();
        }

        Dekker dekker = new Dekker();

        Dekker.myThread t1 = dekker.new myThread(0);
        Dekker.myThread t2 = dekker.new myThread(1);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Output = \n");
        System.out.println("Gia tri lon nhat: " + Dekker.shared_max);
        System.out.println("Gia tri nho nhat: " + Dekker.shared_min);
    }
}
