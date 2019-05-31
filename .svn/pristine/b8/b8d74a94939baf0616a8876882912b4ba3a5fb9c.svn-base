/**
 * 
 */
package com.talentPool.customReports.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRFilters;
import com.talentPool.customReports.dataobject.CRReportTypeColumnMapping;
import com.talentPool.customReports.dataobject.CRReportTypes;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.dataobject.CustomReportDetails;
import com.talentPool.customReports.dataobject.CustomReportModel;

/**
 * @author PraveenK
 * @since  Nov 8, 2011
 */
public interface ICustomReportDAO {
	
	/**
	 * @return
	 * @throws HibernateException
	 */
	List<CRColumn> getReportColumnsList() throws HibernateException;
	
	/**
	 * @return
	 * @throws HibernateException
	 */
	List<CRColumn> getReportColumnsList(boolean activeCols) throws HibernateException;
	
	List<CRColumn> getReportColumnsList(boolean activeCols, String columnProperties) throws HibernateException;
	
	/**
	 * @return
	 * @throws HibernateException
	 */
	List<CRColumn> getActiveReportColumnsList() throws HibernateException;
	
	/**
	 * Retrieves List of {@link CRColumn} for given comma delimited columnIds 
	 * @param selectedColumns
	 * @return <li>List of {@link CRColumn}</li>
	 */
	List<CRColumn> getReportColumns(String columnIds);
	
	/**
	 * Retrieves the <code>CustomReport</code> for the given <code>reportId</code> 
	 * @param reportId
	 * @return {@link CustomReportDetails}
	 */
	CustomReportDetails getCustomReport(String reportId);
	
	
	Long saveCustomReport(CustomReportDetails crd) throws HibernateException;
	
	void updateCRFilePath(Long reportId, String xmlFilePath, String templateFilePath, String queryFilePath, String inactivePositionsQueryFilePath);
	
	void updateCustomReport(Long reportId, String title, String desc, String modifiedBy);
	
	/**
	 * Retrieves {@link CRTableType} List
	 * @return
	 */
	List<CRTableType> getTableTypeList();
	
	List<CRFilters> getCustomReportFilters(CustomReportModel reportModel);
	
	List<CRReportTypes> getCRReportTypes();
	
	CRReportTypes getCRReportTypes(String reportTypeId);
	
	List<CRReportTypeColumnMapping> getCRReportTypeColumnMap(String reportTypeId);
}
