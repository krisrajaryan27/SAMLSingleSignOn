package com.talentPool.common.Logger;

/**
 * @author shreyas
 *
 */

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;

public class TPConsoleAppender extends ConsoleAppender{

	/**
	 * 
	 */
	public TPConsoleAppender() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TPConsoleAppender(Layout arg0, String arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public TPConsoleAppender(Layout arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}