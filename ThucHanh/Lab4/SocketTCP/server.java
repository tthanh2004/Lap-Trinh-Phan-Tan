package ThucHanh.Lab4.SocketTCP;

import java.io.*;
import java.net.*;
import java.util.*;

public class server {
    public final static int SERVER_PORT = 7;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("Server started on port " + SERVER_PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket);
            new Thread(new ClientHandler(socket)).start();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                String result = process(line);
                System.out.println("Received: " + line);
                System.out.println("Processed: " + result);
                out.println(result);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + socket);
        } finally {
            try { socket.close(); } catch (IOException e) {}
        }
    }

    // Sắp xếp đan xen chẵn lẻ
    private String process(String input) {
        String[] tokens = input.trim().split("\\s+");
        List<Integer> even = new ArrayList<>();
        List<Integer> odd = new ArrayList<>();
        for (String token : tokens) {
            int num = Integer.parseInt(token);
            if (num % 2 == 0) even.add(num);
            else odd.add(num);
        }
        StringBuilder sb = new StringBuilder();
        int i = 0, j = 0;
        boolean evenTurn = even.size() > 0;
        while (i < even.size() || j < odd.size()) {
            if (evenTurn && i < even.size()) {
                sb.append(even.get(i++)).append(" ");
            } else if (!evenTurn && j < odd.size()) {
                sb.append(odd.get(j++)).append(" ");
            }
            evenTurn = !evenTurn;
        }
        // Thêm số còn lại nếu có
        while (i < even.size()) sb.append(even.get(i++)).append(" ");
        while (j < odd.size()) sb.append(odd.get(j++)).append(" ");
        return sb.toString().trim();
    }
}