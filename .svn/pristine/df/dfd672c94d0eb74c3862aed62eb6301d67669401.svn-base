/**
 * 
 */
package com.talentPool.license.utils;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * @author pallavi
 * @date Jan 11, 2007
 */
public final class Encrypt {
	public static final String convert(String plainText) {
		String cipherText = null;
		if (plainText != null && plainText.length() > 0) {
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			try {
				KeyGenerator kg = KeyGenerator.getInstance("DES");
				Key key = kg.generateKey();
				Cipher cipher = Cipher.getInstance("DES");
				
				byte [] data = plainText.getBytes();			
				cipher.init(Cipher.ENCRYPT_MODE ,key);
				byte [] result = cipher.doFinal(data);
				cipherText = new String(result);						
			} catch (Exception e){
				// do Nothing
			}
		}
		return cipherText;
	}
}
