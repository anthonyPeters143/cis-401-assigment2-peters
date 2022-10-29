import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.IllegalFormatConversionException;
import java.util.Objects;
import java.util.Scanner;

public class ClientUDP {
    public static void main(String[] args) {
        // Initialize variables
        boolean dataVerify = false;
        byte[] buffer = new byte[65535];
        int serverPort, encryptKey = 2;
        String firstNameInput = "", lastNameInput = "",
                encryptedFirstName, encryptedLastName, encryptedClientKey,
                clientEncryptedString,
                encryptedSsn, encryptedServerKey,
                decryptedSsn, decryptedServerKey,
                ssnString;
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
                } else {
                    // Output error message to user
                    System.out.println("Error, please reenter name inputs\n");
                }

            } while (!dataVerify);

            // Encode inputs
            encryptedFirstName = keyEncoding(firstNameInput, encryptKey);
            encryptedLastName = keyEncoding(lastNameInput, encryptKey);
            encryptedClientKey = keyEncoding(String.valueOf(encryptKey), encryptKey);
            clientEncryptedString = encryptedFirstName + "_" + encryptedLastName + "_" + encryptedClientKey;

            // Output encrypted name and key
            System.out.println("Encrypted user name: " + encryptedFirstName + encryptedLastName);
            System.out.println("Encrypted key: " + encryptedClientKey);

            // Create packet of encrypted data for server address and port
            clientPacket = new DatagramPacket(clientEncryptedString.getBytes(),
                    clientEncryptedString.getBytes().length, serverAddress, serverPort);

            // Send data to server from socket connection
            clientSocket.send(clientPacket);

            // Receive encrypted data from server
            serverPacket = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(serverPacket);

            // Separate encrypted data into ssn and key from packet
            String[] splitServerString = new String(serverPacket.getData(), 0, serverPacket.getLength()).split("_");
            encryptedSsn = splitServerString[0];
            encryptedServerKey = splitServerString[1];

            // Decode data
            decryptedSsn = keyDecoding(encryptedSsn, 2);
            decryptedServerKey = keyDecoding(encryptedServerKey, 2);

            // Check if returned number is == -1
            if (Objects.equals(decryptedSsn, "-1")) {
                // Invalid name input
                ssnString = "Invalid user name";

            } else {
                // SSN returned
                ssnString = decryptedSsn;

            }

            // Output ssn to user
            System.out.println("SSN: " + ssnString);

        } catch (Exception e) {}

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
