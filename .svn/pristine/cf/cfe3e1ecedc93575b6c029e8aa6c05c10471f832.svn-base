package com.talentPool.desktop.constants;

import java.io.File;

import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;


public class DesktopConstants {
	public static String uploadsPath;
	public final static String SUCCESS = "SUCCESS";
	public final static String FAIL = "FAIL";

	public final static String HEADER_SESSION_ID = "sessionid";
	public final static String HEADER_EMAIL_ID = "emailid";
	public final static String HEADER_APPLICANT_ID = "applicantid";
	public final static String HEADER_FILE_NAME = "filename";
	public final static String HEADER_IS_LAST_FILE = "islastfile";
	public final static String HEADER_TRANSFER_COMPLETE = "transfercomplete";

	public final static String HEADER_EMAIL_FROM = "emailfrom";
	public final static String HEADER_EMAIL_TO = "emailto";
	public final static String HEADER_EMAIL_CC = "emailcc";
	public final static String HEADER_EMAIL_BCC = "emailbcc";
	public final static String HEADER_EMAIL_SUBJECT = "emailsubject";
	public final static String HEADER_DATE_SENT = "datesent";
	public final static String HEADER_DATE_RECEIVED = "datereceived";
	public final static String HEADER_EMAIL_SIZE = "emailsize";
	public final static String HEADER_USER_NAME = "username";
	public final static String HEADER_USER_ID = "userid";

	public final static String SCREEN_TYPE_SINGLE_IMPORT = "singleimport";
	public final static String SCREEN_TYPE_BULK_IMPORT = "bulkimport";

	public final static String REQUEST_SOURCE_DESKTOP = "desktop";

	public final static String TEXT_BODY_FILE = "ts-email-text-body.txt";
	public final static String HTML_BODY_FILE = "ts-email-html-body.html";
	public final static String EMAIL_HEADER_FILE = "ts-email-header.txt";

	public final static String IMPORT_STATUS_FILES_TRANSFERRED = "0";
	public final static String IMPORT_STATUS_PARSING_DONE = "1";

	public final static String SESSION_STATUS_INPROCESS = "0";
	public final static String SESSION_STATUS_COMPLETED = "1";

	public final static String SESSION_TYPE_INBOX_BULK_IMPORT = "2";
	public final static String SESSION_TYPE_DESKTOP_IMPORT = "1";
	public final static String SESSION_TYPE_EMAIL_IMPORT = "0";
	public final static String SESSION_TYPE_BROWSER_IMPORT = "4";
	
	public final static String IS_NOT_IMPORTED = "0";
	public final static String IS_IMPORTED = "1";
	
	public final static String PORTAL_NAUKRI = "naukri";
	public final static String PORTAL_MONSTER = "monster";
	public final static String PORTAL_TIMESJOB = "timesjob";

	static {
		uploadsPath = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), TPApplicationProperties.getProperty("uploads.dir"));
		createUploadsDir(uploadsPath);
	}

	private static void createUploadsDir(String dirName) {
		try {
			File dirtocreate = new File(dirName);
			if (!dirtocreate.isDirectory()) {
				dirtocreate.mkdir();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
