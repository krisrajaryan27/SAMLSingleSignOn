
package com.talentPool.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author pallavi
 * @date Apr 26, 2007
 */
public class MergeProperties {
	public static void main(String args[]) {	
		if (args == null || args.length < 2) {
			return;
		}
		
		try {
			String oldFile = args[0];
			String newFile = args[1];
			String isUpgrade = args[2];
			
			boolean upgrade = true;
			if(isUpgrade!=null && isUpgrade!=""){
				upgrade = isUpgrade.equals("1") ? true:false;
			}
			
			Properties oldProperties = new Properties();
			Properties newProperties = new Properties();
			
			oldProperties.load(new FileInputStream(oldFile));
			newProperties.load(new FileInputStream(newFile));
			
			Enumeration enumeration = oldProperties.propertyNames();
			while (enumeration.hasMoreElements()) {
				String elem = (String) enumeration.nextElement();
				if (newProperties.containsKey(elem)) {
					boolean encryptDBCredentical = ifContainDBCredentials(elem);
					// Added just to encrypt password only for new installations
					if(!upgrade && encryptDBCredentical){
						newProperties.setProperty(elem, DBUtils.encryptString(oldProperties.getProperty(elem)));
					}else{
						newProperties.setProperty(elem, oldProperties.getProperty(elem));
					}
				}
			}
			newProperties.store(new FileOutputStream(oldFile), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean ifContainDBCredentials(String elem){
		if(elem.contains("db.username") || elem.contains("db.password")){
			return true;
		}else{
			return false;
		}
	}
}
