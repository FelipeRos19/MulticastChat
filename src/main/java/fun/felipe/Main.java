package fun.felipe;

import fun.felipe.threads.ReceiverThread;
import fun.felipe.threads.SenderThread;

import java.util.Scanner;

public class Main {
    private static final String MULTICAST_ADDRESS = "230.0.0.0";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite seu nome: ");
        String username = scanner.nextLine();

        Thread receiverThread = new Thread(new ReceiverThread(MULTICAST_ADDRESS, PORT));
        receiverThread.start();

        Thread senderThread = new Thread(new SenderThread(username, MULTICAST_ADDRESS, PORT));
        senderThread.start();
    }
}