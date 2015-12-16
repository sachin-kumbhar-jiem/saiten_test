package com.saiten.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESEncryptionDecryptionUtil {
	static String key = "z9enYIulFWr40Hp5";

	static BASE64Decoder decoder = new BASE64Decoder();
	static BASE64Encoder encoder = new BASE64Encoder();

	public static String encrypt(String decrypted) throws Exception {
		String encryptedData = null;
		SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(md5().getBytes());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, sksSpec, ivSpec);
		byte[] decryptedByteArray = cipher.doFinal(decrypted.getBytes("UTF-8"));
		byte[] encryptedByteArray = (new Base64()).encode(decryptedByteArray);
		encryptedData = new String(encryptedByteArray, "UTF-8");
		encryptedData = encryptedData.replace("+", " ");
		return encryptedData;
		/*
		 * return new String(Hex.encodeHex(cipher.doFinal(decrypted
		 * .getBytes("UTF-8"))));
		 */
	}

	public static String decrypt(String encrypted) throws Exception {
		String decryptedData = null;
		encrypted = encrypted.replace(" ", "+");

		SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(), "AES");
		// byte[] encryptedByteArray = decoder.decodeBuffer(decrypted);
		IvParameterSpec ivSpec = new IvParameterSpec(md5().getBytes());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, sksSpec, ivSpec);
		byte[] encryptedByteArray = (new Base64()).decode(encrypted.getBytes());
		byte[] decryptedByteArray = cipher.doFinal(encryptedByteArray);
		decryptedData = new String(decryptedByteArray, "UTF8");
		return decryptedData;

	}

	public static String md5() throws NoSuchAlgorithmException {
		String result = null;
		MessageDigest md = MessageDigest.getInstance("MD5"); // or "SHA-1"
		md.update(key.getBytes());
		BigInteger hash = new BigInteger(1, md.digest());
		result = hash.toString(16);
		if ((result.length() % 2) != 0) {
			result = "0" + result;
		}
		return result.substring(0, 16);
	}

	public static byte[] getIV() throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(key.getBytes("UTF-8"), 0, key.length());
		/* System.out.println("iv: " + md.digest()); */
		return md.digest();
	}
}