import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;
import java.util.Scanner;

public class RSAEncryption {

    // Generate RSA key pair (public and private keys)
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // RSA key size
        return keyPairGenerator.generateKeyPair();
    }

    // Encrypt plaintext using public key
    public static String encrypt(String plaintext, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes); // Encode in Base64 to print as string
    }

    // Decrypt ciphertext using private key
    public static String decrypt(String ciphertext, PrivateKey privateKey) throws Exception {
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
            System.out.print("Enter the message to encrypt: ");
            String plaintext = scanner.nextLine();

            // Generate RSA key pair
            KeyPair keyPair = generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Encrypt the plaintext using the public key
            String encryptedMessage = encrypt(plaintext, publicKey);
            System.out.println("Encrypted Message: " + encryptedMessage);

            // Decrypt the ciphertext using the private key
            String decryptedMessage = decrypt(encryptedMessage, privateKey);
            System.out.println("Decrypted Message: " + decryptedMessage);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
