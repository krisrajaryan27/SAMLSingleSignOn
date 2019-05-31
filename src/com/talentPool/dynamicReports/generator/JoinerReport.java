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
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.custom.utils.CustomFieldUtils;
import com.talentPool.dynamicReports.data.DynamicReportsDataSource;
import com.talentPool.dynamicReports.data.JoinerReportData;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.ReportVersionConstants;

/**
 * @author SumeetS
 *
 */
public class JoinerReport extends DynamicReportGenerator{
	
	private List<JoinerReportData> joinerReportData = null;
	private String fieldsToShow = null;
	private String filterCriteria = null;
	private String reportTitle = null;
	
	public JoinerReport(HttpServletRequest request,
			String filePathname, String exportTo) {
		super(request, filePathname, exportTo);
	}
	
	public JoinerReport(HttpServletRequest request,
			String filePathname, String exportTo,List<JoinerReportData> joinerReportData,
			String fieldsToShow, String filterCriteria, String title){
		super(request, filePathname, exportTo);
		this.joinerReportData = joinerReportData;
		this.fieldsToShow=fieldsToShow;
		this.filterCriteria=filterCriteria;
		this.reportTitle = title;
	}

	@Override
	public DynamicReport buildReport() throws Exception {
		FastReportBuilder drb = new FastReportBuilder();
		addDefaultColumns(drb);
		addColumns(fieldsToShow,drb);
		setReportDefaultProperties(drb, this.reportTitle, this.filterCriteria);
		drb.setHeaderHeight(40);
		return drb.build();
	}

	private void addDefaultColumns(FastReportBuilder drb) {
		int minColWidth = 50;
		int maxColWidth = 150;
		int medColWidth = 100;
		try {
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.REPORT_SERIAL_NUMBER), "srNo",String.class.getName(),minColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_DATE_JOINED), "dateOfJoining", String.class.getName(),medColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_NAME), "name", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_DESIGNATION_OFFERED), "designation", String.class.getName(),medColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_STEP_STATUS_MESSAGE), "status", String.class.getName(),medColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_TEAM), "team", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_POSITION_REQUISITIONER),"requisitioner",String.class.getName(),medColWidth);
		} catch (ColumnBuilderException e) {
			TPLogger.getLogger().debug("method exit - Error Generating Joiner Report");
		} catch (ClassNotFoundException e) {
			TPLogger.getLogger().debug("method exit - Error Generating Joiner Report");
		}
	}

	@Override
	public JRDataSource getDataSource() {
		JRDataSource jd = new DynamicReportsDataSource(getDataSourceCollection());
	return jd;
	}
	
	private List<JoinerReportData> getDataSourceCollection() {
		SimpleDataObject joinerCand = null;
		try {
			if (joinerReportData != null) {
				Iterator<JoinerReportData> itr = joinerReportData.iterator();
				while (itr.hasNext()) {
					joinerCand = itr.next();
					CustomFieldUtils.generateCustomFieldMap(joinerCand.getAttributes(), joinerCand.getString("appCustomFieldValues"), "\\|\\|");
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().debug("method exit - getDataSourceCollection for Joiner Report");
		}
		return joinerReportData;
	}
	
	private void addColumns(String fieldsToShow,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
		String[] fieldsList = fieldsToShow.split(",");
		for (int i = 0; i < fieldsList.length; i++) {
			addColumn(fieldsList[i],drb);
		}
	}
	
	private void addColumn(String key,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
//		int minColWidth = 50;
//		int maxColWidth = 150;
		int medColWidth = 100;
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
