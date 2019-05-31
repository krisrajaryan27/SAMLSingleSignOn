/**
 * 
 */
package com.talentPool.common.Logger;

/**
 * @author shreyas
 * 
 */

import org.apache.log4j.Logger;

public class TPLogger extends Logger {
	
	protected TPLogger(String name) {
		super(name);
	}

	private String loggerName;

	public static Logger getLogger() {
		return Logger.getLogger("com.talentPool");
	}
}
