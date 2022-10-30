import java.io.File;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Class: ServerUDP, Used to connect with ClientUDP to search through database to entry that matches first and last name
 * inputs passed by client. If matching entry is found then ssn value will be returned to client but if it isn't then -1
 * will be returned. Server is set up to be running on IP "192.168.0.156"".
 *
 * @author Anthony Peters
 */
public class ServerUDP {

    /**
     * Method: main, Used to drive ServerUDP for server side connection with ClientUDP. Starts by initializing the
     * database of names and ssn's, will input from Sample.txt file. Then will accept connections from clients, when
     * accepted will create and run a thread passing packet, key, socket address, and copy of database. When finished
     * creating thread will reset buffer for next client request.
     *
     * @param args System input
     * @throws FileNotFoundException Suppress exception from file missing error
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Initialize server data
        int serverPort = 3000, key = 2;
        byte[] buffer = new byte[65535];
        String fileName = "Sample.txt";
        DatagramPacket clientPacket;
        LinkedList<SsnNode> ssnLinkedList;

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

    /**
     * Method: initializeServerData, Will create a file object using given file name and LinkedList object for SsnNodes.
     * Then create scanner that will input from file object. Will need to skip first 2 lines for headers. Will separate
     * by "\t" character and create SsnNode objects to populate LinkedList using firstName, lastName, and ssn values.
     * When finished will return populated LinkedList.
     *
     * @param inputFileName String, name of input file
     * @return LinkedList Initialized database
     * @throws FileNotFoundException Suppress exception from file missing error
     */
    private static LinkedList<SsnNode> initializeServerData(String inputFileName) throws FileNotFoundException {
        // Create file and LinkedList
        File ssnFile = new File("src/" + inputFileName);
        LinkedList<SsnNode> ssnList = new LinkedList<>();

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
            ssnList.add(new SsnNode(splitInput[0].trim(), splitInput[2].trim(), splitInput[4].trim()));
        }

        // Returns data collection to main
        return ssnList;
    }


}

