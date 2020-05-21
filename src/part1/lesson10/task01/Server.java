package part1.lesson10.task01;

import part1.lesson10.task01.client.Admin;
import part1.lesson10.task01.client.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Server.
 *
 * @author Aydar_Safiullin
 */
public class Server {
    public static final int PORT = 5800;
    private static DatagramSocket socket;
    // Map для хранения поключенных клиентов.
    private static Map<Integer, InetAddress> clients = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Сервер запущен");
        try {
            socket = new DatagramSocket(PORT);

            while (true) {
                // ждем подключения клиента.
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                // добавляем клиента в map.
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                clients.put(port, address);

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);
                // проверяем, не вышел ли клиент, чтобы исключить его из рассылки.
                if (received.endsWith(Client.EXIT_MSG)) {
                    clients.remove(port);
                }
                // здесь сервер выключается админом
                if (received.startsWith(Admin.ADMIN_NAME) && received.endsWith(Client.EXIT_MSG)) {
                    return;
                }

                // Делаем рассылку всем клиентам.
                for (Map.Entry<Integer, InetAddress> client : clients.entrySet()) {
                    packet = new DatagramPacket(buffer, buffer.length, client.getValue(), client.getKey());
                    socket.send(packet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Сервер остановлен");
            socket.close();
        }
    }
}
