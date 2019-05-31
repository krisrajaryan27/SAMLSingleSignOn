package com.talentPool.customReports.service;

import com.talentPool.customReports.jaxb.CustomReport;

/**
 * DynamicReport XML Service Interface which will generate XML report template 
 * which will have all details need to generate the report. Details include:
 * <li>Report title, sub title etc..</li>
 * <li>Columns and their properties.</li>
 * <li>Grouping details in any present.</li>
 * <li>Sorting details in any present.</li> 
 * @author PraveenK
 * @since  Nov 7, 2011
 */
public interface ICRXMLService {
	void saveReportXMLFile(CustomReport customReport, String fileName) throws Exception;
	
	/**
	 * Parses the given xml and returns {@link ReportType} object
	 * @param xmlFilePath
	 * @return {@link ReportType}
	 */
	CustomReport getCustomReport(String xmlFilePath);
}
