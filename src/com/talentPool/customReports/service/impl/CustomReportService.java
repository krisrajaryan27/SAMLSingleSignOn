/**
 * 
 */
package com.talentPool.customReports.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.constants.CustomReportColumnConstants;
import com.talentPool.customReports.dao.ICustomReportDAO;
import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRFilters;
import com.talentPool.customReports.dataobject.CRReportTypeColumnMapping;
import com.talentPool.customReports.dataobject.CRReportTypes;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.dataobject.CustomReportDetails;
import com.talentPool.customReports.dataobject.CustomReportModel;
import com.talentPool.customReports.jaxb.CandNamesSpecifications;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;
import com.talentPool.customReports.jaxb.Filter;
import com.talentPool.customReports.jaxb.Filters;
import com.talentPool.customReports.jaxb.ObjectFactory;
import com.talentPool.customReports.jaxb.ReportFormatType;
import com.talentPool.customReports.jaxb.TpReportTemplate;
import com.talentPool.customReports.jaxb.TpReportType;
import com.talentPool.customReports.jaxb.util.XMLGregorianCalendarConverter;
import com.talentPool.customReports.service.ICustomReportService;
import com.talentPool.customReports.utils.CRJSONUtils;
import com.talentPool.customReports.utils.CustomReportUtils;

/**
 * @author PraveenK
 * @since  Nov 8, 2011
 */
public class CustomReportService implements ICustomReportService {
	private ICustomReportDAO _customReportDAO;

