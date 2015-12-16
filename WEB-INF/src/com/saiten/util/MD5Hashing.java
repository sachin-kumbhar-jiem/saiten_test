package com.saiten.util;

import java.security.MessageDigest;

public class MD5Hashing {
	
	private static final String MD5_KEY = "cDWQR#$Rcxsc";
	public static String md5(String password) throws Exception {
		password += MD5_KEY;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        
        //convert the byte to hex format
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	return hexString.toString();
	}
}
