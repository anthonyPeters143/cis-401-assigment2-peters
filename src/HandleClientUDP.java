import java.net.DatagramPacket;
import java.net.InetAddress;

public class HandleClientUDP {
    int clientPort;
    String clientMessage;
    DatagramPacket clientPacket;
    InetAddress clientAddress;

    HandleClientUDP(DatagramPacket packet) {
        // Store packet and packet data
        clientPacket = packet;
        clientPort = packet.getPort();
        clientAddress = packet.getAddress();
        clientMessage = new String(packet.getData()).trim();
    }

    public void run() {
        // Receive input from client

        // Decrypt data
            // Method in ClientUDP

        // Separate name fields
            // Name inputs separated by "_"

        // Check data collection

        // Create reply
            // Return SSN if both names match, if not return -1

        // Encrypt data
            // Method in ClientUDP

    }


}
