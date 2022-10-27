import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.LinkedList;

public class HandleClientUDP {
    int clientPort;
    String clientMessage;
    DatagramPacket clientPacket;
    InetAddress clientAddress;
    LinkedList<ssnNode> databaseLinkedList;

    HandleClientUDP(DatagramPacket packet, LinkedList<ssnNode> databaseInput) {
        // Store packet and packet data
        clientPacket = packet;
        clientPort = packet.getPort();
        clientAddress = packet.getAddress();
        clientMessage = new String(packet.getData()).trim();
        databaseLinkedList = databaseInput;
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
