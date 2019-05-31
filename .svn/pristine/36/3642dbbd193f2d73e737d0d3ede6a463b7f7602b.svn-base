package com.talentPool.reports.manager;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.audit.constants.AuditConstants;
import com.talentPool.audit.manager.AuditManager;
import com.talentPool.calendar.CalendarConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.custom.utils.CustomFieldUtils;
import com.talentPool.dashboard.constants.DashboardConstants;
import com.talentPool.dynamicReports.data.BlackListReportData;
import com.talentPool.dynamicReports.data.DatewiseHiringReportData;
import com.talentPool.dynamicReports.data.HiringActivityReportData;
import com.talentPool.dynamicReports.data.JoinerReportData;
import com.talentPool.dynamicReports.data.OfferedCTCReportData;
import com.talentPool.dynamicReports.data.PendingOffersReportData;
import com.talentPool.dynamicReports.data.TimeToHireReportData;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.masters.dataobject.MasterData;
import com.talentPool.masters.manager.MultipleSelectsManager;
import com.talentPool.masters.utils.StepLevelStaticUtils;
import com.talentPool.masters.utils.StepStaticUtils;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.positions.utils.PositionUtils;
import com.talentPool.positions.utils.PositionWithRightsClause;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.ReportUtils;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.reports.dataobject.CandidateOneStopFileReportData;
import com.talentPool.reports.dataobject.CandidateTATData;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.dataobject.JRCollectionSource;
import com.talentPool.reports.dataobject.MasterReportData;
import com.talentPool.reports.excelReportGenerator.CandidateOffersExcelReport;
import com.talentPool.reports.utils.MonthlyJoinedCandidatesCSVReport;
import com.talentPool.reports.views.ApplicantDetailsView;
import com.talentPool.reports.views.AuditTrailView;
import com.talentPool.reports.views.CandidateStatusReportView;
import com.talentPool.reports.views.ErrorResultView;
import com.talentPool.reports.views.HiringFunnelDetailView;
import com.talentPool.reports.views.OfferedCTCView;
import com.talentPool.reports.views.PendingActionsView;
import com.talentPool.reports.views.SourcewiseImportView;
import com.talentPool.reports.views.callListReportView;
import com.talentPool.reports.views.custom.CandidateOffersView;
import com.talentPool.requisition.constants.RequisitionConstants;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.todo.constants.ToDoConstants;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

@Component
public class ReportManager {

	public void generateReport(String jrXMLFileName, String outputFileName, HashMap params, ArrayList data, String reportFormat, 
			String userId, boolean printOnload, String clientIpAddr) throws Exception {

		// compile the report
		String filePath = Utils.concatFilePath(ReportConstants.JRXML_FOLDER_PATH, jrXMLFileName);

		JasperReport jasperReport = JasperCompileManager.compileReport(filePath);
		JRDataSource jsor = new JRBeanCollectionDataSource(data);

		// fill the report
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jsor);

		// export to format
		String destinationPath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH, outputFileName);

		String htmlHeader = ReportConstants.DEFAULT_REPORT_HTML_HEADER;
		String htmlFooter = "";
		
		if(printOnload){
			htmlHeader += "<body onload=\"javascript: window.print();\" >";
			htmlFooter+="</body>";
		}
		if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_HTML)) {
			JRHtmlExporter exporter = new JRHtmlExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationPath);
			exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);
			exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, "px");
			exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, htmlHeader);
			exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, htmlFooter);
			exporter.exportReport();
		} else if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_PDF)) {
			JRPdfExporter exporter = new JRPdfExporter();
			// exporter.setParameter(JRPdfExporterParameter.METADATA_TITLE,
			// "ToutVirtual,Inc");
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, destinationPath);
			exporter.exportReport();
		} else if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_EXCEL)) {
			//JRXlsExporter exporterXLS = new JRXlsExporter();
			JExcelApiExporter exporterXLS = new JExcelApiExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, destinationPath);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			// exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
			// Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			// exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
			// Boolean.TRUE);
			exporterXLS.exportReport();
			// byteArray = outputByteArray.toByteArray();
			
			AuditManager auditManager = new AuditManager();
			
			auditManager.addAudit(null, AuditConstants.TYPE_GENERATED, (String) params.get("report_title"), AuditConstants.AUDIT_REPORT, 
					userId, null, null, null, true, clientIpAddr);

		}

	}
	public void generateReport(String jrXMLFileName, String outputFileName, HashMap params, ArrayList data, String reportFormat, 
			String userId, String clientIpAddr) throws Exception {		
		generateReport(jrXMLFileName, outputFileName, params, data, reportFormat, userId, false, clientIpAddr);
	}
	
	@SuppressWarnings("unchecked")
	public void generateErrorReport( String outputFileName, HashMap params, ArrayList data, String reportFormat, 
			String userId, String clientIpAddr) throws Exception{
		data.add(new ErrorResultView());
		params.put("report_does_ not_exist", TPLabels.getLabel("report.label.does_not_exist"));
		generateReport(ReportConstants.CUSTOMIZATION_REPORT_ERROR_JRXML, outputFileName, params, data, 
				reportFormat, userId, false, clientIpAddr);
	}
	
	public void generateExcelReport(String reportName, String inputFileCriteria, String outputFileName, HashMap params, 
			ArrayList data, FilterData filterData,String date_criteria, String other_criteria, String userId,
			PermissionSet permissionSet, String clientIpAddr) throws FileNotFoundException, IOException, SQLException{
		
		String filePath = Utils.concatFilePath(ReportConstants.JRXML_FOLDER_PATH, inputFileCriteria);
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		
		//generate EXCEL Reports
		if(reportName.equals(ReportVersionConstants.REPORT_CANDIDATE_OFFERS)){
			CandidateOffersExcelReport candidateOffersExcelReport = new CandidateOffersExcelReport();
			candidateOffersExcelReport.generateCandidateOffersExcelReport(reportName, wb, data, filterData,date_criteria,other_criteria,permissionSet);			
		}
		
		// Write the output to a file
		String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
		String destinationPath = Utils.concatFilePath(basePath, ReportConstants.REPORT_DESTINATION_FOLDER);
		filePath = Utils.concatFilePath(destinationPath, outputFileName);
		FileOutputStream fileOut = new FileOutputStream(filePath);
		wb.write(fileOut);
		fileOut.close();
	
		AuditManager auditManager = new AuditManager();
		
		auditManager.addAudit(null,AuditConstants.TYPE_GENERATED, reportName, AuditConstants.AUDIT_REPORT,  userId,null,null,null,true, clientIpAddr);

	}


	/**
	 * get all positions created within date range.
	 * 
	 * @param toDate
	 * @return
	 */
	public ArrayList getPositions(String userId, PermissionSet permissionSet, String departmentId) {
		ArrayList result = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[4];			
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " tpos.position_code ";
				dynParam[3] = " tpos.position_code ";
			} else {
				dynParam[0] = " tpos.position_title ";
				dynParam[3] = " tpos.position_title ";
			}
			dynParam[1] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
				dynParam[1] = " and tpos.position_id in (select su.position_id from tp_position_step_users su, tp_position_steps ps where su.position_step_id = ps.position_step_id and ps.position_step_status = ? "
						+ " and su.user_id = ? union select position_id from tp_positions where position_requested_by = ? " + " UNION SELECT distinct traf.position_id FROM tp_requisition_approval_feedback traf WHERE traf.by_user_id=? OR traf.to_user_id=? )";

				dynamicContent.add(PositionConstants.STEP_ACTIVE);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
			}
			if(!Utils.isBlankOrNull(departmentId)){
				dynParam[2] = " AND tpos.dept_id IN ("+departmentId+")"; 
			}else{
				dynParam[2] ="";
			}
			
			dq = new DBPreparedQuery("dReportManager_GetPositions", dynParam);
			dq.setString(1, PositionConstants.POSITION_STATUS_DELETED);
			dq.setString(2, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(3, PositionConstants.POSITION_STATUS_CLOSED);
			int cnt = 4;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			result = dq.getResult();
		} catch (Exception exep) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public String getXMLForPositions(ArrayList positions) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("positions");
			for (int i = 0; i < positions.size(); i++) {
				PositionData positionData = (PositionData) positions.get(i);
				wr.startElement("position");
				wr.startElement("id");
				wr.characters(positionData.getPositionId());
				wr.endElement("id");
				wr.startElement("name");
				wr.characters(positionData.getPositionTitle());
				wr.endElement("name");
				wr.endElement("position");
			}
			wr.endElement("positions");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating xml file for positions", e);
		}
		return sWr.getBuffer().toString();
	}

	public String getXMLForSources(ArrayList sources) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("sources");
			for (int i = 0; i < sources.size(); i++) {
				MasterData mData = (MasterData) sources.get(i);
				wr.startElement("source");
				wr.startElement("itemId");
				wr.characters("" + mData.getItemId());
				wr.endElement("itemid");
				wr.startElement("itemName");
				wr.characters(mData.getItemName());
				wr.endElement("name");
				wr.endElement("source");
			}
			wr.endElement("sources");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating xml file for sources", e);
		}
		return sWr.getBuffer().toString();
	}
	/* 
	 *  
	 */
	public PermissionSet getUserPermission(String userId){
		PermissionSet permissionSet=null;
		LoginManager loginManager = new LoginManager();
		try{			
			LoginData loginData = loginManager.getUser(userId);	
			BitSet permissions= loginManager.getUserPermissionsBitSet(loginData.getUserId());
			permissionSet= new PermissionSet(permissions);
		}catch(Exception e){
			TPLogger.getLogger().error("error while fetching", e);
		}
		return	permissionSet;	
	}

	public ArrayList getHiringStatusSummaryReport(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList positions = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[2];			
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParams[0] = " tp.position_code ";
			} else {
				dynParams[0] = " tp.position_title ";
			}
			ArrayList<String> dynamicContent = new ArrayList<String>();			

			dynParams[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
			
			if (filterData.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS)) {
				dynParams[1] += " AND tp.position_status != ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_TEMPLATE);
			}else if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[1] += " AND tp.position_status = ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
			}else if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParams[1] += " AND (tp.position_status = ? OR tp.position_status = ?) ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
				dynParams[1] += " AND tp.position_id = ? ";
				dynamicContent.add(filterData.getPositionId());
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParams[1] += " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParams[1] += " AND tp.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParams[1] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}

			if (!Utils.isBlankOrNull(filterData.getUsers())) {
				dynamicContent.add(PositionConstants.STEP_ACTIVE);
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);

				dynParams[1] += " AND ";
				dynParams[1] += " ( ";
				dynParams[1] += " tp.position_id in (select su.position_id from tp_position_step_users su, tp_position_steps ps " + "	where su.position_step_id = ps.position_step_id and ps.position_step_status = ?  and su.user_id in (" + qMarks + ")) ";
				dynParams[1] += " OR tp.position_requested_by in (" + qMarks + ") ";
				dynParams[1] += " OR tp.position_id in ( SELECT distinct position_id FROM tp_requisition_approval_feedback WHERE by_user_id in (" + qMarks + ") OR to_user_id in (" + qMarks + ") )";
				dynParams[1] += ") ";
			}
			dq = new DBPreparedQuery("dReportManager_GetHiringStatusSummary", dynParams);
			dq.setId(1, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setId(2, PositionConstants.STEP_LEVEL_SELECT);
			dq.setId(3, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(4, PositionConstants.POSITION_STATUS_INPROCESS);
			dq.setString(5, PositionConstants.POSITION_STATUS_REJECTED);
			int cnt = 6;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}

			positions = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating report for hiring status summary report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positions;
	}

	public ArrayList getHiringStatusDetailReport(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[2];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParams[0] = " tp.position_code ";
			} else {
				dynParams[0] = " tp.position_title ";
			}
			ArrayList<String> dynamicContent = new ArrayList<String>();			
			
			dynParams[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
			
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[1] += " AND tp.position_status = ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParams[1] += " AND (tp.position_status = ? OR tp.position_status = ?) ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
				dynParams[1] += " AND tp.position_id = ? ";
				dynamicContent.add(filterData.getPositionId());
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParams[1] += " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParams[1] += " AND tp.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParams[1] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}
			if (!Utils.isBlankOrNull(filterData.getUsers())) {
				dynamicContent.add(PositionConstants.STEP_ACTIVE);
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);

				dynParams[1] += " AND ";
				dynParams[1] += " ( ";
				dynParams[1] += " tp.position_id in (select su.position_id from tp_position_step_users su, tp_position_steps ps " + "	where su.position_step_id = ps.position_step_id and ps.position_step_status = ?  and su.user_id in (" + qMarks + ")) ";
				dynParams[1] += " OR tp.position_requested_by in (" + qMarks + ") ";
				dynParams[1] += " OR tp.position_id in ( SELECT distinct position_id FROM tp_requisition_approval_feedback WHERE by_user_id in (" + qMarks + ") OR to_user_id in (" + qMarks + ") )";
				dynParams[1] += ") ";
			}
			dq = new DBPreparedQuery("dReportManager_GetHiringStatusDetail", dynParams);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			result = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating hiring status detailed report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public ArrayList getmonthlyJoiningSummaryReport(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			java.sql.Date fromDt = null;
			java.sql.Date toDt = null;
			if ((filterData.getDateRange()).equals(ReportConstants.CUSTOM)) {
				fromDt = filterData.getConvertedFromDateMonthly();
				toDt = filterData.getConvertedToDateMonthly();
			} else {
				fromDt = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
				toDt = Utils.convertToSQLDate(filterData.getToDate(), Utils.regEUDateFormat);
			}
			String[] dynParams = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			
			dynParams[0] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
			
			if (!Utils.isBlankOrNull(filterData.getDepartmentId())) {
				dynParams[0] += " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParams[0] += " AND tp.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParams[0] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}

			dq = new DBPreparedQuery("dReportManager_GetMonthlyJoiningSummaryReport", dynParams);
			dq.setString(1, ApplicantConstants.APPLICANT_JOINED);
			dq.setDate(2, fromDt);
			dq.setDate(3, toDt);
			int cnt = 4;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating report for monthly hiring report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public ArrayList getmonthlyJoiningDetailsReport(FilterData filterData, String userId, PermissionSet permissionSet) {

		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;

		try {
			java.sql.Date fromDt = null;
			java.sql.Date toDt = null;
			if ((filterData.getDateRange()).equals(ReportConstants.CUSTOM)) {
				fromDt = filterData.getConvertedFromDateMonthly();
				toDt = filterData.getConvertedToDateMonthly();
			} else {
				fromDt = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
				toDt = Utils.convertToSQLDate(filterData.getToDate(), Utils.regEUDateFormat);
			}

			String[] dynParams = new String[2];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParams[0] = " tp.position_code ";
			} else {
				dynParams[0] = " tp.position_title ";
			}			
			ArrayList<String> dynamicContent = new ArrayList<String>();
			
			dynParams[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
		
			if (!Utils.isBlankOrNull(filterData.getDepartmentId())) {
				dynParams[1] += " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParams[1] += " AND tp.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParams[1] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}

			dq = new DBPreparedQuery("dReportManager_GetMonthlyJoiningDetailsReport", dynParams);			
			dq.setDate(1, fromDt);
			dq.setDate(2, toDt);
			int cnt = 3;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			result = dq.getResult();
		} catch (Exception exep) {
			TPLogger.getLogger().error("Error while populating  populatePositions ", exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public ArrayList getInterviewListData(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[3];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " tp.position_code ";
			} else {
				dynParam[0] = " tp.position_title ";
			}			
			dynParam[1] = "";
			dynParam[2] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			ArrayList<String> dynamicContent2 = new ArrayList<String>();

			String users = filterData.getInterviewers();
			if (!Utils.isBlankOrNull(users)) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(users, dynamicContent);
				dynParam[1] += " WHERE t.attendee_id IN(" + qMarks + ") ";
			}

			Date toDate = null;
			if (!Utils.isBlankOrNull(filterData.getToDate())) {
				toDate = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
				if (toDate != null) {
					toDate = Utils.adjustDateBy(toDate, Calendar.DATE, 1);
				}
				dynParam[2] += " AND tapp.appointment_from_date <= ? ";
				dynamicContent2.add(Utils.getDateConvertedToString(toDate, Utils.redYYYYMMDDFormat));
			}
			dynParam[2] += PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent2, permissionSet);
			
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParam[2] += " AND tapp.applicant_id in (SELECT applicant_id FROM tp_applicants WHERE is_confidential=?) ";
				dynamicContent2.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			dq = new DBPreparedQuery("dReportManager_InterviewList", dynParam);
			dq.setString(1, SelectionProcessConstants.PHONE_INVALID);
			dq.setString(2, SelectionProcessConstants.PHONE_INVALID);
			dq.setString(3, SelectionProcessConstants.PHONE_INVALID);
			dq.setDate(4, Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat));

			int cnt = 5;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			for (int i = 0; i < dynamicContent2.size(); i++) {
				dq.setString(cnt++, dynamicContent2.get(i));
			}

			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for the interview list.", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	/**
	 * @param filterData
	 * @param userId
	 * @param permissionSet
	 * @return
	 */
	public ArrayList getInterviewListStatusData(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String appointmentIds = "";

			dynParams[0]="";	
			if(!Utils.isBlankOrNull(filterData.getDepartmentId()) ){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getDepartmentId(), dynamicContent);
				dynParams[0] += " AND tdept.dept_id IN (" + qMarks + ") ";
			}
			if(!Utils.isBlankOrNull(filterData.getPositionId())){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getPositionId(), dynamicContent);
				dynParams[0] += " AND tp.position_id IN (" + qMarks + ") ";				
			}else if (!Utils.isBlankOrNull(filterData.getPositionFilter()) & filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[0] += " AND tp.position_status IN (" + PositionConstants.POSITION_STATUS_OPENED + ") ";
			}else if (!Utils.isBlankOrNull(filterData.getPositionFilter()) & filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParams[0] += " AND tp.position_status IN ("+PositionConstants.POSITION_STATUS_OPENED+","+PositionConstants.POSITION_STATUS_HOLD+") ";
			}
			if (!Utils.isBlankOrNull(filterData.getUsers())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				dynParams[0] += " AND tu.user_id in (" + qMarks + ") ";
			}
			if (!Utils.isBlankOrNull(filterData.getInterviewers())) {
				appointmentIds=getAppointmentIdsForSelectedInterviewers(filterData.getInterviewers());
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(appointmentIds, dynamicContent);
				dynParams[0] += " AND tapp.appointment_id in (" + qMarks + ") ";
			}
			if (!Utils.isBlankOrNull(filterData.getStepIds())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getStepIds(), dynamicContent);
				dynParams[0] += " AND  tps.step_id in (" + qMarks + ") ";
			}
			if(!Utils.isBlankOrNull(filterData.getFromDate())){
				Date fromDt = Utils.convertToDate(filterData.getFromDate(), Utils.regEUDateFormat);
				dynParams[0] += " AND tapp.appointment_from_date > ?";
				dynamicContent.add(Utils.getDateConvertedToString(fromDt, Utils.redYYYYMMDDFormat));
			}
			if(!Utils.isBlankOrNull(filterData.getToDate())){
				Date toDt = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
				if(toDt!=null){
					toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
				}
				dynParams[0] += " AND tapp.appointment_from_date <= ?" + " ORDER BY tapp.appointment_from_date";
				dynamicContent.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
			}
			dq = new DBPreparedQuery("dReportManager_InterviewListStatus", dynParams);
			int cnt=1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, Integer.toString(CalendarConstants.APPOINTMENT_STATUS_TENTATIVE));
			dq.setString(cnt++, SelectionProcessConstants.STEP_REPEAT);
			for(int i=0;i<dynamicContent.size();i++){
				dq.setString(cnt++,dynamicContent.get(i));
			}
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for the interview list.", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	/**
	 * @param filterData
	 * @param userId
	 * @param permissionSet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<CandidateTATData> getClosedPositionTATData(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList<CandidateTATData> result = new ArrayList<CandidateTATData>();
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = {""};
			ArrayList<String> dynamicContent = new ArrayList<String>();

			java.sql.Date fromDt = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
			Date toDt = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
			toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
			
			if(!Utils.isBlankOrNull(filterData.getDepartmentId())){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getDepartmentId(), dynamicContent);
				dynParams[0] += "AND tp.dept_id IN (" + qMarks + ") ";
			}
			if(!Utils.isBlankOrNull(filterData.getPositionId())){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getPositionId(), dynamicContent);
				dynParams[0] += "AND tp.position_id IN (" + qMarks + ") ";				
			}
			if (!Utils.isBlankOrNull(filterData.getPositionFilter())) {
				if(filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
					dynParams[0] += " AND tp.position_status IN (" + PositionConstants.POSITION_STATUS_OPENED + ") ";
				} else if(filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
					dynParams[0] += " AND tp.position_status IN (" + PositionConstants.POSITION_STATUS_OPENED 
							+ "," + PositionConstants.POSITION_STATUS_HOLD + ") ";
				}
			}
			
			dq = new DBPreparedQuery("dReportManager_ClosedPositionTAT", dynParams);
			int cnt=1;