	/**
	 * @param reportTemplateDAO the reportTemplateDAO to set
	 */
	public void setCustomReportDAO(ICustomReportDAO reportTemplateDAO) {
		this._customReportDAO = reportTemplateDAO;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.poolReports.service.IReportTemplateService#getReportColumns(java.lang.String)
	 */
	@Override
	public Map<String,CRColumn> getReportColumnsMap() {
		Map<String,CRColumn> columnsMap = null;
			List<CRColumn> columnsList = null;
			try {
				columnsList = _customReportDAO.getReportColumnsList();
				if(columnsList!=null){
					columnsMap = new HashMap<String, CRColumn>();
					for (CRColumn crColumn : columnsList) {
						columnsMap.put(crColumn.getColumnProperty(), crColumn);
					}
				}
			} catch (HibernateException e) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				return null;
			}
		return columnsMap;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.ICustomReportService#getReportColumnsList()
	 */
	@Override
	public List<CRColumn> getReportColumnsList() {
		return _customReportDAO.getReportColumnsList();
	}
	
	@Override
	public List<CRColumn> getActiveReportColumnsList() {
		return _customReportDAO.getActiveReportColumnsList();
	}
	
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.ICustomReportService#getReportColumns(java.lang.String)
	 */
	@Override
	public List<CRColumn> getReportColumns(String columnIds) {
		List<CRColumn> columnsList = null;
		if(!Utils.isBlankOrNull(columnIds)){
			try {
				columnsList = _customReportDAO.getReportColumns(columnIds);
			} catch (HibernateException e) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				return null;
			}
		}
		return columnsList;
	}
	
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.ICustomReportService#getCRTableTypeMap()
	 */
	@Override
	public Map<Long, CRTableType> getCRTableTypeMap() {
		List<CRTableType> crTableTypeLst = _customReportDAO.getTableTypeList();
		Map<Long, CRTableType> crTableTypeMap = null;
		if(crTableTypeLst!=null){
			crTableTypeMap = new HashMap<Long, CRTableType>();
			for (CRTableType crTableType : crTableTypeLst) {
				crTableTypeMap.put(crTableType.getTableTypId(), crTableType);
			}			
		}
		return crTableTypeMap;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.ICustomReportService#getCustomReport(java.lang.String)
	 */
	@Override
	public CustomReportDetails getCustomReport(String reportId) {
		return _customReportDAO.getCustomReport(reportId);
	}
	
	public CustomReport createCustomReport(CustomReportModel reportModel){
		ObjectFactory of = new ObjectFactory();
		CustomReport customReport = of.createCustomReport();
		TpReportTemplate tem = of.createTpReportTemplate();
		TpReportType type = of.createTpReportType();
		type.getType().add("Summary");
		type.getType().add("Detail");
		
		String[] columns 	= null;
		String[] rows 		= null;
		String[] measures 	= null;
		
		if(!Utils.isBlankOrNull(reportModel.getColumnIds())){
			columns = reportModel.getColumnIds().split(CommonConstants.DEFAULT_DELIMITER);
		}
		
		if(!Utils.isBlankOrNull(reportModel.getRowIds())){
			rows = reportModel.getRowIds().split(CommonConstants.DEFAULT_DELIMITER);
		}
		
		if(!Utils.isBlankOrNull(reportModel.getMeasureIds())){
			measures = reportModel.getMeasureIds().split(CommonConstants.DEFAULT_DELIMITER);
		}
		
		checkForProcessUser(columns, rows, measures);
		
		Map<String,CRColumn> crColumns =  getReportColumnsMap();
		
		if(columns!=null){
			for (String columnId : columns) {
				CRColumn crColumn = crColumns.get(columnId);
				Field column = createField(crColumn, of);
				customReport.getColumns().add(column);
			}
		}
		
		if(rows!=null){
			for (String rowId : rows) {
				CRColumn crColumn = crColumns.get(rowId);
				Field row = createField(crColumn, of);
				customReport.getRows().add(row);
			}
		}
		
		if(measures!=null){
			for (String measureId : measures) {
				CRColumn crColumn = crColumns.get(measureId);
				Field measure = createField(crColumn, of);
				if(CustomReportUtils.showCandidateNamesInReport(reportModel)){
					measure.setDataType(String.class.getName());
				}
				customReport.getMeasures().add(measure);
			}
		}
		
		setSubtotals(customReport.getRows(), reportModel.getShowRowSubTotalsFor());
		customReport.setShowRowGrandTotal(reportModel.isShowRowGrandTotal());
		customReport.setShowInactivePositions(reportModel.isShowInactivePositions());
		
		ReportFormatType rft = of.createReportFormatType();
		rft.getFormat().add("Html");
		rft.getFormat().add("Pdf");
		rft.getFormat().add("Excel");
		
		customReport.setFilters(buildFilters(of.createFilters(), reportModel.getFiltersJSON()));
		
		if(CustomReportUtils.showCandidateNamesInReport(reportModel)){
			CandNamesSpecifications cns =  of.createCandNamesSpecifications();
			cns.setShowNames(true);
			cns.setStepsToShowNames(reportModel.getStepsToShowNames());
			cns.setStepsToShowNamesAndAttributes(reportModel.getStepsToShowNamesAndAttributes());
			cns.setExtraCandidateAttributes(reportModel.getExtraCandidateAttributes());
			customReport.setCandNamesSpecifications(cns);
		}

		customReport.setReportName(reportModel.getReportTitle());
		customReport.setType("Detail");
		customReport.setDescription("Description");
		customReport.setReportTemplate(tem);
		
		customReport.setReportFormat("Html");
		
		customReport.setReportId("1");
		customReport.setShare("false");
		if(Utils.isBlankOrNull(reportModel.getReportId()))
			customReport.setCreationTime(XMLGregorianCalendarConverter.asXMLGregorianCalendar(new Date()));
		customReport.setLastModifiedTime(XMLGregorianCalendarConverter.asXMLGregorianCalendar(new Date()));
		return customReport;
	}
	
	private void checkForProcessUser(String[] columns, String[] rows, String[] measures){
		if(hasProcessUserField(columns, rows, measures)){
			@SuppressWarnings("unchecked")
			Map<String,String> processUserColumnsMap = CustomReportColumnConstants.getProcessUserColumnsMap();
			if(columns!=null){
				for (int i = 0; i < columns.length; i++) {
					if(processUserColumnsMap.containsKey(columns[i])){
						columns[i] = processUserColumnsMap.get(columns[i]);
					}
				}
			}
			if(rows!=null){
				for (int i = 0; i < rows.length; i++) {
					if(processUserColumnsMap.containsKey(rows[i])){
						rows[i] = processUserColumnsMap.get(rows[i]);
					}
				}
			}
			if(measures!=null){
				for (int i = 0; i < measures.length; i++) {
					if(processUserColumnsMap.containsKey(measures[i])){
						measures[i] = processUserColumnsMap.get(measures[i]);
					}
				}
			}
		}
	}
	
	private boolean hasProcessUserField(String[] columns, String[] rows, String[] measures){
		if(columns!=null){
			for (String column : columns) {
				if(CustomReportColumnConstants.PROCESS_USER.equals(column))
					return true;
			}
		}
		if(rows!=null){
			for (String row : rows) {
				if(CustomReportColumnConstants.PROCESS_USER.equals(row))
					return true;
			}
		}
		if(measures!=null){
			for (String measure : measures) {
				if(CustomReportColumnConstants.PROCESS_USER.equals(measure))
					return true;
			}
		}
		return false;
	}
	
	private Field createField(CRColumn crColumn, final ObjectFactory of){
		Field field = of.createField();
		field.setKey(crColumn.getColumnProperty());
		field.setCategory(crColumn.getColumnCategory());
		field.setTableType(""+crColumn.getCRTableType().getTableTypId());
		field.setDisplayName(crColumn.getColumnDisplayName());
		field.setDbName(crColumn.getColumnDbname());
		field.setWitdh(crColumn.getColumnWidth());
		if(Date.class.getName().equals(crColumn.getColumnDataType())){
			field.setDataType(String.class.getName());	
		}else {
			field.setDataType(crColumn.getColumnDataType());
		}
		
		field.setFieldValueType(crColumn.getValueType());
		field.setCellContent("simplecount");
		field.setCellContentFormat("inline");
		return field;
	}
	
	/**
	 * Converts filtersJSON to <code>{@link List}<{@link Filter}></code> and adds to filters 
	 * @param filters
	 * @param filtersJSON
	 * @return filters built
	 */
	private Filters buildFilters(Filters filters, String filtersJSON) {
		if(!Utils.isBlankOrNull(filtersJSON)){
			List<Filter> filtersList =  CRJSONUtils.converToFilterList(filtersJSON);
			filters.getFilter().addAll(filtersList);				
		}
		return filters;
	}
	
	
	/**
	 * Set the flag whether to show sub totals for a row field
	 * @param rows
	 * @param showRowSubTotals
	 */
	private void setSubtotals(final List<Field> fields, String showSubTotalsFieldIds){
		if(!Utils.isBlankOrNull(showSubTotalsFieldIds)){
			for (String fieldId : showSubTotalsFieldIds.split(CommonConstants.DEFAULT_DELIMITER)) {
				for (Field field : fields) {
					if(fieldId.equals(field.getKey())){
						field.setShowSubTotal(true);
						break;
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.ICustomReportService#saveCustomReport(com.talentPool.customReports.dataobject.CustomReportModel)
	 */
	@Override
	public Long saveCustomReport(CustomReportModel reportModel, String createdBy) {
		Long reportId = null;
		try {
			CustomReportDetails crd = new CustomReportDetails();
			crd.setReportName(reportModel.getReportTitle());
			crd.setReportDesc(reportModel.getReportDesc());
			crd.setCreatedBy(createdBy);
			crd.setModifiedBy(createdBy);
			crd.setDateCreated(new Timestamp(new Date().getTime()));
			crd.setDateModified(new Timestamp(new Date().getTime()));
			crd.setReportTypeId(reportModel.getReportTypeId());
			reportId = _customReportDAO.saveCustomReport(crd);
			
			saveCustomReportLevels(reportId, reportModel);
		} catch (HibernateException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return reportId;
		}
		return reportId;
	}
	
	@Override
	public void updateCustomReport(CustomReportModel reportModel, String modifiedBy) {
		try {
			_customReportDAO.updateCustomReport(Long.parseLong(reportModel.getReportId()),reportModel.getReportTitle(),reportModel.getReportDesc(),modifiedBy);			
		} catch (HibernateException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.ICustomReportService#saveReporthDirectory(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveReportDirectory(Long reportId, String xmlFilePath, String templateFilePath, String queryFilePath, String inactivePositionsQueryFilePath) {
		CustomReportDetails crd = new CustomReportDetails();
		crd.setXmlFilePath(xmlFilePath);
		crd.setReportId(reportId);
		_customReportDAO.updateCRFilePath(reportId, xmlFilePath, templateFilePath, queryFilePath, inactivePositionsQueryFilePath);
	}
	
	@Override
	public List<CRFilters> getCustomReportFilters(CustomReportModel reportModel) {
		List<CRFilters> filtersList = null;		
		try {
			filtersList = _customReportDAO.getCustomReportFilters(reportModel);
		} catch (HibernateException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return null;
		}		
		return filtersList;
	}
	
	public void saveCustomReportLevels(Long reportId, CustomReportModel reportModel) {
		String levelIds = reportModel.getReportLevelIds();
		if (Utils.isBlankOrNull(levelIds)) {
			TPLogger.getLogger().error("no report levels to save");
			return;
		}
			
		AdminManager adminManager = new AdminManager();
		try {
			adminManager.addCustomReportToLevels(reportId, levelIds);
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	@Override
	public CRReportTypes getCRReportTypes(String reportTypeId){
		return _customReportDAO.getCRReportTypes(reportTypeId);
	}
	
	@Override
	public List<CRReportTypes> getCRReportTypes() {
		return _customReportDAO.getCRReportTypes();		
	}
	
	public String getCRReportTypeColumnMap(String reportTypeId){		
		StringBuffer sb = new StringBuffer();		
		List<CRReportTypeColumnMapping> reportTypeColList= _customReportDAO.getCRReportTypeColumnMap(reportTypeId);
		Iterator<CRReportTypeColumnMapping> itr = reportTypeColList.iterator();
		while(itr.hasNext()){
			CRReportTypeColumnMapping crtcm = itr.next();
			String propertyName = crtcm.getCustomReportColumns().getColumnProperty();
			sb.append(propertyName);
			if(itr.hasNext()){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	@Override
	public List<CRColumn> getActiveReportColumnsList(String reportTypeColProp) {
		return _customReportDAO.getReportColumnsList(true,reportTypeColProp);
	}	
}