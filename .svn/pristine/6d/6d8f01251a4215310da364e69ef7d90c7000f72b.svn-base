package com.talentPool.license.utils.test;

import com.talentPool.license.utils.StringToHex;

import junit.framework.TestCase;

public class StringToHexTest extends TestCase {
	public void testNull() {
		String plainText = null;
		String hexText = StringToHex.convert(plainText);
		assertNotNull("Wrong Result", hexText);
		assertEquals("Wrong Result", "", hexText);
	}
	
	public void testEmpty() {
		String plainText = "";
		String hexText = StringToHex.convert(plainText);
		assertNotNull("Wrong Result", hexText);
		assertEquals("Wrong Result", "", hexText);
	}
	
	public void testValid() {
		String plainText = "11-11-00-11";
		String hexText = StringToHex.convert(plainText);
		assertNotNull("Wrong Result", hexText);
		assertEquals("Wrong Result", "31312d31312d30302d3131", hexText);
	}
}
