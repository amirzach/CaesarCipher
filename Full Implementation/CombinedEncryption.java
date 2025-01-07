import java.security.*;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;

public class CombinedEncryption {

    // Set alphabet for Shift Cipher
    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Function to create a substitution table for Shift Cipher
    public static String createSubstitutionTable(int shift) {
        int length = alphabet.length();
        shift = (shift % length + length) % length; // Handle negative shifts
        return alphabet.substring(shift) + alphabet.substring(0, shift);
    }

    // Function to encrypt plaintext using Shift Cipher
    public static String shiftCipherEncrypt(String plaintext, String substitutionTable) {
        StringBuilder ciphertext = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            int index;
            if (Character.isUpperCase(c)) {
                index = alphabet.indexOf(c);
                if (index != -1) {
                    ciphertext.append(substitutionTable.charAt(index));
                } else {
                    ciphertext.append(c); // Keep characters not in alphabet as-is
                }
            } else {
                ciphertext.append(c); // Keep non-alphabet characters as-is
            }
        }
        return ciphertext.toString();
    }

    // Function to decrypt ciphertext using Shift Cipher
    public static String shiftCipherDecrypt(String ciphertext, String substitutionTable) {
        StringBuilder plaintext = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            int index;
            if (Character.isUpperCase(c)) {
                index = substitutionTable.indexOf(c);
                if (index != -1) {
                    plaintext.append(alphabet.charAt(index));
                } else {
                    plaintext.append(c); // Keep characters not in substitution table as-is
                }
            } else {
                plaintext.append(c); // Keep non-alphabet characters as-is
            }
        }
        return plaintext.toString();
    }

    // Generate RSA key pair (public and private keys)
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // RSA key size
        return keyPairGenerator.generateKeyPair();
    }

    // Encrypt plaintext using RSA public key
    public static String rsaEncrypt(String plaintext, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes); // Encode in Base64 to print as string
    }

    // Decrypt ciphertext using RSA private key
    public static String rsaDecrypt(String ciphertext, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedBytes = Base64.getDecoder().decode(ciphertext); // Decode from Base64
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try {
            // Initialize scanner for user input
            Scanner scanner = new Scanner(System.in);

            // Prompt user to enter plaintext
            System.out.print("Enter the plaintext to encrypt: ");
            String plaintext = scanner.nextLine();

            // Input the shift value for Shift Cipher
            System.out.print("Enter the shift value for Shift Cipher: ");
            int shift = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Step 1: Shift Cipher encryption
            String substitutionTable = createSubstitutionTable(shift);
            String shiftEncryptedText = shiftCipherEncrypt(plaintext.toUpperCase(), substitutionTable);

            // Step 2: RSA encryption
            // Generate RSA key pair
            KeyPair keyPair = generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Encrypt the Shift Cipher encrypted text using RSA public key
            String rsaEncryptedText = rsaEncrypt(shiftEncryptedText, publicKey);

            System.out.println("\nEncrypted (RSA + Shift Cipher) Message: " + rsaEncryptedText);

            // Step 3: Decrypt the message

            // Decrypt the RSA encrypted message using the RSA private key
            String rsaDecryptedText = rsaDecrypt(rsaEncryptedText, privateKey);

            // Decrypt the result using Shift Cipher
            String finalDecryptedText = shiftCipherDecrypt(rsaDecryptedText, substitutionTable);

            System.out.println("\nDecrypted Message: " + finalDecryptedText);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
