package dev.naymyohtet.practice.cryptography;

import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Aes {

    private static final String SECRET_KEY = "TWF4aXMgVHJhbnNmb3JtYXRpb24+";

    public static void main(String[] args) {

        Aes encryption = new Aes();
        Scanner scan = new Scanner(System.in);

        // System.out.println("Enter the Key to Encrypt");
        // String encryptionKey = scan.nextLine();
        String encryptionKey = SECRET_KEY;

        System.out.println("Enter the reference of the user");
        String salt = scan.nextLine();

        System.out.println("Enter the Password");
        String password = scan.nextLine();

        String encrypted = encryption.encrypt(salt + password, salt, encryptionKey);

        encryption.decrypt(encrypted, salt, encryptionKey);
    }

    public static String encrypt(String strToEncrypt, String salt, String encryptionKey) {
        try {
            EncryptionConfiguration encConfig = (EncryptionConfiguration) new AesCbcEncryptionConfiguration();
            IvParameterSpec ivspec = encConfig.generateIvParameterSpec("AES", salt,
                    null);

            System.out.println();

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(encryptionKey.toCharArray(), salt.getBytes(), 256, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            String encrypted = Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes()));

            System.out.println("Encrypted Text --> " + encrypted);
            return encrypted;
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String salt, String encryptionKey) {
        try {
            EncryptionConfiguration encConfig = (EncryptionConfiguration) new AesCbcEncryptionConfiguration();
            IvParameterSpec ivspec = encConfig.generateIvParameterSpec("AES", salt,
                    null);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(encryptionKey.toCharArray(), salt.getBytes(), 256, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            String decrypted = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            decrypted = decrypted.replaceAll(salt, "");

            System.out.println("Decrypted Text --> " + decrypted);
            return decrypted;
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
