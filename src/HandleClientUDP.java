import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;

/**
 * Class: HandleClientUDP, Used to handle threads from ServerUDP. Will create a thread using DatagramPacket, key,
 * DatagramSocket, and database from serverUDP. Will receive, decrypt, and separate packet from ServerUDP, then search
 * database for entry with matching first and last name values. If values match then response to client will be ssn
 * value, if not matching then ssn value will be -1.
 *
 * @author Anthony Peters
 */
public class HandleClientUDP {
    int clientPort, key;
    String clientMessage;
    DatagramPacket clientPacket;
    InetAddress clientAddress;
    DatagramSocket server;
    LinkedList<SsnNode> databaseLinkedList;

    /**
     * Method: HandleClientUDP constructor, Argument-constructor that needs DatagramPacket, key, DatagramSocket, and
     * database. Will create packet, port, address, message, key, server address, and database variables.
     *
     * @param packet DatagramPacket, Client packet
     * @param keyInput int, Key value input
     * @param serverInput DatagramSocket, Server memory address
     * @param databaseInput LinkedList, Database of SsnNodes
     */
    HandleClientUDP(DatagramPacket packet, int keyInput, DatagramSocket serverInput, LinkedList<SsnNode> databaseInput) {
        // Store packet and packet data
        clientPacket = packet;
        clientPort = packet.getPort();
        clientAddress = packet.getAddress();
        clientMessage = new String(packet.getData()).trim();
        key = keyInput;
        server = serverInput;
        databaseLinkedList = databaseInput;
    }

    /**
     * Method: run, Will initialize encrypted and decrypted versions of firstName, lastName, key, ssn, response strings,
     * string array, SsnNode, and a response packet. Program will split input into firstName, lastName, and key values
     * that are separated by "_" characters. Next the values will be decrypted using key value, then the database will
     * be searched for entry matching values. If found then ssn and key values will be encrypted and send back in
     * response packet to client connection. If not found then "-1" and key values will be sent back in the response
     * packet.
     */
    public void run() {
        // Initialize variables
        String encryptedFirstName, encryptedLastName, encryptedKey, encryptedSsn, encryptedResponse,
                decryptedFirstName, decryptedLastName, decryptedKey, decryptedSsn, decryptedResponse;
        String[] splitClientInput;
        SsnNode entry;
        DatagramPacket responsePacket;

        try {
            // Separate name and key values, separated by "_"
            splitClientInput = clientMessage.split("_");
            encryptedFirstName = splitClientInput[0];
            encryptedLastName = splitClientInput[1];
            encryptedKey = splitClientInput[2];

            // Decrypt data
            decryptedFirstName = keyDecoding(encryptedFirstName, key);
            decryptedLastName = keyDecoding(encryptedLastName, key);
            decryptedKey = keyDecoding(encryptedKey, key);

            // Check data collection for entry
            entry = searchDatabaseForNames(decryptedFirstName, decryptedLastName, databaseLinkedList);

            // Store ssn value
            if (entry != null) {
                // Entry found, get ssn value from entry
                decryptedSsn = entry.getSsn();

            } else {
                // Entry matching inputs is not found, return -1
                decryptedSsn = "-1";

            }

            // Encrypt data
            encryptedSsn = keyEncoding(decryptedSsn, key);
            encryptedKey = keyEncoding(decryptedKey, key);

            // Create response
            encryptedResponse = encryptedSsn + "_" + encryptedKey;

            // Create response packet for client address and port
            responsePacket = new DatagramPacket(encryptedResponse.getBytes(),
                    encryptedResponse.getBytes().length, clientAddress, clientPort);

            // Send data to client from socket connection
            server.send(responsePacket);

        } catch (Exception e) {}
    }

