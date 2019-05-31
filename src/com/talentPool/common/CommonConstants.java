package com.talentPool.common;

import java.net.UnknownHostException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;

public class CommonConstants {
	public static String HOST_NAME = "";

	public static int NO_MODULE = 0;
	
	public static String SORT_DIR_ASC = "ASC";
	public static String SORT_DIR_DESC = "DESC";

	public static final String YES = "1";
	public static final String NO = "0";
	
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	public static final String IE_PLUGIN = "1";
	public static final String FIREFOX_PLUGIN = "2";
	public static final String OUTLOOK_PLUGIN = "3";
	public static final String CHROME_PLUGIN = "4";
	
	public static final String DEFAULT_DELIMITER = ",";
	
	public static final int PRODUCT_TALENTPOOL = 1;
	public static final int PRODUCT_TEMPLOYEE = 2;
	public static final int PRODUCT_TVENDOR = 3;
	public static final int PRODUCT_TWEBSITE = 4;
	public static final int PRODUCT_CANDIDATE_PORTAL = 5;
	
	/**
	 * Default select value should be used in drop downs against please select option
	 */
	public static final String DEFAULT_SELECT_VALUE = "-1";
	
	/**
	 * Used when building JS Array
	 */
	public static final String NEW_ARRAY = "new Array()";
	
	/**
	 * Used to mean all data is needed
	 */
	public static final String ALL = "_all";
	
	static {
		try {
			HOST_NAME = Utils.getHostName();
		} catch (UnknownHostException e) {
			HOST_NAME = TPApplicationProperties.getProperty("system.name");
			TPLogger.getLogger().error("Unable to get host name, setting default name = " + HOST_NAME, e);
		}
	}
}
