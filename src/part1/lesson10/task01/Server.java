package part1.lesson10.task01;

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
    public static final String ADMIN_NAME = "admin";
    // Map для хранения адресов поключенных клиентов.
    private static Map<Integer, InetAddress> clientsAddresses = new HashMap<>();
    // Map для хранения имен поключенных клиентов.
    private static Map<Integer, String> clientsNames = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Сервер запущен");
        try {
            socket = new DatagramSocket(PORT);

            while (true) {
                // ждем подключения клиента.
                byte[] receiveBuffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                String received = new String(packet.getData(), 0, packet.getLength());

                // добавляем клиента в map
                if (!clientsAddresses.containsKey(port)) {
                    clientsAddresses.put(port, address);
                    clientsNames.put(port, received);
                    continue;
                }

                // здесь сервер выключается админом
                if (clientsNames.get(port).equals("admin") && received.equals(Client.EXIT_MSG)) {
                    return;
                }

                // проверяем, не вышел ли клиент, чтобы исключить его из рассылки.
                if (received.equals(Client.EXIT_MSG)) {
                    clientsAddresses.remove(port);
                    continue;
                }

                // Делаем рассылку всем клиентам.
                String toSend = clientsNames.get(port) + ": " + received;
                System.out.println(toSend);
                byte[] sendBuffer = toSend.getBytes();
                for (Map.Entry<Integer, InetAddress> client : clientsAddresses.entrySet()) {
                    packet = new DatagramPacket(sendBuffer, sendBuffer.length, client.getValue(), client.getKey());
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
