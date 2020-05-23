package part1.lesson10.task02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

/**
 * Server.
 *
 * @author Aydar_Safiullin
 */
public class Server {
    public static final int PORT = 5800;
    private static DatagramSocket socket;
    // Клиент с именем admin будет обладать возможностью выключить сервер.
    public static final String ADMIN_NAME = "admin";
    // Сообщение об отключении сервера.
    public static final String SERVER_OFF_MSG = "Server: Сервер отключен";
    // Map для хранения адресов поключенных клиентов - используется для рассылки.
    private static Map<Integer, InetAddress> clientsAddresses = new HashMap<>();
    // Map для хранения соответствий портов и имен поключенных клиентов - используется для поиска имени по порту.
    private static Map<Integer, String> clientsPorts = new HashMap<>();
    // Map для хранения соответствий портов и имен поключенных клиентов - используется для поиска порта по имени.
    private static Map<String, Integer> clientsNames = new HashMap();


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

                // здесь сервер выключается админом
                if (clientsPorts.get(port).equals("admin") && received.equals(Client.EXIT_MSG)) {
                    sendAll(SERVER_OFF_MSG);
                    return;
                }

                // проверяем, не вышел ли клиент, чтобы исключить его из рассылки.
                if (received.equals(Client.EXIT_MSG)) {
                    disconnectClient(port);
                    continue;
                }

                // Проверяем, это сообщение отправлено конкретному пользователю или всем.
                // Чтобы отправить сообщение конкретному пользователю, необходимо начать его
                // со следующей формы: "@name, ".
                if (received.startsWith("@")) {
                    sendPrivateMessage(address, port, received);
                    continue;
                }

                // Делаем рассылку всем клиентам.
                String toSend = clientsPorts.get(port) + ": " + received;
                System.out.println(toSend);
                sendAll(toSend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
            System.out.println("Сервер отключен");
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
        clientsPorts.put(port, received);
        clientsNames.put(received, port);
        String connectMsg = clientsPorts.get(port) + " подключился к чату";
        // уведомляем всех о подключении нового клиента
        sendAll(connectMsg);
        System.out.println(connectMsg);
    }

    /**
     * Client disconnecting.
     * @param port - port of disconnecting client.
     * @throws IOException
     */
    private static void disconnectClient(int port) throws IOException {
        String disconnectMsg = clientsPorts.get(port) + " покинул чат";
        // уведомляем всех о выходе конкретного пользователя
        sendAll(disconnectMsg);
        clientsAddresses.remove(port);
        System.out.println(disconnectMsg);
    }

    /**
     * Send a private messages.
     * @param address - sender address.
     * @param port - sender port.
     * @param received received message.
     * @throws IOException
     */
    private static void sendPrivateMessage(InetAddress address, int port, String received) throws IOException {
        DatagramPacket packet;
        String name = received.substring(1, received.length()).split(",")[0];
        String privateMsg = clientsPorts.get(port) + ": " + received;
        if (clientsNames.containsKey(name)) {
            byte[] privateBuffer = privateMsg.getBytes();
            int privatePort = clientsNames.get(name);
            InetAddress privateAddress = clientsAddresses.get(privatePort);
            // Отправляем сообщение получателю.
            packet = new DatagramPacket(privateBuffer, privateBuffer.length, privateAddress, privatePort);
            socket.send(packet);
            // Отправляем сообщение отправителю.
            packet = new DatagramPacket(privateBuffer, privateBuffer.length, address, port);
            socket.send(packet);
        } else {
            // Уведомляем отправителя, что данного пользователя не существует
            byte[] privateError = "Server: Такого адрессата не существует".getBytes();
            packet = new DatagramPacket(privateError, privateError.length, address, port);
            socket.send(packet);
        }
        System.out.println(privateMsg);
    }

    /**
     * Send message to all users.
     * @param toSend - sended message.
     * @throws IOException
     */
    private static void sendAll(String toSend) throws IOException {
        DatagramPacket packet;
        byte[] sendBuffer = toSend.getBytes();
        for (Map.Entry<Integer, InetAddress> client : clientsAddresses.entrySet()) {
            packet = new DatagramPacket(sendBuffer, sendBuffer.length, client.getValue(), client.getKey());
            socket.send(packet);
        }
    }
}
