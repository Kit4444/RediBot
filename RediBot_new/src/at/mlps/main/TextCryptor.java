package at.mlps.main;

//Class is made by Grubsic, this is not my property.

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;


public class TextCryptor{

	private final Cipher cipher;
	private byte[] salt;
	private int iterationCount = 1024;
	private int keyStrength = 256;
	private SecretKey key;
	private byte[] iv;

	public TextCryptor(char[] password) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException{
		salt = new byte[100];
		new SecureRandom().nextBytes(salt);

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(password, salt, iterationCount, keyStrength);
		SecretKey tmp = factory.generateSecret(spec);
		key = new SecretKeySpec(tmp.getEncoded(), "AES");
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	}

	public String encrypt(String data) throws Exception{
		cipher.init(Cipher.ENCRYPT_MODE, key);
		AlgorithmParameters params = cipher.getParameters();
		iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] utf8EncryptedData = cipher.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(utf8EncryptedData);
	}

	public String decrypt(String base64EncryptedData) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
		byte[] decryptedData = Base64.getDecoder().decode(base64EncryptedData);
		byte[] utf8 = cipher.doFinal(decryptedData);
		return new String(utf8, StandardCharsets.UTF_8);
	}
}
