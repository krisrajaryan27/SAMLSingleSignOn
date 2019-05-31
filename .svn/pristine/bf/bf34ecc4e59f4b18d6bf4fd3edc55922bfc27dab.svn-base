/**
 * 
 */
package com.talentPool.reportDesign.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/*import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;*/
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.talentPool.budget.utils.BudgetUtils;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.dataobject.ReportData;
import com.talentPool.reportDesign.form.ReportDesignForm;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.manager.ModuleSet;

/**
 * @author Ajeet
 *
 */
public class ReportDesignUtils {
	
	
	
	public static LinkedHashMap<String, String> getJSArrayForReportType(){
		
		LinkedHashMap<String, String> reportTypeMap=new LinkedHashMap<String, String>();
		reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_CANDIDATES, TPLabels.getLabel("reportdesigner.type.candidates"));
		reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_CANDIDATES_WITH_POSITION, TPLabels.getLabel("common.candidate_with_position"));
		reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_CANDIDATES_WITH_ACTIVITY, TPLabels.getLabel("reportdesigner.type.candidates_with_activity"));
		reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_POSITIONS, TPLabels.getLabel("common.positions"));
		reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_POSITIONS_WITH_CANDIDATE, TPLabels.getLabel("common.positions")+" "+TPLabels.getLabel("reportdesigner.type.positions_with_candidates"));
		reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_POSITIONS_WITH_ACTIVITY, TPLabels.getLabel("common.positions")+" "+TPLabels.getLabel("reportdesigner.type.positions_with_activity"));
		/*reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_USERS, TPLabels.getLabel("reportdesigner.type.users"));
		reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_USERS_WITH_CANDIDATE, TPLabels.getLabel("reportdesigner.type.users_with_candidates"));
		reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_USERS_WITH_POSITION, TPLabels.getLabel("reportdesigner.type.users_with_positions")+TPLabels.getLabel("common.positions");
		reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_USERS_WITH_ACTIVITY, TPLabels.getLabel("reportdesigner.type.users_with_activity"));
		*/
		reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_EXPENSE, TPLabels.getLabel("reportdesigner.type.expense"));
		reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_AUDIT, TPLabels.getLabel("reportdesigner.type.audit"));
		if(ModuleSet.isMODULE_BUDGET() && BudgetUtils.isBudgetModuleActive()){
			reportTypeMap.put(ReportDesignConstants.REPORT_TYPE_BUDGET, TPLabels.getLabel("reportdesigner.type.budget"));
		}
		return reportTypeMap;
	}
		
	public static HashMap<String, String> getActivityTypesMap(){
		ArrayList<SimpleDataObject> list = new ArrayList<SimpleDataObject>();
		HashMap<String, String> activityTypesList = new HashMap<String, String>();
			activityTypesList.put("" + SelectionProcessConstants.INTERACTION_IMPORT, (String)SelectionProcessConstants.INTERACTION_TYPES.get("" + SelectionProcessConstants.INTERACTION_IMPORT));
			activityTypesList.put("" + SelectionProcessConstants.INTERACTION_SHORTLISTED, (String)SelectionProcessConstants.INTERACTION_TYPES.get("" + SelectionProcessConstants.INTERACTION_SHORTLISTED));
			activityTypesList.put("" + SelectionProcessConstants.INTERACTION_EMAIL_SENT, (String)SelectionProcessConstants.INTERACTION_TYPES.get("" + SelectionProcessConstants.INTERACTION_EMAIL_SENT));
			activityTypesList.put("" + SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED, (String)SelectionProcessConstants.INTERACTION_TYPES.get("" + SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED));
			activityTypesList.put("" + SelectionProcessConstants.INTERACTION_APPOINTMENTS, (String)SelectionProcessConstants.INTERACTION_TYPES.get("" + SelectionProcessConstants.INTERACTION_APPOINTMENTS));
			activityTypesList.put("" + SelectionProcessConstants.INTERACTION_INTERVIEW, (String)SelectionProcessConstants.INTERACTION_TYPES.get("" + SelectionProcessConstants.INTERACTION_INTERVIEW));
			activityTypesList.put("" + SelectionProcessConstants.INTERACTION_MESSAGE, (String)SelectionProcessConstants.INTERACTION_TYPES.get("" + SelectionProcessConstants.INTERACTION_MESSAGE));
			activityTypesList.put("" + SelectionProcessConstants.INTERACTION_PHONE, (String)SelectionProcessConstants.INTERACTION_TYPES.get("" + SelectionProcessConstants.INTERACTION_PHONE));
			activityTypesList.put("" + SelectionProcessConstants.INTERACTION_NOTE, (String)SelectionProcessConstants.INTERACTION_TYPES.get("" + SelectionProcessConstants.INTERACTION_NOTE));
			activityTypesList.put("" + SelectionProcessConstants.INTERACTION_SMS, (String)SelectionProcessConstants.INTERACTION_TYPES.get("" + SelectionProcessConstants.INTERACTION_SMS));
		return activityTypesList;
	}
	
	public static String getJSArrayForSelectedColumns(String columns) {
		String[] selectedColumnIds = columns.split(",");		
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[");
			for (int i = 0; i < selectedColumnIds.length; i++) {
				sb.append("new SelectOption('" + selectedColumnIds[i]+ "','" + ColumnUtils.columnLabelMap.get(selectedColumnIds[i]) + "'),");
			}	
			if(selectedColumnIds.length>0)
				sb.deleteCharAt(sb.length()-1);
			sb.append("]");

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}
	
	public static void populateReportDataInReportDesignForm(ReportData reportData, ReportDesignForm reportDesignForm){
		reportDesignForm.setReportId(reportData.getReportId());
		reportDesignForm.setReportName(reportData.getReportName());
		reportDesignForm.setReportType(reportData.getReportType());
		reportDesignForm.setColumns(reportData.getColumns());
		reportDesignForm.setGroupBy(reportData.getGroupBy());
		reportDesignForm.setFilters(reportData.getFilters());
		reportDesignForm.setSortBy(reportData.getSortBy());
		reportDesignForm.setSortWith(reportData.getSortWith());
		reportDesignForm.setLevelPermissions(reportData.getLevelPermissions());
		if(reportData.getLevelPermissions()!=null && !reportData.getLevelPermissions().trim().isEmpty()){
			reportDesignForm.setIsSharedReport(ReportDesignConstants.REPORT_ACCESS_SHARED);
		}else{
			reportDesignForm.setIsSharedReport(ReportDesignConstants.REPORT_ACCESS_PRIVATE);
		}
		reportDesignForm.setReportDescription(reportData.getReportDescription());
		
		reportDesignForm.setReportFormat(reportData.getReportFormat());
		reportDesignForm.setReportFilePath(reportData.getReportFilePath());
		reportDesignForm.setSheetIndex(reportData.getSheetIndex());
		reportDesignForm.setRowIndex(reportData.getRowIndex());
	}
	
	public static String getReportSheetArray(String filePath) {
		StringBuffer sb = new StringBuffer();
		try {
			filePath = Utils.concatFilePath(DocumentConstants.documentsPath, filePath);
			Workbook wb = WorkbookFactory.create(new FileInputStream(new File(filePath)));
			int count = wb.getNumberOfSheets();
			sb.append("[");
			for (int i = 0; i < count; i++) {
				sb.append("new SelectOption('" + i+ "','" + wb.getSheetName(i) + "'),");
			}
			if(count>0)
				sb.deleteCharAt(sb.length()-1);
			sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}
	
}
