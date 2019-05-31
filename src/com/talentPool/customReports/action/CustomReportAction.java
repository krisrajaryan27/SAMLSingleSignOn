package com.talentPool.customReports.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ModelDriven;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.constants.CustomReportConstants;
import com.talentPool.customReports.dao.impl.CustomReportDAO;
import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRFilters;
import com.talentPool.customReports.dataobject.CRReportTypes;
import com.talentPool.customReports.dataobject.CustomReportDetails;
import com.talentPool.customReports.dataobject.CustomReportModel;
import com.talentPool.customReports.jaxb.CandNamesSpecifications;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.service.ICRQueryBuilderService;
import com.talentPool.customReports.service.ICRTemplateService;
import com.talentPool.customReports.service.ICRXMLService;
import com.talentPool.customReports.service.ICustomReportService;
import com.talentPool.customReports.utils.CRJSONUtils;
import com.talentPool.customReports.utils.CustomReportUtils;
import com.talentPool.customReports.utils.CustomReportXMLUtils;
import com.talentPool.customReports.utils.DJCrossTabHelper;
import com.talentPool.reports.dataobject.CustomizedReportData;
import com.talentPool.reports.form.ReportForm;
import com.talentPool.reports.manager.CustomizedReportManager;
import com.talentPool.struts2.common.TPActionSupport;

/**
 * @author PraveenK
 * @since  Nov 8, 2011
 */
public class CustomReportAction extends TPActionSupport implements ModelDriven<CustomReportModel> {
	
	
	private static final long serialVersionUID = 1L;
	
	private CustomReportModel reportModel 		= new CustomReportModel();
	private List<CRFilters> crFilters	= null;
	ReportForm reportForm 					= new ReportForm();
	private List<CRReportTypes> crReportTypes=null;
	
	private ICustomReportService _customReportService;
	private ICRXMLService _crXMLService;
	private ICRTemplateService _crTemplateService;
	private ICRQueryBuilderService _crQueryBuilderService;  
	
	/**
	 * Injected though DI
	 * @param drTemplateService the drTemplateService to set
	 */
	public void setCrTemplateService(ICRTemplateService drTemplateService) {
		this._crTemplateService = drTemplateService;
	}

	/**
	 * Injected though DI
	 * @param drQueryBuilderService the drQueryBuilderService to set
	 */
	public void setCrQueryBuilderService(
			ICRQueryBuilderService drQueryBuilderService) {
		this._crQueryBuilderService = drQueryBuilderService;
	}
	
	/**
	 * Injected though DI
	 * @param drReportXMLService the drReportXMLService to set
	 */
	public void setCrXMLService(ICRXMLService drReportXMLService) {
		this._crXMLService = drReportXMLService;
	}


