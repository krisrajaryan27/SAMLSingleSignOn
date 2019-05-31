/**
 * 
 */
package com.talentPool.license.utils;

/**
 * @author pallavi
 * @date Jan 11, 2007
 */
public final class HexToString {
	public static final String convert(String hexText) {
		StringBuffer plainText = new StringBuffer();
		if (hexText != null) {
			for (int i = 0; i < hexText.length(); i+=2) {
				int this_int = Integer.parseInt(hexText.substring(i, i + 2), 16);
				plainText.append((char) this_int);			
			}
		}
		return plainText.toString();
	}
}
