import java.io.File;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Class: ServerUDP, Used to connect with ClientUDP to search through database to entry that matches first and last name
 * inputs passed by client. If matching entry is found then ssn value will be returned to client but if it isn't then -1
 * will be returned. Server will be running on IP "___".
 *
 * @author Anthony Peters
 */
public class ServerUDP {
    public static void main(String[] args) throws FileNotFoundException {
        // Initialize server data
        int serverPort = 3000, key = 2;
        byte[] buffer = new byte[65535];
        String fileName = "Sample.txt";
        DatagramPacket clientPacket;
        LinkedList<ssnNode> ssnLinkedList;

        // Initialize database
        ssnLinkedList = initializeServerData(fileName);

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
                new HandleClientUDP(clientPacket, key, serverSocket, ssnLinkedList).run();

                // Reset buffer for next request
                buffer = new byte[1000];
            }
        } catch (Exception E) {}

    }

    private static LinkedList<ssnNode> initializeServerData(String inputFileName) throws FileNotFoundException {
        // Create file and LinkedList
        File ssnFile = new File("src/" + inputFileName);
        LinkedList<ssnNode> ssnList = new LinkedList<>();

        // Create Scanner for data document
        Scanner fileScanner = new Scanner(ssnFile);

        // Cycle past headers of data
        fileScanner.nextLine();
        fileScanner.nextLine();

        // Input data from txt file to Linked list of dictionaryEntry objects
        while (fileScanner.hasNext()){
            // Input new line of test and split by tab chars
            String[] splitInput = fileScanner.nextLine().split("\t");

            // Add SSN Node including firstName, lastName, and ssn to LinkedList
            ssnList.add(new ssnNode(splitInput[0].trim(), splitInput[2].trim(), splitInput[4].trim()));
        }

        // Returns data collection to main
        return ssnList;
    }


}

