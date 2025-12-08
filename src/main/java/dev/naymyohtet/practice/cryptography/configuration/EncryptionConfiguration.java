package dev.naymyohtet.practice.cryptography.configuration;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;

public interface EncryptionConfiguration {
 public abstract SecretKeyFactory getSecretKeyFactory();
 public abstract Cipher getCipher();
 public abstract SecretKey generateSecretKey(char[] keyData, String salt);
 public abstract IvParameterSpec generateIvParameterSpec(String type, String salt, String name);
 public abstract String encrypt(String initiator, String encryptionKey, String pinToEncypt);
 public abstract String decrypt(String initiator, String encryptionKey, String encryptedPin );
}