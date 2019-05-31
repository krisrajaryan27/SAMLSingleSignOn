/**
 * 
 */
package com.talentPool.customReports.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.crosstabs.base.JRBaseCrosstab;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.talentPool.common.db.DBManager;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dataobject.RunCustomReportModel;
import com.talentPool.customReports.dataobject.TPResultSetDataSource;
import com.talentPool.customReports.service.ICRDataSourceService;
import com.talentPool.customReports.service.ICRExporter;
import com.talentPool.customReports.utils.CustomReportUtils;
import com.talentPool.customReports.utils.FilterCriteriaBuilder;

/**
 * @author PraveenK
 * @since  Nov 28, 2011
 */
public abstract class JasperExporter implements ICRExporter {
	
	protected ICRDataSourceService _crDataSourceService;

	/**
	 * With RunCustomReportModel data generates JasperPrint which can be used to export it in required file formats 
	 * @param rcrm
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	protected JasperPrint generateReport(RunCustomReportModel rcrm) throws JRException, SQLException {
		JRDataSource jsd 					= null;
		JRDataSource emptyDataSource 		= null;
		JasperPrint jasperPrint 			= null;
		JasperReport jasperReport 			= JasperCompileManager.compileReport(rcrm.getTemplateFilePath());
		List<JRBaseCrosstab> crossTabLst 	= CustomReportUtils.isReportHasCrossTabs(jasperReport);
		Connection con						= null;
		try {
			con = DBManager.getConnection();
			if(rcrm.getCandNamesSpecifications()==null){
				jsd = _crDataSourceService.getDataSorce(con, rcrm.getQueryFilePath(), rcrm.getFilters(), rcrm.getUserId(), rcrm.getPermissionSet(), rcrm.isCheckPositionPermission());
				emptyDataSource = _crDataSourceService.getEmptyDataSorce(con);
			}else {
				jsd = _crDataSourceService.getDataSorce(con, rcrm.getQueryFilePath(), rcrm.getFilters(), rcrm.getCandNamesSpecifications(), rcrm.getUserId(), rcrm.getPermissionSet());
				emptyDataSource = _crDataSourceService.getEmptyDataSorce(con);
			}
			
			@SuppressWarnings("rawtypes")
			Map params = new HashMap();
			params.put("subtitle",new FilterCriteriaBuilder(rcrm.getFilters()).getFilterCriteria());
			
			if(!Utils.isListEmptyOrNull(crossTabLst)){
				params.put("sr", jsd);
				CustomReportUtils.addCrossTabParams(crossTabLst, params);
				jasperPrint = JasperFillManager.fillReport(jasperReport, params, emptyDataSource);
			} else {
				jasperPrint = JasperFillManager.fillReport(jasperReport, params, jsd);
			}
			
			if(jsd instanceof TPResultSetDataSource)
				((TPResultSetDataSource) jsd).closeResultSet();
			
			if(rcrm.isShowInactivePositions()) {
				JasperPrint jasperPrint2 = generatePrintForInactivePositions(rcrm, con);
				for (int i = 0; i < jasperPrint2.getPages().size(); i++) {
					jasperPrint.addPage((JRPrintPage) jasperPrint2.getPages().get(i));
				}
			}
		} finally{
			if(con!=null){
				DBManager.release(con);				
			}
		}
		
		return jasperPrint;
	}

	@SuppressWarnings("unchecked")
	protected JasperPrint generatePrintForInactivePositions(RunCustomReportModel rcrm, Connection con) throws JRException, SQLException {
		JRDataSource mainDataSource = null;
		JRDataSource emptyDataSource = null;
		JasperPrint jasperPrint = null;
		JasperReport jasperReport = JasperCompileManager.compileReport(rcrm.getTemplateFilePath());
		List<JRBaseCrosstab> crossTabLst = CustomReportUtils.isReportHasCrossTabs(jasperReport);
		mainDataSource 	= _crDataSourceService.getInactiveDataSorce(con, rcrm.getInactivePositionsQueryFilePath(),
								rcrm.getFilters(), rcrm.getUserId(), rcrm.getPermissionSet());
		emptyDataSource = _crDataSourceService.getEmptyDataSorce(con);
		@SuppressWarnings("rawtypes")
		Map params = new HashMap();
		params.put("subtitle", "Positions With No Activity\n" + new FilterCriteriaBuilder(rcrm.getFilters()).getFilterCriteria());
		params.put("sr", mainDataSource);
		CustomReportUtils.addCrossTabParams(crossTabLst, params);
		jasperPrint = JasperFillManager.fillReport(jasperReport, params, emptyDataSource);
		if (mainDataSource instanceof TPResultSetDataSource)
			((TPResultSetDataSource) mainDataSource).closeResultSet();
		return jasperPrint;
	}
	
	/**
	 * @param _crDataSourceService the _crDataSourceService to set
	 */
	public void setCrDataSourceService(ICRDataSourceService _crDataSourceService) {
		this._crDataSourceService = _crDataSourceService;
	}
	
