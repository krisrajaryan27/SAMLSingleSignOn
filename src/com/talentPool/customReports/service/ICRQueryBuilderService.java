package com.talentPool.customReports.service;

import com.talentPool.customReports.exception.QueryBuilderException;
import com.talentPool.customReports.jaxb.CustomReport;


/**
 * Dynamic Report Query Builder Interface used to build query for given reportType
 * @author PraveenK
 * @since  Nov 7, 2011
 */
public interface ICRQueryBuilderService {
	
	/**
	 * Builds the query and save the query in the file location given
	 * @param customReport
	 * @param filePath
	 * @throws Exception
	 */
	public void saveReportDataQuery(CustomReport customReport, String filePath) throws Exception;
	
	/**
	 * Builds the query and save the query in the file location given
	 * @param customReport
	 * @param filePath
	 * @throws Exception
	 */
	public void saveInactiveReportDataQuery(CustomReport customReport, String filePath) throws Exception;
	
	/**
	 * Builds the query with the details given in  {@link CustomReport}.
	 * @param customReport
	 * @return
	 * @throws QueryBuilderException
	 */
	public String buildQuery(final CustomReport customReport, boolean showInactivePoistions) throws QueryBuilderException; 
}
