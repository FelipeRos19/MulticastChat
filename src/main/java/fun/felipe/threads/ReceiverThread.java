package fun.felipe.threads;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ReceiverThread implements Runnable {
    private final String address;
    private final int port;

    public ReceiverThread(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("Iniciando Thread de Recebimento de Mensagens!");
        try (MulticastSocket socket = new MulticastSocket(this.port)) {
            InetAddress group = InetAddress.getByName(this.address);
            socket.joinGroup(group);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                JSONObject json = new JSONObject(received);
                System.out.println(json.getString("date") + " " + json.getString("time") + " [" + json.getString("username") + "]: " + json.getString("message"));
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
    }
}
