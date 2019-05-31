/**
 * 
 */
package com.talentPool.documents;

import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 *
 */
public class DocumentConstants {
	public static final String CONTENT_DISPOSITION_ATTACHMENT="attachment";
	public static final String CONTENT_DISPOSITION_INLINE="inline";
	
	public static String documentsPath;
	public static String relativeDocumentPath;
	public static String iconsPath;
	public static String relativeIconsPath;
	
	private static String officeVersion;
	private static final String OFFICE_2007 = "2007";
	public static boolean IS_OFFICE_2007 = false;
	private static final String OFFICE_2003 = "2003";
	public static boolean IS_OFFICE_2003 = false;
	public static String[] extensions = null;
	public static String[] indentImportExtensions = null;
	public static final String[] COMMON_EXTENSIONS;
	public static final String[] SINGLE_IMPORT_EXTENSIONS;
	
	public static String sessionFoldersPath;
	public static String relativeSessionFoldersPath;
	
	public static final String DOCUMENT_URL="docs.do?mode=getDocument&fileName=$fileName&contentDisposition=$contentDisposition";
	public static final String DOCUMENT_URL_FORMAT="docs.do?mode=getDocument";
	static {
		relativeDocumentPath = TPApplicationProperties.getProperty("documents.dir");
		documentsPath = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), relativeDocumentPath);
		
		relativeIconsPath = TPApplicationProperties.getProperty("icons.dir");
		iconsPath = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), relativeIconsPath);
		
		officeVersion = TPApplicationProperties.getProperty("office.version");
		if (OFFICE_2007.equalsIgnoreCase(officeVersion)) {
			IS_OFFICE_2007 = true;
		}else if(OFFICE_2003.equalsIgnoreCase(officeVersion)){
			IS_OFFICE_2003 = true;
		}
		extensions = TPApplicationProperties.getProperty("bulk.import.ext.allowed").split(",");
		indentImportExtensions = TPApplicationProperties.getProperty("indent.import.ext.allowed").split(",");
		COMMON_EXTENSIONS = TPApplicationProperties.getProperty("common.ext.allowed").split(",");
		SINGLE_IMPORT_EXTENSIONS = TPApplicationProperties.getProperty("single.import.ext.allowed").split(","); 
			
		relativeSessionFoldersPath = TPApplicationProperties.getProperty("uploads.dir");
		sessionFoldersPath = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), relativeSessionFoldersPath);
	}

	public static final String UNHIDE = "0";
	public static final String HIDE = "1";
}
