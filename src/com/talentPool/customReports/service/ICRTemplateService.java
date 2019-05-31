package com.talentPool.customReports.service;

import com.talentPool.customReports.jaxb.CustomReport;


/**
 * Dynamic Report Template Service Interface instructs the implementations to generate 
 * template files which will be used in run time report generation  
 * @author PraveenK
 * @since  Nov 7, 2011
 */
public interface ICRTemplateService {
	void saveReportTemplateFile(CustomReport customReport, String filePath) throws Exception;
	void editReportTemplateFile(CustomReport customReport);
	void deleteReportTemplateFile();
}
