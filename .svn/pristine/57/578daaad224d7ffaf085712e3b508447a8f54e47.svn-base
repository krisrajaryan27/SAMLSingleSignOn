/**
 * 
 */
package com.talentPool.common.properties;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author shivprasad
 *
 */
public class POPProperties {
	private static ResourceBundle rb = null;
	private static Properties mailProps = null;

	static {
		try {
			rb = ResourceBundle.getBundle("pop");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static Properties getMailProperties() {
		try {
			if (mailProps == null) {
				mailProps = new Properties();
				Enumeration enumx = rb.getKeys();
				while (enumx.hasMoreElements()) {
					String key = (String) (enumx.nextElement());
					mailProps.put(key, rb.getString(key));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mailProps;
	}

}
