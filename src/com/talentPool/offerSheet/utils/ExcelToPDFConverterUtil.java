/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.offerSheet.utils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.parser.converter.JNIProcess;
import com.talentPool.parser.utils.ConverterUtils;

/**
 * Util which converts Excel workbooks to PDF formats.
 * @author PraveenK
 * @since  Mar 28, 2012
 */
public class ExcelToPDFConverterUtil {
	
	/**
	 * Invoke ExcelToPDF.exe which takes input parameters as srcFile, destFile 
	 * @param srcFilePath
	 * @param destFilePath
	 * @return
	 */
	public static boolean convert(String srcFilePath, String destFilePath) {
		boolean result = false;
		try {

			JNIProcess process = new JNIProcess(ConverterUtils.getQueryStringForExcelToPdf(srcFilePath.replace("/", "\\"), destFilePath.replace("/", "\\")));
			Thread converterThread = new Thread(process);
			converterThread.start();
			converterThread.join();
			result = process.getProcessResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return result;
	}
}