    /**
     * Method: searchDatabaseForNames, Used to search copy of database LinkedList containing SsnNodes of entries using
     * strings of firstName, lastName and LinkedList database. Will cycle through LinkedList using for loop, for each
     * entry the program will test if firstName and lastName values matched the passed name values. If they are found
     * then the SsnNode will be returned. If not found then null will be returned.
     *
     * @param firstName String, First name value to search for
     * @param lastName String, Last name value to search for
     * @param database LinkedList, Database of SsnNodes
     * @return SsnNode, Matching SsnNode
     */
    private static SsnNode searchDatabaseForNames(String firstName, String lastName, LinkedList<SsnNode> database) {
        // Cycle through linked list to find node matching first and last name
        for (int index = 0; index < database.size(); index++) {
            if (database.get(index).getFirstName().matches(firstName) &&
                    database.get(index).getLastName().matches(lastName)) {
                // First and last name match database entry
                return database.get(index);
            }
        }

        // No entries match first and last values return null
        return null;
    }

    /**
     * Method: keyEncoding, Method receives a message to encrypt and a key to encrypt it with. Message will be
     * encrypted using a ceaser cipher shifted by key's amount. Method converts message string into a character array,
     * then cycles though array using a for loop. For each character their value is shifted by the key's amount then the
     * encrypted version is added to end of encrypted string variable which is passed back to original method.
     *
     * @param message String, Message to be encrypted
     * @param key int, Key value to be used in ceaser cipher encryption
     * @return String, Encrypted message
     */
    private static String keyEncoding(String message, int key) {
        final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char messageChar;
        char[] messageCharArray = message.toCharArray();
        String encodedMessage = "";

        // Loop though message chars
        for (int i = 0; i < message.length(); i++) {
            // Initialize char from array
            messageChar = messageCharArray[i];

            // Test char and encode if possible
            if (messageChar == '_' || messageChar == '-')
            {
                // Skip encoding the dividing char
                encodedMessage = encodedMessage.concat(String.valueOf(messageChar));

            } else if (String.valueOf(messageChar).matches("\\d") ) {
                // Char is numerical
                encodedMessage = encodedMessage.concat(String.valueOf((char) (key + (int) messageChar)));

            } else {
                // Find position of char within alphabet then concat the encoded char to message
                int position = (key + ALPHABET.indexOf(messageChar)) % 26;

                encodedMessage = encodedMessage.concat(String.valueOf(
                        ALPHABET.charAt(position))
                );

            }
        }

        return encodedMessage;
    }

    /**
     * Method: keyDecoding, Method receives a message to decrypt and a key to decrypt it with. Message will be
     * decrypted using a ceaser cipher shifted backwards by key's amount. Method converts message string into a
     * character array, then cycles though array using a for loop. For each character their value is shifted by the
     * key's amount backwards then the decrypted version is added to end of decrypted string variable which is passed
     * back to original method.
     *
     * @param message String, Message to be decrypted
     * @param key int, Key value to be used in ceaser cipher encryption
     * @return String, Decrypted message
     */
    private static String keyDecoding(String message, int key) {
        final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char messageChar;
        char[] messageCharArray = message.toCharArray();
        String decodedMessage = "";

        // Loop though message chars
        for (int i = 0; i < message.length(); i++) {
            // Initialize char from array
            messageChar = messageCharArray[i];

            // Test char and decode if possible
            if (messageChar == '_' || messageChar == '-')
            {
                // Skip decoding the dividing char
                decodedMessage = decodedMessage.concat(String.valueOf(messageChar));

            } else if (String.valueOf(messageChar).matches("\\d") ) {
                // Char is numerical
                decodedMessage = decodedMessage.concat(String.valueOf((char) ((int) messageChar - key)));

            } else {
                // Find position of char within alphabet then concat the decoded char to message
                int position = (ALPHABET.indexOf(messageChar) - key) % 26;

                // Circle around to other side of alphabet if position is negative
                if (position < 0) {
                    position = ALPHABET.length() + position;
                }

                decodedMessage = decodedMessage.concat(String.valueOf(
                        ALPHABET.charAt(position))
                );

            }
        }

        return decodedMessage;
    }

}
