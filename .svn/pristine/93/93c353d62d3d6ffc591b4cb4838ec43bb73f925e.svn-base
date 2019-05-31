/**
 * 
 */
package com.talentPool.customReports.utils;

import static com.talentPool.common.CommonConstants.DEFAULT_DELIMITER;
import static com.talentPool.common.CommonConstants.NEW_ARRAY;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.crosstabs.JRCrosstabParameter;
import net.sf.jasperreports.crosstabs.base.JRBaseCrosstab;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseGroup;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.constants.CustomReportColumnConstants;
import com.talentPool.customReports.constants.CustomReportConstants;
import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRReportTypes;
import com.talentPool.customReports.dataobject.CustomReportModel;
import com.talentPool.customReports.djhelper.constants.DJHelperConstants;
import com.talentPool.customReports.jaxb.CandNamesSpecifications;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;
import com.talentPool.customReports.jaxb.Filter;
import com.talentPool.customReports.jaxb.Filters;
import com.talentPool.reports.ReportConstants;

/**
 * @author Ajeet
 *
 */
public class CustomReportUtils {

	private static final LinkedHashMap<String, String> reportTypeMap;
	
	static {
		reportTypeMap=new LinkedHashMap<String, String>();
		reportTypeMap.put(CustomReportConstants.CATEGORY_POSITION, TPLabels.getLabel("custom.report.category_position"));
		reportTypeMap.put(CustomReportConstants.CATEGORY_CANDIDATE, TPLabels.getLabel("custom.report.category_cadidate"));
		reportTypeMap.put(CustomReportConstants.CATEGORY_ACTIVITY, TPLabels.getLabel("custom.report.category_activity"));
		reportTypeMap.put(CustomReportConstants.CATEGORY_PROCESS_STATUS, TPLabels.getLabel("custom.report.category_process_status"));
		reportTypeMap.put(CustomReportConstants.CATEGORY_USER, TPLabels.getLabel("custom.report.category_user"));
	}
	