//			dq.setString(cnt++, PositionConstants.POSITION_STATUS_CLOSED);
			dq.setDate(cnt++, fromDt);
			dq.setDate(cnt++, Utils.convertDateToSQLDate(toDt));
			for(int i=0;i<dynamicContent.size();i++){
				dq.setString(cnt++,dynamicContent.get(i));
			}		
			
			result = dq.getResult();
			
			if(!Utils.isBlankOrNull(filterData.getFieldIds())) {
				updateResultSetWithStepDetails(result, filterData.getFieldIds());
			}
			updateResultSetWithPositionStatus(result);
			updateResultSetWithEducationDetails(result);
			updateResultSetWithApplicantFinalStatus(result);
			updateResultSetWithCustomFieldDetails(result);
			updateResultSetWithEmploymentDetails(result);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for closed position TAT report.", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	
	private void updateResultSetWithPositionStatus(
			ArrayList<CandidateTATData> result) {
		String status = "";
		MasterReportManager masterReportManager = new MasterReportManager();
		ArrayList<SimpleDataObject> joiningList = masterReportManager.getPositionIdsWithJoiningCandidates();
		try{
			if (result != null && result.size() > 0) {
				Iterator<CandidateTATData> iterator = result.iterator();
				
				while (iterator.hasNext()) {
					String positionStatus = null;
					CandidateTATData candidateTATData = (CandidateTATData) iterator.next();
					String positionId=""+candidateTATData.getPositionId();
					positionStatus=candidateTATData.getPositionStatus();
					if(Utils.isBlankOrNull(positionStatus)){
						candidateTATData.setAttribute("positionStatus"," ");
					}else{
						if(positionStatus.equals(PositionConstants.POSITION_STATUS_DELETED) || positionStatus.equals(PositionConstants.POSITION_STATUS_CLOSED))
						{
							boolean checkJoinCandidatePresent = false;
							for (int j = 0; j < joiningList.size(); j++) { // loop to get position with joining
								// candidates
								SimpleDataObject sDo = joiningList.get(j);
								String appPositionId = sDo.getString("applicantPositionId");
								if (positionId.equals(appPositionId)) {
									checkJoinCandidatePresent = true;
								}
							}
							if (checkJoinCandidatePresent) {
								status = "Closed";
							} else {
								status = "Cancelled";
							}
							
						
						}
						else if (positionStatus.equals(PositionConstants.POSITION_STATUS_OPENED)) {
							status = "Open";
						} else if (positionStatus.equals(PositionConstants.POSITION_STATUS_HOLD)) {
							status = "On Hold";
						}
						candidateTATData.setAttribute("positionStatus",status);
					}
				}
			}
		}
		catch(Exception ex){
			TPLogger.getLogger().debug(" Position status could not be fine correctly");
		}
	}
	
	public String getFeedbackComment(String processId) {
		DBPreparedQuery dq = null;
		String feedback = null;
		try {
			dq = new DBPreparedQuery("d_Reportmanager_traitComment");
			dq.setString(1, processId);
			feedback = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return feedback;
	}
	
	public String getPositionTitle(String processId) {
		DBPreparedQuery dq = null;
		String positionTitle = null;
		try {
			dq = new DBPreparedQuery("d_Reportmanager_Position_Title_For_Rejected");
			dq.setString(1, processId);
			positionTitle = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionTitle;
	}
	
	public String getProcessId(String applicantId) {
		DBPreparedQuery dq = null;
		String processId = null;
		try {
			dq = new DBPreparedQuery("dReportManager_ApplicantProcessData");
			dq.setString(1,applicantId);
			ArrayList<SimpleDataObject> processList = dq.getResult();
			if(processList!=null && processList.size()>0){
				SimpleDataObject sdo=processList.get(0);
				processId=sdo.getString("processId");
				//traitComment=sdo.getString("traitComment");
				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return processId;
	} 
	private void updateResultSetWithApplicantFinalStatus(
			ArrayList<CandidateTATData> result) {
		String positionStatus = "";
		String backout = ""; 
		String rejected = "Rejected";
		String joined = "Joined";
		String offered = "Offered";
		String inprocess = "Inprocess";
		String select = "SELECT";
		String reject = "REJECT";
		ApplicantManager applicantManager=new ApplicantManager();
		//Date SRFDate = null;
		//int aging = Integer.parseInt(Utils.getDateDiffenence(SRFDate, new Date()));
		try{
			if (result != null && result.size() > 0) {
				MasterReportManager masterReportManager = new MasterReportManager();
				Iterator<CandidateTATData> iterator = result.iterator();
			
				
				DBPreparedQuery dq = null;
				String[] interviewers = { "", "", "", "", "" };
				Date[] interviewDate = { null, null, null, null, null };
				String[] interviewStatus = { "", "", "", "", "" };
				boolean checkOfferDate = true;
				boolean checkShortlistedDate = true;
				PositionManager positionManager=new PositionManager();
				while(iterator.hasNext()){
					CandidateTATData candidateTATData = (CandidateTATData) iterator.next();
					String applicantId="";
					String positionId="";
					String processId="";
				
					String traitComment="";
					String applicantOfferStatus="";
					String finalStatus = "";
					String stepIdTo = "";
					String stepLevelTo = "";
					String stepLevelFrom = "";
					String rejectReason="";
					positionStatus=candidateTATData.getPositionStatus();
					applicantId=""+candidateTATData.getApplicantId();
					//positionId=""+candidateTATData.getApplicantPositionId();
					 positionId=""+candidateTATData.getPositionId();
					rejectReason="";
					applicantOfferStatus="";
					String positionStepName="";
					String candidateFinalStatus="";
					dq = new DBPreparedQuery("dReportManager_ApplicantProcessData");
					dq.setString(1,""+candidateTATData.getApplicantId());
					
					ArrayList<SimpleDataObject> processList = dq.getResult();
					if(processList!=null && processList.size()>0){
						SimpleDataObject sdo=processList.get(0);
						processId=sdo.getString("processId");
						//traitComment=sdo.getString("traitComment");
						
					}
					
					
					if(!Utils.isBlankOrNull(processId)&&!Utils.isBlankOrNull(positionId)){
						//traitComment=getFeedbackComment(processId);
						
						ArrayList<MasterReportData> iData = masterReportManager.getApplicantInteractionData(applicantId, positionId, processId);
						traitComment=getFeedbackComment(processId);
						String title=getPositionTitle(processId);
						int count = 0; // counter to get only 5 scheduled steps
						for (int j = 0; j < iData.size(); j++) {
							MasterReportData interactionData = iData.get(j);
							 stepIdTo = interactionData.getPositionStepIdTo();
							 stepLevelTo = interactionData.getPositionStepLevelTo();
							 stepLevelFrom = interactionData.getPositionStepLevelFrom();
							
							 

							if (j == iData.size() - 1) { // last step to get final status
								if (!Utils.isBlankOrNull(stepIdTo)) {
									
									//traitComment=  applicantManager.getCandidateInterviewFeedback(applicantId, positionId, stepIdTo);
									if(!Utils.isBlankOrNull(traitComment)){
										traitComment=traitComment;
									
									}
									else{
										traitComment="";
									}
									
											candidateFinalStatus=positionManager.getPositionStepName(Integer.parseInt(stepIdTo));
											if(positionStatus.equals("Cancelled")){
												finalStatus = rejected;
												if(!Utils.isBlankOrNull(traitComment)){
													rejectReason=traitComment;
												}
												else{
												rejectReason="Position canceled";
												}
											}
											else if (stepIdTo.equals(SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT) || stepIdTo.equals(SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT)
													|| stepIdTo.equals(SelectionProcessConstants.STEP_NOT_ATTENDED) || stepIdTo.equals(SelectionProcessConstants.STEP_REJECT)) {
												finalStatus = rejected;
												//rejectedDate = interactionData.getProcessDateCreated();
												if(!Utils.isBlankOrNull(stepLevelFrom) && stepLevelFrom.equals(PositionConstants.STEP_LEVEL_ACCEPT)){
													applicantOfferStatus="Reject";
												}
												if(stepIdTo.equals(SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT)){
													finalStatus = rejected;
													if(!Utils.isBlankOrNull(traitComment)){
														rejectReason=traitComment;
													}
													else{
														rejectReason="Candidate Not intersted";
													}
												}
												if(stepIdTo.equals(SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT)){
													finalStatus = rejected;
													if(!Utils.isBlankOrNull(traitComment)){
														rejectReason=traitComment;
													}
													else{
														rejectReason="Position closed ";
													}
												}
												if(stepIdTo.equals(SelectionProcessConstants.STEP_NOT_ATTENDED)){
													finalStatus = rejected;
													if(!Utils.isBlankOrNull(traitComment)){
														rejectReason=traitComment;
													}
													else{
														rejectReason="candidate not attended";
													}
												}
												if(stepIdTo.equals(SelectionProcessConstants.STEP_REJECT)){
													finalStatus = rejected;
													if(!Utils.isBlankOrNull(traitComment)){
														rejectReason=traitComment;
													}
													else{
														rejectReason="candidate could not qualify";
													}
												}
											
											} 
											else if (stepIdTo.equals(SelectionProcessConstants.STEP_JOIN)) {
												finalStatus = joined;
												applicantOfferStatus="Offer accepted";
											
											}
											else if (stepIdTo.equals(SelectionProcessConstants.STEP_ON_HOLD)) {
												finalStatus = "ON HOLD";
											
											}
											else if(positionStatus.equals("Closed")&&!finalStatus.equals(joined)){
												finalStatus = rejected;
												
												if(!Utils.isBlankOrNull(traitComment)){
													rejectReason=traitComment;
												}
												else{
													rejectReason="Position closed ";
												}
											}
											else if(!Utils.isBlankOrNull(stepLevelTo) && stepLevelTo.equals(PositionConstants.STEP_LEVEL_ACCEPT)){
												finalStatus = candidateFinalStatus;
												if(candidateFinalStatus.contains("offer accept")||candidateFinalStatus.contains("offeraccept")){
													applicantOfferStatus="Offer accepted";
												}
												if (!Utils.isBlankOrNull(stepLevelFrom) && stepLevelFrom.equals(PositionConstants.STEP_LEVEL_ACCEPT)){
													applicantOfferStatus="Offer accepted";
												}
											}
											else if(!Utils.isBlankOrNull(stepLevelFrom) && stepLevelFrom.equals(PositionConstants.STEP_LEVEL_ACCEPT)){
												finalStatus = candidateFinalStatus;
												
											}
											else if(!Utils.isBlankOrNull(stepLevelTo) && stepLevelTo.equals(PositionConstants.STEP_LEVEL_SHORTLIST)){
												finalStatus = "Shortlisted";
												
											}
											
											else {
												if(!Utils.isBlankOrNull(candidateFinalStatus)){
												finalStatus = candidateFinalStatus+"InProgress";
												}
													}
											}
							}
						}
								
								
								

					}
				
					else{
						System.out.println("lets see");
					}
					candidateTATData.setAttribute("candidateStatus", finalStatus);
					candidateTATData.setAttribute("rejectReason", rejectReason);
					candidateTATData.setAttribute("comment", traitComment);
					candidateTATData.setAttribute("applicantOfferStatus", applicantOfferStatus);
				}
			}
			
	
		}
		 catch (SQLException e) {
				TPLogger.getLogger().error("Error while getting step details for joined candidates", e);
			}
		catch(Exception ex){
			TPLogger.getLogger().debug(" Position status could not be fine correctly");
		}
	}
	
	/**
	 * @param result
	 * @param fieldIds
	 */
	@SuppressWarnings("unchecked")
	private void updateResultSetWithEducationDetails(
			ArrayList<CandidateTATData> result) {
	
			try {
				DBPreparedQuery dq = null;
				if (result != null && result.size() > 0) {
					Iterator<CandidateTATData> iterator = result.iterator();
					
					while (iterator.hasNext()) {
						StringBuffer qualification = new StringBuffer();
						CandidateTATData candidateTATData = (CandidateTATData) iterator.next();
						
						dq = new DBPreparedQuery("dReportManager_ApplicantEducationInfoData");
						dq.setString(1,""+candidateTATData.getApplicantId());
						ArrayList<SimpleDataObject> degrees = dq.getResult();
						//candidateTATData.setAttribute("educationInfo", educationInfo);
						
						//ArrayList<SimpleDataObject> degrees = (ArrayList<SimpleDataObject>) candidateTATData.getAttribute("educationInfo");
						if (degrees != null && degrees.size() > 0) {
							for (int i = 0; i < degrees.size(); i++) {
								SimpleDataObject sDo = degrees.get(i);
								StringBuffer degree = new StringBuffer();
								if (!Utils.isBlankOrNull(sDo.getString("degree"))) {
									concatStringsBySeparator(degree, sDo.getString("degree"), null);
								}
								if (!Utils.isBlankOrNull(sDo.getString("institute")) || !Utils.isBlankOrNull(sDo.getString("yearOfPassing"))) {
									concatStringsBySeparator(degree, "(", " ");
									concatStringsBySeparator(degree, sDo.getString("institute"), null);
									concatStringsBySeparator(degree, sDo.getString("yearOfPassing"), ", ");
									concatStringsBySeparator(degree, ")", null);
								}
								concatStringsBySeparator(degree, sDo.getString("grade"), " ");
								concatStringsBySeparator(qualification, degree.toString(), " ");
							}
						}
						if(Utils.isBlankOrNull(qualification.toString())){
							candidateTATData.setAttribute("qualification"," ");
						}else{
							candidateTATData.setAttribute("qualification",qualification.toString().replaceAll("&", "&amp;"));
						}
					}
				}
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error while getting step details for joined candidates", e);
			}
		
	}

	/**
	 * @param result
	 * @param fieldIds
	 */
	@SuppressWarnings("unchecked")
	private void updateResultSetWithEmploymentDetails(
			ArrayList<CandidateTATData> result) {
	
			try {
			
				if (result != null && result.size() > 0) {
					Iterator<CandidateTATData> iterator = result.iterator();
					
					while (iterator.hasNext()) {
						
						CandidateTATData candidateTATData = (CandidateTATData) iterator.next();
						ApplicantManager applicantManager=new ApplicantManager();
						ArrayList<EmploymentHistoryData> employmentHistoryDetails =applicantManager.getEmploymentHistoryInfo(""+candidateTATData.getApplicantId());
					
					if(!employmentHistoryDetails.isEmpty()){
						try{
							Collections.sort(employmentHistoryDetails,EmploymentHistoryData.REVERSE_CHRONOLOGICAL );
						}catch (Exception e) {
							TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
						}
						String currentDesignation="";
						currentDesignation=employmentHistoryDetails.get(0).getDesignationName();
						
						if(Utils.isBlankOrNull(currentDesignation)){
							candidateTATData.setAttribute("currentDesignation"," ");
						}else{
							candidateTATData.setAttribute("currentDesignation",currentDesignation);
						}
						
					}
					
						
						
					}
				}
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while getting step details for joined candidates", e);
			}
		
	}
	/**
	 * @param result
	 * @param fieldIds
	 */
	@SuppressWarnings("unchecked")
	private void updateResultSetWithStepDetails(
			ArrayList<CandidateTATData> result, String fieldIds) {
		String applicantIds = "";	
		String offerAcceptedDate="";
		Map<String, CandidateTATData> map = new HashMap<String, CandidateTATData>();
		for(CandidateTATData candidateTATData: result) {
			if(candidateTATData.getApplicantId() != 0) {				
				map.put(""+candidateTATData.getApplicantId(), candidateTATData);
				if(applicantIds != "") {
					applicantIds += ",";
				}
				applicantIds += candidateTATData.getApplicantId();
			}			
		}
		
		ArrayList<String> dynamicContent = new ArrayList<String>();
		String[] dynParams = new String[1];
		ArrayList<SimpleDataObject> stepDetails = new ArrayList<SimpleDataObject>();
		if(!Utils.isBlankOrNull(applicantIds)) {
			try {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(applicantIds, dynamicContent);
				dynParams[0] = " AND tasp.applicant_id IN (" + qMarks + ") ";
				DBPreparedQuery dq = null;
				dq = new DBPreparedQuery("dReportManager_GetClosedPositionTATStepDetails", dynParams);
				int cnt = 1;
				//dq.setInt(cnt++, CalendarConstants.APPOINTMENT_STATUS_NOSHOW);
				//dq.setInt(cnt++, CalendarConstants.APPOINTMENT_STATUS_NOSHOW);
				for (String t : dynamicContent) {
					dq.setString(cnt++, t);
				}
				stepDetails = dq.getResult();
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error while getting step details for joined candidates", e);
			}
		}
		
		for(SimpleDataObject stepDetail : stepDetails) {
			String applicantId = stepDetail.getString("applicantId");
			String stepName = stepDetail.getString("step");
			CandidateTATData cand = map.get(applicantId);
			
			if(!Utils.isBlankOrNull(stepName)){
				if(stepName.toLowerCase().contains("interview")){
					cand.setAttribute(stepName + " Date", stepDetail.getString("stepStartDate"));
					cand.setAttribute(stepName + " Duration", stepDetail.getInt("stepDuration"));
					String statusStep=stepDetail.getString("stepStatus");
					if(!Utils.isBlankOrNull(statusStep)){
						if(statusStep.trim().equalsIgnoreCase("Happened")){
							//applicant_id,position_id,position_step_id_from from tp_applicant_selectionProcess
							String toStepId=stepDetail.getString("positionStepIdTo");
							String id=stepDetail.getString("applicantId");
							String positionId=stepDetail.getString("positionId");
							String stepTitle=getStepTitleForInterview(id,positionId,toStepId);
							cand.setAttribute(stepName + " Status", stepTitle);
						}
						else if(statusStep.trim().equalsIgnoreCase("No Show")||statusStep.trim().contains("No")){
							String toStepId=stepDetail.getString("positionStepIdTo");
							String id=stepDetail.getString("applicantId");
							String positionId=stepDetail.getString("positionId");
							String stepTitle=getStepTitleForInterview(id,positionId,toStepId);
							if(!Utils.isBlankOrNull(stepTitle)){
								cand.setAttribute(stepName + " Status", stepTitle);
							}
							else{
							cand.setAttribute(stepName + " Status", stepDetail.getString("stepStatus"));
							}
						}
						else if(statusStep.trim().equalsIgnoreCase("Reschedule Interview")){
							
							
								cand.setAttribute(stepName + " Status", " ");
							
							
						}
						else if(statusStep.trim().equalsIgnoreCase("Confirm Interview Schedule")){
							String toStepId=stepDetail.getString("positionStepIdTo");
							String id=stepDetail.getString("applicantId");
							String positionId=stepDetail.getString("positionId");
							String stepTitle=getStepTitleForInterview(id,positionId,toStepId);
							if(!Utils.isBlankOrNull(stepTitle)){
								cand.setAttribute(stepName + " Status", " ");
							}
							else{
							cand.setAttribute(stepName + " Status", " ");
							}
						}
						else if(statusStep.trim().equalsIgnoreCase("Tentative Interview Schedule")){
							cand.setAttribute(stepName + " Status", " ");
						}
					}
					else{
					cand.setAttribute(stepName + " Status", stepDetail.getString("stepStatus"));
					}
					cand.setAttribute(stepName + " Interviewer", stepDetail.getString("interviewer"));
				}
				else if(stepName.toLowerCase().contains("offer accept")||stepName.toLowerCase().contains("offeraccept")){
					
					cand.setAttribute("offerAcceptedDate", stepDetail.getString("stepStartDate"));
					cand.setAttribute(stepName + " Date", stepDetail.getString("stepStartDate"));
					cand.setAttribute(stepName + " Duration", stepDetail.getInt("stepDuration"));
				}
				else{
					cand.setAttribute(stepName + " Date", stepDetail.getString("stepStartDate"));
					cand.setAttribute(stepName + " Duration", stepDetail.getInt("stepDuration"));
					//cand.setAttribute(stepName + " Interviewer", stepDetail.getString("interviewer"));
				}
			}
			
			
		}
	}
	
	public String getStepTitleForInterview(String applicantId,String positionId,String toStepId) {
		DBPreparedQuery dq = null;
		String positionStepTitle = null;
		try {
			dq = new DBPreparedQuery("d_Reportmanager_Position_Title_For_Interview");
			dq.setString(1, applicantId);
			dq.setString(2, toStepId);
			dq.setString(3, positionId);
			
			positionStepTitle = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionStepTitle;
	}
	
	
	private void updateResultSetWithCustomFieldDetails(
			ArrayList<CandidateTATData> result) {
	CustomFieldData data = null;
	if (result != null && result.size() > 0) {
		Iterator<CandidateTATData> iterator = result.iterator();
		
		while (iterator.hasNext()) {
			StringBuffer qualification = new StringBuffer();
			CandidateTATData candidateTATData = (CandidateTATData) iterator.next();
			String applicantId=""+candidateTATData.getApplicantId();
	CustomFieldManager customFieldManager = new CustomFieldManager();
	ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(applicantId, CustomFieldConstants.ENTITY_TYPE_APPLICANT);
	if(customFields != null && customFields.size() > 0) {
		for(int i = 0; i < customFields.size(); i++) {
			data = customFields.get(i);
			if(data.getFieldName().equals("app_RelExp")){
			candidateTATData.setAttribute("realExperience", data.getDisplayValue());
			break;
			}
		}
	}
		}
	}
	}
	/**
	 * @param interviewers
	 * @return appointmentIds
	 */
	private String getAppointmentIdsForSelectedInterviewers(String interviewers) {
		String appointmentIds = "";
		ArrayList<String> dynamicContent1 = new ArrayList<String>();
		String[] dynParams = new String[1];
		if (!Utils.isBlankOrNull(interviewers)) {
			try {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(interviewers, dynamicContent1);
				dynParams[0] = "WHERE t.attendee_id IN (" + qMarks + ") ";
				DBPreparedQuery dq = null;
				dq = new DBPreparedQuery("dReportManager_GetInterviewerList", dynParams);
				int cnt = 1;
				for (String t : dynamicContent1) {
					dq.setString(cnt++, t);
				}
				appointmentIds = dq.getStringResult();				

			} catch (SQLException e) {
				TPLogger.getLogger().error("Error while getting appointmentIds for interviewers",e);
			}
		}

		return appointmentIds;
	}
	
	public ArrayList getUserActivityReportData(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[6];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " tpos.position_code ";
			} else {
				dynParam[0] = " tpos.position_title ";
			}			
			
			ArrayList<String> dynamicContent1 = new ArrayList<String>();
			ArrayList<String> dynamicContent2 = new ArrayList<String>();
			ArrayList<String> dynamicContent3 = new ArrayList<String>();
			ArrayList<String> dynamicContent4 = new ArrayList<String>();
			ArrayList<String> dynamicContent5 = new ArrayList<String>();
			
			dynParam[3] = "where 1 "+PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "t3.position_id", dynamicContent3, permissionSet);
						
			dynParam[5] = " ";
			if (!Utils.isBlankOrNull(filterData.getInterviewers())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getInterviewers(), dynamicContent5);
				dynParam[5] += " AND tu.user_id IN (" + qMarks + ") ";
			}
			if (ReportConstants.FILTER_ALL_POSITIONS.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[5] += " AND (t4.position_id != -1 or t4.position_id is not null) ";
			} else if (ReportConstants.FILTER_OPEN_POSITIONS.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[5] += " AND t4.position_id in (select position_id from tp_positions tp where tp.position_status = ? ) ";
				dynamicContent5.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[5] += " AND t4.position_id in (select position_id from tp_positions tp where tp.position_status = ? OR tp.position_status = ? ) ";
				dynamicContent5.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent5.add(PositionConstants.POSITION_STATUS_HOLD);
			} else if (ReportConstants.FILTER_SPECIFIC_POSITION.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[5] += " AND t4.position_id = ? ";
				dynamicContent5.add(filterData.getPositionId());
			} else if (ReportConstants.FILTER_SPECIFIC_DEPARTMENT.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[5] += " AND (select tt.dept_id from tp_positions tt where tt.position_id = t4.position_id) = ? ";
				dynamicContent5.add(filterData.getDepartmentId());
			}
			if (!Utils.isBlankOrNull(filterData.getStages())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getStages(), dynamicContent5);
				dynParam[5] += " AND t4.stage in (" + qMarks + ") ";
			}
			if (!Utils.isBlankOrNull(filterData.getActivities())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getActivities(), dynamicContent5);
				dynParam[5] += " AND t4.interaction_type in (" + qMarks + ") ";
			}
			
			dynParam[1] = " ";
			dynParam[2] = " ";
			dynParam[4] = " ";			
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParam[1] += " AND ta.applicant_id in (SELECT applicant_id FROM tp_applicants WHERE is_confidential=?) ";
				dynamicContent1.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
				
				dynParam[2] += " AND tasp.applicant_id in (SELECT applicant_id FROM tp_applicants WHERE is_confidential=?) ";
				dynamicContent2.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
				
				dynParam[4] += " AND ta.applicant_id in (SELECT applicant_id FROM tp_applicants WHERE is_confidential=?) ";
				dynamicContent4.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			Date toDt = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
			toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);

			dq = new DBPreparedQuery("dReportManager_GetUserActivityData", dynParam);
			int cnt = 1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, InboxConstants.INBOX_FOLDER_SENT);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_EMAIL_SENT);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED);
			dq.setString(cnt++, InboxConstants.INBOX_FOLDER_SENT);
			dq.setString(cnt++, "Email Sent");
			dq.setString(cnt++, "Email Received");
			dq.setString(cnt++, InboxConstants.INBOX_FOLDER_SENT);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_PHONE);
			dq.setString(cnt++, "Logged Call");
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_NOTE);
			dq.setString(cnt++, "Added Note");
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_SMS);
			dq.setString(cnt++, "SMS Sent");
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_STATUS_MESSAGE);
			dq.setString(cnt++, "Changed status to");
			dq.setString(cnt++, SelectionProcessConstants.STATUS_USER_GENERATED);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_APPOINTMENTS);
			dq.setString(cnt++, "Scheduled");
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			
			for (int i = 0; i < dynamicContent1.size(); i++) {
				dq.setString(cnt++, dynamicContent1.get(i));
			}
			
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_INTERVIEW);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REPEAT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			
			for (int i = 0; i < dynamicContent2.size(); i++) {
				dq.setString(cnt++, dynamicContent2.get(i));
			}
			for (int i = 0; i < dynamicContent3.size(); i++) {
				dq.setString(cnt++, dynamicContent3.get(i));
			}
			
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_IMPORT);
			dq.setString(cnt++, "Imported");
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_IMPORT);
			
			for (int i = 0; i < dynamicContent4.size(); i++) {
				dq.setString(cnt++, dynamicContent4.get(i));
			}
			
			dq.setDate(cnt++, Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat));
			dq.setDate(cnt++, Utils.convertDateToSQLDate(toDt));
			
			for (int i = 0; i < dynamicContent5.size(); i++) {
				dq.setString(cnt++, dynamicContent5.get(i));
			}
			
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for the user activity report.", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public ArrayList getPositionActivityReportData(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[5];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " tpos.position_code ";
				dynParam[1] = " tpp.position_code ";
				dynParam[2] = " tpp.position_code ";	
			} else {
				dynParam[0] = " tpos.position_title ";
				dynParam[1] = " tpp.position_title ";
				dynParam[2] = " tpp.position_title ";	
			}
			ArrayList<String> dynamicContent = new ArrayList<String>();
			ArrayList<String> dynamicContent2 = new ArrayList<String>();
			dynParam[3] = "where 1 "+PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "t3.position_id", dynamicContent, permissionSet);
					
			dynParam[4] = "  ";
			if (!Utils.isBlankOrNull(filterData.getInterviewers())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getInterviewers(), dynamicContent2);
				dynParam[4] += " AND tu.user_id IN (" + qMarks + ") ";
			}

			if (ReportConstants.FILTER_ALL_POSITIONS.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[4] += " AND (t4.position_id != -1 or t4.position_id is not null) ";
			} else if (ReportConstants.FILTER_OPEN_POSITIONS.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[4] += " AND t4.position_id in (select position_id from tp_positions tp where tp.position_status = ? ) ";
				dynamicContent2.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[4] += " AND t4.position_id in (select position_id from tp_positions tp where tp.position_status = ? OR tp.position_status = ? ) ";
				dynamicContent2.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent2.add(PositionConstants.POSITION_STATUS_HOLD);
			} else if (ReportConstants.FILTER_SPECIFIC_POSITION.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[4] += " AND t4.position_id = ? ";
				dynamicContent2.add(filterData.getPositionId());
			} else if (ReportConstants.FILTER_SPECIFIC_DEPARTMENT.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[4] += " AND (select tt.dept_id from tp_positions tt where tt.position_id = t4.position_id) = ? ";
				dynamicContent2.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParam[4] += " AND (select tt.sub_dept_id from tp_positions tt where tt.position_id = t4.position_id) = ? ";
					dynamicContent2.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParam[4] += " AND (select tt.sub_sub_dept_id from tp_positions tt where tt.position_id = t4.position_id) = ? ";
						dynamicContent2.add(filterData.getSubSubDepartmentId());
					}
				}
			}
			if (!Utils.isBlankOrNull(filterData.getStages())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getStages(), dynamicContent2);
				dynParam[4] += " AND t4.stage in (" + qMarks + ") ";
			}
			
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParam[4] += " AND ta.is_confidential=? ";
				dynamicContent2.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			Date toDt = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
			toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);

			dq = new DBPreparedQuery("dReportManager_GetPositionActivityData", dynParam);
			int cnt = 1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, InboxConstants.INBOX_FOLDER_SENT);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_EMAIL_SENT);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED);
			dq.setString(cnt++, InboxConstants.INBOX_FOLDER_SENT);
			dq.setString(cnt++, "Email Sent");
			dq.setString(cnt++, "Email Received");
			dq.setString(cnt++, InboxConstants.INBOX_FOLDER_SENT);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_PHONE);
			dq.setString(cnt++, "Logged Call");
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_NOTE);
			dq.setString(cnt++, "Added Note");
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_SMS);
			dq.setString(cnt++, "SMS Sent");
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_STATUS_MESSAGE);
			dq.setString(cnt++, "Changed status to");
			dq.setString(cnt++, SelectionProcessConstants.STATUS_USER_GENERATED);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_APPOINTMENTS);
			dq.setString(cnt++, "Scheduled");
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_INTERVIEW);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REPEAT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setDate(cnt++, Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat));
			dq.setDate(cnt++, Utils.convertDateToSQLDate(toDt));
			for (int i = 0; i < dynamicContent2.size(); i++) {
				dq.setString(cnt++, dynamicContent2.get(i));
			}
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for the position activity report.", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public ArrayList<SourcewiseImportView> getSourcewiseImportData(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList<SourcewiseImportView> sourcewiseImportData = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
				
			dynParam[0] = PositionWithRightsClause.getClauseForUserInList(userId, "ta.user_id", dynamicContent, permissionSet);
	
			if (!Utils.isBlankOrNull(filterData.getFromDate())) {
				dynParam[0] += " and ta.applicant_date_created >= ? ";
				dynamicContent.add(Utils.getDateConvertedToString(Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat), Utils.redYYYYMMDDFormat));
			}
			Date toDt = filterData.getConvertedToDate();
			if (toDt != null) {
				toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
			}

			dynParam[0] += " and ta.applicant_date_created <= ? ";
			dynamicContent.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
			

			if (!Utils.isBlankOrNull(filterData.getSourceCategoryId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getSourceCategoryId(), dynamicContent);				
				dynParam[0] += " AND tst.source_type_id IN (" + qMarks + ") ";				
			} else {
				dynParam[0] += "";
			}
			
			
			if (!Utils.isBlankOrNull(filterData.getSourceId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getSourceId(), dynamicContent);
				dynParam[0] += " AND ta.source_id IN (" + qMarks + ") ";
			} else {
				dynParam[0] += "";
			}			
			
			dq = new DBPreparedQuery("dGetSourcewiseImportData", dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}

			sourcewiseImportData = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the sourcewise import data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sourcewiseImportData;
	}

	private ArrayList getApplicantDetailsData(FilterData filterData, PermissionSet permissionSet) {
		ArrayList applicantDetailsData = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[7];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(filterData.getFromDate())) {
				dynParam[1] = " AND ta.applicant_date_created >= '" + Utils.getDateConvertedToString(Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat), Utils.redYYYYMMDDFormat) + "' ";
			} else {
				dynParam[1] = "";
			}

			Date toDt = filterData.getConvertedToDate();
			if (toDt != null) {
				toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
			}
			
			dynParam[1] += " AND ta.applicant_date_created <= '" + Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat) + "' ";		
			
			if (!Utils.isBlankOrNull(filterData.getSourceCategoryId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getSourceCategoryId(), dynamicContent);				
				dynParam[2] = " AND tst.source_type_id IN (" + qMarks + ") ";				
			} else {
				dynParam[2] = "";
			}
			
			if (!Utils.isBlankOrNull(filterData.getSourceId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getSourceId(), dynamicContent);
				dynParam[3] = " AND ta.source_id IN (" + qMarks + ") ";
			} else {
				dynParam[3] = "";
			}

			if (!Utils.isBlankOrNull(filterData.getMinExp())) {
				dynParam[4] = " AND format(PERIOD_DIFF(EXTRACT(YEAR_MONTH FROM now()),EXTRACT(YEAR_MONTH FROM ta.applicant_working_since))/12, 1) >= " + filterData.getMinExp();
			} else {
				dynParam[4] = "";
			}
			if (!Utils.isBlankOrNull(filterData.getMaxExp())) {
				dynParam[4] += " AND format(PERIOD_DIFF(EXTRACT(YEAR_MONTH FROM now()),EXTRACT(YEAR_MONTH FROM ta.applicant_working_since))/12, 1) <= " + filterData.getMaxExp();
			}
			if (!Utils.isBlankOrNull(filterData.getDegreeIds())) {
				dynParam[5] = " AND ta.applicant_id in (select taei.applicant_id from tp_applicant_educational_info taei where taei.degree_id in (" + filterData.getDegreeIds() + "))";
			} else {
				dynParam[5] = "";
			}
			
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParam[6] = " AND ta.applicant_id in (SELECT applicant_id FROM tp_applicants WHERE is_confidential=?) ";
				dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}else{
				dynParam[6] = "";
			}			
			
			if (!Utils.isBlankOrNull(dynParam[1]) || !Utils.isBlankOrNull(dynParam[2]) || !Utils.isBlankOrNull(dynParam[3]) || !Utils.isBlankOrNull(dynParam[4]) || !Utils.isBlankOrNull(dynParam[5])) {
				dynParam[0] = " WHERE 1 ";
			} else {
				dynParam[0] = "";
			}
			dq = new DBPreparedQuery("dReportManager_ApplicantDetailsData", dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			applicantDetailsData = dq.getResult();
			if (applicantDetailsData != null && applicantDetailsData.size() > 0) {
				Iterator iterator = applicantDetailsData.iterator();
				while (iterator.hasNext()) {
					SimpleDataObject sDo = (SimpleDataObject) iterator.next();
					dq = new DBPreparedQuery("dReportManager_ApplicantEducationInfoData");
					dq.setString(1, sDo.getString("applicantId"));
					ArrayList educationInfo = dq.getResult();
					sDo.setAttribute("educationInfo", educationInfo);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the applicant details data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicantDetailsData;
	}

	public ArrayList getApplicantDetailsReportData(FilterData filterdata, PermissionSet permissionSet) {
		ArrayList applicantDetailsReportData = new ArrayList<ApplicantDetailsView>();
		ArrayList applicantDetailsData = getApplicantDetailsData(filterdata, permissionSet);
		if (applicantDetailsData != null && applicantDetailsData.size() > 0) {
			Iterator<SimpleDataObject> itr = applicantDetailsData.iterator();
			while (itr.hasNext()) {
				SimpleDataObject obj = itr.next();
				ApplicantDetailsView view = new ApplicantDetailsView();
				// Name
				StringBuffer name = new StringBuffer();
				// concatStringsBySeparator(name, "<b>" +
				// obj.getString("applicantName") + "</b>", null);
				view.setName(obj.getString("applicantName"));
				
				if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA()) ){
					view.setSourceCategory(obj.getString("sourceCategory"));
					view.setSource(obj.getString("source"));
				}else{
					view.setSourceCategory(GlobalConstants.CONFIDENTIAL_CHARACTER);
					view.setSource(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}
				
				if(Utils.isBlankOrNull(obj.getString("currentEmployer"))){
					view.setCurrentEmployer(obj.getString("currentEmployer"));
				}else{
					view.setCurrentEmployer(obj.getString("currentEmployer").replaceAll("&", "&amp;"));
				}

				// concatStringsBySeparator(name, obj.getString("source"),
				// "<br/>");
				// concatStringsBySeparator(name,
				// obj.getString("currentEmployer"), "<br/><br/>");
				String experience = "";
				if (!Utils.isBlankOrNull(obj.getString("experience"))) {
					experience = obj.getString("experience");
				}
				view.setExperience(experience);

				// Qualification
				StringBuffer qualification = new StringBuffer();
				ArrayList<SimpleDataObject> degrees = (ArrayList<SimpleDataObject>) obj.getAttribute("educationInfo");
				if (degrees != null && degrees.size() > 0) {
					for (int i = 0; i < degrees.size(); i++) {
						SimpleDataObject sDo = degrees.get(i);
						StringBuffer degree = new StringBuffer();
						if (!Utils.isBlankOrNull(sDo.getString("degree"))) {
							concatStringsBySeparator(degree, sDo.getString("degree"), null);
						}
						if (!Utils.isBlankOrNull(sDo.getString("institute")) || !Utils.isBlankOrNull(sDo.getString("yearOfPassing"))) {
							concatStringsBySeparator(degree, "(", " ");
							concatStringsBySeparator(degree, sDo.getString("institute"), null);
							concatStringsBySeparator(degree, sDo.getString("yearOfPassing"), ", ");
							concatStringsBySeparator(degree, ")", null);
						}
						concatStringsBySeparator(degree, sDo.getString("grade"), " ");
						concatStringsBySeparator(qualification, degree.toString(), "<br/><br/>");
					}
				}
				if(Utils.isBlankOrNull(qualification.toString())){
					view.setQualification(qualification.toString());
				}else{
					view.setQualification(qualification.toString().replaceAll("&", "&amp;"));
				}
				

				// Location
				view.setLocation(obj.getString("location"));

				// Email
				StringBuffer email = new StringBuffer();
				concatStringsBySeparator(email, obj.getString("email1"), null);
				concatStringsBySeparator(email, obj.getString("email2"), "<br/>");
				view.setEmail(email.toString());

				// Phone
				StringBuffer phone = new StringBuffer();
				if (!Utils.isBlankOrNull(obj.getString("cellPhone"))) {
					concatStringsBySeparator(phone, obj.getString("cellPhone") + "(M)", null);
				}
				concatStringsBySeparator(phone, obj.getString("workPhone"), "<br/><br/>");
				concatStringsBySeparator(phone, obj.getString("homePhone"), "<br/><br/>");
				view.setPhone(phone.toString());

				// Skills
				if(Utils.isBlankOrNull(obj.getString("skills"))){
					view.setSkills(obj.getString("skills"));
				}else{
					view.setSkills(obj.getString("skills").replaceAll("&", "&amp;"));
				}				
				applicantDetailsReportData.add(view);
			}
		}
		return applicantDetailsReportData;
	}

	public ArrayList getcallListReport(FilterData filterData, PermissionSet permissionSet) {
		ArrayList applicants = new ArrayList();
		String userId = filterData.getUserId();
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParam[0] = "";
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParam[0] += " AND ta.is_confidential = ? ";
				dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			
//			String orderBy = "";
//			if (filterData.getOrderBy().equals(ReportConstants.SORT_BY_POSITION_NAME)) {
//				orderBy = " tpos.position_title";
//			} else if (filterData.getOrderBy().equals(ReportConstants.SORT_BY_CANDIDATE_NAME)) {
//				orderBy = " ta.applicant_name";
//			} else {
//				orderBy = "step";
//			}
//			dynParam[0] = orderBy;

			dq = new DBPreparedQuery("dReportManager_callListReport", dynParam);
			dq.setId(1, userId);
			applicants = dq.getResult();
			for (int i = 0; i < applicants.size(); i++) {
				callListReportView cRo = (callListReportView) applicants.get(i);
				/* Get ApplicantId from applicants arrayList */
				String applicantId = cRo.getId("applicantId");
				String applicantPositionId = cRo.getId("applicantPositionId");
				try {
					dq = new DBPreparedQuery("dReportManager_GetPositionSkillsInfo");
					dq.setId(1, applicantPositionId);
					int cnt = 2;
					for (int k = 0; k < dynamicContent.size(); k++) {
						dq.setString(cnt++, dynamicContent.get(k));
					}
					ArrayList skills = dq.getResult();
					cRo.setSkills(skills);
				} catch (Exception e) {
					TPLogger.getLogger().error("Error in get Position Skills  for Call List", e);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating report for Call List", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}

	public ArrayList<SimpleDataObject> getSourceTitles() {
		ArrayList<SimpleDataObject> result = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dGetSourceTitles");
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while getting source titles", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public ArrayList getImportReport(FilterData filterData, PermissionSet permissionSet) {
		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			Date toDt = null;
			Date frmDt = null;
			if (!Utils.isBlankOrNull(filterData.getToDate())) {
				toDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
				toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
			}
			if (!Utils.isBlankOrNull(filterData.getFromDate())) {
				frmDt = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			}

			String[] dynParam = new String[3];			
			if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA()) ){  
				dynParam[0] = "ts.source_title";
			}else{
				dynParam[0] = "'"+GlobalConstants.CONFIDENTIAL_CHARACTER+"' as source_title";
			}
			dynParam[1] = " ";
			dynParam[2] = " ";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (filterData.getFilterId().equals(ReportConstants.FILTER_IMPORTED_BY)) {
				dynParam[1] += " AND tu.user_id= ? ";
				dynamicContent.add(filterData.getUserId());
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SOURCE)) {
				dynParam[1] += " AND ts.source_id = ? ";
				dynamicContent.add(filterData.getSourceId());
			}
			if (frmDt != null) {
				dynParam[1] += " AND ta.applicant_date_created >= ? ";
				dynamicContent.add(Utils.getDateConvertedToString(frmDt, Utils.redYYYYMMDDFormat));
			}
			if (toDt != null) {
				dynParam[1] += " AND ta.applicant_date_created <= ? ";
				dynamicContent.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
			}

			if (filterData.getOrderBy().equals(ReportConstants.SORT_BY_IMPORTED_BY)) {
				dynParam[2] += " tu.user_name";
			} else if (filterData.getOrderBy().equals(ReportConstants.SORT_BY_SOURCE)) {
				dynParam[2] += " ts.source_title";
			} else {
				dynParam[2] += " ta.applicant_date_created ";
			}
			dq = new DBPreparedQuery("dReportManager_GetImportReport", dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while getting source titles", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;

	}

	public ArrayList getHiringEfficiencySummaryReport(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			Date toDt = filterData.getConvertedToDate();
			if (toDt != null) {
				toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
			}
			Date frmDt = null;
			if (!Utils.isBlankOrNull(filterData.getFromDate())) {
				frmDt = filterData.getConvertedFromDate();
			}

			String[] dynParam = new String[2];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " tp.position_code ";
			} else {
				dynParam[0] = " tp.position_title ";
			}			
			ArrayList<String> dynamicContent = new ArrayList<String>();			

			dynParam[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
		
			if (frmDt != null) {
				dynParam[1] += " AND ta.joining_date >=? ";
				dynamicContent.add(Utils.getDateConvertedToString(frmDt, Utils.redYYYYMMDDFormat));
			}
			if (toDt != null) {
				dynParam[1] += " AND ta.joining_date <=? ";
				dynamicContent.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
			}
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParam[1] += " AND tp.position_status = ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParam[1] += " AND (tp.position_status = ? OR tp.position_status = ?) ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParam[1] += " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParam[1] += " AND tp.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParam[1] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}			

			dq = new DBPreparedQuery("dReportManager_GetHiringEfficiencySummaryReport", dynParam);			
			dq.setString(1, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(2, PositionConstants.POSITION_STATUS_CLOSED);
			int cnt = 3;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while getting Hiring Efffciecny Report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public ArrayList getHiringEfficiencyDetailsReport(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			Date toDt = filterData.getConvertedToDate();
			if (toDt != null) {
				toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
			}

			Date frmDt = filterData.getConvertedFromDate();

			String[] dynParam = new String[2];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " tp.position_code ";
			} else {
				dynParam[0] = " tp.position_title ";
			}	
			ArrayList<String> dynamicContent = new ArrayList<String>();			

			dynParam[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
		
			if (frmDt != null) {
				dynParam[1] += " AND tajh.joining_date >=? ";
				dynamicContent.add(Utils.getDateConvertedToString(frmDt, Utils.redYYYYMMDDFormat));
			}
			if (toDt != null) {
				dynParam[1] += " AND tajh.joining_date <=? ";
				dynamicContent.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
			}
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParam[1] += " AND tp.position_status = ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParam[1] += " AND (tp.position_status = ? OR tp.position_status = ?) ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParam[1] += " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParam[1] += " AND tp.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParam[1] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}

			dq = new DBPreparedQuery("dReportManager_GetHiringEfficiencyDetailsReport", dynParam);			
			dq.setString(1, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(2, PositionConstants.POSITION_STATUS_CLOSED);
			int cnt = 3;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			result = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error("error while getting Hiring Efffciecny Report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public ArrayList getSourceWiseHiring(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			Date toDt = null;
			Date frmDt = null;
			if (!Utils.isBlankOrNull(filterData.getToDate())) {
				toDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
				toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
			}
			if (!Utils.isBlankOrNull(filterData.getFromDate())) {
				frmDt = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			}
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();			

			dynParam[0] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
		
			if (frmDt != null) {
				dynParam[0] += " AND ta.applicant_date_joined >=? ";
				dynamicContent.add(Utils.getDateConvertedToString(frmDt, Utils.redYYYYMMDDFormat));
			}
			if (toDt != null) {
				dynParam[0] += " AND ta.applicant_date_joined <=? ";
				dynamicContent.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
			}
			
			if (!Utils.isBlankOrNull(filterData.getSourceCategoryId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getSourceCategoryId(), dynamicContent);
				dynParam[0] += " and tst.source_type_id IN (" + qMarks + ") ";				
			}
			
			if (!Utils.isBlankOrNull(filterData.getSourceId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getSourceId(), dynamicContent);
				dynParam[0] += " and ts.source_id IN (" + qMarks + ") ";				
			}

			dq = new DBPreparedQuery("dReportManager_GetSourceWiseHiring", dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}

			result = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error("error while getting Sourc Wise Hiring Report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public ArrayList getHiringFunnelSummaryReport(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;

		try {
			java.util.Date tDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			tDt = Utils.adjustDateBy(tDt, Calendar.DATE, 1);
			java.sql.Date toDt = Utils.convertDateToSQLDate(tDt);

			String[] dynParams = new String[2];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParams[0] = " tp.position_code ";
			} else {
				dynParams[0] = " tp.position_title ";
			}
			ArrayList<String> dynamicContent = new ArrayList<String>();			

			dynParams[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
			
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[1] += " AND tp.position_status = ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParams[1] += " AND (tp.position_status = ? OR tp.position_status = ?)";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
				dynParams[1] += " AND tp.position_id = ? ";
				dynamicContent.add(filterData.getPositionId());
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParams[1] += " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParams[1] += " AND tp.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParams[1] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}

			if (!Utils.isBlankOrNull(filterData.getUsers())) {
				dynamicContent.add(PositionConstants.STEP_ACTIVE);
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);

				dynParams[1] += " AND ";
				dynParams[1] += " ( ";
				dynParams[1] += " tp.position_id in (select su.position_id from tp_position_step_users su, tp_position_steps ps " + "	where su.position_step_id = ps.position_step_id and ps.position_step_status = ?  and su.user_id in (" + qMarks + ")) ";
				dynParams[1] += " OR tp.position_requested_by in (" + qMarks + ") ";
				dynParams[1] += " OR tp.position_id in ( SELECT distinct position_id FROM tp_requisition_approval_feedback WHERE by_user_id in (" + qMarks + ") OR to_user_id in (" + qMarks + ") )";
				dynParams[1] += ") ";
			}

			dq = new DBPreparedQuery("dReportManager_GetHiringFunnelSummaryReport", dynParams);
			int cnt=1;
			dq.setId(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setDate(cnt++, toDt);
			dq.setId(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setId(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setDate(cnt++, toDt);

			dq.setId(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setId(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			dq.setId(cnt++, SelectionProcessConstants.STEP_REPEAT);

			dq.setId(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setId(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setDate(cnt++, toDt);

			dq.setId(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setId(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setDate(cnt++, toDt);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}

			result = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating Hiring Funnel Summary report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;

	}

	public ArrayList<HiringFunnelDetailView> getHiringFunnelDetailReport(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList<HiringFunnelDetailView> result = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			java.util.Date tDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			tDt = Utils.adjustDateBy(tDt, Calendar.DATE, 1);
			java.sql.Date toDt = Utils.convertDateToSQLDate(tDt);

			String[] dynParams = new String[2];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParams[0] = " tp.position_code ";
			} else {
				dynParams[0] = " tp.position_title ";
			}
			dynParams[1] = " t.process_moved_date <= ? ";
			dynamicContent.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));			

			dynParams[1] += PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
									
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[1] += " AND tp.position_status = ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParams[1] += " AND (tp.position_status = ? OR tp.position_status = ?)";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
				dynParams[1] += " AND tp.position_id = ? ";
				dynamicContent.add(filterData.getPositionId());
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParams[1] += " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParams[1] += " AND tp.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParams[1] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}

			if (!Utils.isBlankOrNull(filterData.getUsers())) {
				dynamicContent.add(PositionConstants.STEP_ACTIVE);
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);

				dynParams[1] += " AND ";
				dynParams[1] += " ( ";
				dynParams[1] += " tp.position_id in (select su.position_id from tp_position_step_users su, tp_position_steps ps " + "	where su.position_step_id = ps.position_step_id and ps.position_step_status = ?  and su.user_id in (" + qMarks + ")) ";
				dynParams[1] += " OR tp.position_requested_by in (" + qMarks + ") ";
				dynParams[1] += " OR tp.position_id in ( SELECT distinct position_id FROM tp_requisition_approval_feedback WHERE by_user_id in (" + qMarks + ") OR to_user_id in (" + qMarks + ") )";
				dynParams[1] += ") ";
			}

			dq = new DBPreparedQuery("dReportManager_GetHiringFunnelDetails", dynParams);
			int cnt = 1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			
			dq.setId(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setId(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			dq.setId(cnt++, SelectionProcessConstants.STEP_REPEAT);

			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}

			result = dq.getResult();

			if (result != null && result.size() > 0) {
				List temp = new ArrayList<HiringFunnelDetailView>();
				String applicantStatus = "";
				String stepId = "";
				Iterator<HiringFunnelDetailView> itr = result.iterator();
				while (itr.hasNext()) {
					HiringFunnelDetailView view = itr.next();
					if (!applicantStatus.equalsIgnoreCase(view.getApplicantStatus()) || !stepId.equalsIgnoreCase(view.getStepId())) {
						if (temp.size() > 0) {
							for (int i = 0; i < temp.size(); i++) {
								((HiringFunnelDetailView) temp.get(i)).setGroupCount("" + temp.size());
							}
							temp = new ArrayList<HiringFunnelDetailView>();
						}
						applicantStatus = view.getApplicantStatus();
						stepId = view.getStepId();
						temp.add(view);
					} else {
						temp.add(view);
					}
				}
				if (temp.size() > 0) {
					for (int i = 0; i < temp.size(); i++) {
						((HiringFunnelDetailView) temp.get(i)).setGroupCount("" + temp.size());
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating hiring status detailed report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public String getUsersForIds(String interviewers) {
		String users = "";
		DBPreparedQuery dq = null;
		if (interviewers != null && interviewers.length() > 0) {
			try {
				String[] dynParam = new String[1];
				dynParam[0] = interviewers;
				dq = new DBPreparedQuery("dGetUsersForIds", dynParam);
				SimpleDataObject sDo = (SimpleDataObject) dq.getSingleObjectResult();
				users = sDo.getString("users");
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while getting users for ids", e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
		return users;
	}

	public String stringToStringArray(String[] strings) {
		StringBuffer buffer = new StringBuffer();
		if (strings != null && strings.length > 0) {
			for (int i = 0; i < strings.length; i++) {
				if (buffer.length() > 0) {
					buffer.append(", ");
				}
				buffer.append(strings[i]);
			}
		}
		return buffer.toString();
	}

	private void concatStringsBySeparator(StringBuffer buffer, String value, String separator) {
		if (!Utils.isBlankOrNull(value)) {
			if (buffer == null) {
				buffer = new StringBuffer();
			}
			if (separator != null) {
				if (buffer.length() > 0) {
					buffer.append(separator);
				}
			}
			buffer.append(value);
		}
	}

	public List getOfferToJoinedReportData(FilterData filterData, String userId, PermissionSet permissionSet) {
		List reportData = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[3];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			ArrayList<String> dynamicContent2 = new ArrayList<String>();
			ArrayList<String> dynamicContent3 = new ArrayList<String>();

			dynParam[0] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
			dynParam[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent2, permissionSet);
			dynParam[2] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent3, permissionSet);
						
			if (!Utils.isBlankOrNull(filterData.getFromDate()) || !Utils.isBlankOrNull(filterData.getToDate())) {
				if (!Utils.isBlankOrNull(filterData.getFromDate())) {
					Date fromDate = filterData.getConvertedFromDate();
					dynParam[0] += " and t1.process_moved_date >= ? ";
					dynParam[1] += " and t1.process_moved_date >= ? ";
					dynParam[2] += " and t1.process_moved_date >= ? ";
					dynamicContent.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
					dynamicContent2.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
					dynamicContent3.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
				}
				if (!Utils.isBlankOrNull(filterData.getToDate())) {
					Date toDt = filterData.getConvertedToDate();
					if (toDt != null) {
						toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
					}
					dynParam[0] += " and t1.process_moved_date <= ? ";
					dynParam[1] += " and t1.process_moved_date <= ? ";
					dynParam[2] += " and t1.process_moved_date <= ? ";
					dynamicContent.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
					dynamicContent2.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
					dynamicContent3.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));

				}
			}
			if (ReportConstants.FILTER_OPEN_POSITIONS.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[0] += " and t1.position_id in (select position_id from tp_positions where position_status = ? )";
				dynParam[1] += " and t1.position_id in (select position_id from tp_positions where position_status = ? )";
				dynParam[2] += " and t1.position_id in (select position_id from tp_positions where position_status = ? )";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent2.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent3.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[0] += " and t1.position_id in (select position_id from tp_positions where position_status = ? OR position_status = ? )";
				dynParam[1] += " and t1.position_id in (select position_id from tp_positions where position_status = ? OR position_status = ? )";
				dynParam[2] += " and t1.position_id in (select position_id from tp_positions where position_status = ? OR position_status = ? )";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
				dynamicContent2.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent2.add(PositionConstants.POSITION_STATUS_HOLD);
				dynamicContent3.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent3.add(PositionConstants.POSITION_STATUS_HOLD);
			} else if (ReportConstants.FILTER_SPECIFIC_POSITION.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[0] += " and t1.position_id = ? ";
				dynParam[1] += " and t1.position_id = ? ";
				dynParam[2] += " and t1.position_id = ? ";
				dynamicContent.add(filterData.getPositionId());
				dynamicContent2.add(filterData.getPositionId());
				dynamicContent3.add(filterData.getPositionId());
			} else if (ReportConstants.FILTER_SPECIFIC_DEPARTMENT.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[0] += " and t1.position_id in (select position_id from tp_positions where dept_id = ? ";
				dynParam[1] += " and t1.position_id in (select position_id from tp_positions where dept_id = ? ";
				dynParam[2] += " and t1.position_id in (select position_id from tp_positions where dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				dynamicContent2.add(filterData.getDepartmentId());
				dynamicContent3.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParam[0] += " and sub_dept_id = ? ";
					dynParam[1] += " and sub_dept_id = ? ";
					dynParam[2] += " and sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					dynamicContent2.add(filterData.getSubDepartmentId());
					dynamicContent3.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParam[0] += " and sub_sub_dept_id = ? ";
						dynParam[1] += " and sub_sub_dept_id = ? ";
						dynParam[2] += " and sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
						dynamicContent2.add(filterData.getSubSubDepartmentId());
						dynamicContent3.add(filterData.getSubSubDepartmentId());
					}
				}
				dynParam[0] += " ) ";
				dynParam[1] += " ) ";
				dynParam[2] += " ) ";
			}

			dq = new DBPreparedQuery("dReportManager_OfferToJoinedReport", dynParam);
			int cnt = 1;
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			for (int i = 0; i < dynamicContent2.size(); i++) {
				dq.setString(cnt++, dynamicContent2.get(i));
			}
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			for (int i = 0; i < dynamicContent3.size(); i++) {
				dq.setString(cnt++, dynamicContent3.get(i));
			}
			reportData = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the offer to joined report data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return reportData;
	}

	public List getOfferToJoinedDetailedReportData(FilterData filterData, String userId, PermissionSet permissionSet) {
		List reportData = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = {""};
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(filterData.getFromDate()) || !Utils.isBlankOrNull(filterData.getToDate())) {
				if (!Utils.isBlankOrNull(filterData.getFromDate())) {
					Date fromDate = filterData.getConvertedFromDate();
					dynParam[0] += " and tasp.process_moved_date >= ? ";
					dynamicContent.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
				}
			}
			if (!Utils.isBlankOrNull(filterData.getToDate())) {
				Date toDt = filterData.getConvertedToDate();
				if (toDt != null) {
					toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
				}
				dynParam[0] += " and tasp.process_moved_date <= ? ";
				dynamicContent.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
			}
			if (!Utils.isBlankOrNull(filterData.getUsers()) && !filterData.getUsers().equals("-1")) {
				dynParam[0] += " and tp.position_owner_id in (?) ";
				dynamicContent.add(filterData.getUsers());
			}
			if (!Utils.isBlankOrNull(filterData.getSourceId()) && !filterData.getSourceId().equals("-1")) {
				dynParam[0] += " and ts.source_id in (?) ";
				dynamicContent.add(filterData.getSourceId());
			}
			if (!Utils.isBlankOrNull(filterData.getSourceCategoryId()) && !filterData.getSourceCategoryId().equals("-1")) {
				dynParam[0] += " and ts.source_type_id in (?) ";
				dynamicContent.add(filterData.getSourceCategoryId());
			}
			dq = new DBPreparedQuery("dReportManager_GetOfferToJoinedDetailedReport", dynParam);
			int cnt=1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_OFFER_TO_JOINED_DEFAULT_STEP_ID));
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			reportData = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the offer to joined report data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return reportData;
	}
	
	public List getDetailedOfferToJoinedReportData(FilterData filterData, String userId, PermissionSet permissionSet) {
		List reportData = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[5];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " tpos.position_code ";
			} else {
				dynParam[0] = " tpos.position_title ";
			}

			ArrayList<String> dynamicContent = new ArrayList<String>();
			ArrayList<String> dynamicContent2 = new ArrayList<String>();
			ArrayList<String> dynamicContent3 = new ArrayList<String>();
			ArrayList<String> dynamicContent4 = new ArrayList<String>();

			dynParam[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
			dynParam[2] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent2, permissionSet);
			dynParam[3] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent3, permissionSet);
			dynParam[4] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent4, permissionSet);
			
			if (!Utils.isBlankOrNull(filterData.getFromDate()) || !Utils.isBlankOrNull(filterData.getToDate())) {
				if (!Utils.isBlankOrNull(filterData.getFromDate())) {
					Date fromDate = filterData.getConvertedFromDate();
					dynParam[1] += " and t1.process_moved_date >= ? ";
					dynParam[2] += " and t1.process_moved_date >= ? ";
					dynParam[3] += " and t1.process_moved_date >= ? ";
					dynParam[4] += " and t1.process_moved_date >= ? ";
					dynamicContent.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
					dynamicContent2.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
					dynamicContent3.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
					dynamicContent4.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
				}
				if (!Utils.isBlankOrNull(filterData.getToDate())) {

					Date toDt = filterData.getConvertedToDate();
					if (toDt != null) {
						toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
					}
					dynParam[1] += " and t1.process_moved_date <= ? ";
					dynParam[2] += " and t1.process_moved_date <= ? ";
					dynParam[3] += " and t1.process_moved_date <= ? ";
					dynParam[4] += " and t1.process_moved_date <= ? ";
					dynamicContent.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
					dynamicContent2.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
					dynamicContent3.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
					dynamicContent4.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
				}
			}
			if (ReportConstants.FILTER_OPEN_POSITIONS.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[1] += " and t1.position_id in (select position_id from tp_positions where position_status = ? )";
				dynParam[2] += " and t1.position_id in (select position_id from tp_positions where position_status = ? )";
				dynParam[3] += " and t1.position_id in (select position_id from tp_positions where position_status = ? )";
				dynParam[4] += " and t1.position_id in (select position_id from tp_positions where position_status = ? )";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent2.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent3.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent4.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[1] += " and t1.position_id in (select position_id from tp_positions where position_status = ? OR position_status = ? )";
				dynParam[2] += " and t1.position_id in (select position_id from tp_positions where position_status = ? OR position_status = ? )";
				dynParam[3] += " and t1.position_id in (select position_id from tp_positions where position_status = ? OR position_status = ? )";
				dynParam[4] += " and t1.position_id in (select position_id from tp_positions where position_status = ? OR position_status = ? )";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
				dynamicContent2.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent2.add(PositionConstants.POSITION_STATUS_HOLD);
				dynamicContent3.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent3.add(PositionConstants.POSITION_STATUS_HOLD);
				dynamicContent4.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent4.add(PositionConstants.POSITION_STATUS_HOLD);
			} else if (ReportConstants.FILTER_SPECIFIC_POSITION.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[1] += " and t1.position_id = ? ";
				dynParam[2] += " and t1.position_id = ? ";
				dynParam[3] += " and t1.position_id = ? ";
				dynParam[4] += " and t1.position_id = ? ";
				dynamicContent.add(filterData.getPositionId());
				dynamicContent2.add(filterData.getPositionId());
				dynamicContent3.add(filterData.getPositionId());
				dynamicContent4.add(filterData.getPositionId());
			} else if (ReportConstants.FILTER_SPECIFIC_DEPARTMENT.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[1] += " and t1.position_id in (select position_id from tp_positions where dept_id = ? ";
				dynParam[2] += " and t1.position_id in (select position_id from tp_positions where dept_id = ? ";
				dynParam[3] += " and t1.position_id in (select position_id from tp_positions where dept_id = ? ";
				dynParam[4] += " and t1.position_id in (select position_id from tp_positions where dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				dynamicContent2.add(filterData.getDepartmentId());
				dynamicContent3.add(filterData.getDepartmentId());
				dynamicContent4.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParam[1] += " and sub_dept_id = ? ";
					dynParam[2] += " and sub_dept_id = ? ";
					dynParam[3] += " and sub_dept_id = ? ";
					dynParam[4] += " and sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					dynamicContent2.add(filterData.getSubDepartmentId());
					dynamicContent3.add(filterData.getSubDepartmentId());
					dynamicContent4.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParam[1] += " and sub_sub_dept_id = ? ";
						dynParam[2] += " and sub_sub_dept_id = ? ";
						dynParam[3] += " and sub_sub_dept_id = ? ";
						dynParam[4] += " and sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
						dynamicContent2.add(filterData.getSubSubDepartmentId());
						dynamicContent3.add(filterData.getSubSubDepartmentId());
						dynamicContent4.add(filterData.getSubSubDepartmentId());
					}
				}
				dynParam[1] += " ) ";
				dynParam[2] += " ) ";
				dynParam[3] += " ) ";
				dynParam[4] += " ) ";
			}
			dq = new DBPreparedQuery("dReportManager_GetOfferToJoinedReport_Detail", dynParam);
			int cnt = 1;
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}

			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			for (int i = 0; i < dynamicContent2.size(); i++) {
				dq.setString(cnt++, dynamicContent2.get(i));
			}

			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			for (int i = 0; i < dynamicContent3.size(); i++) {
				dq.setString(cnt++, dynamicContent3.get(i));
			}
			
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			for (int i = 0; i < dynamicContent4.size(); i++) {
				dq.setString(cnt++, dynamicContent4.get(i));
			}

			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REPEAT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);

			reportData = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the offer to joined report data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return reportData;
	}

	public List getCandidateComparisonReportData(FilterData filterData) {
		List reportData = new ArrayList<ArrayList>();
		DBPreparedQuery dq = null;
		try {
			String positionId = filterData.getPositionId();
			List headersData = getHeadersForCandidateComparisonReport(positionId, filterData.getStepIds(), filterData.getApplicants());
			List<String> headers = (List) headersData.get(0);
			ArrayList<String> applicantIdsList = (ArrayList<String>) headersData.get(2);

			List rowsData = getRowsForCandidateComparisonReport(positionId, filterData.getStepIds(), headers.size());
			List<SimpleDataObject> rows = (List) rowsData.get(0);
			List<String> traitIds = (List) rowsData.get(1);

			List data = getDataForCandidateComparisonReport(positionId, filterData.getStepIds(), filterData.getApplicants());
			Iterator<SimpleDataObject> itr = data.iterator();

			while (itr.hasNext()) {
				SimpleDataObject sdo = itr.next();
				String multipleSelectIds = sdo.getString("selectFieldIds");
				if(!Utils.isBlankOrNull(multipleSelectIds)) {
					String selectFieldDesc = (new MultipleSelectsManager()).getMultipleSelectFieldDescFromIds(multipleSelectIds);
					String traitComment = selectFieldDesc + ";" + sdo.getString("traitComment");
					sdo.setAttribute("traitComment", traitComment);
				}
				int applicantIndex = applicantIdsList.indexOf(sdo.getString("applicantId"));
				int rowIndex = traitIds.indexOf(sdo.getString("traitId"));
				if (rowIndex >= 0) {
					String feedback = rows.get(rowIndex).getString("CV_" + applicantIndex);
					feedback += sdo.getString("userFullName") + ":\n" + sdo.getString("traitComment") + "\n";
					rows.get(rowIndex).setAttribute("CV_" + applicantIndex, feedback);
				}
			}
			reportData.add(headers);
			reportData.add(rows);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while building data for the candidate comparison report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

		return reportData;
	}
	
	private List getHeadersForCandidateComparisonReport(String positionId, String stepIds, String applicantIds) {
		List<List> result = new ArrayList<List>();
		List<String> headers = new ArrayList<String>();
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(stepIds)) {
				String[] arrIds = stepIds.split(",");
				String qMarks = "";
				for (int i = 0; i < arrIds.length; i++) {
					qMarks += (i > 0) ? ",?" : "?";
					dynamicContent.add(arrIds[i].trim());
				}
				ArrayList<String> clo = (ArrayList<String>) dynamicContent.clone();
				dynamicContent.addAll(clo);
				dynParam[0] += " AND ( tasp.position_step_id_from in (" + qMarks + ") OR tasp.position_step_id_to in (" + qMarks + ") )";
			}
			// required to add ? to avoid sql injection
			if (!Utils.isBlankOrNull(applicantIds)) {
				String[] arrIds = applicantIds.split(",");
				String qMarks = "";
				for (int i = 0; i < arrIds.length; i++) {
					qMarks += (i > 0) ? ",?" : "?";
					dynamicContent.add(arrIds[i].trim());
				}
				dynParam[0] += " and tasp.applicant_id in (" + qMarks + ")";
			}

			dq = new DBPreparedQuery("dReportManager_GetHeadersForCandidateComparisonReport", dynParam);
			dq.setString(1, positionId);
			int cnt = 2;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			ArrayList<String> applicantIdsList = new ArrayList<String>();
			List<SimpleDataObject> headerObjects = dq.getResult();
			if (headerObjects != null && headerObjects.size() > 0) {
				for (int i = 0; i < headerObjects.size(); i++) {
					if ((i % 3) == 0) {
						headers.add(new String());
						applicantIdsList.add("x" + i);
					}
					SimpleDataObject sdo = headerObjects.get(i);
					headers.add(sdo.getString("applicantName"));
					applicantIdsList.add(sdo.getString("applicantId"));
				}
			}
			result.add(headers);
			result.add(headerObjects);
			result.add(applicantIdsList);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting headers for the candidate comparison report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

		return result;
	}

	private List getRowsForCandidateComparisonReport(String positionId, String stepIds, int noOfHeaders) {
		List<List> result = new ArrayList<List>();
		List<SimpleDataObject> rows = new ArrayList<SimpleDataObject>();
		List<String> traitIds = new ArrayList<String>();
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(stepIds)) {
				String[] arrIds = stepIds.split(",");
				String qMarks = "";
				for (int i = 0; i < arrIds.length; i++) {
					qMarks += (i > 0) ? ",?" : "?";
					dynamicContent.add(arrIds[i].trim());
				}
				dynParam[0] = " and tps.position_step_id in (" + qMarks + ")";
			}

			dq = new DBPreparedQuery("dReportManager_GetColumnsForCandidateComparisonReport", dynParam);
			dq.setString(1, positionId);
			dq.setInt(2, PositionConstants.STEP_NOT_DEFAULT);
			int cnt = 3;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}

			List<SimpleDataObject> rowResultSet = dq.getResult();
			if (rowResultSet != null && rowResultSet.size() > 0) {
				for (int j = 0; j < rowResultSet.size(); j++) {
					SimpleDataObject sdo = rowResultSet.get(j);
					traitIds.add(sdo.getString("traitId"));
					SimpleDataObject row = new SimpleDataObject();
					row.setAttribute("CV_-1", sdo.getString("stepTitle"));
					int counter = 0;
					for (int i = 0; i < noOfHeaders; i++) {
						if ((i % 3) == 0) {
							row.setAttribute("CV_" + (counter++), sdo.getString("traitTitle"));
						}
						row.setAttribute("CV_" + (counter++), "");
					}
					rows.add(row);
				}
			}
			result.add(rows);
			result.add(traitIds);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting rows for the candidate comparison report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	private List getDataForCandidateComparisonReport(String positionId, String stepIds, String applicantIds) {
		List data = new ArrayList<String>();
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(stepIds)) {
				String[] arrIds = stepIds.split(",");
				String qMarks = "";
				for (int i = 0; i < arrIds.length; i++) {
					qMarks += (i > 0) ? ",?" : "?";
					dynamicContent.add(arrIds[i].trim());
				}
				dynParam[0] += " AND  tps.position_step_id in (" + qMarks + ") ";
			}
			// required to add ? to avoid sql injection
			if (!Utils.isBlankOrNull(applicantIds)) {
				String[] arrIds = applicantIds.split(",");
				String qMarks = "";
				for (int i = 0; i < arrIds.length; i++) {
					qMarks += (i > 0) ? ",?" : "?";
					dynamicContent.add(arrIds[i].trim());
				}
				dynParam[0] += " AND tasp.applicant_id in (" + qMarks + ") ";
			}

			dq = new DBPreparedQuery("dReportManager_GetDataForCandidateComparisonReport", dynParam);
			dq.setString(1, positionId);
			dq.setInt(2, PositionConstants.STEP_NOT_DEFAULT);
			int cnt = 3;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			data = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for the candidate comparison report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

		return data;
	}

	public void generateDynamicReport(String outputFileName, Map parameters, List reportHeader, List reportData, String reportFormat, 
			boolean isGroupPresent, String cssSuffix, String userId, String clientIpAddr) {
		String dynaReportHome = Utils.concatFilePath(ReportConstants.JRXML_FOLDER_PATH, ReportConstants.DYNA_REPORT_FOLDER);
		StringBuffer subReportFileBuffer = new StringBuffer();
		int colWidth = 120;
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		try {
			// Start: Report Header
			StringBuffer header = new StringBuffer();
			readFile(Utils.concatFilePath(dynaReportHome, "01_report_header.jrxml"), header);
			String headerString = header.toString();
			int columnWidth = (colWidth * reportHeader.size());
			int pageWidth = columnWidth + 60;
			if (reportHeader.size() > 0) {
				subReportFileBuffer.append(headerString.replace("columnWidth=\"782", "columnWidth=\"" + columnWidth).replace("pageWidth=\"842", "pageWidth=\"" + pageWidth));
			} else {
				subReportFileBuffer.append(headerString);
			}
			// End: Report Header

			// Start: Report CSS
			readFile(Utils.concatFilePath(dynaReportHome, "02_report_css.jrxml"), subReportFileBuffer);
			// End: Report CSS

			// Start: Report Other Paramaters
			readFile(Utils.concatFilePath(dynaReportHome, "03_report_other_parameter.jrxml"), subReportFileBuffer);
			// End: Report Other Paramaters

			// Start: Report Column Header Parameters
			header = new StringBuffer();
			readFile(Utils.concatFilePath(dynaReportHome, "04_report_column_header_paramater.jrxml"), header);
			headerString = header.toString();

			for (int i = 0; i < reportHeader.size(); i++) {
				parameters.put("CH_" + i, reportHeader.get(i));
				subReportFileBuffer.append(headerString.replaceFirst("_0", "_" + i));
			}
			// End: Report Column Header Parameters

			// Start: Report Column Value Field
			StringBuffer field = new StringBuffer();
			readFile(Utils.concatFilePath(dynaReportHome, "05_report_column_value_field.jrxml"), field);
			String fieldString = field.toString();
			for (int i = 0; i < reportHeader.size(); i++) {
				subReportFileBuffer.append(fieldString.replaceFirst("_0", "_" + i));
			}
			// End: Report Column Value Field

			// Start: Report Group
			if (isGroupPresent) {
				subReportFileBuffer.append(fieldString.replaceFirst("_0", "_-1"));

				// Start: Report Group Header
				header = new StringBuffer();
				readFile(Utils.concatFilePath(dynaReportHome, "050_report_group_head.jrxml"), header);
				headerString = header.toString();
				subReportFileBuffer.append(headerString.replaceFirst("_0", "_-1"));
				// End: Report Group Header

				header = new StringBuffer();
				readFile(Utils.concatFilePath(dynaReportHome, "050_report_group.jrxml"), header);
				headerString = header.toString();
				if (!Utils.isBlankOrNull(cssSuffix)) {
					headerString = headerString.replace("style=\"group_header", "style=\"group_header" + cssSuffix);
				}
				int kk;
				for (kk = 0; kk < (reportHeader.size() / 4); kk++) {
					if (!Utils.isBlankOrNull(cssSuffix) && ((kk + 1) == ((reportHeader.size() / 4)))) {
						headerString = headerString.replace("style=\"group_header", "style=\"group_header" + cssSuffix);
					}
					subReportFileBuffer.append(headerString.replace("_0", "_-1").replace("x=\"0", "x=\"" + (kk * colWidth * 4)));
				}
				if ((reportHeader.size() % 4) != 0) {
					if (!Utils.isBlankOrNull(cssSuffix) && ((kk + 1) == ((reportHeader.size() / 4)))) {
						headerString = headerString.replace("style=\"group_header", "style=\"group_header" + cssSuffix);
					}
					subReportFileBuffer.append(headerString.replace("_0", "_-1").replace("x=\"0", "x=\"" + (kk * colWidth * 4)).replace("width=\"480", "width=\"" + (colWidth * (reportHeader.size() % 4))));
				}

				// Start: Report Group Header
				readFile(Utils.concatFilePath(dynaReportHome, "050_report_group_tail.jrxml"), subReportFileBuffer);
				// End: Report Group Header
			}
			// End: Report Group

			// Start: Report Background
			readFile(Utils.concatFilePath(dynaReportHome, "06_report_background.jrxml"), subReportFileBuffer);
			// End: Report Background

			// Start: Report Title
			readFile(Utils.concatFilePath(dynaReportHome, "07_report_title.jrxml"), subReportFileBuffer);
			// End: Report Title

			// Start: Report Page Header
			readFile(Utils.concatFilePath(dynaReportHome, "08_report_page_header.jrxml"), subReportFileBuffer);
			// End: Report Page Header

			// Start: Report Column Header Head
			readFile(Utils.concatFilePath(dynaReportHome, "09_report_column_header_head.jrxml"), subReportFileBuffer);
			// End: Report Column Header Head

			// Start: Report Column Header
			header = new StringBuffer();
			readFile(Utils.concatFilePath(dynaReportHome, "10_report_column_header.jrxml"), header);
			headerString = header.toString();
			if (!Utils.isBlankOrNull(cssSuffix)) {
				headerString = headerString.replace("style=\"column_header", "style=\"column_header" + cssSuffix);
			}
			for (int i = 0; i < reportHeader.size(); i++) {
				if (!Utils.isBlankOrNull(cssSuffix) && ((i + 1) == reportHeader.size())) {
					headerString = headerString.replace("style=\"column_header", "style=\"column_header" + cssSuffix);
				}
				subReportFileBuffer.append(headerString.replaceFirst("_0", "_" + i).replace("x=\"0", "x=\"" + (i * colWidth)));
			}
			// End: Report Column Header

			// Start: Report Column Header Tail
			readFile(Utils.concatFilePath(dynaReportHome, "11_report_column_header_tail.jrxml"), subReportFileBuffer);
			// End: Report Column Header Tail

			// Start: Report Detail Head
			readFile(Utils.concatFilePath(dynaReportHome, "12_report_detail_head.jrxml"), subReportFileBuffer);
			// End: Report Detail Head

			// Start: Report Detail No Data Message
			readFile(Utils.concatFilePath(dynaReportHome, "13_report_detail_no_data_message.jrxml"), subReportFileBuffer);
			// End: Report Detail No Data Message

			// Start: Report Detail Fields
			field = new StringBuffer();
			readFile(Utils.concatFilePath(dynaReportHome, "14_report_detail_field.jrxml"), field);
			fieldString = field.toString();
			if (!Utils.isBlankOrNull(cssSuffix)) {
				fieldString = fieldString.replace("style=\"detail_style", "style=\"detail_style" + cssSuffix);
			}
			for (int i = 0; i < reportHeader.size(); i++) {
				if (!Utils.isBlankOrNull(cssSuffix) && ((i + 1) == reportHeader.size())) {
					fieldString = fieldString.replace("style=\"detail_style", "style=\"detail_style" + cssSuffix);
				}
				subReportFileBuffer.append(fieldString.replaceFirst("_0", "_" + i).replace("x=\"0", "x=\"" + (i * colWidth)));
			}
			// End: Report Detail Fields

			// Start: Report Detail Tail
			readFile(Utils.concatFilePath(dynaReportHome, "15_report_detail_tail.jrxml"), subReportFileBuffer);
			// End: Report Detail Tail

			// Start: Report Column Footer
			readFile(Utils.concatFilePath(dynaReportHome, "16_report_column_footer.jrxml"), subReportFileBuffer);
			// End: Report Column Footer

			// Start: Report Page Footer
			readFile(Utils.concatFilePath(dynaReportHome, "17_report_page_footer.jrxml"), subReportFileBuffer);
			// End: Report Page Footer

			// Start: Report Last Page Footer
			readFile(Utils.concatFilePath(dynaReportHome, "18_report_last_page_footer.jrxml"), subReportFileBuffer);
			// End: Report Last Page Footer

			// Start: Report Summary
			readFile(Utils.concatFilePath(dynaReportHome, "19_report_summary.jrxml"), subReportFileBuffer);
			// End: Report Summary

			// Start: Report Footer
			readFile(Utils.concatFilePath(dynaReportHome, "20_report_footer.jrxml"), subReportFileBuffer);
			// End: Report Footer

			String destinationPath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH, outputFileName);
			jasperReport = JasperCompileManager.compileReport(new ByteArrayInputStream(subReportFileBuffer.toString().getBytes()));
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRCollectionSource(reportData));

			if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_HTML)) {
				JRHtmlExporter exporter = new JRHtmlExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationPath);
				exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);
				exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, "px");
				exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "<title>Talentpool Report</title>");
				exporter.exportReport();
			} else if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_PDF)) {
				JRPdfExporter exporter = new JRPdfExporter();
				// exporter.setParameter(JRPdfExporterParameter.METADATA_TITLE,
				// "ToutVirtual,Inc");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, destinationPath);
				exporter.exportReport();
			} else if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_EXCEL)) {
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, destinationPath);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				// exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
				// Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				// exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
				// Boolean.TRUE);
				exporterXLS.exportReport();
				// byteArray = outputByteArray.toByteArray();
				AuditManager auditManager = new AuditManager();
				
				auditManager.addAudit(null, AuditConstants.TYPE_GENERATED, (String) parameters.get("report_title"),
						AuditConstants.AUDIT_REPORT, userId, null, null, null, true, clientIpAddr);

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating dynamic report", e);
		}
	}

	private void readFile(String fileName, StringBuffer buffer) {
		BufferedReader bufferedReader = null;
		String lineRead = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(fileName));
			while ((lineRead = bufferedReader.readLine()) != null) {
				buffer.append(lineRead);
				buffer.append('\n');
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while reading file " + fileName, e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					TPLogger.getLogger().error(e);
				}
			}
		}
	}

	public ArrayList getPositionSummaryReport(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList columnAndData = new ArrayList();
		ArrayList<String> columnHeaders = new ArrayList<String>();
		ArrayList<SimpleDataObject> columnValues = new ArrayList<SimpleDataObject>();
		DBPreparedQuery dq = null;
		try {
			Date toDt = filterData.getConvertedToDate();
			if (toDt != null) {
				toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
			}
			Date frmDt = filterData.getConvertedFromDate();

			String[] dynParam = new String[1];
			dynParam[0] = "";

			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
				dynParam[0] += " AND tpos.position_id in (select su.position_id from tp_position_step_users su, tp_position_steps st where st.position_step_id = su.position_step_id and st.position_step_status = ? "
						+ " and su.user_id = ? union select position_id from tp_positions where position_requested_by = ?  " + " UNION SELECT distinct traf.position_id FROM tp_requisition_approval_feedback traf WHERE traf.by_user_id=? OR traf.to_user_id=? )";
				dynamicContent.add(PositionConstants.STEP_ACTIVE);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
			}
		
			if (frmDt != null) {
				dynParam[0] += " AND tpos.position_date_created >= ? ";
				dynamicContent.add(Utils.getDateConvertedToString(frmDt, Utils.redYYYYMMDDFormat));
			}
			if (toDt != null) {
				dynParam[0] += " AND tpos.position_date_created <= ? ";
				dynamicContent.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
			}
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParam[0] += " AND tpos.position_status= ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParam[0] += " AND (tpos.position_status = ? OR tpos.position_status = ?)";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
				dynParam[0] += " AND tpos.position_id= ? ";
				dynamicContent.add(filterData.getPositionId());
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParam[0] += " AND tpos.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParam[0] += " AND tpos.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParam[0] += " AND tpos.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}
			dq = new DBPreparedQuery("dReportManager_GetPositionSummaryReport", dynParam);

			int cnt = 1;
			dq.setId(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setId(cnt++, PositionConstants.STEP_ACTIVE);

			dq.setId(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setId(cnt++, PositionConstants.STEP_ACTIVE);

			dq.setId(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setId(cnt++, PositionConstants.STEP_ACTIVE);
			dq.setId(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setId(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);

			dq.setId(cnt++, ApplicantConstants.APPLICANT_NOT_JOINED);
			dq.setId(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setId(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setId(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setId(cnt++, PositionConstants.STEP_ACTIVE);

			dq.setString(cnt++, PositionConstants.POSITION_SKILL_PRIMARY);
			dq.setInt(cnt++, UserConstants.ROLE_RECRUITER);
			dq.setId(cnt++, PositionConstants.STEP_ACTIVE);

			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setId(cnt++, ApplicantConstants.APPLICANT_JOINED);

			ArrayList<SimpleDataObject> result = dq.getResult();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; (result != null && i < result.size()); i++) {
				SimpleDataObject sdo = (SimpleDataObject) result.get(i);
				String positionId = sdo.getString("positionId");
				sb.append(positionId + ",");
			}
			String positionIds = sb.toString();
			if (positionIds != null && positionIds.length() > 0) {
				positionIds = positionIds.substring(0, positionIds.length() - 1);
			}
			if (!Utils.isBlankOrNull(positionIds)) {
				dynParam = new String[1];
				dynParam[0] = " AND tpos.position_id in (?)";
				dq = new DBPreparedQuery("dReportManager_GetPositionSummaryReportSteps", dynParam);
				dq.setId(1, SelectionProcessConstants.STEP_REJECT);
				dq.setId(2, SelectionProcessConstants.STEP_NOT_ATTENDED);
				dq.setId(3, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
				dq.setInt(4, PositionConstants.STEP_NOT_DEFAULT);
				dq.setId(5, PositionConstants.STEP_ACTIVE);
				dq.setString(6, positionIds);
				ArrayList<SimpleDataObject> setpSummary = dq.getResult();
				// construct step header names list
				ArrayList<String> stepHeaders = new ArrayList<String>();
				String prevPositionId = null;
				for (int i = 0; setpSummary != null && i < setpSummary.size(); i++) {
					SimpleDataObject sdo = setpSummary.get(i);
					String positionId = sdo.getString("positionId");
					if (positionId.equals(prevPositionId) || prevPositionId == null) {
						stepHeaders.add(sdo.getString("positionStepTitle"));
					} else {
						break;
					}
					prevPositionId = positionId;
				}
				columnHeaders = getPositionFunnelReportColumnNames(stepHeaders);
				columnValues = getPositionFunnelReportColumnValues(result, setpSummary, stepHeaders.size());
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating Current Summary report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		columnAndData.add(0, columnHeaders);
		columnAndData.add(1, columnValues);
		return columnAndData;

	}

	private ArrayList<SimpleDataObject> getPositionFunnelReportColumnValues(ArrayList<SimpleDataObject> summaryData, ArrayList<SimpleDataObject> stepData, int dynamicColumnCount) {
		ArrayList<SimpleDataObject> columnValues = new ArrayList<SimpleDataObject>();
		try {
			for (int i = 0; (summaryData != null && i < summaryData.size()); i++) {
				SimpleDataObject sdo = summaryData.get(i);
				String positionId = sdo.getString("positionId");
				String skills = Utils.isBlankOrNull(sdo.getString("positionPrimarySkills")) ? "" : sdo.getString("positionPrimarySkills");
				String requestedByDate = Utils.isBlankOrNull(sdo.getString("hireByDate")) ? "" : sdo.getString("hireByDate");
				String broadSkills = Utils.isBlankOrNull(sdo.getString("positionPrimarySkills")) ? "" : sdo.getString("positionPrimarySkills");
				String positionCode = Utils.isBlankOrNull(sdo.getString("positionCode")) ? "" : sdo.getString("positionCode");
				String departmentName = Utils.isBlankOrNull(sdo.getString("departmentName")) ? "" : sdo.getString("departmentName");
				String leadTime = Utils.isBlankOrNull(sdo.getString("leadTime")) ? "" : sdo.getString("leadTime");
				String originalOnboardReqDate = Utils.isBlankOrNull(sdo.getString("hireByDate")) ? "" : sdo.getString("hireByDate");
				String updatedOnboardReqDate = "";
				String aging = Utils.isBlankOrNull(sdo.getString("age")) ? "" : sdo.getString("age");
				String recruiters = Utils.isBlankOrNull(sdo.getString("recruiters")) ? "" : sdo.getString("recruiters");
				int vacancies = sdo.getInt("vacancies");

				int noOfOffers = sdo.getInt("noOffered");
				String expectedJoiningDate = Utils.isBlankOrNull(sdo.getString("expectedJoiningDate")) ? "" : sdo.getString("expectedJoiningDate");
				String joiningDate = Utils.isBlankOrNull(sdo.getString("dateJoined")) ? "" : sdo.getString("dateJoined");
				int noJoined = sdo.getInt("noJoined");
				int noOffersDeclined = sdo.getInt("noOfferRejected");
				int ratioOpenOfferToVacancies = 0;
				int noOpenOffers = noOfOffers - noJoined - noOffersDeclined;
				String source = Utils.isBlankOrNull(sdo.getString("sourceTitle")) ? "" : sdo.getString("sourceTitle");
				String positionCloseDate = Utils.isBlankOrNull(sdo.getString("closuredate")) ? "" : sdo.getString("closuredate");
				String positionCloseTLAnalysis = "";
				String remarks = "";

				if (noOpenOffers < 0) {
					noOpenOffers = 0;
				}
				int netOpenVacancies = vacancies - noJoined;
				if (netOpenVacancies > 0) {
					ratioOpenOfferToVacancies = noOpenOffers / netOpenVacancies;
				}
				if (noJoined > 0) {
					expectedJoiningDate = "";
				}

				SimpleDataObject cv = new SimpleDataObject();
				int colNo = 0;
				String ColPrefix = "CV_";
				cv.setAttribute(ColPrefix + colNo++, skills);
				cv.setAttribute(ColPrefix + colNo++, requestedByDate);
				cv.setAttribute(ColPrefix + colNo++, broadSkills);
				cv.setAttribute(ColPrefix + colNo++, positionCode);
				cv.setAttribute(ColPrefix + colNo++, departmentName);
				cv.setAttribute(ColPrefix + colNo++, leadTime);
				cv.setAttribute(ColPrefix + colNo++, originalOnboardReqDate);
				cv.setAttribute(ColPrefix + colNo++, updatedOnboardReqDate);
				cv.setAttribute(ColPrefix + colNo++, aging);
				cv.setAttribute(ColPrefix + colNo++, recruiters);
				cv.setAttribute(ColPrefix + colNo++, "" + vacancies);

				ArrayList<SimpleDataObject> stepForPosition = getPositionFunnelReportStepsForPosition(stepData, positionId);
				int listSize = 0;
				if (stepForPosition != null) {
					listSize = stepForPosition.size();
				}
				for (int c = 0; c < dynamicColumnCount; c++) {
					String noReceived = "";
					String noRejected = "";
					String noForwarded = "";
					if (c <= listSize - 1) {
						SimpleDataObject stdt = stepForPosition.get(c);
						noReceived = stdt.getString("noAvailable");
						noRejected = stdt.getString("noRejected");
						noForwarded = stdt.getString("noForwarded");
					}
					cv.setAttribute(ColPrefix + colNo++, noReceived);
					cv.setAttribute(ColPrefix + colNo++, noRejected);
					cv.setAttribute(ColPrefix + colNo++, noForwarded);

				}

				cv.setAttribute(ColPrefix + colNo++, "" + noOfOffers);
				cv.setAttribute(ColPrefix + colNo++, expectedJoiningDate);
				cv.setAttribute(ColPrefix + colNo++, joiningDate);
				cv.setAttribute(ColPrefix + colNo++, "" + noJoined);
				cv.setAttribute(ColPrefix + colNo++, "" + noOffersDeclined);
				cv.setAttribute(ColPrefix + colNo++, "" + ratioOpenOfferToVacancies);
				cv.setAttribute(ColPrefix + colNo++, "" + noOpenOffers);
				cv.setAttribute(ColPrefix + colNo++, source);
				cv.setAttribute(ColPrefix + colNo++, positionCloseDate);
				cv.setAttribute(ColPrefix + colNo++, positionCloseTLAnalysis);
				cv.setAttribute(ColPrefix + colNo++, remarks);
				columnValues.add(cv);

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR in getPositionFunnelReportColumnValues ", e);
		}

		return columnValues;
	}

	private ArrayList<SimpleDataObject> getPositionFunnelReportStepsForPosition(ArrayList<SimpleDataObject> summaryData, String positionId) {
		ArrayList<SimpleDataObject> stepSummary = new ArrayList<SimpleDataObject>();
		try {
			boolean found = false;
			for (int i = 0; i < summaryData.size(); i++) {
				SimpleDataObject sdo = summaryData.get(i);
				if (positionId.equals(sdo.getString("positionId"))) {
					stepSummary.add(sdo);
					found = true;
				} else {
					if (found)
						break;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR in getPositionFunnelReportStepsForPosition ", e);
		}
		return stepSummary;
	}

	private ArrayList<String> getPositionFunnelReportColumnNames(ArrayList<String> stepHeaders) {
		ArrayList<String> ch = new ArrayList<String>();
		try {

			ch.add(TPLabels.getLabel("position_summary_report.label.col_skills"));
			ch.add(TPLabels.getLabel("common.hire_by_date"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_broad_skills"));
			ch.add(TPLabels.getLabel("common.position_code"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_department"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_lead_time"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_original_onboard_date"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_updated_onboard_date"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_ageing"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_recruiters"));
			ch.add(TPLabels.getLabel("common.vacancies"));

			for (int i = 0; stepHeaders != null && i < stepHeaders.size(); i++) {
				String stepName = stepHeaders.get(i);
				ch.add("CV received in " + stepName);
				ch.add("CV rejected in " + stepName);
				ch.add("CV forwarded from " + stepName);
			}

			ch.add(TPLabels.getLabel("position_summary_report.label.col_no_of_offers"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_expected_joining_date"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_joining_date"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_no_joined"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_offer_decline"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_ratio_openoffer_netopen")+" "+TPLabels.getLabel("common.vacancies"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_no_open_offer"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_source"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_closure_date"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_closure_tl_analysis"));
			ch.add(TPLabels.getLabel("position_summary_report.label.col_remarks"));
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR in getPositionFunnelReportColumnNames ", e);
		}

		return ch;
	}

	public String getApplicantForStepsInXml(String stepIds) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			List applicants = getApplicantsForSteps(stepIds);
			wr.startDocument();
			wr.startElement("applicants");
			if (applicants != null && applicants.size() > 0) {
				Iterator itr = applicants.iterator();
				while (itr.hasNext()) {
					SimpleDataObject data = (SimpleDataObject) itr.next();

					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", String.valueOf(data.getString("applicantId")));
					wr.startElement("", "applicant", "", atr);
					String applicantName = (data.getString("applicantName") == null) ? "" : data.getString("applicantName");
					wr.characters(applicantName);
					wr.endElement("applicant");
				}
			}
			wr.endElement("applicants");
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while generating the xml file for steps for position", e);
		}
		return sWr.toString();
	}

	private List getApplicantsForSteps(String stepIds) {
		List applicants = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[2];
			dynParam[0] = "";
			dynParam[1] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(stepIds)) {
				String[] arrIds = stepIds.split(",");
				String qMarks = "";
				for (int i = 0; i < arrIds.length; i++) {
					qMarks += (i > 0) ? ",?" : "?";
					dynamicContent.add(arrIds[i].trim());
				}
				ArrayList<String> clo = (ArrayList<String>) dynamicContent.clone();
				dynamicContent.addAll(clo);
				dynParam[0] = qMarks;
				dynParam[1] = qMarks;
			}
			dq = new DBPreparedQuery("dReportManager_GetApplicantForSteps", dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			applicants = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the applicants for the step", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}

	private class FetchApplicant implements Callable<List<SimpleDataObject>>{
		private PermissionSet permissionSet;
		private String userRole;
		private String userIds;
		private String userId;
		private final String DASHBOARDCONSTANT = DashboardConstants.TODO_LIST_TYPE_LIST;
		
		
		public FetchApplicant(PermissionSet permissionSet, String userRole,String userIds,String userId) {
			this.permissionSet = permissionSet;
			this.userRole = userRole;
			this.userId=userId;
			this.userIds=userIds;
		}
		
		@Override
		public List<SimpleDataObject> call() throws Exception {
			TPLogger.getLogger().info("Starting fetch thread "+ Thread.currentThread().getName());
			long time = System.nanoTime();
			SelectionProcessManager manager = new SelectionProcessManager();
			List<SimpleDataObject> response = manager.getApplicants(permissionSet, userRole, userIds, null, null, null, null, null, null, null, userId, DashboardConstants.TODO_LIST_TYPE_LIST, null, null);
			TPLogger.getLogger().info("Ending fetch thread "+ Thread.currentThread().getName() +" Time taken : "+ (System.nanoTime()-time));
			return response;
		}
		
	}
	
	public ArrayList getToDoList(PermissionSet permissionSet, FilterData filterData, String userRole, String userId) {
		ArrayList<PendingActionsView> toDoList = new ArrayList<PendingActionsView>();		
		try {
			SelectionProcessManager manager = new SelectionProcessManager();
			String[] userIds = filterData.getInterviewers().split(",");
//			List<SimpleDataObject> applicantsToDos = manager.getApplicants(permissionSet, userRole, userId, null, null, null, null, null, null, null, userIds[i], DashboardConstants.TODO_LIST_TYPE_LIST, null, null);
			Map<String,List<SimpleDataObject>> valMap = new HashMap<String,List<SimpleDataObject>>();
			ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			for(int i=0;i<userIds.length;i++){
				FetchApplicant fetchThread = new FetchApplicant(permissionSet, userRole, userId, userIds[i]);
				Future<List<SimpleDataObject>> result = executorService.submit(fetchThread);
				valMap.put(userIds[i], result.get());
			}
			executorService.shutdown();
			executorService.awaitTermination(Long.MAX_VALUE,TimeUnit.SECONDS);
			for (int i = 0; i < userIds.length; i++) {
				List<SimpleDataObject> applicantsToDos = valMap.get(userIds[i]);
				ArrayList<PendingActionsView> newTodos = new ArrayList<PendingActionsView>();				
				String user = getUsersForIds(userIds[i]);
				for (int j = 0; j < applicantsToDos.size(); j++) {
					PendingActionsView view = new PendingActionsView();					
					SimpleDataObject sdo = (SimpleDataObject) applicantsToDos.get(j);	
					if(sdo.getInt("actionType") != DashboardConstants.ACTION_REQUIRED_REQUISITION_APPROVAL) {
						view.setCandidate(sdo.getString("applicantName"));						
					} else {
						view.setCandidate("NA");
					}	
					try {
						view.setDueOn(DateUtils.getSystemDateFormat(sdo.getDate("processDateCreated")));						
					} catch (ClassCastException e) {
						TPLogger.getLogger().error("ClassCastException: processDateCreated cannot be fetched in DATETIME Format.");
						view.setDueOn("");
					}
					view.setPosition(sdo.getString("applicantPosition"));					
					view.setUser(user);
					view.setStepTitle(sdo.getString("applicantStep"));
					view.setAttribute("action", sdo.getString("actionRequired"));	
					newTodos.add(view);
				}	
				toDoList.addAll(newTodos);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			
		}
		return toDoList;
	}

	public List getOverallRecruitmentCostSummaryReport(FilterData filterData) {
		List data = null;
		DBPreparedQuery dq = null;
		try {
			java.util.Date tDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			java.sql.Date toDt = Utils.convertDateToSQLDate(tDt);

			java.util.Date tDt2 = Utils.adjustDateBy(tDt, Calendar.DATE, 1);
			java.sql.Date toDt2 = Utils.convertDateToSQLDate(tDt2);

			java.util.Date fDt = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			java.sql.Date fromDt = Utils.convertDateToSQLDate(fDt);

			dq = new DBPreparedQuery("dReportManager_OverallRecruitmentCostSummary");
			dq.setDate(1, fromDt);
			dq.setDate(2, toDt);
			dq.setDate(3, fromDt);
			dq.setDate(4, toDt2);
			data = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for overall recruitment cost summary report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	public int getNoOfCandidatesJoinedOverPeriod(FilterData data) {
		int numberOfCandidates = 0;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dReportManager_GetNoOfCandidatesJoined");

			java.util.Date tDt = Utils.convertToDate(data.getToDate(), "dd/MM/yyyy");
			tDt = Utils.adjustDateBy(tDt, Calendar.DATE, 1);
			java.sql.Date toDt = Utils.convertDateToSQLDate(tDt);

			java.util.Date fDt = Utils.convertToDate(data.getFromDate(), "dd/MM/yyyy");
			java.sql.Date fromDt = Utils.convertDateToSQLDate(fDt);

			dq.setString(1, SelectionProcessConstants.APPLICANT_JOINED);
			dq.setDate(2, fromDt);
			dq.setDate(3, toDt);
			List<SimpleDataObject> result = dq.getResult();

			if (result != null && result.size() > 0) {
				numberOfCandidates = result.get(0).getInt("count");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the number of candidates joined over period", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return numberOfCandidates;
	}

	public List getOverallRecruitmentCostDetailReport(FilterData filterData) {
		List data = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];			
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParams[0] = " tp.position_code ";
			} else {
				dynParams[0] = " tp.position_title ";
			}
			java.util.Date tDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			java.sql.Date toDt = Utils.convertDateToSQLDate(tDt);

			java.util.Date tDt2 = Utils.adjustDateBy(tDt, Calendar.DATE, 1);
			java.sql.Date toDt2 = Utils.convertDateToSQLDate(tDt2);

			java.util.Date fDt = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			java.sql.Date fromDt = Utils.convertDateToSQLDate(fDt);

			dq = new DBPreparedQuery("dReportManager_OverallRecruitmentCostDetail", dynParams);
			dq.setDate(1, fromDt);
			dq.setDate(2, toDt);
			dq.setDate(3, fromDt);
			dq.setDate(4, toDt2);
			data = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for overall recruitment cost detail report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	public List getPositionwiseRecruitmentCostSummaryReport(FilterData filterData) {
		List data = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[2];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " tp.position_code ";
			} else {
				dynParam[0] = " tp.position_title ";
			}
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParam[1] = " and tcp.position_id in (select tpos.position_id " + " from tp_positions tpos " + " where tpos.position_status = " + PositionConstants.POSITION_STATUS_OPENED + ")";
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
				dynParam[1] = " and tcp.position_id = " + filterData.getPositionId();
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParam[1] = " and tcp.position_id in (select tpos.position_id " + " from tp_positions tpos " + " where tpos.dept_id = " + filterData.getDepartmentId() + ")";
			} else {
				dynParam[1] = "";
			}
			java.util.Date tDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			java.sql.Date toDt = Utils.convertDateToSQLDate(tDt);

			java.util.Date tdt2 = Utils.adjustDateBy(tDt, Calendar.DATE, 1);
			java.sql.Date toDt2 = Utils.convertDateToSQLDate(tdt2);

			java.util.Date fDt = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			java.sql.Date fromDt = Utils.convertDateToSQLDate(fDt);

			dq = new DBPreparedQuery("dReportManager_GetPositionwiseRecruitmentCostSummaryReport", dynParam);
			dq.setDate(1, fromDt);
			dq.setDate(2, toDt);
			dq.setDate(3, fromDt);
			dq.setDate(4, toDt2);
			data = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for positionwise recruitment cost summary report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}
	
	public Map<String,List<OfferedCTCView>> getOfferCTCDetailsReport(FilterData filterData) {
		List data = null;
		List data1 = null;
		Map<String,List<OfferedCTCView>> map = new HashMap<String,List<OfferedCTCView>>();
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			if(!Utils.isBlankOrNull(filterData.getPositionId())){
				dynParam[0] = " AND  position_code = "+filterData.getPositionId();	
			}else {
				dynParam[0] = "";
			}
			java.util.Date tDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			java.sql.Date toDt = Utils.convertDateToSQLDate(tDt);

			java.util.Date fDt = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			java.sql.Date fromDt = Utils.convertDateToSQLDate(fDt);

			dq = new DBPreparedQuery("dReportManager_GetOfferCodeReport", dynParam);
			dq.setDate(1, fromDt);
			dq.setDate(2, toDt);
			data = dq.getResult();
			for(OfferedCTCView offeredCTCView : (ArrayList<OfferedCTCView>)data){
				dq = new DBPreparedQuery("dReportManager_GetOferedCTCDetailsReport", dynParam);
				dq.setDate(1, fromDt);
				dq.setDate(2, toDt);
				dq.setString(3,offeredCTCView.getOfferCode());
				data1 = dq.getResult();
				map.put(offeredCTCView.getOfferCode(), (ArrayList<OfferedCTCView>)data1);
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for CTC details report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return map;
	}

	public List getPositionwiseRecruitmentCostDetailReport(FilterData filterData) {
		List data = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[2];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " tp.position_code ";
			} else {
				dynParam[0] = " tp.position_title ";
			}
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParam[1] = " and tcp.position_id in (select tpos.position_id " + " from tp_positions tpos " + " where tpos.position_status = " + PositionConstants.POSITION_STATUS_OPENED + ")";
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
				dynParam[1] = " and tcp.position_id = " + filterData.getPositionId();
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParam[1] = " and tcp.position_id in (select tpos.position_id " + " from tp_positions tpos " + " where tpos.dept_id = " + filterData.getDepartmentId() + ")";
			} else {
				dynParam[1] = "";
			}
			java.util.Date tDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			java.sql.Date toDt = Utils.convertDateToSQLDate(tDt);

			java.util.Date tdt2 = Utils.adjustDateBy(tDt, Calendar.DATE, 1);
			java.sql.Date toDt2 = Utils.convertDateToSQLDate(tdt2);

			java.util.Date fDt = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			java.sql.Date fromDt = Utils.convertDateToSQLDate(fDt);

			dq = new DBPreparedQuery("dReportManager_GetPositionwiseRecruitmentCostDetailReport", dynParam);
			dq.setDate(1, fromDt);
			dq.setDate(2, toDt);
			dq.setDate(3, fromDt);
			dq.setDate(4, toDt2);
			data = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for overall positionwise cost detail report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
		return data;
	}

	public List getSourcewiseRecruitmentCostSummaryReport(FilterData filterData) {
		List data = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];

			if (!Utils.isBlankOrNull(filterData.getSourceId())) {
				dynParam[0] = " and tc.source_id = " + filterData.getSourceId();
			} else {
				dynParam[0] = "";
			}
			java.util.Date tDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			java.sql.Date toDt = Utils.convertDateToSQLDate(tDt);

			java.util.Date tdt2 = Utils.adjustDateBy(tDt, Calendar.DATE, 1);
			java.sql.Date toDt2 = Utils.convertDateToSQLDate(tdt2);

			java.util.Date fDt = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			java.sql.Date fromDt = Utils.convertDateToSQLDate(fDt);

			dq = new DBPreparedQuery("dReportManager_GetSourcewiseRecruitmentCostSummaryReport", dynParam);
			dq.setDate(1, fromDt);
			dq.setDate(2, toDt);
			dq.setDate(3, fromDt);
			dq.setDate(4, toDt2);
			data = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for sourcewise recruitment cost summary report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	public List getSourcewiseRecruitmentCostDetailReport(FilterData filterData) {
		List data = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];

			if (!Utils.isBlankOrNull(filterData.getSourceId())) {
				dynParam[0] = " and tc.source_id = " + filterData.getSourceId();
			} else {
				dynParam[0] = "";
			}
			java.util.Date tDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			java.sql.Date toDt = Utils.convertDateToSQLDate(tDt);

			java.util.Date tdt2 = Utils.adjustDateBy(tDt, Calendar.DATE, 1);
			java.sql.Date toDt2 = Utils.convertDateToSQLDate(tdt2);

			java.util.Date fDt = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			java.sql.Date fromDt = Utils.convertDateToSQLDate(fDt);

			dq = new DBPreparedQuery("dReportManager_GetSourcewiseRecruitmentCostDetailReport", dynParam);
			dq.setDate(1, fromDt);
			dq.setDate(2, toDt);
			dq.setDate(3, fromDt);
			dq.setDate(4, toDt2);
			data = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for overall sourcewise cost detail report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	public List getTransactionCostReport(FilterData filterData) {
		List data = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " tp.position_code ";
			} else {
				dynParam[0] = " tp.position_title ";
			}
			
			java.util.Date tDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			java.util.Date tdt = Utils.adjustDateBy(tDt, Calendar.DATE, 1);
			java.sql.Date toDt = Utils.convertDateToSQLDate(tdt);

			java.util.Date fDt = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			java.sql.Date fromDt = Utils.convertDateToSQLDate(fDt);

			dq = new DBPreparedQuery("dReportManager_GetTransactionCostReport", dynParam);
			dq.setDate(1, fromDt);
			dq.setDate(2, toDt);
			data = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for transaction cost report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	/*
	 * Starts:Candidate Status Report
	 */	
	public ArrayList getCandidateStatusReport(FilterData filterData, String userId, PermissionSet permissionSet) {

		DBPreparedQuery dq = null;
		ArrayList<CandidateStatusReportView> applicants = null;
		ArrayList<CandidateStatusReportView> applicantsLastAction = null;
		Date toDate = null;
		Date fromDate = null;		
		try {
			if (!Utils.isBlankOrNull(filterData.getToDate())) {
				toDate = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
				toDate = Utils.adjustDateBy(toDate, Calendar.DATE, 1);
			}
			if (!Utils.isBlankOrNull(filterData.getFromDate())) {
				fromDate = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			}	
			String[] dynParam = new String[2];
			if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA()) ){  
				dynParam[0] = "tst.source_type, ts.source_title";
			}else{
				dynParam[0] = "'" + GlobalConstants.CONFIDENTIAL_CHARACTER + "' as source_type, '"
						+ GlobalConstants.CONFIDENTIAL_CHARACTER + "' as source_title";
			}
			
			ArrayList<String> dynamicContent = new ArrayList<String>();				
			
			dynParam[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "ta.applicant_position_id", dynamicContent, permissionSet);
					
			if (!Utils.isBlankOrNull(filterData.getSourceCategoryId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getSourceCategoryId(), dynamicContent);
				dynParam[1] += " and tst.source_type_id IN (" + qMarks + ") ";				
			}
			
			if (!Utils.isBlankOrNull(filterData.getSourceId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getSourceId(), dynamicContent);
				dynParam[1] += " and ts.source_id IN (" + qMarks + ") ";
				//dynamicContent.add(filterData.getSourceId());
			}
			
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParam[1] += " AND tpos.position_status = ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParam[1] += " AND (tpos.position_status = ? OR tpos.position_status = ?) ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
				dynParam[1] += " AND tpos.position_id = ? ";
				dynamicContent.add(filterData.getPositionId());
			}else{
				dynParam[1] += " AND tpos.position_status != ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_TEMPLATE);
			}
			
			if (filterData.getActionId().equals(ReportConstants.FILTER_IMPORTED_BY)) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				dynParam[1] += " AND ta.user_id in (" + qMarks + ") ";
				dynParam[1] += " AND ta.applicant_date_created >=? ";
				dynParam[1] += " AND ta.applicant_date_created <=? ";
				dynamicContent.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
				dynamicContent.add(Utils.getDateConvertedToString(toDate, Utils.redYYYYMMDDFormat));
			}
			if (filterData.getActionId().equals(ReportConstants.FILTER_MOVED_BY)) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);

				dynParam[1] += " AND ta.applicant_id in ";
				dynParam[1] += " ( SELECT distinct tasp.applicant_id ";
				dynParam[1] += " FROM tp_applicant_selection_process tasp ";
				dynParam[1] += " LEFT JOIN tp_applicant_selection_process_traits  taspt ";
				dynParam[1] += " ON(tasp.process_id = taspt.process_id) ";
				dynParam[1] += " WHERE ( tasp.user_id in (" + qMarks + ") OR taspt.user_id in (" + qMarks + ") ) ";
				dynParam[1] += " AND tasp.process_moved_date >=? AND tasp.process_moved_date <=now() )";
				dynamicContent.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
			}
			if (filterData.getActionId().equals(ReportConstants.FILTER_REJECTED_BY)) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);

				dynParam[1] += "	AND ta.applicant_id in ";
				dynParam[1] += "	( SELECT distinct tasp.applicant_id ";
				dynParam[1] += "	FROM tp_applicant_selection_process tasp";
				dynParam[1] += "	WHERE tasp.user_id in (" + qMarks + ") ";
				dynParam[1] += " AND tasp.process_moved_date <=now()";
				dynParam[1] += " AND tasp.process_moved_date >=?";
				dynParam[1] += " AND tasp.position_step_id_to=?)";
				dynamicContent.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
				dynamicContent.add(SelectionProcessConstants.STEP_REJECT);
			}

			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParam[1] += " AND ta.is_confidential = ? ";
				dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}			
			
			dq = new DBPreparedQuery("dReportManager_GetCandidateStatusDetailReport", dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			applicants = dq.getResult();

			if (applicants != null && applicants.size() > 0) {
				for (int i = 0; i < applicants.size(); i++) {
					CandidateStatusReportView data = applicants.get(i);
					ApplicantManager applicantManager = new ApplicantManager();
					if (!Utils.isBlankOrNull(data.getApplicantId())) {
						data.setResponsibleUsers(" ");
						data.setActionRequired(" ");
						if (!Utils.isBlankOrNull(data.getApplicantPositionId()) && !Utils.isBlankOrNull(data.getApplicantStepId())) {
							SimpleDataObject summaryData = applicantManager.getApplicantShortlistSummary("" + data.getApplicantId(), data.getApplicantPositionId(), data.getApplicantStepId(), 1);
							if(summaryData!=null){
								String users = (summaryData.getString("userResponsible") == null) ? "" : summaryData.getString("userResponsible");
								String actionRequired = (summaryData.getString("actionRequired") == null) ? "" : summaryData.getString("actionRequired");
								String positionStepTitle=(data.getPositionStepTitle() == null) ? "" : data.getPositionStepTitle();
								if ("schedule".equalsIgnoreCase(actionRequired)) {
									actionRequired = actionRequired + " " + positionStepTitle;
								} else if ("conduct".equalsIgnoreCase(actionRequired)) {
									actionRequired = actionRequired + " " + positionStepTitle + " on " + Utils.getDateConvertedToString(summaryData.getDate("appointmentDate"), "dd-MMM") + " at " + Utils.getDateConvertedToString(summaryData.getDate("appointmentDate"), "h:mm a");
								} else if ("confirm".equalsIgnoreCase(actionRequired)) {
									actionRequired = actionRequired + " attendance for " + positionStepTitle;
								} else if ("feedback".equalsIgnoreCase(actionRequired)) {
									actionRequired = "enter feedback for " + positionStepTitle;
								} else if ("hold".equalsIgnoreCase(actionRequired)) {
									actionRequired = "On Hold";
								}
								if (!Utils.isBlankOrNull(users)) {
									actionRequired = users + " to " + actionRequired;
								}
								data.setActionRequired(actionRequired);
							}
							else{
								data.setActionRequired("");
							}
							
						}
						dq = new DBPreparedQuery("dReportManager_GetCandidateStatusLastAction");
						dq.setId(1, data.getApplicantId());
						String lastAction = "";
						SimpleDataObject sDO = (SimpleDataObject) dq.getSingleObjectResult();
						if (sDO != null) {
							String positionStepIdTo =(sDO.getString("positionStepIdTo") == null) ? "" :sDO.getString("positionStepIdTo"); 
							String processMovedDate = "";
							
							try {
								processMovedDate = DateUtils.getSystemDateFormat(sDO.getDate("processMovedDate"));
							} catch (ClassCastException e) {
								TPLogger.getLogger().error("ClassCastException: processMovedDate cannot be fetched in DATETIME Format.");
							}
							
							if(Utils.isBlankOrNull(processMovedDate)){
								processMovedDate="";
							}
							String positionStepTitle = sDO.getString("positionStepTitle");
							String name = (sDO.getString("name")== null) ? "" :sDO.getString("name"); 
							if ("0".equalsIgnoreCase(positionStepIdTo)) {
								lastAction = "Rejected by " + name + " on " + processMovedDate;
							} else if ("-2".equalsIgnoreCase(positionStepIdTo)) {
								lastAction = "Moved to Joined by " + name + " on " + processMovedDate;
							} else if ("-1".equalsIgnoreCase(positionStepIdTo)) {
								lastAction = "Moved to On Hold by " + name + " on " + processMovedDate;
							} else if ("-3".equalsIgnoreCase(positionStepIdTo)) {
								lastAction = "Moved from Confirm attendance, not attended reschedule by " + name + " on " + processMovedDate;
							} else if ("-4".equalsIgnoreCase(positionStepIdTo)) {
								lastAction = "Moved to Rejected before interview(Not Interested) by " + name + " on " + processMovedDate;
							} else if ("-5".equalsIgnoreCase(positionStepIdTo)) {
								lastAction = "Moved from Confirm attendance, attended  by " + name + " on " + processMovedDate;
							} else if ("-6".equalsIgnoreCase(positionStepIdTo)) {
								lastAction = "Moved from Confirm attendance, not attended reject by " + name + " on " + processMovedDate;
							} else if ("-7".equalsIgnoreCase(positionStepIdTo)) {
								lastAction = "Rejected because position is closed by " + name + " on " + processMovedDate;
							} else if (!Utils.isBlankOrNull(positionStepTitle)) {
								lastAction = "Moved to " + positionStepTitle + " by " + name + " on " + processMovedDate;
							}
						}
						data.setLastAction(lastAction);
					}
				}
			}

		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while getting Candidate Status Report", e);
		} 
		/*catch(Exception e){
			TPLogger.getLogger().error("Error while getting Candidate Status Report", e);
		}*/
		finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}

	/* Ends:Candidate Status Report */	
	
	/* Getting Position Name */
	public String getPositionName(String positionId) {
		DBPreparedQuery dq = null;
		String positionName = null;
		try {
			dq = new DBPreparedQuery("dReportManager_fetchPositionDetail");
			dq.setId(1, positionId);
			positionName = dq.getStringResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting Position Name info", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionName;
	}
	
	
	public ArrayList getCandidateOffersReport(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList reportData = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParam[0] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tasp.position_id", dynamicContent, permissionSet);
						
			if (!Utils.isBlankOrNull(filterData.getFromDate()) || !Utils.isBlankOrNull(filterData.getToDate())) {
				if (!Utils.isBlankOrNull(filterData.getFromDate())) {
					Date fromDate = filterData.getConvertedFromDate();
					dynParam[0] += " and tasp.process_moved_date >= ? ";
					dynamicContent.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
				}
				if (!Utils.isBlankOrNull(filterData.getToDate())) {
					Date toDt = filterData.getConvertedToDate();
					if (toDt != null) {
						toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
					}
					dynParam[0] += " and tasp.process_moved_date <= ? ";
					dynamicContent.add(Utils.getDateConvertedToString(toDt, Utils.redYYYYMMDDFormat));
				
				}
			}
			if (ReportConstants.FILTER_OPEN_POSITIONS.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[0] += " and tasp.position_id in (select position_id from tp_positions where position_status = ? )";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				
			} else if (ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[0] += " and tasp.position_id in (select position_id from tp_positions where position_status = ? OR position_status = ? )";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
				
			} else if (ReportConstants.FILTER_SPECIFIC_POSITION.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[0] += " and tasp.position_id = ? ";
				dynamicContent.add(filterData.getPositionId());
			} else if (ReportConstants.FILTER_SPECIFIC_DEPARTMENT.equalsIgnoreCase(filterData.getFilterId())) {
				dynParam[0] += " and tasp.position_id in (select position_id from tp_positions where dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParam[0] += " and sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParam[0] += " and sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
				dynParam[0] += " ) ";
			}

			dq = new DBPreparedQuery("dReportManager_CandidateOffersReport", dynParam);
			int cnt = 1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);			
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			reportData = dq.getResult();			
			reportData = getCustomFields(reportData, CustomFieldConstants.ENTITY_TYPE_APPLICANT);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the offer to joined report data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return reportData;
	}
	
	private ArrayList getCustomFields(ArrayList<CandidateOffersView> reportData, int entityId){
		DBPreparedQuery dq = null;
		ArrayList customFields = null;
		try {
			String[] dynParams = new String[1]; 
			if(entityId == CustomFieldConstants.ENTITY_TYPE_APPLICANT) {
				dynParams[0] = " tp_custom_field_values_applicant ";
				for (int i = 0; i < reportData.size(); i++) {
					CandidateOffersView cov = (CandidateOffersView) reportData.get(i);
					String appId = (String)cov.getApplicantId();
					
					dq = new DBPreparedQuery("dReportManager_GetCustomFields", dynParams);
					dq.setString(1, appId);
					customFields = dq.getResult();
					
					HashMap<String, String> customField = new HashMap<String, String>();
					for (int j = 0; j < customFields.size(); j++) {
						SimpleDataObject sD = (SimpleDataObject) customFields.get(j);
						customField.put((String)sD.getAttribute("fieldName"),(String)sD.getAttribute("fieldValue"));
					}				
					cov.setCustomFields(customField);
					reportData.set(i, cov);
				}
			}					
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting custom fields", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return reportData;
	}
	
	public ArrayList getJoinedCandidatesCSVReport(FilterData filterData, String userId, PermissionSet permissionSet) {
		ArrayList result = new ArrayList();
		DBPreparedQuery dq = null;
		int cnt = 1;
		try {
			java.sql.Date fromDt = null;
			java.sql.Date toDt = null;			
			if ((filterData.getDateRange()).equals(ReportConstants.CUSTOM)) {
				fromDt = filterData.getConvertedFromDateMonthly();
				toDt = filterData.getConvertedToDateMonthly();
			} else {
				fromDt = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
				toDt = Utils.convertToSQLDate(filterData.getToDate(), Utils.regEUDateFormat);
			}
			
			String[] dynParams = new String[2];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParams[0] = " tp.position_code ";
			} else {
				dynParams[0] = " tp.position_title ";
			}			
			
			dynParams[1] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
				dynParams[1] += " AND tp.position_id in (select su.position_id from tp_position_step_users su, tp_position_steps st where st.position_step_id = su.position_step_id and st.position_step_status = ? "
						+ " and su.user_id = ? union select position_id from tp_positions where position_requested_by = ?  " + " UNION SELECT distinct traf.position_id FROM tp_requisition_approval_feedback traf WHERE traf.by_user_id=? OR traf.to_user_id=? )";
				dynamicContent.add(PositionConstants.STEP_ACTIVE);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
			}			
			
			if (!Utils.isBlankOrNull(filterData.getDepartmentId())) {
				dynParams[1] += " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParams[1] += " AND tp.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParams[1] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}
			dq = new DBPreparedQuery("dReportManager_GetMonthlyJoiningCSVReport", dynParams);	
			dq.setString(cnt++, Utils.EXP_YRS);
			dq.setString(cnt++, Utils.EXP_MOS);
			
			//Show Current CTC or not
			dq.setBoolean(cnt++, ImportConfigurationManager.isCurrentCTCViewable(permissionSet));
			dq.setString(cnt++, GlobalConstants.CONFIDENTIAL_CHARACTER);

			//Show Expected CTC or not
			dq.setBoolean(cnt++, ImportConfigurationManager.isExpectedCTCViewable(permissionSet));
			dq.setString(cnt++, GlobalConstants.CONFIDENTIAL_CHARACTER);
			
			//Show Level Offered or not			
			dq.setBoolean(cnt++, ImportConfigurationManager.isLevelOfferedViewable(permissionSet));
			dq.setString(cnt++, GlobalConstants.CONFIDENTIAL_CHARACTER);
			
			//Show Designation Offered or not
			dq.setBoolean(cnt++, ImportConfigurationManager.isDesignationOfferedViewable(permissionSet));
			dq.setString(cnt++, GlobalConstants.CONFIDENTIAL_CHARACTER);
			
			//Show Target CTC or not
			dq.setBoolean(cnt++, ImportConfigurationManager.isCTCOfferedViewable(permissionSet));
			dq.setString(cnt++, GlobalConstants.CONFIDENTIAL_CHARACTER);
			
			//Show Source Title or not
			dq.setBoolean(cnt++, ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE, permissionSet.isSHOW_CONFIDENTIAL_DATA()));
			dq.setString(cnt++, GlobalConstants.CONFIDENTIAL_CHARACTER);
			
			dq.setDate(cnt++, fromDt);
			dq.setDate(cnt++, toDt);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			result = dq.getResult();
		} catch (Exception exep) {
			TPLogger.getLogger().error("Error while populating  populatePositions ", exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	public void generateCSVReport(String reportName,String  outputFileName, ArrayList data, String userId) throws FileNotFoundException, IOException, SQLException{
		String report = "";
		if(reportName.equals(ReportVersionConstants.REPORT_JOINED_CANDIDATES_CSV)){
			MonthlyJoinedCandidatesCSVReport mJoinedCandidatesCSVReport = new MonthlyJoinedCandidatesCSVReport();
			report = mJoinedCandidatesCSVReport.generateMontlyJoinedCandidateCSVReport(reportName, data);
		}
		
		String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
		String destinationPath = Utils.concatFilePath(basePath, ReportConstants.REPORT_DESTINATION_FOLDER);
		String filePath = Utils.concatFilePath(destinationPath, outputFileName);		
		FileWriter writer = new FileWriter(filePath);
		writer.write(report.toCharArray());
		writer.close();		
		
		//AuditManager auditManager = new AuditManager();
		//auditManager.addAudit(null,AuditConstants.TYPE_GENERATED, reportName, AuditConstants.AUDIT_REPORT,  userId,null,null,null,true);
	}
	
	public List<PendingOffersReportData> getPendingOffersList(FilterData filterData,String userId, PermissionSet permissionSet){
		List<PendingOffersReportData> pendingOffersList = null; 
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[3];
			
			
			dynParams[0] = ColumnUtils.getExperinceFormat("ta.applicant_working_since");
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParams[1] = " tp.position_code ";
			} else {
				dynParams[1] = " tp.position_title ";
			}
			ArrayList<String> dynamicContent = new ArrayList<String>();			
			
			dynParams[2] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
			
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[2] += " AND tp.position_status = ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
				dynParams[2] += " AND tp.position_id = ? ";
				dynamicContent.add(filterData.getPositionId());
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParams[2] += " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParams[2] += " AND tp.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParams[2] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}
			
			if (!Utils.isBlankOrNull(filterData.getUsers())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				dynParams[2] += " AND todo.user_id IN (" + qMarks + ")";
			}
			
			dq = new DBPreparedQuery("dReportManager_GetPendingOffersList", dynParams);
			int cnt = 1;
			dq.setString(cnt++, ToDoConstants.TODO_TYPE_SELECTION);
			dq.setString(cnt++,PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			
			pendingOffersList =(List<PendingOffersReportData>) dq.getResult();
			
		} catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return pendingOffersList;
	}
	
	/**
	 * Fetches Rejected Candidate Report Data
	 * @param filterData
	 * @param userId
	 * @param permissionSet
	 * @return
	 * @throws SQLException
	 */
	public List<SimpleDataObject> getRejectedCandiadatesList(FilterData filterData, String userId, PermissionSet permissionSet) throws SQLException {
		List<SimpleDataObject> rcdLst = null;
		DBPreparedQuery dq = null;
		String[] dynParam = {"","",""};
		ArrayList<String> dynamicContent = new ArrayList<String>();
		ArrayList<java.sql.Date> dynamicContent1 = new ArrayList<>();
		int cnt=1;
		try {
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParam[0] += " AND ta.is_confidential=? ";
				dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			dynParam[0] += PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
			if(!Utils.isBlankOrNull(filterData.getFilterId())) {
				if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
					dynParam[0] += " AND tp.position_status = ? ";
					dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
					dynParam[0] += " AND tp.position_id = ? ";
					dynamicContent.add(filterData.getPositionId());
				} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
					dynParam[0] += " AND tp.dept_id = ? ";
					dynamicContent.add(filterData.getDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
						dynParam[0] += " AND tp.sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubDepartmentId());
						if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
							dynParam[0] += " AND tp.sub_sub_dept_id = ? ";
							dynamicContent.add(filterData.getSubSubDepartmentId());
						}
					}
				}
			}
			if(!Utils.isBlankOrNull(filterData.getPositionOwnerId())){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getPositionOwnerId(), dynamicContent);
				dynParam[0] += " AND tp.position_owner_id in (" + qMarks + ") ";
			}
			if (!Utils.isBlankOrNull(filterData.getStepIds())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getStepIds(), dynamicContent);
				dynParam[0] += " AND  tps.step_id in (" + qMarks + ") ";
			}
			if (!Utils.isBlankOrNull(filterData.getStages())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getStages(), dynamicContent);
				dynParam[0] += " AND tsl.step_level in (" + qMarks + ") ";
			}
			if (!Utils.isBlankOrNull(filterData.getUsers())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				dynParam[0] += " AND tu.user_id in (" + qMarks + ") ";
			}
			
			if (!Utils.isBlankOrNull(filterData.getFromDate()) && !Utils.isBlankOrNull(filterData.getToDate())) {
				java.sql.Date fromDt = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
				java.sql.Date toDt = Utils.convertToSQLDate(filterData.getToDate(), Utils.regEUDateFormat);
				toDt = Utils.convertToSQLDate(filterData.getToDate(), Utils.regEUDateFormat);
				dynParam[1] += " AND date(tasp.process_moved_date) between ? AND ?";
				dynamicContent1.add(fromDt);
				dynamicContent1.add(toDt);
			}
			
			dynParam[2] += buildOrderByQuery(filterData);
			
			dq = new DBPreparedQuery("dReportManager_GetRejectedCandidates", dynParam);
			// following constants injected to represent rejection state
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			for (String dynParamVal : dynamicContent) {
				dq.setString(cnt++, dynParamVal);
			}
			for (java.sql.Date dynParamVal : dynamicContent1) {
				dq.setDate(cnt++, dynParamVal);
			}
			rcdLst = (List<SimpleDataObject>) dq.getResult();
			TPLogger.getLogger().debug("Total rejected candidates fetched " + (Utils.isListEmptyOrNull(rcdLst)?" ":rcdLst.size()));
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return rcdLst;
	}
	
	public List<SimpleDataObject> getAllCandiadatesList(FilterData filterData, String userId, PermissionSet permissionSet) throws SQLException {
		List<SimpleDataObject> rcdLst = null;
		DBPreparedQuery dq = null;
		String[] dynParam = {"","",""};
		ArrayList<String> dynamicContent = new ArrayList<String>();
		ArrayList<java.sql.Date> dynamicContent1 = new ArrayList<>();
		int cnt=1;
		try {
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParam[0] += " AND ta.is_confidential=? ";
				dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			if (!Utils.isBlankOrNull(filterData.getSourceCategoryId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getSourceCategoryId(), dynamicContent);				
				dynParam[0] += " AND ts.source_type_id IN (" + qMarks + ") ";				
			} else {
				dynParam[0] += "";
			}
			
			
			if (!Utils.isBlankOrNull(filterData.getSourceId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getSourceId(), dynamicContent);
				dynParam[0] += " AND ta.source_id IN (" + qMarks + ") ";
			} else {
				dynParam[0] += "";
			}	
			
			if (!Utils.isBlankOrNull(filterData.getFromDate()) && !Utils.isBlankOrNull(filterData.getToDate())) {
				java.sql.Date fromDt = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
				java.sql.Date toDt = Utils.convertToSQLDate(filterData.getToDate(), Utils.regEUDateFormat);
				toDt = Utils.convertToSQLDate(filterData.getToDate(), Utils.regEUDateFormat);
				dynParam[1] += " AND date(ta.applicant_date_created) between ? AND ?";
				dynamicContent1.add(fromDt);
				dynamicContent1.add(toDt);
			}
			
			dq = new DBPreparedQuery("dReportManager_GetAllCandidates", dynParam);
			// following constants injected to represent rejection state
			for (String dynParamVal : dynamicContent) {
				dq.setString(cnt++, dynParamVal);
			}
			for (java.sql.Date dynParamVal : dynamicContent1) {
				dq.setDate(cnt++, dynParamVal);
			}
			rcdLst = (List<SimpleDataObject>) dq.getResult();
			TPLogger.getLogger().debug("Total candidates fetched " + (Utils.isListEmptyOrNull(rcdLst)?" ":rcdLst.size()));
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return rcdLst;
	}
	
	private String buildOrderByQuery(FilterData filterData){
		String orderBy="";
		String[] groupByColumns = Utils.getBlankIfNull(filterData.getGroupBy()).split(",");
		for (int i = 0; i < groupByColumns.length; i++) {
			if(ReportDesignConstants.COLUMN_REJECTED_BY.equals(groupByColumns[i])){
  				orderBy+="rejected_by";
  			}else if(ReportDesignConstants.COLUMN_POSITION_TITLE.equals(groupByColumns[i])){
  				orderBy+="position_title";
  			}else if(ReportDesignConstants.COLUMN_DEPARTMENT.equals(groupByColumns[i])){
  				orderBy+="dept_name";
  			}else if(ReportDesignConstants.COLUMN_POSITION_CODE.equals(groupByColumns[i])){
  				orderBy+="position_code";
  			}else if(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE.equals(groupByColumns[i])){
  				orderBy+="source_title";
  			}else if(ReportDesignConstants.COLUMN_STEP_LEVEL.equals(groupByColumns[i])){
  				orderBy+="step_level_name";
  			}else if(ReportDesignConstants.COLUMN_STEP_NAME.equals(groupByColumns[i])){
  				orderBy+="step_title";
  			}
			if(!Utils.isBlankOrNull(orderBy)){
				orderBy+=",";
			}
		}
		return orderBy;
	}
	
	public List<TimeToHireReportData> getTimeToHire(FilterData filterData,String userId, PermissionSet permissionSet){
		List<TimeToHireReportData> timeToHireList = null; 
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[2];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParams[0] = " tp.position_code ";
			} else {
				dynParams[0] = " tp.position_title ";
			}
			ArrayList<String> dynamicContent = new ArrayList<String>();			
			
			dynParams[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
			
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[1] += " AND tp.position_status = ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
				dynParams[1] += " AND tp.position_id = ? ";
				dynamicContent.add(filterData.getPositionId());
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParams[1] += " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParams[1] += " AND tp.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParams[1] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}
			java.sql.Date fromDt = null;
			java.sql.Date toDt = null;
			fromDt = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
			toDt = Utils.convertToSQLDate(filterData.getToDate(), Utils.regEUDateFormat);
			
			dq = new DBPreparedQuery("dReportManager_GetTimeToHire", dynParams);
			int cnt = 1;
			dq.setInt(cnt++, UserConstants.ROLE_RECRUITER);
			dq.setString(cnt++, "Joined");
			dq.setString(cnt++, "Offered");
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setDate(cnt++, fromDt);
			dq.setDate(cnt++, toDt);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}			
			timeToHireList =(List<TimeToHireReportData>) dq.getResult();
			
		} catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return timeToHireList;
	}
	
	public List<HiringActivityReportData> getHiringActivitySummary(FilterData filterData,String userId, PermissionSet permissionSet){
		List<HiringActivityReportData> activityList = null;
		ArrayList<String> dynamicContent1 			= null;
		ArrayList<String> dynamicContent2 			= null;
		DBPreparedQuery dq		= null;
		String[] dynParams		= null;
		String stepTitles		= null;
		try {
			dynParams 		= new String[2];
			dynamicContent1 	= new ArrayList<String>();
			dynamicContent2 	= new ArrayList<String>();
			stepTitles		= filterData.getStepTitles();
			
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParams[0] = " tp.position_code ";
			} else {
				dynParams[0] = " tp.position_title ";
			}
			
			dynParams[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent1, permissionSet);
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[1] += "  tp.position_status = ? ";
				dynamicContent1.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
				dynParams[1] += " AND tp.position_id = ? ";
				dynamicContent1.add(filterData.getPositionId());
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParams[1] += " AND tp.dept_id = ? ";
				dynamicContent1.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParams[1] += " AND tp.sub_dept_id = ? ";
					dynamicContent1.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParams[1] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent1.add(filterData.getSubSubDepartmentId());
					}
				}
			}
			
			if (!Utils.isBlankOrNull(filterData.getUsers())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent1);
				dynParams[1] += " AND tasp.user_id in (" + qMarks + ") ";
			}
			
			if (!Utils.isBlankOrNull(filterData.getPositionOwnerId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getPositionOwnerId(), dynamicContent1);
				dynParams[1] += " AND tp.position_owner_id in (" + qMarks + ") ";
			}
			
			
			
			java.sql.Date fromDt = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
			Date toDt = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
			toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
			
			dq = new DBPreparedQuery("dReportManager_GetHiringActivitySummary", dynParams);
			int cnt = 1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_JOINED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REPEAT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_RESCHEDULE);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_BACK_OUT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_REJECT);
			
			
			for (int i = 0; i < dynamicContent1.size(); i++) {
				dq.setString(cnt++, dynamicContent1.get(i));
			}
			dq.setDate(cnt++, fromDt);
			dq.setDate(cnt++, Utils.convertDateToSQLDate(toDt));
			dq.setDate(cnt++, fromDt);
			dq.setDate(cnt++, Utils.convertDateToSQLDate(toDt));
			for (int i = 0; i < dynamicContent2.size(); i++) {
				dq.setString(cnt++, dynamicContent2.get(i));
			}
			
			activityList 	=	(List<HiringActivityReportData>) dq.getResult();
			
		} catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return activityList;
	}
	
	public Object[] modifyActivityList(List<HiringActivityReportData> activityList){
		List<HiringActivityReportData> modifiedList 	= null;
		Map<String,HiringActivityReportData> posSdoMap 	= null;
		Map<String,String> headerMap 		= null;
		HiringActivityReportData harData 	= null;
		HiringActivityReportData newHarData = null;
		String headerId 	= null;
		Object[] obj 		= new Object[2];
		if(activityList!=null){
			
			headerMap 	 = new HashMap<String, String>();
			modifiedList = new ArrayList<HiringActivityReportData>();
			posSdoMap  	 = new HashMap<String, HiringActivityReportData>();
			Iterator<HiringActivityReportData> itr = activityList.iterator();
			
			while (itr.hasNext()) {
				harData = itr.next();
				if(!posSdoMap.containsKey(harData.getPositionId())){
					newHarData = new HiringActivityReportData();
					posSdoMap.put(harData.getPositionId(), newHarData);
					newHarData.setAttributes(harData.getAttributes());
					setCustomFieldsData(newHarData);
					headerId = Utils.replaceSpaceWithDoubleUnderscore(Utils.convertToLowerCase(harData.getStepTitle()));
					if(!headerMap.containsKey(headerId)){
						headerMap.put(headerId, harData.getStepTitle());
					}
					newHarData.setAttribute(headerId, harData.getCount());
					modifiedList.add(newHarData);
				}else{
					newHarData =posSdoMap.get(harData.getPositionId());
					headerId = Utils.replaceSpaceWithDoubleUnderscore(Utils.convertToLowerCase(harData.getStepTitle()));
					if(!headerMap.containsKey(headerId)){
						headerMap.put(headerId, harData.getStepTitle());
					}
					newHarData.setAttribute(headerId, harData.getCount());
				}
			}	
		}
		obj[0] = modifiedList;
		obj[1] = headerMap;
		return obj;
	}
	
	private void setCustomFieldsData(HiringActivityReportData hard){
		if(CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)){
			CustomFieldUtils.generateCustomFieldMap(hard.getAttributes(), hard.getPosCustomFieldValues(), "\\|\\|");
		}
	}
	
	public String getXMLForHiringActivitySummaryFields(List<SimpleDataObject> sdoList,Map<String,String> columMap){
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if(columMap!=null){
				Iterator<String> itr = columMap.keySet().iterator();
				while (itr.hasNext()) {
					String key = itr.next();
					String value = columMap.get(key);
					AttributesImpl atr = new AttributesImpl();
					
					atr.addAttribute("", "id", "", "", key);
					wr.startElement("", "row", "", atr);

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "key");
					wr.startElement("", "userdata", "", atr);
					wr.characters(key);
					wr.endElement("userdata");
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "type");
					wr.startElement("", "userdata", "", atr);
					wr.characters("FIELD");
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(value);
					wr.endElement("cell");

					wr.endElement("row");
				}
			}
			
			if (sdoList != null) {
				for (int i = 0; i < sdoList.size(); i++) {
					SimpleDataObject sdo = (SimpleDataObject) sdoList.get(i);
					String positionStepTitle = sdo.getString("positionStepTitle");

					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", Utils.convertToLowerCase(positionStepTitle));
					wr.startElement("", "row", "", atr);

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "type");
					wr.startElement("", "userdata", "", atr);
					wr.characters("STEP");
					wr.endElement("userdata");
					
					
					wr.startElement("cell");
					wr.characters(positionStepTitle);
					wr.endElement("cell");

					wr.endElement("row");
				}
			}
			
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	public List<HiringActivityReportData> getHiringActivityDetails(FilterData filterData, String userId, PermissionSet permissionSet) {
		List<HiringActivityReportData> activityList = null; 
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[2];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParams[0] = " tp.position_code ";
			} else {
				dynParams[0] = " tp.position_title ";
			}
			ArrayList<String> dynamicContent = new ArrayList<String>();			
			
			dynParams[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
			
			if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[1] += " AND tp.position_status = ? ";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
				dynParams[1] += " AND tp.position_id = ? ";
				dynamicContent.add(filterData.getPositionId());
			} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)) {
				dynParams[1] += " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());
				if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
					dynParams[1] += " AND tp.sub_dept_id = ? ";
					dynamicContent.add(filterData.getSubDepartmentId());
					if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
						dynParams[1] += " AND tp.sub_sub_dept_id = ? ";
						dynamicContent.add(filterData.getSubSubDepartmentId());
					}
				}
			}
			
			if (!Utils.isBlankOrNull(filterData.getUsers())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getUsers(), dynamicContent);
				dynParams[1] += " AND tasp.user_id in (" + qMarks + ") ";
			}
			
			if (!Utils.isBlankOrNull(filterData.getPositionOwnerId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getPositionOwnerId(), dynamicContent);
				dynParams[1] += " AND tp.position_owner_id in (" + qMarks + ") ";
			}
			
			
			if (!Utils.isBlankOrNull(filterData.getStages())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getStages(), dynamicContent);
				dynParams[1] += "AND tps.position_step_level IN (" + qMarks + ") ";
			}
			
			java.sql.Date fromDt = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
			Date toDt = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
			toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
			
			dq = new DBPreparedQuery("dReportManager_GetHiringActivityDetails", dynParams);
			int cnt = 1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_JOINED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REPEAT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_RESCHEDULE);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_BACK_OUT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_REJECT);
						
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REPEAT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);						
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);

			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setDate(cnt++, fromDt);
			dq.setDate(cnt++, Utils.convertDateToSQLDate(toDt));
			activityList =(List<HiringActivityReportData>) dq.getResult();
			
		} catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return activityList;
	}
	
	/**
	 * @param filterData
	 * @return
	 */
	public String getFilterCriteria(FilterData filterData) {
		String other_criteria = "";
		try {
			
			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat), Utils.convertToSQLDate(filterData.getToDate(),
					Utils.regEUDateFormat));
			if(!Utils.isBlankOrNull(date_criteria)){
				other_criteria += date_criteria;
			}
			
			
			String users = getUsersForIds(filterData.getUsers());
			if (!Utils.isBlankOrNull(users)) {
				if (other_criteria.length() > 0) {
					other_criteria += "\\n";
				}
				other_criteria += "Recruiters : " + users;
			}
			
			if(!Utils.isBlankOrNull(filterData.getPositionOwnerId())){
				String positionOwners = getUsersForIds(filterData.getPositionOwnerId());
				if (other_criteria.length() > 0) {
					other_criteria += "\\n";
				}
				other_criteria += TPLabels.getLabel("global.position_owner") + " : "+ positionOwners;
			}
			
			String searchInText = filterData.getSearchInText();
			if (!Utils.isBlankOrNull(searchInText)) {
				if (other_criteria.length() > 0) {
					other_criteria += "\\n";
				}
				other_criteria += "Report for : " + searchInText;
			}
			
			if(!Utils.isBlankOrNull(filterData.getStepIds())){
				if (other_criteria.length() > 0) {
					other_criteria += "\\n";
				}
				other_criteria += TPLabels.getLabel("common.step") + " : "+ StepStaticUtils.getStepNames(filterData.getStepIds());
			}
			
			if(!Utils.isBlankOrNull(filterData.getStages())){
				if (other_criteria.length() > 0) {
					other_criteria += "\\n";
				}
				other_criteria += TPLabels.getLabel("common.stage") + " : "+ StepLevelStaticUtils.getStepLevelNames(filterData.getStages());
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}		
		return other_criteria;
	}

	public int showMigrationMessageOnReports() {
		DBPreparedQuery dq = null;
		int showMigrationMessage = 0;
		try {
			dq = new DBPreparedQuery("dReportManager_showMigrationMessageOnReports");
			showMigrationMessage = dq.getIntResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return showMigrationMessage;
	}
	
	public void saveMigrationMessageFlag(String migrationMessageFlag, String userId) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dReportManager_saveMigrationMessageFlag");
			dq.setString(1, migrationMessageFlag);
			dq.setString(2, userId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	/**
	 * 
	 * @param filterData
	 * @param userId
	 * @param permissionSet
	 * @return
	 */
	public List<DatewiseHiringReportData> getDatewiseHiringReport(FilterData filterData,String userId, PermissionSet permissionSet){
		List<DatewiseHiringReportData> datewiseHiringReportList = null; 
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParams[0]="";
			java.sql.Date fromDt = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
			Date toDt = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
			toDt = Utils.adjustDateBy(toDt, Calendar.DATE, 1);
			
			if (!Utils.isBlankOrNull(filterData.getStages())) {
				String[] arrIds = filterData.getStages().split(",");
				String stages ="";				
				for(String id:arrIds){
					if(id.equals(RequisitionConstants.STEP_REQUISITION_APPROVAL)){
						stages+="Requisition Approval,";	
					}
					if(id.equals(PositionConstants.STEP_LEVEL_SHORTLIST)){
						stages+="Shortlist,";	
					}
					if(id.equals(PositionConstants.STEP_LEVEL_SELECT)){
						stages+="Select,";
					}
					if(id.equals(PositionConstants.STEP_LEVEL_ACCEPT)){
						stages+="Hire";
					}					
				}
				
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(stages, dynamicContent);
				dynParams[0] += "AND processdata.process_stage IN (" + qMarks + ") ";
			}
			
			if(!Utils.isBlankOrNull(filterData.getDepartmentId())){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getDepartmentId(), dynamicContent);
				dynParams[0] += "AND processdata.dept_id IN (" + qMarks + ") ";
			}
			if(!Utils.isBlankOrNull(filterData.getPositionId())){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getPositionId(), dynamicContent);
				dynParams[0] += "AND processdata.position_id IN (" + qMarks + ") ";				
			}
			if (!Utils.isBlankOrNull(filterData.getPositionOwnerId())) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getPositionOwnerId(), dynamicContent);
				dynParams[0] += " AND processdata.position_owner_id IN (" + qMarks + ") ";
			}
			if (!Utils.isBlankOrNull(filterData.getPositionFilter()) & filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				//String qMarks = Utils.setDynamicParamsAndReturnQmarks(PositionConstants.POSITION_STATUS_OPENED, dynamicContent);
				dynParams[0] += " AND processdata.position_status IN (" + PositionConstants.POSITION_STATUS_OPENED + ") ";
			}			
			
			
			int cnt = 1;
			dq = new DBPreparedQuery("dReportManager_GetRequisitionApprovalHiringStepDatewiseHiring", dynParams);
			dq.setString(cnt++, PositionConstants.POSITIONS_TYPE_EXTERNAL);
			dq.setString(cnt++, PositionConstants.POSITIONS_TYPE_INTERNAL);
			dq.setString(cnt++, TPLabels.getLabel("position.description.position_type_external"));
			dq.setString(cnt++, TPLabels.getLabel("position.description.position_type_internal"));
			
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_CLOSED);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_INPROCESS);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_HOLD);
			
			dq.setString(cnt++, PositionConstants.POSITIONS_TYPE_OF_VACANCY_FRESH);
			dq.setString(cnt++, PositionConstants.POSITIONS_TYPE_OF_VACANCY_REPLACEMENT);
			dq.setString(cnt++, TPLabels.getLabel("position.description.fresh"));
			dq.setString(cnt++, TPLabels.getLabel("position.description.replacement"));
			
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_CLOSED);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_INPROCESS);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_HOLD);
			
			dq.setDate(cnt++, fromDt);
			dq.setDate(cnt++, Utils.convertDateToSQLDate(toDt));
			
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_CLOSED);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_INPROCESS);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_HOLD);
			
			dq.setDate(cnt++, fromDt);
			dq.setDate(cnt++, Utils.convertDateToSQLDate(toDt));
			
			dq.setString(cnt++, PositionConstants.POSITION_SKILL_PRIMARY);
			dq.setString(cnt++, PositionConstants.POSITION_SKILL_SECONDARY);
						
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			datewiseHiringReportList =(List<DatewiseHiringReportData>) dq.getResult();
			
		} catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return datewiseHiringReportList;
	}
	/**
	 * @param filterData
	 * @return joinerReportData
	 */
	public List<JoinerReportData> getJoinerReportData(FilterData filterData) {
		List<JoinerReportData> joinerReportData = null; 
		DBPreparedQuery dq = null;
		try{
			String[] dynParams = new String[1];
			dynParams[0]="";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if(!Utils.isBlankOrNull(filterData.getDepartmentId()) ){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getDepartmentId(), dynamicContent);
				dynParams[0] += " AND td.dept_id IN (" + qMarks + ") ";
			}
			if(!Utils.isBlankOrNull(filterData.getPositionId())){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getPositionId(), dynamicContent);
				dynParams[0] += " AND tp.position_id IN (" + qMarks + ") ";				
			}else if (!Utils.isBlankOrNull(filterData.getPositionFilter()) & filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[0] += " AND tp.position_status IN (" + PositionConstants.POSITION_STATUS_OPENED + ") ";
			}else if (!Utils.isBlankOrNull(filterData.getPositionFilter()) & filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParams[0] += " AND tp.position_status IN ("+PositionConstants.POSITION_STATUS_OPENED+","+PositionConstants.POSITION_STATUS_HOLD+") ";
			}
			if(!filterData.getDateRange().equals(ReportConstants.FILTER_OPTION_SELECT)){
				if(!Utils.isBlankOrNull(filterData.getFromDate())){
					Date fromDate = new Date();
					fromDate = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
					dynParams[0] += " AND ta.applicant_date_joined >= ? ";
					dynamicContent.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
				}
				if (!Utils.isBlankOrNull(filterData.getToDate())) {
					Date toDate = new Date();
					toDate = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
					if (toDate != null) {
						toDate = Utils.adjustDateBy(toDate, Calendar.DATE, 1);
					}
					dynParams[0] += " AND ta.applicant_date_joined < ? ";
					dynamicContent.add(Utils.getDateConvertedToString(toDate, Utils.redYYYYMMDDFormat));
				}
			}
			dq = new DBPreparedQuery("dReportManager_GetJoinerReport", dynParams);
			int count =1 ;
			dq.setString(count++,PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(count++,PositionConstants.STEP_LEVEL_ACCEPT);
			for(String t: dynamicContent){
				dq.setString(count++, t);
			}
			
			joinerReportData = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return joinerReportData;
	}	
	
	/**
	 * @param filterData
	 * @return joinedReportData
	 */
	public List<JoinerReportData> getJoinedReportData(FilterData filterData) {
		List<JoinerReportData> joinerReportData = null; 
		DBPreparedQuery dq = null;
		try{
			String[] dynParams = new String[1];
			dynParams[0]="";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if(!Utils.isBlankOrNull(filterData.getDepartmentId()) ){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getDepartmentId(), dynamicContent);
				dynParams[0] += " AND td.dept_id IN (" + qMarks + ") ";
			}
			if(!Utils.isBlankOrNull(filterData.getPositionId())){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getPositionId(), dynamicContent);
				dynParams[0] += " AND tp.position_id IN (" + qMarks + ") ";				
			}else if (!Utils.isBlankOrNull(filterData.getPositionFilter()) & filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[0] += " AND tp.position_status IN (" + PositionConstants.POSITION_STATUS_OPENED + ") ";
			}else if (!Utils.isBlankOrNull(filterData.getPositionFilter()) & filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParams[0] += " AND tp.position_status IN ("+PositionConstants.POSITION_STATUS_OPENED+","+PositionConstants.POSITION_STATUS_HOLD+") ";
			}
			if(!filterData.getDateRange().equals(ReportConstants.FILTER_OPTION_SELECT)){
				if(!Utils.isBlankOrNull(filterData.getFromDate())){
					Date fromDate = new Date();
					fromDate = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
					dynParams[0] += " AND ta.applicant_date_joined >= ? ";
					dynamicContent.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
				}
				if (!Utils.isBlankOrNull(filterData.getToDate())) {
					Date toDate = new Date();
					toDate = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
					if (toDate != null) {
						toDate = Utils.adjustDateBy(toDate, Calendar.DATE, 1);
					}
					dynParams[0] += " AND ta.applicant_date_joined < ? ";
					dynamicContent.add(Utils.getDateConvertedToString(toDate, Utils.redYYYYMMDDFormat));
				}
			}
			dq = new DBPreparedQuery("dReportManager_GetJoinedReport", dynParams);
			int count =1 ;
			for(String t: dynamicContent){
				dq.setString(count++, t);
			}
			
			joinerReportData = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return joinerReportData;
	}
	
	/**
	 * @param filterData
	 * @return blackListReportData
	 */
	public List<BlackListReportData> getBlackListReportData(FilterData filterData) {
		List<BlackListReportData> blackListReportData = null; 
		DBPreparedQuery dq = null;
		try{
			String[] dynParams = new String[1];
			dynParams[0]="";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			/*if(!Utils.isBlankOrNull(filterData.getDepartmentId()) ){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getDepartmentId(), dynamicContent);
				dynParams[0] += " AND td.dept_id IN (" + qMarks + ") ";
			}
			if(!Utils.isBlankOrNull(filterData.getPositionId())){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getPositionId(), dynamicContent);
				dynParams[0] += " AND tp.position_id IN (" + qMarks + ") ";				
			}else if (!Utils.isBlankOrNull(filterData.getPositionFilter()) & filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[0] += " AND tp.position_status IN (" + PositionConstants.POSITION_STATUS_OPENED + ") ";
			}else if (!Utils.isBlankOrNull(filterData.getPositionFilter()) & filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParams[0] += " AND tp.position_status IN ("+PositionConstants.POSITION_STATUS_OPENED+","+PositionConstants.POSITION_STATUS_HOLD+") ";
			}*/
			if(!filterData.getDateRange().equals(ReportConstants.FILTER_OPTION_SELECT)){
				if(!Utils.isBlankOrNull(filterData.getFromDate())){
					Date fromDate = new Date();
					fromDate = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
					dynParams[0] += " AND tabh.date_created >= ? ";
					dynamicContent.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
				}
				if (!Utils.isBlankOrNull(filterData.getToDate())) {
					Date toDate = new Date();
					toDate = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
					if (toDate != null) {
						toDate = Utils.adjustDateBy(toDate, Calendar.DATE, 1);
					}
					dynParams[0] += " AND tabh.date_created < ? ";
					dynamicContent.add(Utils.getDateConvertedToString(toDate, Utils.redYYYYMMDDFormat));
				}
			}
			dq = new DBPreparedQuery("dReportManager_GetBlackListedReport", dynParams);
			int count =1 ;
			for(String t: dynamicContent){
				dq.setString(count++, t);
			}
			
			blackListReportData = dq.getResult();
			updateResultSetforPositionSteps(blackListReportData);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return blackListReportData;
	}
	
	private void updateResultSetforPositionSteps(
			List<BlackListReportData> result) {
		DBPreparedQuery dq = null;
		try{
			if (result != null && result.size() > 0) {
				Iterator<BlackListReportData> iterator = result.iterator();
				
				while (iterator.hasNext()) {
							BlackListReportData blackListReportData = (BlackListReportData) iterator.next();
							String applicantId=""+blackListReportData.getApplicantId();
							String position="";
							String step="";
								
							dq = new DBPreparedQuery("dReportManager_BlacklistedPositionData");
							dq.setString(1,""+applicantId);
							
							ArrayList<SimpleDataObject> processList = dq.getResult();
							if(processList!=null && processList.size()>0){
								SimpleDataObject sdo=processList.get(0);
								position=sdo.getString("position");
								step=sdo.getString("step");
								
							}
									
									
								
								if(!Utils.isBlankOrNull(position))
								{
								blackListReportData.setAttribute("position",position);
								}
								else{
									blackListReportData.setAttribute("position","NA");
								}
								if(!Utils.isBlankOrNull(step))
								{
									blackListReportData.setAttribute("step",step);
								}
								else{
									blackListReportData.setAttribute("step","NA");
								}
								
					
				}
			}
		}
		catch(Exception ex){
			TPLogger.getLogger().debug(" Position status could not be fine correctly");
		}
		finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public ArrayList<AuditTrailView>  getAuditTrailReport(FilterData filterData) {

		ArrayList<AuditTrailView> auditTrailView = null; 
		DBPreparedQuery dq = null;
		try{
			String[] dynParams = new String[1];
			dynParams[0]="";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			/*if(!Utils.isBlankOrNull(filterData.getDepartmentId()) ){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getDepartmentId(), dynamicContent);
				dynParams[0] += " AND td.dept_id IN (" + qMarks + ") ";
			}
			if(!Utils.isBlankOrNull(filterData.getPositionId())){
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(filterData.getPositionId(), dynamicContent);
				dynParams[0] += " AND tp.position_id IN (" + qMarks + ") ";				
			}else if (!Utils.isBlankOrNull(filterData.getPositionFilter()) & filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
				dynParams[0] += " AND tp.position_status IN (" + PositionConstants.POSITION_STATUS_OPENED + ") ";
			}else if (!Utils.isBlankOrNull(filterData.getPositionFilter()) & filterData.getPositionFilter().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
				dynParams[0] += " AND tp.position_status IN ("+PositionConstants.POSITION_STATUS_OPENED+","+PositionConstants.POSITION_STATUS_HOLD+") ";
			}*/
			if(!Utils.isBlankOrNull(filterData.getEntityType())){
				if(filterData.getEntityType().equals(AuditConstants.TYPE_ALL)){
					dynParams[0] += "  tpe.entity_field IN "+"(" +"select distinct (entity_field) from tp_audit_entries"+")"+" ";
				}
				else{
					dynParams[0] += "  tpe.entity_field = ?";
					dynamicContent.add(filterData.getEntityType());
				}
			}
			if(!filterData.getDateRange().equals(ReportConstants.FILTER_OPTION_SELECT)){
				if(!Utils.isBlankOrNull(filterData.getFromDate())){
					Date fromDate = new Date();
					fromDate = Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat);
					dynParams[0] += " AND tpe.date_created >= ? ";
					dynamicContent.add(Utils.getDateConvertedToString(fromDate, Utils.redYYYYMMDDFormat));
				}
				if (!Utils.isBlankOrNull(filterData.getToDate())) {
					Date toDate = new Date();
					toDate = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
					if (toDate != null) {
						toDate = Utils.adjustDateBy(toDate, Calendar.DATE, 1);
					}
					dynParams[0] += " AND tpe.date_created < ? ";
					dynamicContent.add(Utils.getDateConvertedToString(toDate, Utils.redYYYYMMDDFormat));
				}
			}
			
			if(!Utils.isBlankOrNull(filterData.getAuditType())){
				
				
				if(filterData.getAuditType().equals(AuditConstants.AUDIT_ALL)){
					
					dynParams[0] += " AND tpe.audit_type IN "+"(" +"select distinct (audit_type) from tp_audit_entries"+")"+" ";
				}
				else{
					dynParams[0] += " AND tpe.audit_type = ?";
					dynamicContent.add(filterData.getAuditType());
				}
			}
			dq = new DBPreparedQuery("dReportManager_GetAuditTrailViewReport", dynParams);
			int count =1 ;
			for(String t: dynamicContent){
				dq.setString(count++, t);
			}
			
			auditTrailView = dq.getResult();
			
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return auditTrailView;
	}

	/**
	 * @param filterData
	 * @param userId
	 * @param permissionSet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<CandidateOneStopFileReportData> getOneStopFileReportData(FilterData filterData) {
		ArrayList<CandidateOneStopFileReportData> result = new ArrayList<CandidateOneStopFileReportData>();
		DBPreparedQuery dq = null;
		try {
			java.util.Date tDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			java.sql.Date toDt = Utils.convertDateToSQLDate(tDt);

			java.util.Date fDt = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			java.sql.Date fromDt = Utils.convertDateToSQLDate(fDt);
			
			dq = new DBPreparedQuery("dReportManager_OneStopFileReportCandidates");
			dq.setDate(1, fromDt);
			dq.setDate(2, toDt);
			result = dq.getResult();
			if(!CollectionUtils.isEmpty(result)&& result.size()>0){
			    updateResultSetWithStepData(result);
			    updateResultSetWithPositionData(result);
			    updateResultSetWithCustomFieldData(result);
			    updateResultSetWithOfferData(result);
			    updateResultSetWithOfferApprovalData(result);
			    
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for one stop file report.", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	
	private void updateResultSetWithOfferApprovalData(ArrayList<CandidateOneStopFileReportData> candidateOneStopFileReportDataList) {
		ArrayList<CandidateOneStopFileReportData> result = new ArrayList<CandidateOneStopFileReportData>();
		DBPreparedQuery dq = null;
		try {
		
		for(CandidateOneStopFileReportData candidateOneStopFileReportData : candidateOneStopFileReportDataList){
			dq = new DBPreparedQuery("dReportManager_OneStopFileReportOfferApprovalData");
			dq.setString(1,candidateOneStopFileReportData.getPositionId());
			dq.setString(2,TPLabels.getLabel("one_stop_file_report.field.stepId"));
			result = dq.getResult();
			if(!CollectionUtils.isEmpty(result)&& result.size()>0){
			  candidateOneStopFileReportData.setOfferedApprovalDate(result.get(0).getOfferedApprovalDate());
			}
		 }
		}
		catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for one stop file report.", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		
	}
	private void updateResultSetWithOfferData(ArrayList<CandidateOneStopFileReportData> candidateOneStopFileReportDataList) {
		ArrayList<CandidateOneStopFileReportData> result = new ArrayList<CandidateOneStopFileReportData>();
		DBPreparedQuery dq = null;
		try {
		
		for(CandidateOneStopFileReportData candidateOneStopFileReportData : candidateOneStopFileReportDataList){
			dq = new DBPreparedQuery("dReportManager_OneStopFileReportOfferGenerationData");
			dq.setString(1,candidateOneStopFileReportData.getApplicantId());
			result = dq.getResult();
			if(!CollectionUtils.isEmpty(result)&& result.size()>0){
			  candidateOneStopFileReportData.setOfferedDate(result.get(0).getOfferedDate());
			}
		 }
		}
		catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for one stop file report.", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	private void updateResultSetWithCustomFieldData(ArrayList<CandidateOneStopFileReportData> candidateOneStopFileReportDataList) {
		CustomFieldData data = null;
		if (candidateOneStopFileReportDataList != null && candidateOneStopFileReportDataList.size() > 0) {
		    Iterator<CandidateOneStopFileReportData> iterator = candidateOneStopFileReportDataList.iterator();
			while (iterator.hasNext()) {
				CandidateOneStopFileReportData candidateOneStopFileReportData = (CandidateOneStopFileReportData) iterator.next();
				String applicantId=""+candidateOneStopFileReportData.getApplicantId();
		        CustomFieldManager customFieldManager = new CustomFieldManager();
		        ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(applicantId, CustomFieldConstants.ENTITY_TYPE_APPLICANT);
		        if(customFields != null && customFields.size() > 0) {
			    for(int i = 0; i < customFields.size(); i++) {
			    	data = customFields.get(i);
			    	if(data.getFieldName().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.custom_field.Tier"))){
			    		candidateOneStopFileReportData.setTier(data.getDisplayValue());
			    	}
			    	else if(data.getFieldName().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.custom_field.LocalOutstation"))){
			    		candidateOneStopFileReportData.setLocalOutstation(data.getDisplayValue());
			    	}
			    	else if(data.getFieldName().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.custom_field.LateralCampusIntern"))){
			    		candidateOneStopFileReportData.setLateralCampusIntern(data.getDisplayValue());
			    	}
			    	else if(data.getFieldName().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.custom_field.Gender"))){
			    		candidateOneStopFileReportData.setGender(data.getDisplayValue());
			    	}  	
			    }
		     }
		   }
		}
	}
	
	private void updateResultSetWithPositionData(ArrayList<CandidateOneStopFileReportData> candidateOneStopFileReportDataList) {
		ArrayList<CandidateOneStopFileReportData> result = new ArrayList<CandidateOneStopFileReportData>();
		DBPreparedQuery dq = null;
		try {
		
		for(CandidateOneStopFileReportData candidateOneStopFileReportData : candidateOneStopFileReportDataList){
			dq = new DBPreparedQuery("dReportManager_OneStopFileReportPositionData");
			dq.setString(1,candidateOneStopFileReportData.getApplicantId());
			result = dq.getResult();
			if(!CollectionUtils.isEmpty(result)&& result.size()>0){
			   mapPositionData(result,candidateOneStopFileReportData);
		    }
		}
		}
		catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for one stop file report.", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		
	}
	
	private void mapPositionData(ArrayList<CandidateOneStopFileReportData> result,
			CandidateOneStopFileReportData candidateOneStopFileReportData) {
		
		candidateOneStopFileReportData.setRequisitionRaiseDate(result.get(0).getRequisitionRaiseDate());
		candidateOneStopFileReportData.setRequisitionAllocationDate(result.get(0).getRequisitionAllocationDate());
		if(!Utils.isBlankOrNullOrDefault(result.get(0).getRequisitionStatus())){
			candidateOneStopFileReportData.setRequisitionStatus(PositionUtils.getPositionStatusValues(result.get(0).getRequisitionStatus()));
		}
		candidateOneStopFileReportData.setSubBU(result.get(0).getSubBU());
		candidateOneStopFileReportData.setPositionId(result.get(0).getPositionId());
	}
	
	private void updateResultSetWithStepData(ArrayList<CandidateOneStopFileReportData> candidateOneStopFileReportDataList) {
		ArrayList<CandidateOneStopFileReportData> result = new ArrayList<CandidateOneStopFileReportData>();
		DBPreparedQuery dq = null;
		try {
		
		for(CandidateOneStopFileReportData candidateOneStopFileReportData : candidateOneStopFileReportDataList){
			String[] dynParam = new String[1];
			dynParam[0] = TPLabels.getLabel("one_stop_file_report.field.formIds");
			dq = new DBPreparedQuery("dReportManager_OneStopFileReportStepData",dynParam);
			dq.setString(1,candidateOneStopFileReportData.getApplicantId());
			result = dq.getResult();
			if(!CollectionUtils.isEmpty(result)&& result.size()>0){
			  mapFeedBackFormData(result,candidateOneStopFileReportData);
			}
	    	}
		}
		catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data for one stop file report.", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	private void mapFeedBackFormData(ArrayList<CandidateOneStopFileReportData> result,
			CandidateOneStopFileReportData candidateOneStopFileReportData) {
		
		for(CandidateOneStopFileReportData candidateOneStopFileReportDataTemp :result){
	     	if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Requisition_Number"))){
				candidateOneStopFileReportData.setRequisitionNumber(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Requisition_Raise_Date"))){
				candidateOneStopFileReportData.setRequisitionRaiseDate(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Requisition_Allocation_Date"))){
				candidateOneStopFileReportData.setRequisitionAllocationDate(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Hire_Type"))){
				candidateOneStopFileReportData.setHireType(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Source"))){
				candidateOneStopFileReportData.setSource(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Source_Name"))){
				candidateOneStopFileReportData.setSourceName(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Candidate_Name"))){
				candidateOneStopFileReportData.setCandidateName(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Qualification"))){
				candidateOneStopFileReportData.setQualification(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Primary_Skills"))){
				candidateOneStopFileReportData.setPrimarySkills(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Notice_Period"))){
				candidateOneStopFileReportData.setNoticePeriod(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Interview_Score"))){
				candidateOneStopFileReportData.setInterviewScore(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Communication_Score"))){
				candidateOneStopFileReportData.setCommunicationScore(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Location"))){
				candidateOneStopFileReportData.setLocation(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Last_Company"))){
				candidateOneStopFileReportData.setLastCompany(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Contact_Details"))){
				candidateOneStopFileReportData.setContactDetails(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Offered_Designation"))){
				candidateOneStopFileReportData.setOfferedDesignation(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Current_CTC"))){
				candidateOneStopFileReportData.setCurrentCTC(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Offered_CTC"))){
				candidateOneStopFileReportData.setOfferedCTC(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Hike_Percent"))){
				candidateOneStopFileReportData.setHikePercent(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Notice_Period_Buy_Out_Days"))){
				candidateOneStopFileReportData.setNoticePeriodBuyOutDays(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Joining_Bonus"))){
				candidateOneStopFileReportData.setJoiningBonus(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Contractor_Cost"))){
				candidateOneStopFileReportData.setContractorCost(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Recruiter_Name"))){
				candidateOneStopFileReportData.setRecruiterName(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Band"))){
				candidateOneStopFileReportData.setBand(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Offered_Approval_Date"))){
				candidateOneStopFileReportData.setOfferedApprovalDate(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Offered_Date"))){
				candidateOneStopFileReportData.setOfferedDate(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Expected_DOJ"))){
				candidateOneStopFileReportData.setExpectedDOJ(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Emp_ID"))){
				candidateOneStopFileReportData.setEmpID(candidateOneStopFileReportDataTemp.getTraitComment());
			}
			else if(candidateOneStopFileReportDataTemp.getFeedbackFieldTitle().trim().equalsIgnoreCase(TPLabels.getLabel("one_stop_file_report.field.Experience"))){
				candidateOneStopFileReportData.setExperience(candidateOneStopFileReportDataTemp.getTraitComment());
			}
		}
	}
	
	 public void convertMapToList(Map<String, List<OfferedCTCView>> offeredCTCViewMap, List<OfferedCTCReportData> offeredCTCReportDataList) {
			
			Set<String> keySet = offeredCTCViewMap.keySet();
			for(String offerCode : keySet) { 	
				OfferedCTCReportData offeredCTCReportData= new OfferedCTCReportData();
				offeredCTCReportData.setOfferCode(offerCode);
				for(OfferedCTCView offeredCTCView : offeredCTCViewMap.get(offerCode)){
					if(StringUtils.isNotEmpty(offeredCTCView.getAttributeName())){
					offeredCTCReportData.setDateCreated(offeredCTCView.getDateCreated());
					offeredCTCReportData.setApplicantId(offeredCTCView.getApplicantId());
					offeredCTCReportData.setPositionId(offeredCTCView.getPositionId());
					offeredCTCReportData.setTemplateId(offeredCTCView.getTemplateId());
					
					if(offeredCTCView.getAttributeName().equalsIgnoreCase(TPLabels.getLabel("report.column.requisitionerNumber"))){
						offeredCTCReportData.setRequisitionerNumber(offeredCTCView.getAttributeValue());	
					}else if(offeredCTCView.getAttributeName().equalsIgnoreCase(TPLabels.getLabel("report.column.name"))){
						offeredCTCReportData.setName(offeredCTCView.getAttributeValue());	
					}
					else if(offeredCTCView.getAttributeName().equalsIgnoreCase(TPLabels.getLabel("report.column.doj"))){
						offeredCTCReportData.setDoj(offeredCTCView.getAttributeValue());	
					}
					else if(offeredCTCView.getAttributeName().equalsIgnoreCase(TPLabels.getLabel("report.column.designation"))){
						offeredCTCReportData.setDesignation(offeredCTCView.getAttributeValue());	
					}
					else if(offeredCTCView.getAttributeName().equalsIgnoreCase(TPLabels.getLabel("report.column.band"))){
						offeredCTCReportData.setBand(offeredCTCView.getAttributeValue());	
					}
					else if(offeredCTCView.getAttributeName().equalsIgnoreCase(TPLabels.getLabel("report.column.level"))){
						offeredCTCReportData.setLevel(offeredCTCView.getAttributeValue());	
					}
					else if(offeredCTCView.getAttributeName().equalsIgnoreCase(TPLabels.getLabel("report.column.bandLevel"))){
						offeredCTCReportData.setBandLevel(offeredCTCView.getAttributeValue());	
					}
					else if(offeredCTCView.getAttributeName().equalsIgnoreCase(TPLabels.getLabel("report.column.addressLine1"))){
						offeredCTCReportData.setAddressLine1(offeredCTCView.getAttributeValue());	
					}
					else if(offeredCTCView.getAttributeName().equalsIgnoreCase(TPLabels.getLabel("report.column.addressLine2"))){
						offeredCTCReportData.setAddressLine2(offeredCTCView.getAttributeValue());	
					}
					else if(offeredCTCView.getAttributeName().equalsIgnoreCase(TPLabels.getLabel("report.column.addressLine3"))){
						offeredCTCReportData.setAddressLine3(offeredCTCView.getAttributeValue());	
					}
					else if(offeredCTCView.getAttributeName().equalsIgnoreCase(TPLabels.getLabel("report.column.offeredCtc"))){
						offeredCTCReportData.setTargetCTC(offeredCTCView.getAttributeValue());	
					}
					else if(offeredCTCView.getAttributeName().equalsIgnoreCase(TPLabels.getLabel("report.column.joiningBonus"))){
						offeredCTCReportData.setJoiningBonus(offeredCTCView.getAttributeValue());	
					}
				 }
				}
				offeredCTCReportDataList.add(offeredCTCReportData);
			}			
		}
}