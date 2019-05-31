/**
 * 
 */
package com.talentPool.repository;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 * 
 */
public class RepositoryConstants {
	public static String DEFAULT_REPOSITORY_PATH;

	public static final String STATE_NEW = "New";
	public static final String STATE_INPROCESS = "Inprocess";
	public static final String STATE_JOINED = "Joined";
	public static final String STATE_PROCESSED = "Processed";
	public static final String STATE_APPLIED = "Applied";

	public static final String ACTION_ADD_DOC = "add";
	public static final String ACTION_DELETE_DOC = "delete";
	public static final String ACTION_UPDATE_DOC = "update";
	public static final String ACTION_BATCH_UPDATE = "batch_update";
	public static final String ACTION_OPTIMIZE = "optimize";

	public static int BATCH_SIZE = 100;
	public static final String BLANK_DATE = "999999999";
	public static final String FRESHER_DATE = "999999";
	
	public static final String APPLICANT_EMPLOYEE = "1";
	public static final String APPLICANT_NOT_EMPLOYEE = "0";

	public static final String REJECT_REASON_REJECT = "REJECT";
	public static final String REJECT_REASON_NOT_INTERESTED = "NOT_INTERESTED";
	public static final String REJECT_REASON_NOT_ATTENDED = "NOT_ATTENDED";
	public static final String REJECT_REASON_POSITION_CLOSED= "POSITION_CLOSED";
	
	// constant for blank number, all number in custom fields are stored as double
	public static final double BLANK_NUMBER = 99999999999999d;
	// const for blank string
	public static final String BLANK_STRING = "vvvvvvvvvvvvvv";
	// all dates in custom fields are stored as long
	public static final long BLANK_LONG = 99999999999999L;
	
	static {
		// ensure inxed directory is in place
		DEFAULT_REPOSITORY_PATH = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), TPApplicationProperties.getProperty("parser.dir.index"));
		try {
			FileHandler fileHandler = new FileHandler();
			fileHandler.deleteDirectory(DEFAULT_REPOSITORY_PATH);
			fileHandler.createDirectory(DEFAULT_REPOSITORY_PATH);
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while creating index directorty", e);
		}
		try {
			BATCH_SIZE = Integer.parseInt(TPApplicationProperties.getProperty("parser.index.batch.size").trim());
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to set batch size from properties ", e);
			BATCH_SIZE = 100;
		}
	}

}