	/**
	 * Builds JSArray with candidate attributes which can be configured in report designer to display in summary reports 
	 * @return JSArray of Candidate attributes
	 * if any exception {@link CommonConstants}.NEW_ARRAY 
	 */
	public static String getJSArrayForCandidateAttributes(){
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[");
			Utils.getJSArraySelectOption(CustomReportColumnConstants.SOURCE, TPLabels.getLabel("common.source"), sb);
			Utils.getJSArraySelectOption(CustomReportColumnConstants.APPLICANT_CURRENT_STEP_NAME, TPLabels.getLabel("common.step"), sb.append(DEFAULT_DELIMITER));
			Utils.getJSArraySelectOption(CustomReportColumnConstants.STATUS_MESSAGE, TPLabels.getLabel("custom_report_column.label.applicant_status_message"), sb.append(DEFAULT_DELIMITER));
			Utils.getJSArraySelectOption(CustomReportColumnConstants.APPLICANT_DATE_JOINED, TPLabels.getLabel("custom_report_column.label.joining_date"), sb.append(DEFAULT_DELIMITER));
			sb.append("]");
		} catch (Exception e) {
			sb.append(NEW_ARRAY);
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return sb.toString();
	}
	
	public static String getJSArrayForFields(Map<String, CRColumn> crColumns, String rowIds){
		StringBuffer sb = new StringBuffer();
		try {
			boolean first = true;
			sb.append("[");
			for (String rowId : rowIds.split(DEFAULT_DELIMITER)){
				if(first){
					Utils.getJSArraySelectOption(rowId, crColumns.get(rowId).getColumnDisplayName(), sb);
					first = false;
				}else{
					Utils.getJSArraySelectOption(rowId, crColumns.get(rowId).getColumnDisplayName(), sb.append(DEFAULT_DELIMITER));
				}
			}
			sb.append("]");
		} catch (Exception e) {
			sb.append(NEW_ARRAY);
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return sb.toString();
	}

	public static String getCategoryLabel(String category){
		return reportTypeMap.get(category);	
	}
	
	public static HashSet<String> getColumnTypeSet(List<Field> columns,HashSet<String> mySet){
		CollectionUtils.collect(columns, new Transformer() {
			@Override
			public Object transform(Object input) {
				return ((Field)input).getTableType();
			}
		},mySet);
		return mySet;
	}
	
	public static HashSet<Integer> getTableTypeSet(List<Field> columns,HashSet<Integer> mySet){
		CollectionUtils.collect(columns, new Transformer() {
			@Override
			public Object transform(Object input) {
				return ((Field)input).getCategory();
			}
		},mySet);
		return mySet;
	}
	
	public static HashSet<String> getColumnTypeSet(CustomReport report){
		HashSet<String> mySet = new HashSet<String>();
		if(report.getColumns()!=null){
			getColumnTypeSet(report.getColumns(), mySet);	
		}
		if(report.getRows()!=null){
			getColumnTypeSet(report.getRows(), mySet);	
		}
		if(report.getMeasures()!=null){
			getColumnTypeSet(report.getMeasures(), mySet);	
		}
		return mySet;
	}
	
	public static String getFieldIdsLst(List<Field> columns){
		StringBuilder fieldsLst = null;
		boolean seperator = false;
		if(!Utils.isListEmptyOrNull(columns)){
			fieldsLst = new StringBuilder();
			for (Field column : columns) {
				if(seperator) {
					fieldsLst.append(CommonConstants.DEFAULT_DELIMITER).append(replaceProcessUserColumns(column.getKey()));					
				} else {
					fieldsLst.append(replaceProcessUserColumns(column.getKey()));
					seperator = true;
				}
			}
			return fieldsLst.toString();
		}
		return "";
	}
	
	private static String replaceProcessUserColumns(String value){
		if(CustomReportColumnConstants.getProcessUserColumnsMap().containsValue(value))
			return (String) CustomReportColumnConstants.getProcessUserColumnsMap().getKey(value);
		else
			return value;
	}
	
	public static HashSet<String> getColumnTypeSetFromCRColumn(List<CRColumn> columns){
		HashSet<String> mySet = new HashSet<String>();
		CollectionUtils.collect(columns, new Transformer() {
			@Override
			public Object transform(Object input) {
				return ((CRColumn)input).getColumnCategory();
			}
		},mySet);
		return mySet;
	}
	
	public static String getCustomReportPath(String path){
		return CustomReportConstants.CUSTOM_REPORT_ABSOLUTE_FILE_PATH+File.separator+path;
	}
	
	public static void deleteCustomReportFiles(File path) {
		if(path.isDirectory()) {
			for(File file:path.listFiles()) {
				deleteCustomReportFiles(file);
			}
		}
		if(path!=null)
			path.delete();
	}
	
	/**
	 * Checks if any Crosstabs present in jasperReprt, any identified crosstabs are returned as list.
	 * @param jasperReport
	 * @return {@link JRBaseCrosstab} list if crossTabs present
	 * <Br> Null or empty list if no crossTabs present in JasperReport
	 */
	public static List<JRBaseCrosstab> isReportHasCrossTabs(final JasperReport jasperReport){
		List<JRBaseCrosstab> jsCrossTabLst = null;
		if(jasperReport.getGroups()!=null){
			jsCrossTabLst = new ArrayList<>();
			for (JRGroup jgroup : jasperReport.getGroups()) {
				if(jgroup instanceof JRBaseGroup){
					JRBand band = jgroup.getGroupHeaderSection().getBands()[0];
					JRElement[] jrElements = band.getElements();
					for (JRElement jrElement : jrElements) {
						if(jrElement instanceof JRBaseCrosstab){
							jsCrossTabLst.add((JRBaseCrosstab)jrElement);
						}
					}					
				}
			}	
		}
		return jsCrossTabLst;
	}
	
	public static String getFilterIdsLst(List<Filter> filters){
		StringBuilder fieldsLst = null;
		boolean seperator = false;
		if(!Utils.isListEmptyOrNull(filters)){
			fieldsLst = new StringBuilder();
			for (Filter filter : filters) {
				if(seperator) {
					fieldsLst.append(CommonConstants.DEFAULT_DELIMITER).append(filter.getFilterId());					
				} else {
					fieldsLst.append(filter.getFilterId());
					seperator = true;
				}
			}
			return fieldsLst.toString();
		}
		return "";
	}

	public static Date getReportsAsOfDate(String dateRange){
		Date toDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		try{
			if(dateRange.equals(ReportConstants.YESTERDAY)){
				toDate = Utils.adjustDateBy(toDate, Calendar.DATE, -1);
			} else if(dateRange.equals(ReportConstants.END_OF_LAST_WEEK)){
				toDate=Utils.adjustDateBy(toDate, Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK));					
			} else if(dateRange.equals(ReportConstants.END_OF_LAST_MONTH)){
				toDate=Utils.adjustDateBy(toDate, Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH));
			} else if(dateRange.equals(ReportConstants.END_OF_LAST_QUARTER)){
				toDate=DateUtils.getCurrentQuarterStartDate();
				toDate=Utils.adjustDateBy(toDate, Calendar.DATE, -1);
			} else if(dateRange.equals(ReportConstants.END_OF_LAST_CALENDAR_YEAR)){
				toDate=Utils.adjustDateBy(toDate,Calendar.DATE, -cal.get(Calendar.DAY_OF_YEAR));
			} else if(dateRange.equals(ReportConstants.END_OF_LAST_FINANCIAL_YEAR)){
				toDate=DateUtils.getCurrentFinancialStartDate();
				toDate=Utils.adjustDateBy(toDate, Calendar.DATE, -1);
			}
		} catch (Exception e) {			
			TPLogger.getLogger().error("Error while getting As of Date to-date range", e);
		}
		return toDate;
	}	
	

