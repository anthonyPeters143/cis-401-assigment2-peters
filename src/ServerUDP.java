import java.io.File;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Scanner;

public class ServerUDP {
    public static void main(String[] args) {
        // Initialize server data
        int serverPort = 3000;
        byte[] buffer = new byte[65535];
        String clientData, sendingData;
        DatagramPacket clientPacket, sendingPacket;
        InetAddress clientAddress;

        // Initialize database

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
                new HandleClientUDP(clientPacket).run();

                // Reset buffer for next request
                buffer = new byte[1000];
            }
        } catch (Exception E) {

        }



    }

    private static void initializeServerData(File inputFile) throws FileNotFoundException {
        // Reads data into a data collection
            // Linked list

        // Returns data collection to main

        // Create file and LinkedList
        File ssnFile = new File("src/" + String.valueOf(inputFile));
        LinkedList<ssnNode> ssnList = new LinkedList<>();

        // Create Scanner for data document
        Scanner fileScanner = new Scanner(ssnFile);

        // Input data from txt file to Linked list of dictionaryEntry objects
        while (fileScanner.hasNext()){
            // Input new line of test and split by tab chars
            fileScanner.nextLine().replaceAll()
            String[] splitInput = fileScanner.nextLine().split("\t");

            // Add SSN Node to LinkedList
            ssnList.add()

            ssnList.addNode(new DictionaryEntry(splitInput[0], splitInput[1]));
        }

        return dictionaryList;
    }


}

