/**
 * Created : Nov 6, 2014 5:02:53 PM
 * @author : Sachinm
 */
package com.talentPool.parser.utils;

import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;

/**
 * @author Sachinm
 *
 */
public class ConverterUtils {

	/**
	 * @return jni path
	 */
	private static String getOfficeConverterPath(){
		String jniAbsolutePath= Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"),TPApplicationProperties.getProperty("parser.folder.jni") );
		return Utils.concatFilePath(jniAbsolutePath,TPApplicationProperties.getProperty("jni.office.converters"));
	}
	
	/**
	 * @param sourcePath
	 * @param destinationPath
	 * @param replaceParams
	 * @return query for wordreplaceutils
	 */
	public static String getQueryStringForWordReplaceUtils(String sourcePath,String destinationPath,String replaceParams){
		return getQueryString("WordFindReplace")+" -s " + sourcePath+" -d "+destinationPath + " -r " + replaceParams;
	}
	
	/**
	 * @param sourcePath
	 * @param destinationPath
	 * @return query for exceltopdf converter
	 */
	public static String getQueryStringForExcelToPdf(String sourcePath,String destinationPath){
		return getQueryString("ExcelToPDF")+ " -s "+sourcePath+" -d "+ destinationPath;
	}
	
	/**
	 * @param sourcePath
	 * @param destinationPath
	 * @return query for wordtohtml converter
	 */
	public static String getQueryStringForWordToHtml(String sourcePath,String destinationPath){
		return getQueryString("WordToHtml")+ " -s "+sourcePath+" -d "+ destinationPath;
	}
	
	/**
	 * @param functionType
	 * @return FUNCTIONTYPE
	 */
	private static String getQueryString(String functionType){
		return getOfficeConverterPath()+ " -t " +functionType;
	}
	
}
