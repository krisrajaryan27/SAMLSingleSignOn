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
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.views.AuditTrailView;

public class AuditTrailLogReport extends DynamicReportGenerator{
	private List<AuditTrailView> auditTrailView = null;
	private String fieldsToShow = null;
	private String filterCriteria = null;
	private String reportTitle = null;
	
	public AuditTrailLogReport(HttpServletRequest request,
			String filePathname, String exportTo) {
		super(request, filePathname, exportTo);
	}
	
	public AuditTrailLogReport(HttpServletRequest request,
			String filePathname, String exportTo,List<AuditTrailView> auditTrailView,
			String fieldsToShow, String filterCriteria, String title){
		super(request, filePathname, exportTo);
		this.auditTrailView = auditTrailView;
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
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_AUDIT_DESC), "auditDesc", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_AUDIT_TYPE), "auditType", String.class.getName(),medColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_AUDIT_CRATED_BY), "userName", String.class.getName(),medColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_AUDIT_ENTITY_FIELD), "entityField", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_AUDIT_DATE_CREATED), "dateCreated", String.class.getName(),maxColWidth);
			//drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_POSITION_TITLE), "position", String.class.getName(),medColWidth);
			//drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_STEP_NAME), "step", String.class.getName(),maxColWidth);
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
	
	private List<AuditTrailView> getDataSourceCollection() {
		SimpleDataObject joinerCand = null;
		try {
			if (auditTrailView != null) {
				Iterator<AuditTrailView> itr = auditTrailView.iterator();
				while (itr.hasNext()) {
					joinerCand = itr.next();
					//CustomFieldUtils.generateCustomFieldMap(joinerCand.getAttributes(), joinerCand.getString("appCustomFieldValues"), "\\|\\|");
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().debug("method exit - getDataSourceCollection for Joiner Report");
		}
		return auditTrailView;
	}
	
	private void addColumns(String fieldsToShow,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
		String[] fieldsList = fieldsToShow.split(",");
		for (int i = 0; i < fieldsList.length; i++) {
			//addColumn(fieldsList[i],drb);
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
