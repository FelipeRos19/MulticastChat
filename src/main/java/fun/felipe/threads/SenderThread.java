package fun.felipe.threads;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SenderThread implements Runnable {
    private final String username;
    private final String address;
    private final Scanner scanner;
    private final int port;

    public SenderThread(String username, String address, int port) {
        this.username = username;
        this.address = address;
        this.port = port;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        System.out.println("Iniciando Thread de Envio de Mensagens!");
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress group = InetAddress.getByName(this.address);

            while (true) {
                String message = scanner.nextLine();
                String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

                JSONObject json = new JSONObject();
                json.put("date", date);
                json.put("time", time);
                json.put("username", this.username);
                json.put("message", message);

                byte[] buffer = json.toString().getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, this.port);
                socket.send(packet);
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
    }
}
