package at.mlps.main;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class EncrypterDecrypter {
	
	private Cipher cipher;
	
	public EncrypterDecrypter() {
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public byte[] encrypt(byte[] input, Key key) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(input);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] decrypt(byte[] input, Key key) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(input);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Key generateKey() {
		Key key = null;
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			keygen.init(128);
			key = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return key;
	}
}