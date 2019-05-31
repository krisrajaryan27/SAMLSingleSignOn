/**
 * 
 */
package com.talentPool.common.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author pallavi
 * @date Apr 26, 2007
 */
public class DecryptProperties {
	public static void main(String args[]) {	
		if (args == null || args.length < 3) {
			return;
		}
		
		try {
			String dbUser = args[0];
			String dbPassword = args[1];
			String fileName = args[2];
			
			OutputStream stream = new FileOutputStream(fileName);
			
			String username = DBUtils.decryptString(dbUser);
			stream.write(username.getBytes());
			
			stream.write(System.getProperty("line.separator").getBytes());
			
			String password = DBUtils.decryptString(dbPassword);
			if(password == null || password.trim() == "") {
				password = args[1];
			}
			stream.write(password.getBytes());
			
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
