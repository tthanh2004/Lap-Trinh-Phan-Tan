package ThucHanh.Lab5;

import java.rmi.Naming;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        try {
            SapXepService service = (SapXepService) Naming.lookup("rmi://localhost/SapXepService");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập số phần tử: ");
            int n = scanner.nextInt();

            int[] arr = new int[n];
            System.out.println("Nhập các số nguyên dương:");
            for (int i = 0; i < n; i++) {
                arr[i] = scanner.nextInt();
            }

            int[] result = service.SapXep(arr);

            System.out.print("Kết quả sau sắp xếp: ");
            for (int num : result) {
                System.out.print(num + " ");
            }

        } catch (Exception e) {
            System.err.println("Lỗi client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
