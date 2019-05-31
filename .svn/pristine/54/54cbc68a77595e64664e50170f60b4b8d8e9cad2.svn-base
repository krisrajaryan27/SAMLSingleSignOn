package com.talentPool.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;

/**
 * @author shivprasad
 * 
 */
public class EncryptionUtils {
	
	private static byte[] saltBytes = { (byte) 0xa3, (byte) 0x51, (byte) 0x56, (byte) 0x7b, (byte) 0x9d, (byte) 0xf5, (byte) 0xf3, (byte) 0xff };
	private static byte[] ivBytes = { 0x00, 0x03, 0x05, 0x54, 0x68, 0x43, 0x67, 0x45, 0x64, 0x32, 0x21, 0x14, 0x20, 0x76, 0x31, 0x62 };
	private static int pswdIterations = 1000;
	private static int keySize = 256;

	public static String encryptString(String strToEncrypt) {
		String encoded = "";
		//TODO : 
		// 1. Add 'SALT' to x
		// 2. Apply hash at least 1000 times
		// 3. Add 'SALT' to the final hashed value
		try {
			encoded = byteArrayToHexString(encrypt(strToEncrypt));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Encrypting String", e);
		}
		return encoded;
	}

	public static byte[] encrypt(String x) throws Exception {
		java.security.MessageDigest d = null;
		d = java.security.MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(x.getBytes());
		return d.digest();
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}
	
	/**
	 * Un Encrypts the password which is originally encrypted in login screen using encrypt.js utility
	 * @param text
	 * @param rnd
	 * @return decrypted passord
	 */
	public static String decryptPassword(String text, int rnd){
		String output = "";
		try{
			if(!Utils.isBlankOrNull(text)){
				text = text.substring(4,text.length()-3);
				int textLength = text.length();
				char temp[] = new char[textLength];
						
				for (int i = 0; i < textLength; i++) {
					int j  = (int)text.charAt(i) - rnd;
					temp[i] = (char)j;
				}
				for (int i = 0; i < textLength; i++) {
					output += temp[i];
				}
				
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return output;
	}
	
	public static String encrypt(String plainText, String keyString){
		//return callEncryptServiceCall(plainText, keyString);
		String encryptedText="";
		try {
			encryptedText= encryptLogic(plainText, keyString);
			return encryptedText;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidParameterSpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptedText;
	}
	
	/**
	 * @param plainText
	 * @param keyString
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidParameterSpecException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static String encryptLogic(String plainText, String keyString)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidParameterSpecException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException, InvalidKeyException,
			InvalidAlgorithmParameterException {
		
		if(Utils.isBlankOrNull(plainText)){
			return "";
		}
		// Derive the key, given password and salt.
		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(keyString.toCharArray(), saltBytes,
				pswdIterations, keySize);
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(ivBytes));

		byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		return new BASE64Encoder().encode(encryptedTextBytes);
	}

	public static String decrypt(String encryptedText, String keyString){
		//return callDecryptServiceCall(encryptedText,keyString);
		String a="";
		try {
			a=decryptLogic(encryptedText,keyString);
			return a;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	
	
	/**
	 * @param encryptedText
	 * @param keyString
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 */
	public static String decryptLogic(String encryptedText, String keyString)throws NoSuchAlgorithmException, InvalidKeySpecException,
	NoSuchPaddingException, InvalidKeyException,
	InvalidAlgorithmParameterException, IOException {
		byte[] encryptedTextBytes = new BASE64Decoder().decodeBuffer(encryptedText);

		// Derive the key, given password and salt.
		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(keyString.toCharArray(), saltBytes,
				pswdIterations, keySize);

		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		// Decrypt the message, given derived key and initialization vector.
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
		String decryptedText = "";
		byte[] decryptedTextBytes = null;
		try {
			decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
			decryptedText = new String(decryptedTextBytes,"UTF-8");
		} catch (IllegalBlockSizeException e) {
			TPLogger.getLogger().debug("error calling decrypting service : ",e);
		} catch (BadPaddingException e) {
			TPLogger.getLogger().debug("error calling decrypting service : ",e);
		}catch(NullPointerException e){
			TPLogger.getLogger().debug("error decrypting :"+decryptedText+" @@@ " + encryptedText,e);
			decryptedText = encryptedText;
		}
		return decryptedText;
	}	
	
	/**
	 * @param value
	 * @param key
	 */
	private static String callEncryptServiceCall(String value,String key){
		Client client = Client.create();
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("value",value);
		formData.add("key", key);
		WebResource webResource = client.resource(Utils.buildExternalTalentPoolURL("rest/encryptDecryptService/encrypt"));
		ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class,formData);
		String res = response.getEntity(String.class);
		TPLogger.getLogger().debug("calling encrypting service : " + res);
		response.close();
		return res;
	}
	
	
	/**
	 * @param value
	 * @param key
	 */
	private static String callDecryptServiceCall(String value,String key){
		Client client = Client.create();
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("value",value);
		formData.add("key", key);
		WebResource webResource = client.resource(Utils.buildExternalTalentPoolURL("rest/encryptDecryptService/decrypt"));
		ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class,formData);
		String res = response.getEntity(String.class);
		TPLogger.getLogger().debug("calling decrypting service: " + res);
		response.close();
		return res;
	}
	
	public static void main(String[] a) {
		System.out.println(byteArrayToHexString(ivBytes));
		String pswd = "abcdef";
		try {
			System.out.println("1 : " + encrypt(pswd,"K6C04dAePkZRU7ykC8q10nf78e53n3EU"));
		} catch(Exception e){
			System.out.println(e);
		}
		
		pswd = "123123";
		try {
			System.out.println("2 : " + recursiveDecrypt("NLmXol0GfPaj3EXYvvUKHA==","K6C04dAePkZRU7ykC8q10nf78e53n3EU"));
			String result = "";
			String s = "DrIRqz31iNR+iAVU0GSH79sFh83XDRvJW7v+eKSD4vk=";
			
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param encryptedString
	 * @param key
	 * @return returns recursively decrypted fields that mistakingly got encrypted twice
	 */
	private static String recursiveDecrypt(String encryptedString, String key) {
		String s = encryptedString;
		String result = "";
		if(!Utils.isBlankOrNull(encryptedString)){
			int cnt = 0;
			while (true) {
				try {
					if(cnt>99){
						System.out.println("infinite loop detected");
						throw new Exception();
					}
					s = decrypt(s,key);
					if(Utils.isBlankOrNull(s)){
						break;
					}
					result = s;
					cnt++;
				} catch (Exception e) {
					TPLogger.getLogger().debug("Double encryption found");
					break;
				}
			}
		}else{
			result = encryptedString;
		}
		return result;
	}
}
