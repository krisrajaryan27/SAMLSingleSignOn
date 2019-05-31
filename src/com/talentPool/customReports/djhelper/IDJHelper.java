/**
 * 
 */
package com.talentPool.customReports.djhelper;

import net.sf.jasperreports.engine.JRException;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;

import com.talentPool.customReports.exception.DynamicReportGenerationException;
import com.talentPool.customReports.jaxb.CustomReport;

/**
 * @author PraveenK
 * @since  Dec 8, 2011
 */
public interface IDJHelper {
	
	/**
	 * Generates JRXML based on the information provided by {@link CustomReport} object 
	 * and save it to the given file path
	 * @param reportType
	 * @param filePath
	 */
	void generateJRXML(final CustomReport customReport, String filePath) throws DynamicReportGenerationException, JRException;
	
	/**
	 * Generates JRXML based on the information provided by {@link CustomReport} object 
	 * and save it to the given file path
	 * @param reportType
	 * @param filePath
	 * @param layout
	 */
	void generateJRXML(final CustomReport customReport, String filePath, LayoutManager layout) throws DynamicReportGenerationException, JRException;
	
	/**
	 * Generates {@link DynamicReport} using the reportDetails
	 * <li>Adds the report Details</li>
	 * <li>Builds and add the report columns</li>
	 * <li>Builds and add the report groups if any available</li>
	 * @return {@link DynamicReport} built using reportDetails
	 * @throws DynamicReportGenerationException
	 */
	DynamicReport generateDynamicReport() throws DynamicReportGenerationException;
}
