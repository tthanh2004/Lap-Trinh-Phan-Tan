package ThucHanh.Lab4.SocketTCP;

import java.io.*;
import java.net.*;
import java.util.*;

public class client {
    public final static String SERVER_IP = "127.0.0.1";
    public final static int SERVER_PORT = 7;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int n = rand.nextInt(5) + 5;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(rand.nextInt(100) + 1).append(" ");
            }
            String numbers = sb.toString().trim();
            System.out.println("Sending: " + numbers);
            out.println(numbers);

            String response = in.readLine();
            System.out.println("Received from server: " + response);

            System.out.print("Press Enter to send next sequence, or type 'exit' to quit: ");
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("exit")) break;
        }

        socket.close();
    }
}