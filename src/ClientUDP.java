import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class ClientUDP {
    public static void main(String[] args) {
        // Initialize variables
        String nameInput, firstNameInput = "", lastNameInput = "";
        boolean dataVerify = false;
        DatagramPacket clientPacket;
        Scanner input = new Scanner(System.in);

        try {
            // Set up connection with server
            DatagramSocket clientSocket = new DatagramSocket()

            // Loop till name input is correct
            do {
                try {
                    // Prompt for data from user
                    System.out.println("Enter the first name: ");
                    firstNameInput = input.next().trim().toLowerCase();
                    System.out.println("Enter the last name: ");
                    lastNameInput = input.next().trim().toLowerCase();

                } catch (Exception e) {}

                // Verify data doesn't contain any digits
                if (firstNameInput.matches("/D") && lastNameInput.matches("/D")) {
                    // Input correct
                    dataVerify = true;
                }

            } while (dataVerify);

            // Encode input


            // Send data to server


            // Receive data from server

            // Decode data

            // Output data or error message to user

        } catch (Exception e) {

        }

    }

    private static String encryptData(String toEncryption) {
        // Ini var
        char tempEncryptChar;
        String encryptedString = "";

        // Encrypt each character of string argument
        for (int i = 0; i < toEncryption.length(); i++) {
            // Pull each char from passed string
            tempEncryptChar = toEncryption.charAt(i);

            // Determine which char is passed then add converted encrypted version to encrypted version
            switch (tempEncryptChar) {
                case 'a' -> encryptedString = encryptedString.concat("c");
                case 'b' -> encryptedString = encryptedString.concat("d");
                case 'c' -> encryptedString = encryptedString.concat("e");
                case 'd' -> encryptedString = encryptedString.concat("f");
                case 'e' -> encryptedString = encryptedString.concat("g");
                case 'f' -> encryptedString = encryptedString.concat("h");
                case 'g' -> encryptedString = encryptedString.concat("i");
                case 'h' -> encryptedString = encryptedString.concat("j");
                case 'i' -> encryptedString = encryptedString.concat("k");
                case 'j' -> encryptedString = encryptedString.concat("l");
                case 'k' -> encryptedString = encryptedString.concat("m");
                case 'l' -> encryptedString = encryptedString.concat("n");
                case 'm' -> encryptedString = encryptedString.concat("o");
                case 'n' -> encryptedString = encryptedString.concat("p");
                case 'o' -> encryptedString = encryptedString.concat("q");
                case 'p' -> encryptedString = encryptedString.concat("r");
                case 'q' -> encryptedString = encryptedString.concat("s");
                case 'r' -> encryptedString = encryptedString.concat("t");
                case 's' -> encryptedString = encryptedString.concat("u");
                case 't' -> encryptedString = encryptedString.concat("v");
                case 'u' -> encryptedString = encryptedString.concat("w");
                case 'v' -> encryptedString = encryptedString.concat("x");
                case 'w' -> encryptedString = encryptedString.concat("y");
                case 'x' -> encryptedString = encryptedString.concat("z");
                case 'y' -> encryptedString = encryptedString.concat("a");
                case 'z' -> encryptedString = encryptedString.concat("b");
            }
        }

        return encryptedString;
    }
    private static String decryptData(String fromEncryption) {
        // Ini var
        char tempEncryptChar;
        String decryptedString = "";

        // Encrypt each character of string argument
        for (int i = 0; i < fromEncryption.length(); i++) {
            // Pull each char from passed string
            tempEncryptChar = fromEncryption.charAt(i);

            // Determine which char is passed then add converted encrypted version to encrypted version
            switch (tempEncryptChar) {
                case 'c' -> fromEncryption = fromEncryption.concat("a");
                case 'd' -> fromEncryption = fromEncryption.concat("b");
                case 'e' -> fromEncryption = fromEncryption.concat("c");
                case 'f' -> fromEncryption = fromEncryption.concat("d");
                case 'g' -> fromEncryption = fromEncryption.concat("e");
                case 'h' -> fromEncryption = fromEncryption.concat("f");
                case 'i' -> fromEncryption = fromEncryption.concat("g");
                case 'j' -> fromEncryption = fromEncryption.concat("h");
                case 'k' -> fromEncryption = fromEncryption.concat("i");
                case 'l' -> fromEncryption = fromEncryption.concat("j");
                case 'm' -> fromEncryption = fromEncryption.concat("k");
                case 'n' -> fromEncryption = fromEncryption.concat("l");
                case 'o' -> fromEncryption = fromEncryption.concat("m");
                case 'p' -> fromEncryption = fromEncryption.concat("n");
                case 'q' -> fromEncryption = fromEncryption.concat("o");
                case 'r' -> fromEncryption = fromEncryption.concat("p");
                case 's' -> fromEncryption = fromEncryption.concat("q");
                case 't' -> fromEncryption = fromEncryption.concat("r");
                case 'u' -> fromEncryption = fromEncryption.concat("s");
                case 'v' -> fromEncryption = fromEncryption.concat("t");
                case 'w' -> fromEncryption = fromEncryption.concat("u");
                case 'x' -> fromEncryption = fromEncryption.concat("v");
                case 'y' -> fromEncryption = fromEncryption.concat("w");
                case 'z' -> fromEncryption = fromEncryption.concat("x");
                case 'a' -> fromEncryption = fromEncryption.concat("y");
                case 'b' -> fromEncryption = fromEncryption.concat("z");
            }
        }

        return decryptedString;
    }

    private static String keyEncoding(String message, int key) {
        final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
        char messageChar;
        char[] messageCharArray = message.toCharArray();
        String encodedMessage = "";

        // Loop though message chars
        for (int i = 0; i < message.length(); i++) {
            // Initialize char from array
            messageChar = messageCharArray[i];

            // Test char and encode if possible
            if (messageChar == '_')
            {
                // Skip encoding the dividing char
                encodedMessage = encodedMessage.concat(String.valueOf(messageChar));

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
        final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
        char messageChar;
        char[] messageCharArray = message.toCharArray();
        String decodedMessage = "";

        // Loop though message chars
        for (int i = 0; i < message.length(); i++) {
            // Initialize char from array
            messageChar = messageCharArray[i];

            // Test char and decode if possible
            if (messageChar == '_')
            {
                // Skip decoding the dividing char
                decodedMessage = decodedMessage.concat(String.valueOf(messageChar));

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