	/**
	 * @param rcrm
	 * @return list of all jasper-prints
	 * @throws JRException
	 * @throws SQLException
	 */
	protected List<JasperPrint> getAllJasperPrints(RunCustomReportModel rcrm) throws JRException, SQLException {
		JRDataSource jsd 					= null;
		JRDataSource emptyDataSource 		= null;
		JasperPrint jasperPrint 			= null;
		
		// TODO : find a better solution for exporting the reports to PDF properly
		// Since this method is used for PDF exports, we'll use the templateFilePath generated for PDFs
		String templateFilePath 			= rcrm.getTemplateFilePath();
		JasperReport jasperReport 			= JasperCompileManager.compileReport(templateFilePath.replace(".jrxml", "_ForPDF.jrxml"));
		List<JRBaseCrosstab> crossTabLst 	= CustomReportUtils.isReportHasCrossTabs(jasperReport);
		Connection con						= null;
		List<JasperPrint> allPrints			= null;
		try {
			con = DBManager.getConnection();
			if(rcrm.getCandNamesSpecifications()==null){
				jsd = _crDataSourceService.getDataSorce(con, rcrm.getQueryFilePath(), rcrm.getFilters(), rcrm.getUserId(), rcrm.getPermissionSet(), rcrm.isCheckPositionPermission());
				emptyDataSource = _crDataSourceService.getEmptyDataSorce(con);
			}else {
				jsd = _crDataSourceService.getDataSorce(con, rcrm.getQueryFilePath(), rcrm.getFilters(), rcrm.getCandNamesSpecifications(), rcrm.getUserId(), rcrm.getPermissionSet());
				emptyDataSource = _crDataSourceService.getEmptyDataSorce(con);
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("subtitle",new FilterCriteriaBuilder(rcrm.getFilters()).getFilterCriteria());
			
			if(!Utils.isListEmptyOrNull(crossTabLst)){
				params.put("sr", jsd);
				CustomReportUtils.addCrossTabParams(crossTabLst, params);
				jasperPrint = JasperFillManager.fillReport(jasperReport, params, emptyDataSource);
			} else {
				jasperPrint = JasperFillManager.fillReport(jasperReport, params, jsd);
			}
			
			if(jsd instanceof TPResultSetDataSource)
				((TPResultSetDataSource) jsd).closeResultSet();
			
			allPrints = new ArrayList<JasperPrint>();
			if (jasperPrint != null) {
				allPrints.add(jasperPrint);
			}
			if(rcrm.isShowInactivePositions()) {
				JasperPrint jasperPrint2 = generatePrintForInactivePositions(rcrm, con);
				allPrints.add(jasperPrint2);
			}
		} finally{
			if(con!=null){
				DBManager.release(con);				
			}
		}
		
		return allPrints;
	}
}
