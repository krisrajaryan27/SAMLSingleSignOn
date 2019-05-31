/**
 * 
 */
package com.talentPool.dynamicReports.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;

import org.apache.commons.lang.StringUtils;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.custom.utils.CustomFieldUtils;
import com.talentPool.dynamicReports.data.DatewiseHiringReportData;
import com.talentPool.dynamicReports.data.DynamicReportsDataSource;
import com.talentPool.dynamicReports.utils.DJUtils;
import com.talentPool.dynamicReports.utils.DynamicReportsColumnUtils;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author shantanu
 *	 
 */
public class DatewiseHiringReport extends DynamicReportGenerator{
	private List<DatewiseHiringReportData> datewiseHiringReportData = null;
	private String fieldsToShow = null;
	private String filterCriteria = null;

	
	public DatewiseHiringReport(HttpServletRequest request,String filePathname, String exportTo){
		super(request, filePathname, exportTo);
	}	
	
	public DatewiseHiringReport(HttpServletRequest request,
			String filePathname, String exportTo,List<DatewiseHiringReportData> datewiseHiringReportData,
			String fieldsToShow,String filterCriteria) {
		super(request, filePathname, exportTo);
		this.datewiseHiringReportData = datewiseHiringReportData;
		this.fieldsToShow=fieldsToShow;
		this.filterCriteria=filterCriteria;
	}
	
	public DynamicReport buildReport() throws Exception{
		FastReportBuilder drb = new FastReportBuilder();
		PermissionSet permissioSet=null;
		String fieldsToShow	= "";
		try{
			Map<String,String> madFieldsMap =  DynamicReportsColumnUtils.getMandatoryFields(permissioSet,ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT,null);
			String mandFields = StringUtils.join(madFieldsMap.keySet(), CommonConstants.DEFAULT_DELIMITER);
			//String fieldsToShow	= Utils.getBlankIfNull(mandFields)+CommonConstants.DEFAULT_DELIMITER+Utils.getBlankIfNull(this.fieldsToShow);
			if(Utils.isBlankOrNull(this.fieldsToShow)){
				fieldsToShow = Utils.getBlankIfNull(mandFields);
			}else{
				//fieldsToShow = Utils.getBlankIfNull(this.fieldsToShow)+CommonConstants.DEFAULT_DELIMITER+Utils.getBlankIfNull(mandFields);
				fieldsToShow = Utils.getBlankIfNull(mandFields)+CommonConstants.DEFAULT_DELIMITER+Utils.getBlankIfNull(this.fieldsToShow);
			}
			addColumns(fieldsToShow,drb);
			setReportDefaultProperties(drb, ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT), this.filterCriteria);
			drb.addField("positionId", String.class.getName());			
			//if(!ReportConstants.FORMAT_EXCEL.equalsIgnoreCase(exportTo)){
				addGroups(drb,mandFields,fieldsToShow);
			//}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		
		return drb.build();

	}
	public JRDataSource getDataSource(){
		JRDataSource jd = new DynamicReportsDataSource(getDataSourceCollection());
		return jd;
	}
	
	
	private List<DatewiseHiringReportData> getDataSourceCollection() {
		DatewiseHiringReportData spsd = null;
		try {
			if (datewiseHiringReportData != null) {
				Iterator<DatewiseHiringReportData> itr = datewiseHiringReportData.iterator();
				while (itr.hasNext()) {
					spsd = itr.next();
					if(!Utils.isBlankOrNull(spsd.getPosCustomFieldValues()))
						CustomFieldUtils.generateCustomFieldMap(spsd.getAttributes(), spsd.getPosCustomFieldValues(), "\\|\\|");
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().debug("method exit - getDataSourceCollection for Employee Details Report");
		}
		return datewiseHiringReportData;
	}
	
	private void addColumns(String fieldsToShow,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
		String[] fieldsList = fieldsToShow.split(",");
		for (int i = 0; i < fieldsList.length; i++) {
			addColumn(fieldsList[i],drb);
		}
	}
	
	private void addColumn(String key,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
		int minColWidth = 100;
		int maxColWidth = 200;
		int mediumColWidth = 150;
		
		if(ReportDesignConstants.COLUMN_POSITION_TYPE_EXT_INT.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionTypeExtInt", String.class.getName(),maxColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_CODE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionCode", String.class.getName(),maxColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_TITLE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionTitle", String.class.getName(),maxColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionDateExpiry", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_REQUESTED_BY.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionRequestedBy", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_BUDGET_BAND.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "bandName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_BUDGET_GRADE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "gradeName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_DEPARTMENT.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "deptName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "subDeptName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB_SUB.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "subSubDeptName", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_POSITION_LEVEL.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionLevel", String.class.getName(),maxColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_VACANCY_TYPE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "typeOfVacancy", String.class.getName(),maxColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_REPLACEMENT_EMP_CODE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "replacementEmpCode", String.class.getName(),maxColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_STATUS.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionStatusValue", String.class.getName(),maxColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_PRIMARY_SKILLS.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionPrimarySkills", String.class.getName(),maxColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_SECONDARY_SKILLS.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionSecondarySkills", String.class.getName(),maxColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_LOCATION.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "locationName", String.class.getName(),maxColWidth);
		}		
		else if(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_STEP_NAME.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "processStepName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_STEP_DATE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "processStepDate", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_STAGE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "processStage", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_PROCESSED_BY.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "processedByName", String.class.getName(),maxColWidth);
		}
		else if(ReportDesignConstants.COLUMN_CANDIDATE_NAME.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_CANDIDATE_DATE_JOINED.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantDateJoined", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_CANDIDATE_LEVEL_OFFERED.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantLevelOffered", String.class.getName(),minColWidth);
		}		
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				if(!Utils.isBlankOrNull(cData.getFieldId()) && cData.getFieldId().endsWith(key)){
					drb.addColumn(cData.getFieldDisplayName(),"CUS_"+cData.getFieldId(), String.class.getName(),minColWidth);
				}
			}
		}	
		
				
	}
	private void addGroups(FastReportBuilder drb,String mandFields,String fieldsToShow){
		//String[] mandatoryFields=mandFields.split(",");		
		//String[] selectedFields=fieldsToShow.split(",");		
		//int startIndex =selectedFields.length-mandatoryFields.length;
		//DJUtils.addGroups(startIndex,startIndex+2, drb, GroupLayout.DEFAULT, null);
		//DJUtils.addGroup(0, drb, GroupLayout.DEFAULT, null);
		DJUtils.addGroups(0, 0,drb,GroupLayout.DEFAULT, null);
	}
}
