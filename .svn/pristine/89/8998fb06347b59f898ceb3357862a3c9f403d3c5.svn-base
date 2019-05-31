/**
 * 
 */
package com.talentPool.inbox.utils;

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.Iterator;

/**
 * @author shivprasad
 * 
 */
public class UserDefinedCharset extends CharsetProvider {
	static final String NAME = "UTF-7";
	static final String ALIASES[] = { "x-user-defined" };
	public UserDefinedCharset(){
		
	}
	public Iterator<Charset> charsets() {
		return null;
	}

	public Charset charsetForName(String charsetName) {
		return Charset.forName("UTF-8");

	}

}
