package ThucHanh.Lab4.SocketUDP;

import java.net.*;
import java.io.*;
import java.util.*;

public class server {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(2018);
        System.out.println("UDP Server started...");
        byte[] buf = new byte[2048];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            new Thread(new ClientHandler(socket, packet)).start();
        }
    }
}

class ClientHandler implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket packet;

    public ClientHandler(DatagramSocket socket, DatagramPacket packet) {
        this.socket = socket;

        byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());
        this.packet = new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
    }

    @Override
    public void run() {
        try {
            String received = new String(packet.getData(), 0, packet.getLength()).trim();
            System.out.println("Received from client " + packet.getAddress() + ":" + packet.getPort() + " -> " + received);

            String result = process(received);

            System.out.println("Processed: " + result);

            byte[] sendBuf = result.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, packet.getAddress(), packet.getPort());
            socket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        boolean evenTurn = true;
        while (i < even.size() || j < odd.size()) {
            if (evenTurn && i < even.size()) {
                sb.append(even.get(i++)).append(" ");
            } else if (!evenTurn && j < odd.size()) {
                sb.append(odd.get(j++)).append(" ");
            }
            evenTurn = !evenTurn;
        }

        while (i < even.size()) sb.append(even.get(i++)).append(" ");
        while (j < odd.size()) sb.append(odd.get(j++)).append(" ");
        return sb.toString().trim();
    }
}