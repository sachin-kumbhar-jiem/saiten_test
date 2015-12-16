package com.saiten.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	static String key = "cDWQR#$Rcxsc";

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String password = "work201";
		key = password + key;
		/* System.out.println(md5()); */
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
		return result;
	}
}