	/**
	 *  Injected though DI
	 * @param reportTemplateService the reportTemplateService to set
	 */
	public void setCustomReportService(
			ICustomReportService reportTemplateService) {
		this._customReportService = reportTemplateService;
	}
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String editCustomReport() {
		String columnIds = null;
		String rowIds = null;
		String measureIds = null;
		String filtersJSON = null;
		String showRowSubTotalsFor = null;
		StringBuffer sb = new StringBuffer();
		try {
			if(!Utils.isBlankOrNull(reportModel.getReportId())){
				CustomReportDetails customReportDetails = _customReportService.getCustomReport(reportModel.getReportId());
				CRReportTypes crReportTypes = _customReportService.getCRReportTypes(customReportDetails.getReportTypeId());
				sb.append("new SelectOption('" + Utils.escapeJavaScript("-1") + "','" + Utils.escapeJavaScript(TPLabels.getLabel("common.selectlist.select")) + "')");
				getRequest().setAttribute("crReportTypesJSArray", sb.toString());				
				String xmlFilePath = CustomReportUtils.getCustomReportPath(customReportDetails.getXmlFilePath());
				CustomReport customReport = _crXMLService.getCustomReport(xmlFilePath);
				columnIds = CustomReportUtils.getFieldIdsLst(customReport.getColumns());
				rowIds = CustomReportUtils.getFieldIdsLst(customReport.getRows());
				measureIds = CustomReportUtils.getFieldIdsLst(customReport.getMeasures());
				filtersJSON = CRJSONUtils.converToFiltersJSON(customReport.getFilters().getFilter());
				showRowSubTotalsFor = CustomReportUtils.getFieldsToShowSubTotalsFor(customReport.getRows());
				reportModel.setColumnIds(columnIds);
				reportModel.setRowIds(rowIds);
				reportModel.setMeasureIds(measureIds);
				reportModel.setFiltersJSON(filtersJSON);
				reportModel.setShowRowSubTotalsFor(showRowSubTotalsFor);
				reportModel.setShowRowGrandTotal(customReport.isShowRowGrandTotal());
				reportModel.setReportTypeId(crReportTypes.getCrReportTypeId()+"");
				reportModel.setAddEditMode(CustomReportConstants.CR_EDIT);
				reportModel.setReportTypeName(crReportTypes.getCrReportTypeName());
				reportModel.setReportTypeKindId(crReportTypes.getCrReportTypeKindId());
				//getRequest().setAttribute("crReportTypes", crReportTypes);								
				populateCandNamesSpecifications(customReport);
				reportModel.setShowInactivePositions(customReport.isShowInactivePositions());
			}			
		} catch (Exception e) {
			addActionError(TPLabels.getLabel("custom_report.error.unable_to_edit"));
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	private void populateCandNamesSpecifications(CustomReport customReport){
		CandNamesSpecifications cns = customReport.getCandNamesSpecifications();
		if(cns!=null){
			reportModel.setShowCandidateNames(cns.isShowNames());
			reportModel.setExtraCandidateAttributes(cns.getExtraCandidateAttributes());
			reportModel.setStepsToShowNames(cns.getStepsToShowNames());
			reportModel.setStepsToShowNamesAndAttributes(cns.getStepsToShowNamesAndAttributes());			
		}else{
			reportModel.setShowCandidateNames(false);
		}
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String customizeReport() throws Exception {		
		try{
			crReportTypes=_customReportService.getCRReportTypes();
			String crReportTypesJSArray = CustomReportUtils.getJSArrayForCRReportTypes(crReportTypes);
			getRequest().setAttribute("crReportTypesJSArray", crReportTypesJSArray);						
			if(Utils.isBlankOrNull(reportModel.getAddEditMode())){				
				reportModel.setAddEditMode(CustomReportConstants.CR_ADD);
			}			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}		
		return SUCCESS;
	}
	
	public String addNewCustomReport() throws Exception {
		/*crReportTypes=_customReportService.getCRReportTypes();
		String crReportTypesJSArray = CustomReportUtils.getJSArrayForCRReportTypes(crReportTypes);
		getRequest().setAttribute("crReportTypesJSArray", crReportTypesJSArray);*/
		return SUCCESS;
	}
	
	public String saveReportCategory() throws Exception {
		return "reportColumns";	
	}
	
	public String getReportColumns() throws Exception{
		String xmlFile = "";
		List<CRColumn> columnsList = null;
		String reportTypeColProp = null;
		String 	reprtTypId = null;
		try {
			reprtTypId = reportModel.getReportTypeId();			
			if(!Utils.isBlankOrNull(reprtTypId)){
				reportTypeColProp = _customReportService.getCRReportTypeColumnMap(reprtTypId);
				columnsList = _customReportService.getActiveReportColumnsList(reportTypeColProp);
				xmlFile 	= CustomReportXMLUtils.getXMLforAvailableColumns(columnsList);				
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		getRequest().setAttribute("xmlFile", xmlFile);
		return SUCCESS;	
	}
	
	public String saveReportColumns() throws Exception {
		return "reportGrouping";	
	}
	
	public String saveReportGrouping() throws Exception {
		return "saveReport";	
	}
	
	public String candidateNamesSpecification(){
		String attributesJSArray = CustomReportUtils.getJSArrayForCandidateAttributes();
		getRequest().setAttribute("attributesJSArray", attributesJSArray);
		return SUCCESS;			
	}
	
	public String configureReportTotals() {
		try {
			String rowIds = reportModel.getRowIds();
			Map<String,CRColumn>  cRColumnMap = _customReportService.getReportColumnsMap();
			String rowsJSArray = CustomReportUtils.getJSArrayForFields(cRColumnMap, rowIds);
			getRequest().setAttribute("rowsJSArray", rowsJSArray);			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String selectFilters() throws Exception {
		crFilters = _customReportService.getCustomReportFilters(reportModel);
		return SUCCESS;
	}
	
	
	public String saveFilters()throws Exception {
		//Customized reports (Customer specific reports)
		ArrayList<CustomizedReportData> customizedReports = new CustomizedReportManager().getAllCustomizedReports();
		getRequest().setAttribute("customizedReports", customizedReports);
		getRequest().setAttribute("customizedReportsSize", customizedReports.size());
		return SUCCESS;
	}
	
	public String saveAsNewReport() {
		try {
			CustomReport customReport = _customReportService.createCustomReport(reportModel);
			Long reportId = _customReportService.saveCustomReport(reportModel, getUserId());
			String rootPath = CustomReportConstants.CUSTOM_REPORT_ABSOLUTE_FILE_PATH;
			String fileName = "CR_"+ reportId ;
			String folderPath = Utils.concatFilePath(rootPath,fileName);
			File folder = new File(folderPath);
			folder.mkdir();
			
			String xmlFilePath = Utils.concatFilePath(fileName, fileName+".xml");
			String templateFilePath = Utils.concatFilePath(fileName, fileName+".jrxml");
			String queryFilePath = Utils.concatFilePath(fileName, fileName+".sql");
			String inactivePositionsQueryFilePath = "";
			
			_crXMLService.saveReportXMLFile(customReport, rootPath+File.separator+xmlFilePath);
			_crTemplateService.saveReportTemplateFile(customReport, rootPath+File.separator+templateFilePath);
			try {
				DJCrossTabHelper.modifyCrossTabHeader(rootPath+File.separator+templateFilePath, customReport);
			} catch (Exception e) {
				System.out.println("Error while modifying the crosstab header cell");
			}
			_crQueryBuilderService.saveReportDataQuery(customReport,  rootPath+File.separator+queryFilePath);
			
			if(reportModel.isShowInactivePositions()) {
				inactivePositionsQueryFilePath = Utils.concatFilePath(fileName, fileName+"_inactive.sql");
				_crQueryBuilderService.saveInactiveReportDataQuery(customReport, rootPath+File.separator+inactivePositionsQueryFilePath);
			}
			
			_customReportService.saveReportDirectory(reportId, xmlFilePath,templateFilePath,queryFilePath,inactivePositionsQueryFilePath);
			
			getRequest().setAttribute("reportId", "reportId");
			//reportModel.setReportId(""+reportId);
		} catch (Exception e) {
			addActionError(TPLabels.getLabel("custom_report.error.error_save_new_report"));
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
			return SUCCESS;	
		}
	
	public String editReport() {
		try {
			String reportId = reportModel.getReportId();
			CustomReportDetails customReportDetails = _customReportService.getCustomReport(reportId);
			reportModel.setReportTitle(customReportDetails.getReportName());
			reportModel.setReportDesc(customReportDetails.getReportDesc());
			CustomReport customReport = _customReportService.createCustomReport(reportModel);
			String xmlFilePath 			= CustomReportUtils.getCustomReportPath(customReportDetails.getXmlFilePath());
			String templateFilePath 	= CustomReportUtils.getCustomReportPath(customReportDetails.getTemplateFilePath());
			String queryFilePath 		= CustomReportUtils.getCustomReportPath(customReportDetails.getQueryFilePath());
			String inactivePositionsQueryFilePath = "";
			
			_crXMLService.saveReportXMLFile(customReport, xmlFilePath);
			_crTemplateService.saveReportTemplateFile(customReport, templateFilePath);
			try {
				DJCrossTabHelper.modifyCrossTabHeader(templateFilePath, customReport);
			} catch (Exception e) {
				System.out.println("Error while modifying the crosstab header cell");
			}
			_crQueryBuilderService.saveReportDataQuery(customReport, queryFilePath);
			
			if(reportModel.isShowInactivePositions()) {
				if(Utils.isBlankOrNull(customReportDetails.getInactivePositionsQueryFilePath())) {
					inactivePositionsQueryFilePath = customReportDetails.getQueryFilePath().replace(".sql", "_inactive.sql");
					customReportDetails.setInactivePositionsQueryFilePath(inactivePositionsQueryFilePath);
					_customReportService.saveReportDirectory(Long.parseLong(reportId), customReportDetails.getXmlFilePath(), customReportDetails.getTemplateFilePath(), customReportDetails.getQueryFilePath(), inactivePositionsQueryFilePath);
				}
				inactivePositionsQueryFilePath = CustomReportUtils.getCustomReportPath(customReportDetails.getInactivePositionsQueryFilePath());
				_crQueryBuilderService.saveInactiveReportDataQuery(customReport, inactivePositionsQueryFilePath);													
			}	
			
			_customReportService.updateCustomReport(reportModel, getUserId());
		} catch (Exception e) {
			addActionError(TPLabels.getLabel("custom_report.error.error_customize"));
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;	
	}
	
	public String deleteCustomReport() {
		try {
			if(!Utils.isBlankOrNull(reportModel.getReportId())){
				long reportId = Long.decode(reportModel.getReportId());
				CustomReportDAO dao = new CustomReportDAO();
				dao.deleteCustomReport(reportId);	
				deleteCustomReportFiles(reportId);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return SUCCESS;	
	}
	
	public void deleteCustomReportFiles(long reportId) {
		String rootPath = CustomReportConstants.CUSTOM_REPORT_ABSOLUTE_FILE_PATH;
		String folderName = "CR_"+ reportId ;
		String folderPath = Utils.concatFilePath(rootPath,folderName);
		File folder = new File(folderPath);
		CustomReportUtils.deleteCustomReportFiles(folder);
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
	 * @return the reportModel
	 */
	public CustomReportModel getReportModel() {
		return reportModel;
	}

	/**
	 * @param reportModel the reportModel to set
	 */
	public void setReportModel(CustomReportModel reportModel) {
		this.reportModel = reportModel;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	@Override
	public CustomReportModel getModel() {
		return reportModel;
	}

	/**
	 * @return the crFilters
	 */
	public List<CRFilters> getCrFilters() {
		return crFilters;
	}

	/**
	 * @param crFilters the crFilters to set
	 */
	public void setCrFilters(List<CRFilters> crFilters) {
		this.crFilters = crFilters;
	}

}
