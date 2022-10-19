import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerUDP {
    public static void main(String[] args) {
        // Initialize server data
        int clientPort, serverPort = 3000;
        byte[] buffer = new byte[65535];
        String clientData, sendingData;
        DatagramPacket clientPacket, sendingPacket;
        InetAddress clientAddress;

        try {
            // Set up connection with client
            DatagramSocket serverSocket = new DatagramSocket(serverPort);

            // Start thread loop
            while (true) {

                // Output connection message
                System.out.println("Waiting for connection");

                // Accept socket connection
                clientPacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(clientPacket);

                // Create and run new thread
                new HandleClientUDP().run();

            }
        } catch (Exception E) {

        }



    }

    private static void initializeServerData(File inputFile) {
        // Reads data into a data collection

        // Returns data collection to main
    }

}

