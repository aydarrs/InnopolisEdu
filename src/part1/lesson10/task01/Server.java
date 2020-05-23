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
    // Map для хранения адресов поключенных клиентов - используется для рассылки.
    private static Map<Integer, InetAddress> clientsAddresses = new HashMap<>();
    // Map для хранения соответствий портов и имен поключенных клиентов - используется для поиска имени по порту
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

                // добавляем клиента в наши map
                if (!clientsAddresses.containsKey(port)) {
                    connectClient(address, port, received);
                    continue;
                }

                // Делаем рассылку всем клиентам.
                String toSend = clientsNames.get(port) + ": " + received;
                System.out.println(toSend);
                sendMsg(toSend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Сервер остановлен");
            socket.close();
        }
    }

    /**
     * Add new client and notify all about this.
     * @param address - address of new client.
     * @param port - port of new client.
     * @param received - received message with name of client.
     * @throws IOException
     */
    private static void connectClient(InetAddress address, int port, String received) throws IOException {
        clientsAddresses.put(port, address);
        clientsNames.put(port, received);
        String connectInfo = clientsNames.get(port) + " подключился к чату";
        System.out.println(connectInfo);
        // уведомляем всех о подключении нового клиента
        sendMsg(connectInfo);
    }

    /**
     * Send message to all users.
     * @param toSend - sended message.
     * @throws IOException
     */
    private static void sendMsg(String toSend) throws IOException {
        byte[] sendBuffer = toSend.getBytes();
        for (Map.Entry<Integer, InetAddress> client : clientsAddresses.entrySet()) {
            DatagramPacket packet = new DatagramPacket(sendBuffer, sendBuffer.length, client.getValue(), client.getKey());
            socket.send(packet);
        }
    }
}
