package at.mlps.main;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.simpleyaml.configuration.file.YamlFile;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64DecoderStream;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;

public class EncoderDecoderClass {
	
	static Cipher ecipher;
	static Cipher dcipher;
	static SecretKey key;
	static String token;
	static KeyPair kp;
	
	public void init() {
		YamlFile file = new YamlFile("configs/configuration.yml");
		try {
			file.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(file.contains("BotConfig.Cypher")) {
			token = file.getString("BotConfig.Cypher");
		}else {
			String token = generateToken(32);
			file.set("BotConfig.Cypher", token);
			try {
				file.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			kp = kpg.generateKeyPair();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			key = KeyGenerator.getInstance("DES").generateKey();
			
			ecipher = Cipher.getInstance("DES");
			dcipher = Cipher.getInstance("DES");
			
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String encrypt(String s) {
		try {
			byte[] utf8 = s.getBytes("UTF8");
			byte[] enc = ecipher.doFinal(utf8);
			enc = BASE64EncoderStream.encode(enc);
			return new String(enc);
		}catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	public String decrypt(String s) {
		try {
			byte[] dec = BASE64DecoderStream.decode(s.getBytes());
			byte[] utf8 = dcipher.doFinal(dec);
			return new String(utf8, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}	
	}
	
	private static final SecureRandom secRan = new SecureRandom();
	private static final Base64.Encoder b64e = Base64.getUrlEncoder();
	
	public static String generateToken(int length) {
		byte[] randomBytes = new byte[length];
		secRan.nextBytes(randomBytes);
		return b64e.encodeToString(randomBytes);
	}
}