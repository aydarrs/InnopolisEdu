package part1.lesson10.task02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.locks.LockSupport;


/**
 * Client.
 *
 * @author Aydar_Safiullin
 */
public class Client {
    private DatagramSocket socket;
    private InetAddress address;
    private String name;
    // Сообщение для выхода и отключения сервера админом
    public static final String EXIT_MSG = "quit";


    public Client() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
            startTalking();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            LockSupport.parkNanos(1_000_000_000L);
            socket.close();
            System.out.println(socket.isClosed() ? "Соединение закрыто" : "Соединение не закрыто");
        }
    }

    /**
     * Start talking process.
     * @throws IOException
     */
    private void startTalking() throws IOException {
        System.out.println("Введите своё имя");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        name = reader.readLine();
        sendMessage(name);

        startListening();
        String msg;
        while (!EXIT_MSG.equals(msg = reader.readLine())) {
            sendMessage(msg);
        }
        // Если клиент админ, то он уведомляет остальных об отключении сервера.
        if (Server.ADMIN_NAME.equals(name)) {
            sendMessage("Внимание, сейчас сервер будет отключен!");
        }
        sendMessage(EXIT_MSG);
        System.out.println("Вы покинули чат");

        reader.close();
    }

    /**
     * Receive messages.
     */
    private void startListening() {
        Runnable listener = () -> {
            while (true) {
                try {
                    byte[] listenBuffer = new byte[256];
                    DatagramPacket packet = new DatagramPacket(listenBuffer, listenBuffer.length);
                    socket.receive(packet);
                    String received = new String(packet.getData(), 0, packet.getLength());
                    // Если мы вышли из чата, прекращаем принимать сообщения.
                    if ((name + " покинул чат").equals(received)) {
                        return;
                    }
                    System.out.println(received);
                    // Если сервер отключен, прекращаем принимать сообщения.
                    if (Server.SERVER_OFF_MSG.equals(received)) {
                        return;
                    }
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
