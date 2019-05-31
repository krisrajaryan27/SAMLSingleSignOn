/**
 * 
 */
package com.talentPool.customReports.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import net.sf.jasperreports.engine.JRException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dataobject.CustomReportDetails;
import com.talentPool.customReports.dataobject.RunCustomReportModel;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Filter;
import com.talentPool.customReports.jaxb.Filters;
import com.talentPool.customReports.service.ICRExporter;
import com.talentPool.customReports.service.ICRXMLService;
import com.talentPool.customReports.service.ICustomReportService;
import com.talentPool.customReports.utils.CRJSONUtils;
import com.talentPool.customReports.utils.CustomReportUtils;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.form.ReportForm;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.struts2.common.TPActionSupport;

/**
 * @author PraveenK
 * @since  Nov 10, 2011
 */
public class RunCustomReportAction extends TPActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7855711952431071832L;
	
	private String reportId;
	private String reportName;
	private String filters;
	private boolean showReport; 
	private String outputFileName;
	private List<Filter> crFilters;
	private InputStream inputStream;
	private String filtersJSON;
	
	private ICustomReportService _customReportService;
	private ICRExporter _crExporter;
	private ICRXMLService _crXMLService;
	
	ReportForm reportForm = new ReportForm();

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		if(GlobalConstants.ENABLED.equals(getRequest().getParameter("showMigrationMessage"))){
			return "migrationmessage";
		}else {
			CustomReportDetails crd 	= _customReportService.getCustomReport(getReportId());
			String xmlFilePath 			= CustomReportUtils.getCustomReportPath(crd.getXmlFilePath());
			if(!Utils.isBlankOrNull(xmlFilePath)){
				CustomReport customReport 	= _crXMLService.getCustomReport(xmlFilePath);
				crFilters = CustomReportUtils.getFiltersOnVisibility(customReport.getFilters(), true);
				setReportName(customReport.getReportName());
			}else {
				TPLogger.getLogger().error("Files are missing for reportId: "+getReportId());
				addActionError(TPLabels.getLabel("custom_report.error.unable_to_load_report"));
				return ERROR;
			}
			return SUCCESS;
		}
	}
	
	public String saveMigrationMessageFlag() throws Exception {
		if(GlobalConstants.DISABLED.equals(getRequest().getParameter("migrationMessageFlag"))){
			ReportManager reportManager = new ReportManager();
			reportManager.saveMigrationMessageFlag(GlobalConstants.DISABLED, getUserId());
		}
		return SUCCESS;
	}
	
	public String runReport() throws Exception {
		try {
			CustomReportDetails crd 	= _customReportService.getCustomReport(getReportId());
			crFilters 					= CRJSONUtils.converToFilterList(filtersJSON);
			RunCustomReportModel rcrm 	= buildRunCustomReportModel(crd, crFilters);
			String fileName 			= getRequest().getSession(false).getId() + String.valueOf(System.currentTimeMillis());
			this.outputFileName 		= _crExporter.exportReport(rcrm, fileName);
			this.outputFileName 		= Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER, this.outputFileName);
			showReport 					= true;
		} catch (JRException e) {
			TPLogger.getLogger().error(TPLabels.getLabel("custom_report.error.unable_to_generate"), e);
			addActionError(TPLabels.getLabel("custom_report.error.unable_to_generate"));
			return ERROR;
		} catch (FileNotFoundException e) {
			TPLogger.getLogger().error(TPLabels.getLabel("custom_report.error.unable_to_generate"), e);
			addActionError(TPLabels.getLabel("custom_report.error.unable_to_generate"));
			return ERROR;
		} catch (SQLException e) {
			TPLogger.getLogger().error(TPLabels.getLabel("custom_report.error.unable_to_generate"), e);
			addActionError(TPLabels.getLabel("custom_report.error.unable_to_generate"));
			return ERROR;
		}catch (Exception e) {
			TPLogger.getLogger().error(TPLabels.getLabel("custom_report.error.unable_to_generate"), e);
			addActionError(TPLabels.getLabel("custom_report.error.unable_to_generate"));
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String exportReport() throws Exception {
		ByteArrayOutputStream outputStream = null;
		try {
			CustomReportDetails crd 	= _customReportService.getCustomReport(getReportId());
			crFilters 					= CRJSONUtils.converToFilterList(filtersJSON);
			RunCustomReportModel rcrm 	= buildRunCustomReportModel(crd, crFilters);
			outputStream = _crExporter.exportReport(rcrm);
			inputStream=new ByteArrayInputStream(outputStream.toByteArray());
			getResponse().setContentLength(outputStream.toByteArray().length);
		} catch (JRException e) {
			TPLogger.getLogger().error(TPLabels.getLabel("custom_report.error.unable_to_generate"), e);
			addActionError(TPLabels.getLabel("custom_report.error.unable_to_generate"));
			return ERROR;
		} catch (FileNotFoundException e) {
			TPLogger.getLogger().error(TPLabels.getLabel("custom_report.error.unable_to_generate"), e);
			addActionError(TPLabels.getLabel("custom_report.error.unable_to_generate"));
			return ERROR;
		}catch (SQLException e) {
			TPLogger.getLogger().error(TPLabels.getLabel("custom_report.error.unable_to_generate"), e);
			addActionError(TPLabels.getLabel("custom_report.error.unable_to_generate"));
			return ERROR;
		}catch (Exception e) {
			TPLogger.getLogger().error(TPLabels.getLabel("custom_report.error.unable_to_generate"), e);
			addActionError(TPLabels.getLabel("custom_report.error.unable_to_generate"));
			return ERROR;
		}finally{
			if(outputStream!=null){
				outputStream.flush();
				outputStream.close();
			}
		}
	    return SUCCESS;
	} 
	
	private RunCustomReportModel buildRunCustomReportModel(CustomReportDetails crd, List<Filter> crFilters){
		RunCustomReportModel rcrm = new RunCustomReportModel();
		CustomReport customReport = _crXMLService.getCustomReport(CustomReportUtils.getCustomReportPath(crd.getXmlFilePath()));
		
		Filters filters = new Filters();
		filters.getFilter().addAll(crFilters);
		filters.getFilter().addAll(CustomReportUtils.getFiltersOnVisibility(customReport.getFilters(), false));
		
		rcrm.setQueryFilePath(CustomReportUtils.getCustomReportPath(crd.getQueryFilePath()));
		rcrm.setInactivePositionsQueryFilePath(CustomReportUtils.getCustomReportPath(crd.getInactivePositionsQueryFilePath()));
		rcrm.setTemplateFilePath(CustomReportUtils.getCustomReportPath(crd.getTemplateFilePath()));
		
		rcrm.setFilters(filters);
		rcrm.setCandNamesSpecifications(customReport.getCandNamesSpecifications());
		rcrm.setUserId(getUserId());
		rcrm.setPermissionSet(getPermissionSet());
		rcrm.setShowInactivePositions(customReport.isShowInactivePositions());
		
		rcrm.setCheckPositionPermission(CustomReportUtils.isPositionPermissionCheckRequired(crd.getReportTypeId()));
		
		return rcrm;
	}
	
	/**
	 * Injected through DI
	 * @param drDataSourceService the drDataSourceService to set
	 */
	public void setCrExporter(ICRExporter _crExporter) {
		this._crExporter = _crExporter;
	}
	
	/**
	 * Injected though DI
	 * @param drReportXMLService the drReportXMLService to set
	 */
	public void setCrXMLService(ICRXMLService drReportXMLService) {
		this._crXMLService = drReportXMLService;
	}
	
	/**
	 * Injected though DI
	 * @param drReportXMLService the drReportXMLService to set
	 */
	public void setCustomReportService(ICustomReportService _customReportService) {
		this._customReportService = _customReportService;
	}
	
	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the filters
	 */
	public String getFilters() {
		return filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(String filters) {
		this.filters = filters;
	}

	/**
	 * @return the showReport
	 */
	public boolean isShowReport() {
		return showReport;
	}

	/**
	 * @param showReport the showReport to set
	 */
	public void setShowReport(boolean showReport) {
		this.showReport = showReport;
	}

	/**
	 * @return the outputFileName
	 */
	public String getOutputFileName() {
		return outputFileName;
	}

	/**
	 * @param outputFileName the outputFileName to set
	 */
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	/**
	 * @return the reportForm
	 */
	public ReportForm getReportForm() {
		return reportForm;
	}

	/**
	 * @param reportForm the reportForm to set
	 */
	public void setReportForm(ReportForm reportForm) {
		this.reportForm = reportForm;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}



	/**
	 * @return the filtersJSON
	 */
	public String getFiltersJSON() {
		return filtersJSON;
	}

	/**
	 * @param filtersJSON the filtersJSON to set
	 */
	public void setFiltersJSON(String filtersJSON) {
		this.filtersJSON = filtersJSON;
	}

	/**
	 * @return the crFilters
	 */
	public List<Filter> getCrFilters() {
		return crFilters;
	}

	/**
	 * @param crFilters the crFilters to set
	 */
	public void setCrFilters(List<Filter> crFilters) {
		this.crFilters = crFilters;
	}

}
