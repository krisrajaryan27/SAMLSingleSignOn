/**
 * 
 */
package com.talentPool.common.properties;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import com.talentPool.common.Logger.TPLogger;

/**
 * @author shivprasad
 * 
 */
public class SMTPProperties {
	private static ResourceBundle rb = null;
	private static Properties mailProps = null;

	static {
		try {
			rb = ResourceBundle.getBundle("smtp");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting smtp properties", e);
		}
	}

	/**
	 * @return
	 */
	public static Properties getMailProperties() {
		try {
			if (mailProps == null) {
				mailProps = new Properties();
				Enumeration<String> enumx = rb.getKeys();
				while (enumx.hasMoreElements()) {
					String key = (String) (enumx.nextElement());
					mailProps.put(key, rb.getString(key));
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting smtp mail properties", e);
		}
		return mailProps;
	}

}
