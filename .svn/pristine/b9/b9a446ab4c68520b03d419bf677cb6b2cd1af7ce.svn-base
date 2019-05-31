/**
 * 
 */
package com.talentPool.dynamicReports.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;

import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.dynamicReports.data.DynamicReportsDataSource;
import com.talentPool.dynamicReports.data.HiringActivityReportData;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.ReportVersionConstants;

/**
 * @author Ajeet
 *
 */
public class HiringActivityReport extends DynamicReportGenerator {

	private List<HiringActivityReportData> hiringActivityReportData = null;
	private Map<String,String> headerMap = null;
	private String fieldsToShow = null;
	private String stepsToShow = null;
	private String filterCriteria = null;
	
	public HiringActivityReport(HttpServletRequest request,
			String filePathname, String exportTo) {
		super(request, filePathname, exportTo);
	}
	
	public HiringActivityReport(HttpServletRequest request,
			String filePathname, String exportTo,List<HiringActivityReportData> hiringActivityReportData,
			Map<String,String> headerMap,String fieldsToShow,String stepsToShow, String filterCriteria) {
		super(request, filePathname, exportTo);
		this.hiringActivityReportData = hiringActivityReportData;
		this.headerMap=headerMap;
		this.fieldsToShow=fieldsToShow;
		this.stepsToShow=stepsToShow;
		this.filterCriteria=filterCriteria;
	}
	
	@Override
	public DynamicReport buildReport() throws Exception {
		FastReportBuilder drb = new FastReportBuilder();
		addColumns(fieldsToShow,drb);
		addColumns(stepsToShow,headerMap,drb);		
		setReportDefaultProperties(drb, ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_ACTIVITY),this.filterCriteria);
		return drb.build();
	}

	@Override
	public JRDataSource getDataSource() {
		JRDataSource jd = new DynamicReportsDataSource(getDataSourceCollection());
		return jd;
	}
	
	private void addColumns(String fieldsToShow,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
		String[] fieldsList = fieldsToShow.split(",");
		for (int i = 0; i < fieldsList.length; i++) {
			addColumn(fieldsList[i],drb);
		}
	}
	
	/*
	 *Custom Fields are not generated as it is already done during Data modification 
	 * 
	*/
	private List<HiringActivityReportData> getDataSourceCollection() {
		return hiringActivityReportData;
	}
	
	private void addColumn(String key,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
		int minColWidth = 100;
		int maxColWidth = 200;
		int mediumColWidth = 150;
		
		if(ReportDesignConstants.COLUMN_DEPARTMENT.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "deptName",mediumColWidth);
		} else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "subDeptName",minColWidth);
		} else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB_SUB.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "subSubDeptName", minColWidth);
		}else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB3.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "sub3DeptName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB4.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "sub4DeptName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_TITLE.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "positionTitle", 2*minColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_OWNER.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "positionOwnerName", minColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "positionDateExpiry", minColWidth);
		} else if(ReportDesignConstants.COLUMN_POSITION_DATE_CREATED.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "positionDateCreated", minColWidth);
		} else if(ReportDesignConstants.COLUMN_POSITION_DATE_APPROVED.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "positionDateApproved", minColWidth);
		} else if(ReportDesignConstants.COLUMN_BUDGET_GRADE.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "gradeName", minColWidth);
		} else if(ReportDesignConstants.COLUMN_BUDGET_BAND.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "bandName", minColWidth);
		} else if(ReportDesignConstants.COLUMN_POSITION_LOCATION.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "locationName", minColWidth);
		} else if(ReportDesignConstants.COLUMN_TODO_DUE_DATE.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "dueDate", minColWidth);
		} else if(ReportDesignConstants.COLUMN_STEP_NAME.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "positionStepTitle", maxColWidth);
		} else if(ReportDesignConstants.COLUMN_POSITION_VACANCIES.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "positionNoOfOpenings", minColWidth);
		} 
		
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				if(!Utils.isBlankOrNull(cData.getFieldId()) && cData.getFieldId().endsWith(key)){
					addColumn(drb,cData.getFieldDisplayName(),"CUS_"+cData.getFieldId(), minColWidth);
				}
			}
		}	
		 if(ReportDesignConstants.COLUMN_POSITION_APPLIED_COUNT.equals(key)){
				addColumn(drb,ColumnUtils.getColumnLabel(key), "appliedCount", minColWidth,Long.class.getName());
			} 
	}
	
	private void addColumns(String stepsToShow,Map<String,String> headerMap,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
		if(!Utils.isBlankOrNull(stepsToShow)){
			int defaultColWidth = 70;
			String[] steps = stepsToShow.split(",");
			String stepName = null;
			for (int i = 0; i < steps.length; i++) {
				stepName = Utils.replaceSpaceWithDoubleUnderscore(steps[i]);
				if(headerMap.containsKey(stepName)){
					String value = headerMap.get(stepName);
					addColumn(drb,value, stepName,defaultColWidth,Integer.class.getName());
				}
			}
		}
	}
	
}
