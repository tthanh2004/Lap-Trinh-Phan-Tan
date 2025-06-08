package ThucHanh.Lab5;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

// Giao diện từ xa
interface SapXepService extends Remote {
    int[] SapXep(int[] arr) throws RemoteException;
}

// Lớp triển khai giao diện
public class server extends UnicastRemoteObject implements SapXepService {

    protected server() throws RemoteException {
        super();
    }

    // Hàm sắp xếp chẵn lẻ đan xen
    public int[] SapXep(int[] arr) throws RemoteException {
        int n = arr.length;
        int[] chan = new int[n];
        int[] le = new int[n];
        int c = 0, l = 0;

        for (int i : arr) {
            if (i % 2 == 0) chan[c++] = i;
            else le[l++] = i;
        }

        int[] result = new int[n];
        int i = 0, j = 0, k = 0;
        while (i < c && j < l) {
            result[k++] = chan[i++];
            result[k++] = le[j++];
        }
        while (i < c) result[k++] = chan[i++];
        while (j < l) result[k++] = le[j++];
        return result;
    }

    // Hàm main chạy server
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); // mở RMI registry trên port 1099
            server obj = new server();
            Naming.rebind("rmi://localhost/SapXepService", obj);
            System.out.println(">> Server đã sẵn sàng.");
        } catch (Exception e) {
            System.err.println("Lỗi server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
