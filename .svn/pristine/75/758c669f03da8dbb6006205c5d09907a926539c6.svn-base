/**
 * 
 */
package com.talentPool.parser;


import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 * 
 */
public class ParserConstants {
	public static final String FIELD_NAME = "name";
	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_PHONE = "phone";
	public static final String FIELD_SOURCE = "source";
	public static final String FIELD_FORWARD_HEADER = "forward_header";
	public static final String FIELD_EDUCATION = "education";
	public static final String FIELD_SKILLS = "skills";
	public static final String FIELD_YEAR_OF_PASSING="year_of_passing";
	public static final String FIELD_LOCATION = "location";
	public static final String FIELD_CURRENT_EMPLOYER = "current_employer";
	public static final String FIELD_CURRENT_CTC = "current_ctc";
	public static final String FIELD_WORKING_SINCE = "working_since";
	public static final String FIELD_EMPLOYMENT_HISTORY = "work experience";
	// Section Headings
	public static final String SECTION_EDUCATION = "Education";
	public static final String SECTION_SKILLS = "Skills";
	public static final String SECTION_EMPLOYMENT = "Employment";
	public static final String SECTION_OBJECTIVE = "Objective";
	public static final String SECTION_PERSONAL = "Personal";
	public static final String SECTION_OTHER = "Other";
	public static final String SECTION_EMPLOYMENT_HISTORY = "Work Experience";

	public static final int PHONE_TYPE_OTHER = 0;
	public static final int PHONE_TYPE_MOBILE = 1;

	public static final int NON_NAUKRI_RESUME = 0;
	public static final int OLD_NAUKRI_RESUME = 1;
	public static final int NEW_NAUKRI_RESUME = 2;
	public static final int LATEST_NAUKRI_RESUME = 3;
	public static final int US_MONSTER = 4;
	public static final int MONSTER_IND = 5;
	public static final int NON_MONSTER_RESUME = 6;
	/*
	 * dictionary is loaded only once and all the words in the dictionary should be in lower case
	 */
	public static String dictionary = "";
	public static String commonWords = "";

	/**
	 * xsl file for the word to xml conversion.
	 */
	public static String JNI_PATH="";
	public static String WORD_TO_HTML_FILE="";
	
	static {
		// load the dictionary
		try {
			String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
			String dictionaryPath = Utils.concatFilePath(basePath, TPApplicationProperties.getProperty("parser.file.dictionary"));
			String commonWordsPath = Utils.concatFilePath(basePath, TPApplicationProperties.getProperty("parser.file.commonwords"));

			//File srcFile = new File(dictionaryPath);
			//dictionary = ParserUtils.getContents(srcFile).toLowerCase();
			FileHandler fileHandler = new FileHandler();
			dictionary = fileHandler.getTextFileContent(dictionaryPath, null).toLowerCase();
			//File commonWordsFile = new File(commonWordsPath);
			commonWords = fileHandler.getTextFileContent(commonWordsPath,null).toLowerCase();
			
			JNI_PATH = TPApplicationProperties.getProperty("parser.folder.jni");
			
			String jniAbsolutePath= Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"),JNI_PATH );
			WORD_TO_HTML_FILE = Utils.concatFilePath(jniAbsolutePath,TPApplicationProperties.getProperty("parser.file.wordtohtml"));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while loading dictionary", e);
		}
	}

	public static final String DEGREE_ID_OTHER = "1";
}
