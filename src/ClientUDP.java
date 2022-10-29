import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientUDP {
    public static void main(String[] args) {
        // Initialize variables
        boolean dataVerify = false;
        byte[] buffer = new byte[65535];
        int serverPort, encryptKey = 2;
        String firstNameInput = "", lastNameInput = "",
                clientString, serverString,
                encryptedFirstName, encryptedLastName, encryptedkey,
                clientEncryptedString, serverEncryptedString,
                ssnString = "Invalid user name";
        DatagramPacket clientPacket, serverPacket;
        InetAddress serverAddress;
        Scanner input = new Scanner(System.in);

        try {
            // Set up connection with server
            DatagramSocket clientSocket = new DatagramSocket();
            serverPort = 3000;
            // TODO CHANGE TO SERVER IP WHEN FINISHED
            serverAddress = InetAddress.getByName("localhost");

            // Loop till name input is correct
            do {
                try {
                    // Prompt for data from user
                    System.out.print("Enter the first name: ");
                    firstNameInput = input.next().trim().toUpperCase();
                    System.out.print("Enter the last name: ");
                    lastNameInput = input.next().trim().toUpperCase();

                } catch (Exception e) {}

                // Verify data doesn't contain any digits
                if (firstNameInput.matches("\\D+") && lastNameInput.matches("\\D+")) {
                    // Input correct
                    dataVerify = true;
                }

            } while (!dataVerify);

            // Encode input
            encryptedFirstName = keyEncoding(firstNameInput, encryptKey);
            encryptedLastName = keyEncoding(lastNameInput, encryptKey);
            encryptedkey = keyEncoding(String.valueOf(encryptKey), encryptKey);
            clientEncryptedString = encryptedFirstName + "_" + encryptedLastName + "_" + encryptedkey;

            // Output encrypted name and key
            System.out.println("Encrypted user name: " + encryptedFirstName + encryptedLastName);
            System.out.println("Encrypted key: " + encryptedkey);

            // Create packet of encrypted data for server address and port
            clientPacket = new DatagramPacket(clientEncryptedString.getBytes(),
                    clientEncryptedString.getBytes().length, serverAddress, serverPort);

            // Send data to server from socket connection
            clientSocket.send(clientPacket);

            // Receive encrypted data from server
            serverPacket = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(serverPacket);

            // Separate encrypted data from packet
            serverEncryptedString = new String(serverPacket.getData(), 0, serverPacket.getLength());

            // Decode data
            // TODO TEST DECRYPT METHOD
            serverString = keyDecoding(serverEncryptedString, 2);

            // Output data or error message to user
            try {
                // Check if returned number is == -1
                if (Integer.parseInt(serverString) == -1) {
                    // Invalid name input
                    ssnString = "Invalid user name";

                } else {
                    // SSN returned
                    ssnString = serverString;
                }
            } catch (Exception e) {}

            System.out.println("SSN: " + ssnString);

        } catch (Exception e) {

        }

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
