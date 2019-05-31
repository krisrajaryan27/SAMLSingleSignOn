/**
 * 
 */
package com.talentPool.license.utils;

/**
 * @author pallavi
 * @date Jan 11, 2007
 */
public final class StringToHex {
	public static final String convert(String plainText) {
		StringBuffer hexText = new StringBuffer();		
		if (plainText != null) {
			for (int i = 0; i < plainText.length(); i++) {
				int this_int = (int) plainText.charAt(i);
				hexText.append(Integer.toHexString(this_int));	
			}
		}
		return hexText.toString();
	}
}
