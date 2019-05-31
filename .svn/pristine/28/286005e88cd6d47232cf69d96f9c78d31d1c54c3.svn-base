/**
 * 
 */
package com.talentPool.dynamicReports.generator;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.dynamicReports.data.DynamicReportsDataSource;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.reports.views.HiringFunnelDetailView;

/**
 * @author Shantanu
 *
 */
public class HiringFunnelDetailReport extends DynamicReportGenerator {
	private List<HiringFunnelDetailView> hiringFunnelDetailData = null;
	private String fieldsToShow = null;
	private String filterCriteria = null;

	public HiringFunnelDetailReport(HttpServletRequest request,	String filePathname, String exportTo) {
		super(request, filePathname, exportTo);
	}
	
	public HiringFunnelDetailReport(HttpServletRequest request,
			String filePathname, String exportTo,List<HiringFunnelDetailView> hiringFunnelDetailData,
			String fieldsToShow,String filterCriteria) {
		super(request, filePathname, exportTo);
		this.hiringFunnelDetailData = hiringFunnelDetailData;
		this.fieldsToShow=fieldsToShow;
		this.filterCriteria=filterCriteria;
	}
	
	
	@Override
	public DynamicReport buildReport() throws Exception {
		FastReportBuilder drb = new FastReportBuilder();
		addColumns(fieldsToShow,drb);
		setReportDefaultProperties(drb, ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_FUNNEL), this.filterCriteria);
		return drb.build();
	}
	
	@Override
	public JRDataSource getDataSource() {
		JRDataSource jd = new DynamicReportsDataSource(getDataSourceCollection());
		return jd;
	}

	private List<HiringFunnelDetailView> getDataSourceCollection() {
		HiringFunnelDetailView hfdv = null;
		try {
			if (hiringFunnelDetailData != null) {
				Iterator<HiringFunnelDetailView> itr = hiringFunnelDetailData.iterator();
				while (itr.hasNext()) {
					hfdv = itr.next();
//					if(!Utils.isBlankOrNull(hfdv.getPosCustomFieldValues()))
//						CustomFieldUtils.generateCustomFieldMap(hfdv.getAttributes(), hfdv.getPosCustomFieldValues(), "\\|\\|");
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().debug("method exit - getDataSourceCollection for Employee Details Report");
		}
		return hiringFunnelDetailData;
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
		}else if(ReportDesignConstants.COLUMN_CANDIDATE_ID.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantId", Long.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_HRMS_CODE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "applicantHrmsCode", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_CANDIDATE_EMPLOYEE_CODE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "employeeCode", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_TITLE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionTitle", String.class.getName(),maxColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_BUSINESS_UNIT.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "buName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_COST_CENTER.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "costCenterName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE.equals(key)){
			addColumn(drb,ColumnUtils.getColumnLabel(key), "positionDateExpiry", minColWidth);
		}else if(ReportDesignConstants.COLUMN_STEP_NAME.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "positionStepTitle", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_DEPARTMENT.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "deptName", String.class.getName(),mediumColWidth);
		} else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "subDeptName", String.class.getName(),minColWidth);
		} else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB_SUB.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "subSubDeptName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB3.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "sub3DeptName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_DEPARTMENT_SUB4.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "sub4DeptName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_BUDGET_BAND.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "bandName", String.class.getName(),minColWidth);
		}else if(ReportDesignConstants.COLUMN_BUDGET_GRADE.equals(key)){
			drb.addColumn(ColumnUtils.getColumnLabel(key), "gradeName", String.class.getName(),minColWidth);
		}
		
		/*if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				if(!Utils.isBlankOrNull(cData.getFieldId()) && cData.getFieldId().endsWith(key)){
					drb.addColumn(cData.getFieldDisplayName(),"CUS_"+cData.getFieldId(), String.class.getName(),minColWidth);
				}
			}
		}*/		
	}
}
