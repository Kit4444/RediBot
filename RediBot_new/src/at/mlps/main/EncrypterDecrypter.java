package at.mlps.main;

import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

public class EncrypterDecrypter {
	
	Cipher cipher;
	
	public void onLoad() {
		YamlFile file = new YamlFile("configs/configuration.yml");
		try {
			file.load();
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
			keyGen.initialize(2048);
			KeyPair pair = keyGen.generateKeyPair();
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*private Cipher cipher;
	
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
	}*/
}