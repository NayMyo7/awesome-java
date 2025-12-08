package dev.naymyohtet.practice.cryptography.configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AesCbcEncryptionConfiguration extends BaseEncryptionConfiguration {
	private SecretKeyFactory secretKeyFactory;
	private static final String SECRET_KEY = "TWF4aXMgVHJhbnNmb3JtYXRpb24+";

	@Override
	public SecretKeyFactory getSecretKeyFactory() {
		if (secretKeyFactory == null) {
			try {
				secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return secretKeyFactory;
	}

	@Override
	public Cipher getCipher() {
		try {
			return Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SecretKey generateSecretKey(char[] keyData, String salt) {
		try {
			return getSecretKeyFactory().generateSecret(new PBEKeySpec(keyData, salt.getBytes(), 256, 256));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IvParameterSpec generateIvParameterSpec(String type, String salt, String name) {
		return new IvParameterSpec(generateIV(type, salt, name, 16));
	}

	public static void main(String[] args) {
		AesCbcEncryptionConfiguration aes = new AesCbcEncryptionConfiguration();
		System.out.println("Enter the reference of the user:");
		String initiator = "9693704522";
		String pin = "2025";

		String encrypted = aes.encrypt(initiator, pin, null);
		System.out.println("Encrypted Text --> " + encrypted);

		String decrypted = aes.decrypt(encrypted, pin, null);
		System.out.println("Decrypted Text --> " + decrypted);
	}

	@Override
	public String encrypt(String initator, String pinToEncypt, String type) {// String keyType

		IvParameterSpec ivspec = generateIvParameterSpec("AES", initator, null);

		SecretKeyFactory factory;
		String encrypted = null;

		String encryptionKey = SECRET_KEY;
		fixKeyLength();
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(encryptionKey.toCharArray(), initator.getBytes(), 256, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			pinToEncypt = initator + pinToEncypt;
			encrypted = Base64.getEncoder()
					.encodeToString(cipher.doFinal(pinToEncypt.getBytes(StandardCharsets.UTF_8)));

		} catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException
				| NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return encrypted;
	}

	@Override
	public String decrypt(String initiator, String text, String type) {// String keyType -> will be picked for different
																		// key types
		String decryptedPin = null;

		String keyIndex = "aes.encryption.key";
		if (type != null) {
			keyIndex = keyIndex + type;
		}
		String encryptionKey = SECRET_KEY;
		fixKeyLength();
		try {
			IvParameterSpec ivspec = generateIvParameterSpec("AES", initiator, null);
			SecretKey secretKey = generateSecretKey(encryptionKey.toCharArray(), initiator);
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
			Cipher cipher = getCipher();
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);
			decryptedPin = new String(cipher.doFinal(Base64.getDecoder().decode(text)));

			decryptedPin = decryptedPin.replaceAll(initiator, "");
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			// logger needs to be addedd
			e.printStackTrace();
		}
		return decryptedPin;
	}

	public static void fixKeyLength() {
		String errorString = "Failed manually overriding key-length permissions.";
		int newMaxKeyLength;
		try {
			if ((newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES")) < 256) {
				Class c = Class.forName("javax.crypto.CryptoAllPermissionCollection");
				Constructor con = c.getDeclaredConstructor();
				con.setAccessible(true);
				Object allPermissionCollection = con.newInstance();
				Field f = c.getDeclaredField("all_allowed");
				f.setAccessible(true);
				f.setBoolean(allPermissionCollection, true);

				c = Class.forName("javax.crypto.CryptoPermissions");
				con = c.getDeclaredConstructor();
				con.setAccessible(true);
				Object allPermissions = con.newInstance();
				f = c.getDeclaredField("perms");
				f.setAccessible(true);
				((Map) f.get(allPermissions)).put("*", allPermissionCollection);

				c = Class.forName("javax.crypto.JceSecurityManager");
				f = c.getDeclaredField("defaultPolicy");
				f.setAccessible(true);
				Field mf = Field.class.getDeclaredField("modifiers");
				mf.setAccessible(true);
				mf.setInt(f, f.getModifiers() & ~Modifier.FINAL);
				f.set(null, allPermissions);

				newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES");
			}
		} catch (Exception e) {
			throw new RuntimeException(errorString, e);
		}
		if (newMaxKeyLength < 256)
			throw new RuntimeException(errorString); // hack failed
	}

}
