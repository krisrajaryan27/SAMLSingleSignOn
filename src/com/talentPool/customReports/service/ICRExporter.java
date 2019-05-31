/**
 * 
 */
package com.talentPool.customReports.service;

import java.io.ByteArrayOutputStream;

import com.talentPool.customReports.dataobject.RunCustomReportModel;

/**
 * @author PraveenK
 * @since  Nov 28, 2011
 */
public interface ICRExporter {

	/**
	 * 
	 * @param templateFilePath
	 * @param queryFilePath
	 * @param filters
	 * @param showNames TODO
	 */
	String exportReport(RunCustomReportModel rcrm, String outputFileName) throws Exception;
	
	/**
	 * 
	 * @param templateFilePath
	 * @param queryFilePath
	 * @param filters
	 * @param showNames TODO
	 */
	ByteArrayOutputStream exportReport(RunCustomReportModel rcrm) throws Exception;
}
