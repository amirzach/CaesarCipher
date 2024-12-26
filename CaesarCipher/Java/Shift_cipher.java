import java.util.Scanner;

public class Shift_cipher {

    private static String alphabet = "";

    // Function to create a substitution table
    public static String createSubstitutionTable(int shift) {
        int length = alphabet.length();
        shift = (shift % length + length) % length; // Handle negative shifts
        return alphabet.substring(shift) + alphabet.substring(0, shift);
    }

    // Function to encrypt plaintext
    public static String encrypt(String plaintext, String substitutionTable) {
        StringBuilder ciphertext = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            int index;
            if (Character.isLowerCase(c)) {
                index = alphabet.indexOf(c);
                if (index != -1) {
                    ciphertext.append(substitutionTable.charAt(index));
                } else {
                    ciphertext.append(c); // Keep characters not in alphabet as-is
                }
            } else if (Character.isUpperCase(c)) {
                index = alphabet.indexOf(Character.toLowerCase(c));
                if (index != -1) {
                    ciphertext.append(Character.toUpperCase(substitutionTable.charAt(index)));
                } else {
                    ciphertext.append(c); // Keep characters not in alphabet as-is
                }
            } else {
                ciphertext.append(c); // Keep non-alphabet characters as-is
            }
        }
        return ciphertext.toString();
    }

    // Function to decrypt ciphertext
    public static String decrypt(String ciphertext, String substitutionTable) {
        StringBuilder plaintext = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            int index;
            if (Character.isLowerCase(c)) {
                index = substitutionTable.indexOf(c);
                if (index != -1) {
                    plaintext.append(alphabet.charAt(index));
                } else {
                    plaintext.append(c); // Keep characters not in substitution table as-is
                }
            } else if (Character.isUpperCase(c)) {
                index = substitutionTable.indexOf(Character.toLowerCase(c));
                if (index != -1) {
                    plaintext.append(Character.toUpperCase(alphabet.charAt(index)));
                } else {
                    plaintext.append(c); // Keep characters not in substitution table as-is
                }
            } else {
                plaintext.append(c); // Keep non-alphabet characters as-is
            }
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the alphabet
        System.out.println("Enter alphabet:");
        alphabet = scanner.nextLine();

        // Input plaintext
        System.out.println("Enter plaintext:");
        String plaintext = scanner.nextLine();

        // Input shift value
        System.out.println("Enter shift:");
        int shift = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Create substitution table for encryption
        String substitutionTable = createSubstitutionTable(shift);

        // Encrypt plaintext
        String ciphertext = encrypt(plaintext, substitutionTable);

        System.out.println("\nPlaintext:");
        System.out.println(plaintext);
        System.out.println("Ciphertext:");
        System.out.println(ciphertext);

        // Decrypt ciphertext using the same substitution table
        String decryptedText = decrypt(ciphertext, substitutionTable);

        System.out.println("Decrypted text:");
        System.out.println(decryptedText);

        scanner.close();
    }
}
