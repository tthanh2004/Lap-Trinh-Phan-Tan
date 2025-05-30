package ThucHanh.Lab4.SocketUDP;

import java.net.*;
import java.io.*;
import java.util.*;

public class client {
    public static void main(String[] args) throws IOException {
        InetAddress serverAddress = InetAddress.getByName("localhost");
        int port = 2018;
        DatagramSocket socket = new DatagramSocket();
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        while (true) {
            int n = rand.nextInt(5) + 5;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(rand.nextInt(100) + 1).append(" ");
            }
            String sendStr = sb.toString().trim();
            System.out.println("Sending: " + sendStr);

            byte[] sendBuf = sendStr.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, serverAddress, port);
            socket.send(sendPacket);


            byte[] recvBuf = new byte[2048];
            DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
            socket.receive(recvPacket);
            String received = new String(recvPacket.getData(), 0, recvPacket.getLength()).trim();
            System.out.println("Received from server: " + received);


            try { Thread.sleep(2000); } catch (InterruptedException e) {}
        }
    }
}