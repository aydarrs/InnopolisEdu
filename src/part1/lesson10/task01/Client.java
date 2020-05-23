package part1.lesson10.task01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.concurrent.locks.LockSupport;


/**
 * Client.
 *
 * @author Aydar_Safiullin
 */
public class Client {
    private DatagramSocket socket;
    private InetAddress address;

    public Client() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
            startTalking();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            LockSupport.parkNanos(1_500_000_000L);
            socket.close();
        }
    }

    /**
     * Start talking process.
     * @throws IOException
     */
    private void startTalking() throws IOException {
        System.out.println("Введите своё имя");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = reader.readLine();
        sendMessage(name);

        startListening();
        String msg;
        while (true) {
            msg = reader.readLine();
            sendMessage(msg);
        }
    }

    /**
     * Receive messages.
     */
    private void startListening() {
        // Слушаем и выводим в консоль принятые сообщения в отдельном потоке
        Runnable listener = () -> {
            while (true) {
                try {
                    byte[] listenBuffer = new byte[256];
                    DatagramPacket packet = new DatagramPacket(listenBuffer, listenBuffer.length);
                    socket.receive(packet);
                    String received = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(received);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(listener);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Method for messages sending.
     * @param msg - send message.
     * @throws IOException
     */
    private void sendMessage(String msg) throws IOException {
        byte[] buffer = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, Server.PORT);
        socket.send(packet);
    }
}
