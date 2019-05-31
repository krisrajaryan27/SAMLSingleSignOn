/**
 * 
 */
package com.talentPool.offerSheet.utils;

import com.talentPool.common.Logger.TPLogger;

import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.parser.converter.JNIProcess;
import com.talentPool.parser.utils.ConverterUtils;

/**
 * @author pallavi
 *
 */
public class WordReplaceUtil {
	public final String SEPARATOR = "***";
	public static final String COMASEPARATOR = "AAA";
	private final String VARVALSEPARATOR = "___";
	
	public boolean replace(String srcFilePath, String destFilePath, Object[] findText, 
									Object[] replaceText) {
		boolean result = false;
		try {
			String replaceParams = convertArrayToString(findText,replaceText);
			JNIProcess process = new JNIProcess(ConverterUtils.getQueryStringForWordReplaceUtils(srcFilePath.replace("/", "\\"), destFilePath.replace("/", "\\"), replaceParams));
			Thread converterThread = new Thread(process);
			converterThread.start();
			converterThread.join();
			result=process.getProcessResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return result;
	}
	
	private String convertArrayToString(Object[] findText, Object[] replaceText) {
		StringBuffer buffer = new StringBuffer();
		if(findText != null && findText.length > 0) {
			for(int i=0;i<findText.length;i++) {
				if(buffer.length() > 0) {
					buffer.append(SEPARATOR);
				}
				buffer.append("\""+findText[i]+"\""+VARVALSEPARATOR+"\""+replaceText[i]+"\"");
			}
		}
		return buffer.toString();
	}
}
