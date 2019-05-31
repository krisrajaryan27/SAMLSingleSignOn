package com.talentPool.dynamicReports.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.custom.utils.CustomFieldUtils;
import com.talentPool.dynamicReports.data.DynamicReportsDataSource;
import com.talentPool.dynamicReports.utils.DJUtils;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.ReportVersionConstants;

public class AllCandidatesReport extends DynamicReportGenerator {
	
	private List<SimpleDataObject> allCandidates = null;
	private String fieldsToShow 	= null;
	private String filterCriteria 	= null;
	private String groupBy			= null;

	public AllCandidatesReport(HttpServletRequest request,
			String filePathname, String exportTo) {
		super(request, filePathname, exportTo);
	}
	
	public AllCandidatesReport(HttpServletRequest request,
			String filePathname, String exportTo,List<SimpleDataObject> allCandidates,
			String fieldsToShow,String filterCriteria,String groupBy) {
		super(request, filePathname, exportTo);
		this.allCandidates = allCandidates;
		this.fieldsToShow=fieldsToShow;
		this.filterCriteria=filterCriteria;
		this.groupBy=groupBy;
	}

	@Override
	public DynamicReport buildReport() throws Exception {
		FastReportBuilder drb = new FastReportBuilder();
		if(!Utils.isBlankOrNull(groupBy))
			addGrouByColumns(groupBy,drb);
		addColumns(fieldsToShow,drb);
		if(!Utils.isBlankOrNull(groupBy))
			addGroups(drb);
		setReportDefaultProperties(drb, ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_ALL_CANDIDATES), this.filterCriteria);
		if(!Utils.isBlankOrNull(filterCriteria))
			drb.setSubtitleHeight(30+filterCriteria.split("\\\\n").length * 10);
		return drb.build();
	}
	
	@Override
	public JRDataSource getDataSource() {
		JRDataSource jd = new DynamicReportsDataSource(getDataSourceCollection());
		return jd;
	}

	private List<SimpleDataObject> getDataSourceCollection() {
		SimpleDataObject rejCand = null;
		try {
			if (allCandidates != null) {
				Iterator<SimpleDataObject> itr = allCandidates.iterator();
				while (itr.hasNext()) {
					rejCand = itr.next();
					CustomFieldUtils.generateCustomFieldMap(rejCand.getAttributes(), rejCand.getString("appCustomFieldValues"), "\\|\\|");
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().debug("method exit - getDataSourceCollection for Employee Details Report");
		}
		return allCandidates;
	}
	
	private void addColumns(String fieldsToShow,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
		String[] fieldsList = fieldsToShow.split(",");
		List<String> groups = Utils.convertCommaSepStringToList(groupBy);
		for (int i = 0; i < fieldsList.length; i++) {
			if(!groups.contains(fieldsList[i]))
				addColumn(fieldsList[i],drb);
		}
	}
	
	private void addGrouByColumns(String groupsToShow, FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
		List<String> groups = Utils.convertCommaSepStringToList(groupsToShow);
		for (int i = 0; i < groups.size(); i++) {
			addColumn(groups.get(i),drb);
		}
	}
	
	private void addGroups(FastReportBuilder drb){
		List<String> groups = Utils.convertCommaSepStringToList(groupBy);
		DJUtils.addGroups(groups, drb, GroupLayout.DEFAULT);
	}
	
	private void addColumn(String key,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
		int minColWidth = 100;
		int maxColWidth = 200;
		int medColWidth = 150;
		if(ReportDesignConstants.COLUMN_CANDIDATE_NAME.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantName", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL1.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantEmail1", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_CELL_PHONE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantCellPhone", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_EMPLOYER.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantCurrentEmployer", String.class.getName(),medColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_CTC.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "currentCTC", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_EXPECTED_CTC.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "expectedCTC", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "sourceTitle", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_POSITION_TITLE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionTitle", String.class.getName(),maxColWidth);
		} else if(ReportDesignConstants.COLUMN_POSITION_CODE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionCode", String.class.getName(),maxColWidth);
		} else if(ReportDesignConstants.COLUMN_DEPARTMENT.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "deptName", String.class.getName(),maxColWidth);
		} else if(ReportDesignConstants.COLUMN_STEP_NAME.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "stepTitle", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_STEP_LEVEL.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "stepLevelName", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_SKILLS.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "skills", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_SUB_CATEGORY.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "subCategory", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_CATEGORY.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "category", String.class.getName(),medColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL2.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantEmail2", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_WORK_PHONE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantWorkPhone", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_HOME_PHONE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantHomePhone", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_PASSPORT_NUMBER.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "passportNumber", String.class.getName(),maxColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_DATE_OF_BIRTH.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "dateOfBirth", String.class.getName(),maxColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_CITY.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "location", String.class.getName(),maxColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_EXPERIENCE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "exp", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_NOTICE_PERIOD.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantNoticePeriod", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_LEVEL_OFFERED.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantLevelOffered", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_DESIGNATION_OFFERED.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantDesignationOffered", String.class.getName(),maxColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_BASIC.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "offeredBasic", String.class.getName(),maxColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_CTC.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "offeredCtc", String.class.getName(),maxColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_INPUT_SALARY_VARIABLE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "inputSalaryVariable", String.class.getName(),minColWidth);
		} 
		
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				if(!Utils.isBlankOrNull(cData.getFieldId()) && cData.getFieldId().endsWith(key)){
					drb.addColumn(cData.getFieldDisplayName(),"CUS_"+cData.getFieldId(), String.class.getName(),medColWidth);
				}
			}
		}
		
	}
}