	public static String getReportsAsOfDateAsString(String dateRange) {
		return Utils.getDateConvertedToString(getReportsAsOfDate(dateRange), DateConstants.DB_DATE_TIME_PATTERN);

	}
	
	public static Date[] getFromAndToDateFromDateRange(String dateRange) throws Exception {
		Date toDate = null;
		Date fromDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		
		Date[] fromAndToDates = new Date[2];
	
		if(dateRange.equals(ReportConstants.TODAY)) {
			toDate=new Date();
			fromDate=new Date();
		} else if(dateRange.equals(ReportConstants.YESTERDAY)) {
			toDate = Utils.adjustDateBy(new Date(), Calendar.DAY_OF_MONTH, -1);
			fromDate=Utils.adjustDateBy(new Date(), Calendar.DAY_OF_MONTH, -1);
		} else if(dateRange.equals(ReportConstants.CURRENT_WEEK)) {
			toDate=new Date();
			fromDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK)+1);
		} else if(dateRange.equals(ReportConstants.PREVIOUS_WEEK)) {
			toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK));
			fromDate=Utils.adjustDateBy(toDate,Calendar.DATE,-Calendar.DAY_OF_WEEK+1);
		} else if(dateRange.equals(ReportConstants.CURRENT_MONTH)) {
			toDate=new Date();				
			fromDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH)+1);
		} else if(dateRange.equals(ReportConstants.PREVIOUS_MONTH)) {
			toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH));
			cal.setTime(toDate);
			fromDate=Utils.adjustDateBy(toDate,Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH)+1);
		} else if(dateRange.equals(ReportConstants.CURRENT_QUARTER)) {
			toDate=new Date();
			fromDate=DateUtils.getCurrentQuarterStartDate();
		} else if(dateRange.equals(ReportConstants.PREVIOUS_QUARTER)) {
			toDate=DateUtils.getCurrentQuarterStartDate();
			toDate=Utils.adjustDateBy(toDate, Calendar.DATE, -1);
			fromDate=DateUtils.getCurrentQuarterStartDate();
			fromDate=Utils.adjustDateBy(fromDate, Calendar.MONTH, -3);
		} else if(dateRange.equals(ReportConstants.CURRENT_CALENDAR_YEAR)) {
			toDate=new Date();
			fromDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_YEAR)+1);
		} else if(dateRange.equals(ReportConstants.PREVIOUS_CALENDAR_YEAR)) {
			toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_YEAR));
			fromDate=Utils.adjustDateBy(toDate,Calendar.YEAR, -1);
			fromDate=Utils.adjustDateBy(fromDate, Calendar.DATE, 1);
		} else if(dateRange.equals(ReportConstants.CURRENT_FINANCIAL_YEAR)) {
			toDate=new Date();
			fromDate=DateUtils.getCurrentFinancialStartDate();
		} else if(dateRange.equals(ReportConstants.PREVIOUS_FINANCIAL_YEAR)) {
			toDate=DateUtils.getCurrentFinancialStartDate();
			toDate=Utils.adjustDateBy(toDate, Calendar.DATE, -1);
			fromDate=DateUtils.getCurrentFinancialStartDate();
			fromDate=Utils.adjustDateBy(fromDate,Calendar.YEAR, -1);
		}	
		
		fromAndToDates[0]=fromDate;
		fromAndToDates[1]=toDate;
		return fromAndToDates;
	}
	
	public static String[] getFromAndToDateFromDateRangeAsString(String dateRange) throws Exception {
		Date[] fromAndToDates = getFromAndToDateFromDateRange(dateRange);
		String[] fromAndToDatesAsString = new String[2];
		fromAndToDatesAsString[0] = Utils.getDateConvertedToString(fromAndToDates[0], DateConstants.DB_DATE_TIME_PATTERN);
		fromAndToDatesAsString[1] = Utils.getDateConvertedToString(fromAndToDates[1], DateConstants.DB_DATE_TIME_PATTERN);
		return fromAndToDatesAsString;
	}
	
	public static List<Filter> getFiltersOnVisibility(Filters filters, boolean visibility){
		List<Filter> newFilters = new ArrayList<Filter>();
		if(!Utils.isListEmptyOrNull(filters.getFilter())){
			for (Filter filter : filters.getFilter()) {
				if(filter.isVisibility()==visibility) {
					newFilters.add(filter);
				} 
			}
		}
		return newFilters;
	}
	
	public static boolean showCandidateNamesInReport(CustomReport customReport){
		if(customReport!=null){
			CandNamesSpecifications candNamesSpecifications = customReport.getCandNamesSpecifications();
			return candNamesSpecifications!=null && candNamesSpecifications.isShowNames() &&
					( !Utils.isBlankOrNull(candNamesSpecifications.getStepsToShowNames())
						|| !Utils.isBlankOrNull(candNamesSpecifications.getStepsToShowNamesAndAttributes())	
					);
		}
		return false;
	}
	
	public static boolean showCandidateNamesInReport(CustomReportModel reportModel){
		if(reportModel!=null){
			return reportModel!=null && reportModel.isShowCandidateNames() && 
					( !Utils.isBlankOrNull(reportModel.getStepsToShowNames())
						|| !Utils.isBlankOrNull(reportModel.getStepsToShowNamesAndAttributes())	
					);
		}
		return false;
	}
	
	/**
	 * @param reportModel
	 * @return whether the position permission check is required or not 
	 */
	public static boolean isPositionPermissionCheckRequired(String reportType){
		if(!Utils.isBlankOrNull(reportType) && "3".equals(reportType)) {
			return false;
		}
		return true;
	}

	public static String getJSArrayForCRReportTypes(List<CRReportTypes> crReportTypes){				
		StringBuffer sb = new StringBuffer();
		try{						
			Iterator<CRReportTypes> itr=crReportTypes.iterator();
			sb.append("[");
			sb.append("new SelectOption('" + Utils.escapeJavaScript("-1") + "','" + Utils.escapeJavaScript(TPLabels.getLabel("common.selectlist.select")) + "'),");
			while(itr.hasNext()){
				CRReportTypes crrt=itr.next();
				String crReportTypeid=crrt.getCrReportTypeId()+"";
				String crReportTypeName=crrt.getCrReportTypeName();
				String crReportTypeKind=crrt.getCrReportTypeKindId();
				String reportTypeIdKindId=crReportTypeid+"|"+crReportTypeKind;
				sb.append("new SelectOption('" + Utils.escapeJavaScript(reportTypeIdKindId) + "','" + Utils.escapeJavaScript(crReportTypeName) + "')");
				if(itr.hasNext()){
					sb.append(",");
				}
			}		
			sb.append("]");			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
			sb = new StringBuffer(NEW_ARRAY);
		}		
		return sb.toString();		
	}	
	
	public static String getFieldsToShowSubTotalsFor(List<Field> fieldLst){
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Field field : fieldLst) {
			if(field.isShowSubTotal()){
				if(first){
					sb.append(field.getKey());
					first = false;
				}else{
					sb.append(DEFAULT_DELIMITER).append(field.getKey());
				}
								
			}
		}
		return sb.toString();
	}
	
	/**
	 * Create new instance for valueformatters , totalproviders and custom expressions and add to the jasperRunTimeParams
	 * which then will be injected into JasperReport while filling the report.
	 * @param crossTabLst
	 * @param jasperRunTimeParams
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addCrossTabParams(final List<JRBaseCrosstab> crossTabLst, final Map jasperRunTimeParams){
		for (JRBaseCrosstab crosstab : crossTabLst) {
			for (JRCrosstabParameter jrCrosstabParameter : crosstab.getParameters()) {
				if(jrCrosstabParameter.getName().indexOf(DJHelperConstants.TOTAL_PROVIDER_PARAM_NAME_SUFFIX)!=-1
						|| jrCrosstabParameter.getName().indexOf(DJHelperConstants.VALUE_FORMATTER_PARAM_NAME_SUFFIX)!=-1){
					try {
						jasperRunTimeParams.put(jrCrosstabParameter.getName(), jrCrosstabParameter.getValueClass().newInstance());
					} catch (InstantiationException | IllegalAccessException e) {
						TPLogger.getLogger().error("Could not create object for total provider parameter-"+jrCrosstabParameter.getName(), e);
					}
				}
			}
		}
	}
}