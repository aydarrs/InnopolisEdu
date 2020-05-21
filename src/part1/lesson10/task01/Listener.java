package part1.lesson10.task01;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Listener.
 *
 * @author Aydar_Safiullin
 */
public class Listener extends Thread {
    public static final int PORT = 5900;
    private DatagramSocket socket;
    @Override
    public void run() {
        byte[] buffer = new byte[256];
        try {
            while (true) {
                socket = new DatagramSocket(PORT);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                if ("end".equals(received)) {
                    System.out.println("Клиент вышел из чата");
                    return;
                }
                System.out.println(received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
