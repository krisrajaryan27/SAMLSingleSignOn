/**
 * 
 */
package com.talentPool.customReports.service;

import java.util.List;
import java.util.Map;

import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRFilters;
import com.talentPool.customReports.dataobject.CRReportTypes;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.dataobject.CustomReportDetails;
import com.talentPool.customReports.dataobject.CustomReportModel;
import com.talentPool.customReports.jaxb.CustomReport;

/**
 * @author PraveenK
 * @since  Nov 8, 2011
 */
public interface ICustomReportService {
	
	/**
	 * Returns all report columns available to design a report.
	 * @return reportColumns map with key as columnPropertyName and {@link CRColumn} object as value
	 */
	Map<String,CRColumn> getReportColumnsMap();

	/**
	 * Returns all report columns available to design a report.
	 * @return reportColumns list of {@link CRColumn} objects.
	 */
	List<CRColumn> getReportColumnsList();
	
	
	/**
	 * Returns all report columns available to design a report.
	 * @return reportColumns list of {@link CRColumn} objects.
	 */
	List<CRColumn> getActiveReportColumnsList();
	
	
	/**
	 * Retrieves List of {@link CRColumn} for given comma delimited columnIds 
	 * @param selectedColumns
	 * @return List of {@link CRColumn}
	 */
	List<CRColumn> getReportColumns(String columnIds);
	
	/**
	 * Retrieves the <code>CustomReport</code> for the given <code>reportId</code> 
	 * @param reportId
	 * @return {@link CustomReportDetails}
	 */
	CustomReportDetails getCustomReport(String reportId);
	
	void updateCustomReport(CustomReportModel reportModel, String modifiedBy); 
	
	CustomReport createCustomReport(CustomReportModel reportModel);
	Long saveCustomReport(CustomReportModel reportModel, String createdBy);
	void saveReportDirectory(Long reportId, String xmlFilePath, String templateFilePath, String queryFilePath, String inactivePositionsQueryFilePath);
	
	
	Map<Long,CRTableType> getCRTableTypeMap();
	
	List<CRFilters> getCustomReportFilters(CustomReportModel reportModel);

	void saveCustomReportLevels(Long reportId, CustomReportModel reportModel);
	
	CRReportTypes getCRReportTypes(String reportTypeId);
	
	List<CRReportTypes> getCRReportTypes();
	
	String getCRReportTypeColumnMap(String reportTypeId);
	
	List<CRColumn> getActiveReportColumnsList(String reportTypeColProp);	

}
