package com.talentPool.dynamicReports.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.custom.utils.CustomFieldUtils;
import com.talentPool.dynamicReports.data.DynamicReportsDataSource;
import com.talentPool.dynamicReports.data.PendingOffersReportData;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.ReportVersionConstants;

public class PendingOffersReport extends DynamicReportGenerator {
	
	private List<PendingOffersReportData> pendingOffersReportData = null;
	private String fieldsToShow = null;
	private String filterCriteria = null;

	public PendingOffersReport(HttpServletRequest request,
			String filePathname, String exportTo) {
		super(request, filePathname, exportTo);
	}
	
	public PendingOffersReport(HttpServletRequest request,
			String filePathname, String exportTo,List<PendingOffersReportData> pendingOffersReportData,
			String fieldsToShow,String filterCriteria) {
		super(request, filePathname, exportTo);
		this.pendingOffersReportData = pendingOffersReportData;
		this.fieldsToShow=fieldsToShow;
		this.filterCriteria=filterCriteria;
	}

	@Override
	public DynamicReport buildReport() throws Exception {
		FastReportBuilder drb = new FastReportBuilder();
		addColumns(fieldsToShow,drb);
		setReportDefaultProperties(drb, ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_PENDING_OFFER), this.filterCriteria);
		return drb.build();
	}
	
	@Override
	public JRDataSource getDataSource() {
		JRDataSource jd = new DynamicReportsDataSource(getDataSourceCollection());
		return jd;
	}

	private List<PendingOffersReportData> getDataSourceCollection() {
		PendingOffersReportData pord = null;
		try {
			if (pendingOffersReportData != null) {
				Iterator<PendingOffersReportData> itr = pendingOffersReportData.iterator();
				while (itr.hasNext()) {
					pord = itr.next();
					CustomFieldUtils.generateCustomFieldMap(pord.getAttributes(), pord.getPosCustomFieldValues(), "\\|\\|");
					//pord.setApplicantExperience(Utils.getExperienceConstructed(pord.getApplicantWorkingSince()));
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().debug("method exit - getDataSourceCollection for Employee Details Report");
		}
		return pendingOffersReportData;
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
		
		if(ReportDesignConstants.COLUMN_CANDIDATE_NAME.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantName", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_EXPERIENCE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantWorkingSince", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_CELL_PHONE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantCellPhone", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL1.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantEmail1", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL2.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantEmail2", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_EMPLOYER.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantCurrentEmployer", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_DATE_JOINED.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantDateJoined", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_CANDIDATE_DESIGNATION_OFFERED.equals(key)) {
			drb.addColumn(ColumnUtils.getColumnLabel(key), "designationOffered", String.class.getName(),mediumColWidth);
		} else if(ReportDesignConstants.COLUMN_DEPARTMENT.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "deptName", String.class.getName(),mediumColWidth);
		} else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "subDeptName", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB_SUB.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "subSubDeptName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB3.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "sub3DeptName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB4.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "sub4DeptName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_TITLE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionTitle", String.class.getName(),maxColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionDateExpiry", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_POSITION_DATE_CREATED.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionDateCreated", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_POSITION_DATE_APPROVED.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionDateApproved", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_BUDGET_GRADE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "gradeName", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_POSITION_LOCATION.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "locationName", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_TODO_DUE_DATE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "dueDate", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_STEP_NAME.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionStepTitle", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "sourceTitle", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_STEP_STATUS_MESSAGE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "statusMessage", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_STEP_STATUS_MESSAGE_UPDATED_DATE.equals(key)){
			drb.addColumn(TPLabels.getLabel("report.label.last_interaction_date"), "lastInteractionDate", String.class.getName(),minColWidth);
		} 
		else if(ReportDesignConstants.COLUMN_POSITION_REQUESTED_BY.equals(key)){
			drb.addColumn(TPLabels.getLabel("add_position.label.requested_by"), "positionRequestedBy", String.class.getName(),minColWidth);
		} 
		else if(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_PROCESSED_BY.equals(key)){
			drb.addColumn(TPLabels.getLabel("add_position.label.processed_by"), "recruitmentManager", String.class.getName(),minColWidth);
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
}
