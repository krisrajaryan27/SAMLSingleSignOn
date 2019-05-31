/**
 * 
 */
package com.talentPool.desktop.utils;

import com.talentPool.common.Logger.TPLogger;


/**
 * @author shivprasad
 * 
 */
public class DesktopUtils {
	public static String getJSArrayForMonths() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[");
			for (int i = 0; i < 12; i++) {
				sb.append("new SelectOption('" + i + "','" + i + "'),");
			}
			sb.append("new SelectOption('" + 12 + "','" + 12 + "')");
			sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error("error while generating javascript array for months", e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}

	public static String getJSArrayForYears() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[");
			for (int i = 0; i < 50; i++) {
				sb.append("new SelectOption('" + i + "','" + i + "'),");
			}
			sb.append("new SelectOption('" + 50 + "','" + 50 + "')");
			sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error("error while generating javascript array for years", e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}

}
