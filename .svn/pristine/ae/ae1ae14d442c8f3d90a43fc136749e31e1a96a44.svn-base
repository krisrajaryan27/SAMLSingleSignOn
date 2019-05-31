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
public final class Decrypt {
	public static final String decrypt(String cipherText) {
		String plainText = null;
		if (cipherText != null && cipherText.length() > 0) {
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			try {
				KeyGenerator kg = KeyGenerator.getInstance("DES");
				Key key = kg.generateKey();
				Cipher cipher = Cipher.getInstance("DES");
				
				byte [] data = cipherText.getBytes();			
				cipher.init(Cipher.DECRYPT_MODE ,key);
				byte [] result = cipher.doFinal(data);
				plainText = new String(result);
			} catch (Exception e){
				// do Nothing
			}
		}
		return plainText;
	}
}
