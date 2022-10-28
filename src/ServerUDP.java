import java.io.File;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Scanner;

public class ServerUDP {
    public static void main(String[] args) throws FileNotFoundException {
        // Initialize server data
        int serverPort = 3000, key = 2;
        byte[] buffer = new byte[65535];
        String clientData, sendingData, fileName = "Sample.txt";
        DatagramPacket clientPacket, sendingPacket;
        InetAddress clientAddress;
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
                new HandleClientUDP(clientPacket, key, ssnLinkedList).run();

                // Reset buffer for next request
                buffer = new byte[1000];
            }
        } catch (Exception E) {

        }



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
            ssnList.add(new ssnNode(splitInput[0], splitInput[2], splitInput[4].trim()));
        }

        // Returns data collection to main
        return ssnList;
    }


}

