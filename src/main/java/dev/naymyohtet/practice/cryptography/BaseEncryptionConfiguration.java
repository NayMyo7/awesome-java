package dev.naymyohtet.practice.cryptography;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;

//import com.utiba.delirium.plugin.Singleton;
//import com.utiba.delirium.plugin.Singleton;

public abstract class BaseEncryptionConfiguration implements EncryptionConfiguration {//
    public abstract SecretKeyFactory getSecretKeyFactory();

    public abstract Cipher getCipher();

    public abstract SecretKey generateSecretKey(char[] keyData, String salt);

    public abstract IvParameterSpec generateIvParameterSpec(String type, String salt, String name);

    protected byte[] generateIV(String type, String salt, String name, int length) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // if (type != null) md.update(stringToByteArray(type));
            // if (salt != null) md.update(stringToByteArray(salt));
            // if (name != null) md.update(stringToByteArray(name));

            if (type != null)
                md.update(type.getBytes());
            if (salt != null)
                md.update(salt.getBytes());
            if (name != null)
                md.update(name.getBytes());
            byte[] sha = md.digest();
            byte[] iv = new byte[length];
            for (int scan = 0; scan < sha.length; scan++) {
                iv[scan % length] = (byte) (iv[scan % length] ^ sha[scan]);
            }

            return iv;
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }
}
