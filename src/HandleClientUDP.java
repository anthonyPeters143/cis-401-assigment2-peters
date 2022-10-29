import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;

public class HandleClientUDP {
    int clientPort, key;
    String clientMessage;
    DatagramPacket clientPacket;
    InetAddress clientAddress;
    DatagramSocket server;
    LinkedList<ssnNode> databaseLinkedList;

    HandleClientUDP(DatagramPacket packet, int keyInput, DatagramSocket serverInput, LinkedList<ssnNode> databaseInput) {
        // Store packet and packet data
        clientPacket = packet;
        clientPort = packet.getPort();
        clientAddress = packet.getAddress();
        clientMessage = new String(packet.getData()).trim();
        key = keyInput;
        server = serverInput;
        databaseLinkedList = databaseInput;
    }

    public void run() {
        // Initialize variables
        String encryptedFirstName, encryptedLastName, encryptedKey, encryptedSsn, encryptedResponse,
                decryptedFirstName, decryptedLastName, decryptedKey, decryptedSsn, decryptedResponse;
        String[] splitClientInput;
        ssnNode entry;
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

    private static ssnNode searchDatabaseForNames(String firstName, String lastName, LinkedList<ssnNode> database) {
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
