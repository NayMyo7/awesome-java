package dev.naymyohtet.practice.cryptography;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {
    private static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static final String PBKDF2_ALGO = "PBKDF2WithHmacSHA256";
    private static final String SECRET_KEY = "TWF4aXMgVHJhbnNmb3JtYXRpb24+";
    private static final int ITERATIONS = 256;
    private static final int KEY_LENGTH = 256;
    private static final int IV_LENGTH = 16;
    private static final String CHARSET = "UTF-8";

    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {

            // System.out.println("Enter the Key to Encrypt:");
            // String encryptionKey = scan.nextLine();

            System.out.println("Enter the reference of the user:");
            String salt = scan.nextLine();

            System.out.println("Enter the Password:");
            String password = scan.nextLine();

            String combined = salt + password;

            String encrypted = encrypt(combined, salt, SECRET_KEY);
            decrypt(encrypted, salt, SECRET_KEY);
        }
    }

    // -----------------------------
    // Common Key + IV Generators
    // -----------------------------

    private static SecretKeySpec generateSecretKey(String encryptionKey, String salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2_ALGO);
        KeySpec spec = new PBEKeySpec(encryptionKey.toCharArray(), salt.getBytes(CHARSET), ITERATIONS, KEY_LENGTH);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), ALGORITHM);
    }

    private static IvParameterSpec generateIv(String salt) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(ALGORITHM.getBytes());
            if (salt != null)
                md.update(salt.getBytes());
            byte[] sha = md.digest();
            
            byte[] iv = new byte[IV_LENGTH];
            for (int scan = 0; scan < sha.length; scan++) {
                iv[scan % IV_LENGTH] = (byte) (iv[scan % IV_LENGTH] ^ sha[scan]);
            }

            return new IvParameterSpec(iv);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    // -----------------------------
    // Encrypt
    // -----------------------------

    public static String encrypt(String plainText, String salt, String encryptionKey) {
        try {
            SecretKeySpec secretKey = generateSecretKey(encryptionKey, salt);
            IvParameterSpec iv = generateIv(salt);

            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(CHARSET));
            String encrypted = Base64.getEncoder().encodeToString(encryptedBytes);

            System.out.println("Encrypted Text --> " + encrypted);
            return encrypted;

        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.getMessage());
            return null;
        }
    }

    // -----------------------------
    // Decrypt
    // -----------------------------

    public static String decrypt(String encryptedText, String salt, String encryptionKey) {
        try {
            SecretKeySpec secretKey = generateSecretKey(encryptionKey, salt);
            IvParameterSpec iv = generateIv(salt);

            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            String decrypted = new String(cipher.doFinal(decodedBytes), CHARSET);

            // Remove salt prefix
            decrypted = decrypted.replaceFirst(salt, "");

            System.out.println("Decrypted Text --> " + decrypted);
            return decrypted;

        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.getMessage());
            return null;
        }
    }
}
