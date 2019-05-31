/**
 *
 */
package com.talentPool.reports.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.DQMetaData;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dao.impl.CustomReportDAO;
import com.talentPool.customReports.dataobject.CustomReportDetails;
import com.talentPool.dynamicReports.data.BlackListReportData;
import com.talentPool.dynamicReports.data.DatewiseHiringReportData;
import com.talentPool.dynamicReports.data.HiringActivityReportData;
import com.talentPool.dynamicReports.data.JoinerReportData;
import com.talentPool.dynamicReports.data.OfferedCTCReportData;
import com.talentPool.dynamicReports.data.PendingOffersReportData;
import com.talentPool.dynamicReports.data.TimeToHireReportData;
import com.talentPool.dynamicReports.generator.AllCandidatesReport;
import com.talentPool.dynamicReports.generator.AuditTrailLogReport;
import com.talentPool.dynamicReports.generator.BlackListCandidateReport;
import com.talentPool.dynamicReports.generator.CandidateOneStopFileReport;
import com.talentPool.dynamicReports.generator.ClosedPositionTATReport;
import com.talentPool.dynamicReports.generator.DatewiseHiringReport;
import com.talentPool.dynamicReports.generator.HiringActivityDetailsReport;
import com.talentPool.dynamicReports.generator.HiringActivityReport;
import com.talentPool.dynamicReports.generator.HiringFunnelDetailReport;
import com.talentPool.dynamicReports.generator.JoinerReport;
import com.talentPool.dynamicReports.generator.PendingOffersReport;
import com.talentPool.dynamicReports.generator.PreFormattedDynamicReportGenerator;
import com.talentPool.dynamicReports.generator.RejectedCandidatesReport;
import com.talentPool.dynamicReports.generator.TimeToHireReport;
import com.talentPool.dynamicReports.utils.DynamicReportsColumnUtils;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.dataobject.DepartmentData;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.masters.manager.ReportTemplateManager;
import com.talentPool.masters.manager.StepManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.positions.utils.PositionUtils;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.dataobject.ReportData;
import com.talentPool.reportDesign.manager.ReportDesignManager;
import com.talentPool.reportDesign.report.FilterCriteria;
import com.talentPool.reportDesign.report.ReportTypes;
import com.talentPool.reportDesign.report.factory.ReportTypeFactory;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.ReportUtils;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.reports.dataobject.CandidateOneStopFileReportData;
import com.talentPool.reports.dataobject.CandidateTATData;
import com.talentPool.reports.dataobject.CustomizedReportData;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.dataobject.OauthResponseObject;
import com.talentPool.reports.dataobject.Person;
import com.talentPool.reports.form.ReportForm;
import com.talentPool.reports.manager.CustomizedReportManager;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.reports.utils.FilterConverter;
import com.talentPool.reports.views.AuditTrailView;
import com.talentPool.reports.views.HiringFunnelDetailView;
import com.talentPool.reports.views.OfferedCTCView;
import com.talentPool.reports.views.callListReportView;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;
import com.talentPool.user.manager.UserManager;
import com.talentPool.user.utils.UserUtils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

/**
 * @author shivprasad
 * 
 */
public class ReportAction extends TPDispatchAction {

	DQMetaData dqMetaData = null;
	String newApplicationURL= TPApplicationProperties.getProperty("common.newtalentpool.application_url");
	String newApplicationAuthAPI= TPApplicationProperties.getProperty("report.newtalentpool.get_oauthtoken_api");
	String newApplicationDownloadAPI = TPApplicationProperties.getProperty("report.newtalentpool.post_XLSreport_api");

	public ActionForward testReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		try {
			Person p1 = new Person(new Long(1), "po", "Lightbuddie");
			Person p2 = new Person(new Long(2), "Jason", "Carrora");
			Person p3 = new Person(new Long(3), "Alexandru", "Papesco");
			Person p4 = new Person(new Long(4), "Jay", "Boss");

			// SimpleDataObject p1 = new SimpleDataObject();
			// p1.setAttribute("name", "aa");
			// p1.setAttribute("lastname", "bb");
			/*
			 * store everything in a list - normally, this should be coming from
			 * a database but for the sake of simplicity, I left that out
			 */
			ArrayList myList = new ArrayList();
			myList.add(p1);
			myList.add(p2);
			myList.add(p3);
			myList.add(p4);

			// get jrxml path
			String filePath = Utils.concatFilePath(ReportConstants.JRXML_FOLDER_PATH, "test_report.jrxml");
			String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"),
					TPApplicationProperties.getProperty("ui.dir"));
			String destinationPath = Utils.concatFilePath(basePath, ReportConstants.REPORT_DESTINATION_FOLDER);
			destinationPath = Utils.concatFilePath(destinationPath, "/x.html");

			JasperReport jasperReport = JasperCompileManager.compileReport(filePath);
			JRDataSource jsor = new JRBeanCollectionDataSource(myList);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, jsor);
			JRHtmlExporter exporter = new JRHtmlExporter();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationPath);
			exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);
			exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, "px");

			exporter.exportReport();

		} catch (Exception e) {
			TPLogger.getLogger().error("Caught Exception", e);
		}

		ActionForward actionForward = new ActionForward();
		actionForward.setContextRelative(false);
		actionForward.setRedirect(true);
		// actionForward.setPath(".." + File.separator +
		// ReportsConstants.JR_GEN_FOLDER + File.separator + jrReportName + "_"
		// + session.getId() + dateJasper + "." + exportTo);
		actionForward.setPath("reportsout/x.html");
		return actionForward;

	}

	public ActionForward reportFilter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "reportHome";
		ReportForm reportForm = (ReportForm) actionForm;
		String reportName = reportForm.getReportName();
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_REPORTS;
		// if(!isUserAuthorized(request, CommonConstants.NO_MODULE,
		// permissions,null,null,reportName)) {
		if (!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions, null, null, null)) {
			forward = "authorizationFailure";
			return mapping.findForward(forward);
		}

		String userId = (String) request.getSession(false).getAttribute("userId");
		String userRole = (String) request.getSession(false).getAttribute("userRoles");
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
		ReportManager reportManager = new ReportManager();
		PositionManager positionManager = new PositionManager();
		ReportDesignManager designManager = new ReportDesignManager();
		ReportUtils reportUtils = new ReportUtils();
		StepManager stepManager = new StepManager();
		ArrayList<ReportData> customReports = null;
		if (userRole.equals(UserConstants.ROLE_ADMIN + "")) {
			customReports = designManager.getAllReports();
		} else {
			customReports = designManager.getCustomReportsForUser(userId);
		}
		CustomReportDAO customReportDAO = new CustomReportDAO();
		List<CustomReportDetails> newCustomReports = customReportDAO.fetchCustomReportListForUser(userId);
		try {
			request.setAttribute("t", NavigationConstants.T_REPORT);
			Date lastRunTime = customReportDAO.getLastRunDate();
			if (lastRunTime == null) {
				request.setAttribute("schedulerLastRunTime",
						TPLabels.getLabel("report.label.master_data_not_available"));
			} else {
				request.setAttribute("schedulerLastRunTime", DateUtils.getSystemDateTimeFormat(lastRunTime));
			}

			int showMigrationMessage = reportManager.showMigrationMessageOnReports();
			request.setAttribute("newCustomReports", newCustomReports);
			request.setAttribute("showMigrationMessage", showMigrationMessage);

			request.setAttribute("customReports", customReports);
			request.setAttribute("customReportsSize", customReports.size());
			request.setAttribute("newCustomReportsSize", newCustomReports.size());

			// Customized reports (Customer specific reports)
			ArrayList<CustomizedReportData> customizedReports = new CustomizedReportManager()
					.getAllCustomizedReportsForUser(userId);
			request.setAttribute("customizedReports", customizedReports);
			request.setAttribute("customizedReportsSize", customizedReports.size());

			ArrayList<PositionData> positions = reportManager.getPositions(userId, permissionSet, null);
			if (reportName.equalsIgnoreCase(ReportVersionConstants.REPORT_HIRING_STATUS)) {
				request.setAttribute("positions", positions);
				request.setAttribute("usersXml", reportUtils.getUsersFilterXMLForReports());
				forward = "hiringStatusFilter";
			}
			if (reportName.equalsIgnoreCase(ReportVersionConstants.REPORT_CALL_LIST)) {
				forward = "callListReport";
				SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
				List applicantsInProcess = selectionProcessManager.getApplicants(permissionSet, userRole, userId, null,
						null, null, null,
						PositionConstants.STEP_LEVEL_SHORTLIST + "," + PositionConstants.STEP_LEVEL_SELECT, null, null,
						null, null, null, null);
				request.setAttribute("applicants", applicantsInProcess);
			}
			if (reportName.equalsIgnoreCase(ReportVersionConstants.REPORT_IMPORT_REPORT)) {
				forward = "importReportFilter";
				reportForm.setUsersList(String.valueOf(UserConstants.ROLE_RECRUITER) + ", "
						+ String.valueOf(UserConstants.ROLE_HR_MANAGER));
				reportForm.setSourceIds(CommonUtils.getSourceIds());
				reportForm.setSourceNames(CommonUtils.getSourceNames());
			}
			if (reportName.equalsIgnoreCase(ReportVersionConstants.REPORT_HIRING_EFICIENCY)) {
				forward = "hiringEfficiencyReportFilter";
			}
			if (reportName.equalsIgnoreCase(ReportVersionConstants.REPORT_SOURCE_WISE_HIRING)) {
				forward = "sourceWiseHiringChart";
			}
			if (reportName.equalsIgnoreCase(ReportVersionConstants.REPORT_MONTHLY_JOINING_REPORT)) {
				forward = "monthlyJoiningReport";
			}
			if (reportName.equalsIgnoreCase(ReportVersionConstants.REPORT_HIRING_FUNNEL)) {
				String fieldsXml = ReportUtils.getFieldsXML(permissionSet, ReportVersionConstants.REPORT_HIRING_FUNNEL,
						null);
				request.setAttribute("fieldsXml", fieldsXml);
				request.setAttribute("positions", positions);
				request.setAttribute("usersXml", reportUtils.getUsersFilterXMLForReports());
				forward = "hiringFunnelFilter";
			}

			if (reportName.equalsIgnoreCase(ReportVersionConstants.REPORT_INTERVIEW_LIST)) {
				List interviewerList = positionManager
						.getUsersForRole(UserConstants.ROLE_ADMIN + ", " + UserConstants.ROLE_CXO + ", "
								+ UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_REQUISITIONER + ", "
								+ UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_INTERVIEWER);
				reportForm.setInterviewerList(interviewerList);
				reportForm.setFromDate(Utils.getDateConvertedToString(new Date(), "dd/MM/yyyy"));
				forward = "interviewList";
			}
			if (reportName.equalsIgnoreCase(ReportVersionConstants.REPORT_INTERVIEW_STATUS)) {
				if (!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions, null, null,
						ReportVersionConstants.REPORT_INTERVIEW_STATUS)) {
					forward = "authorizationFailure";
					return mapping.findForward(forward);
				}
				List interviewerList = positionManager
						.getUsersForRole(UserConstants.ROLE_ADMIN + ", " + UserConstants.ROLE_CXO + ", "
								+ UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_REQUISITIONER + ", "
								+ UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_INTERVIEWER);
				reportForm.setInterviewerList(interviewerList);
				reportForm.setFromDate(Utils.getDateConvertedToString(new Date(), "dd/MM/yyyy"));
				request.setAttribute("reportName", reportName);
				forward = "interviewListStatus";
			}
			if (ReportVersionConstants.REPORT_USER_ACTIVITY.equalsIgnoreCase(reportName)) {
				List interviewerList = positionManager
						.getUsersForRole(UserConstants.ROLE_ADMIN + ", " + UserConstants.ROLE_CXO + ", "
								+ UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_REQUISITIONER + ", "
								+ UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_INTERVIEWER);
				reportForm.setInterviewerList(interviewerList);
				reportForm.setFromDate(Utils.getDateConvertedToString(
						new Date(new Date().getTime() - Utils.MILLIS_IN_A_DAY), "dd/MM/yyyy"));
				forward = "userActivity";
			}
			if (ReportVersionConstants.REPORT_POSITION_ACTIVITY.equalsIgnoreCase(reportName)) {
				List interviewerList = positionManager
						.getUsersForRole(UserConstants.ROLE_ADMIN + ", " + UserConstants.ROLE_CXO + ", "
								+ UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_REQUISITIONER + ", "
								+ UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_INTERVIEWER);
				reportForm.setInterviewerList(interviewerList);
				reportForm.setFromDate(Utils.getDateConvertedToString(
						new Date(new Date().getTime() - Utils.MILLIS_IN_A_DAY), "dd/MM/yyyy"));
				forward = "positionActivity";
			}
			if (ReportVersionConstants.REPORT_SOURCEWISE_IMPORT.equalsIgnoreCase(reportName)) {
				// Get list of sources.
				forward = "sourcewiseImportReport";
			}
			if (ReportVersionConstants.REPORT_APPLICANT_DETAILS.equalsIgnoreCase(reportName)) {
				// Get list of sources.
				forward = "applicantDetailsReport";
			}
			if (ReportVersionConstants.REPORT_OFFER_TO_JOINED.equalsIgnoreCase(reportName)) {
				forward = "offerToJoinedReport";
			}
			if (ReportVersionConstants.REPORT_OFFER_CTC.equalsIgnoreCase(reportName)) {
				request.setAttribute("positions", positions);
				request.setAttribute("usersXml", reportUtils.getUsersFilterXMLForReports());
				String reportTemplateJsArray = getReportTemplateJsArray(ReportVersionConstants.REPORT_OFFER_CTC);
				request.setAttribute("reportTemplateJsArray", reportTemplateJsArray);
				forward = "offerCTCReport";
			}
			if (ReportVersionConstants.REPORT_OFFER_TO_JOINED_DETAILED.equalsIgnoreCase(reportName)) {
				request.setAttribute("usersXml", reportUtils.getUsersFilterXMLForReports());
				forward = "offerToJoinedDetailedReport";
			}
			if (ReportVersionConstants.REPORT_POSITION_SUMMARY.equalsIgnoreCase(reportName)) {
				forward = "positionSummary";
			}
			if (ReportVersionConstants.REPORT_CANDIDATE_COMPARISON.equalsIgnoreCase(reportName)) {
				forward = "candidateComparisonReport";
			}
			if (ReportVersionConstants.REPORT_PENDING_ACTION.equalsIgnoreCase(reportName)) {
				List interviewerList = positionManager
						.getUsersForRole(UserConstants.ROLE_ADMIN + ", " + UserConstants.ROLE_CXO + ", "
								+ UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_REQUISITIONER + ", "
								+ UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_INTERVIEWER);
				reportForm.setInterviewerList(interviewerList);
				forward = "pendingActions";
			}
			if (ReportVersionConstants.REPORT_RECRUITMENT_COST.equalsIgnoreCase(reportName)) {
				request.setAttribute("positions", positions);
				forward = "recruitmentCostFilter";
			}
			/* Start : Candidate Report Status */
			if (reportName.equalsIgnoreCase(ReportVersionConstants.REPORT_CANDIDATE_STATUS)) {
				request.setAttribute("positions", positions);
				request.setAttribute("usersXml",
						reportUtils.getUsersFilterXMLForReports(UserConstants.ROLE_ADMIN + ", " + UserConstants.ROLE_CXO
								+ ", " + UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_REQUISITIONER + ", "
								+ UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_INTERVIEWER));
				forward = "candidateStatusReport";
			}
			/* End : Candidate Report Status */
			if (ReportVersionConstants.MASTER_REPORT.equalsIgnoreCase(reportName)) {
				forward = "masterReport";
			}
			if (ReportVersionConstants.MASTER_DATA_REPORT.equalsIgnoreCase(reportName)) {
				forward = "masterDataReport";
			}
			if (ReportVersionConstants.REPORT_CANDIDATE_OFFERS.equalsIgnoreCase(reportName)) {
				forward = "candidateOffersReport";
			}
			if (ReportVersionConstants.REPORT_PENDING_OFFER.equalsIgnoreCase(reportName)) {
				List users = positionManager
						.getUsersForRole(UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_RECRUITER);
				String usersXml = UserUtils.getXMLforActiveUsers(users);
				String fieldsXml = ReportUtils.getFieldsXML(permissionSet, ReportVersionConstants.REPORT_PENDING_OFFER,
						null);
				String reportTemplateJsArray = getReportTemplateJsArray(ReportVersionConstants.REPORT_PENDING_OFFER);
				request.setAttribute("fieldsXml", fieldsXml);
				request.setAttribute("reportTemplateJsArray", reportTemplateJsArray);
				request.setAttribute("usersXml", usersXml);
				forward = "pendingOffersReport";
			}

			if (ReportVersionConstants.REPORT_REJECTED_CANDIDATES.equalsIgnoreCase(reportName)) {
				String fieldsXml = ReportUtils.getFieldsXML(permissionSet,
						ReportVersionConstants.REPORT_REJECTED_CANDIDATES, null);
				List users = positionManager.getUsersForRole(UserConstants.ROLE_ADMIN + ", " + UserConstants.ROLE_CXO
						+ ", " + UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_REQUISITIONER + ", "
						+ UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_INTERVIEWER);
				String usersXml = UserUtils.getXMLforActiveUsers(users);
				List<MasterStepData> stages = stepManager.getAllStages();
				String stageXML = reportUtils.getStageGridXML(stages);
				List<MasterStepData> steps = stepManager.getAllSteps("false");
				String stepsXML = reportUtils.getStepGridXML(steps);
				request.setAttribute("fieldsXml", fieldsXml);
				request.setAttribute("usersXml", usersXml);
				request.setAttribute("stageXML", stageXML);
				request.setAttribute("stepsXML", stepsXML);
				forward = "rejectedCandidatesReport";
			}

			if (ReportVersionConstants.REPORT_ALL_CANDIDATES.equalsIgnoreCase(reportName)) {
				String fieldsXml = ReportUtils.getFieldsXML(permissionSet, ReportVersionConstants.REPORT_ALL_CANDIDATES,
						null);
				List users = positionManager.getUsersForRole(UserConstants.ROLE_ADMIN + ", " + UserConstants.ROLE_CXO
						+ ", " + UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_REQUISITIONER + ", "
						+ UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_INTERVIEWER);
				String usersXml = UserUtils.getXMLforActiveUsers(users);
				List<MasterStepData> stages = stepManager.getAllStages();
				String stageXML = reportUtils.getStageGridXML(stages);
				List<MasterStepData> steps = stepManager.getAllSteps("false");
				String stepsXML = reportUtils.getStepGridXML(steps);
				request.setAttribute("fieldsXml", fieldsXml);
				request.setAttribute("usersXml", usersXml);
				request.setAttribute("stageXML", stageXML);
				request.setAttribute("stepsXML", stepsXML);
				forward = "allCandidatesReport";
			}

			if (ReportVersionConstants.REPORT_TIME_TO_HIRE.equalsIgnoreCase(reportName)) {
				String fieldsXml = ReportUtils.getFieldsXML(permissionSet, ReportVersionConstants.REPORT_TIME_TO_HIRE,
						null);
				String reportTemplateJsArray = getReportTemplateJsArray(ReportVersionConstants.REPORT_TIME_TO_HIRE);
				request.setAttribute("fieldsXml", fieldsXml);
				request.setAttribute("reportTemplateJsArray", reportTemplateJsArray);
				forward = "timeToHire";
			}
			if (ReportVersionConstants.REPORT_HIRING_ACTIVITY.equalsIgnoreCase(reportName)) {
				if (ReportConstants.REPORT_TYPE_DETAILS.equals(reportForm.getReportType())) {
					setHiringActivityDetailsReportData(request, userId, permissionSet);
					forward = "hiringActivityDetails";
				} else {
					setHiringActivityReportData(request, userId, permissionSet);
					forward = "hiringActivity";
				}
			}
			if (ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT.equalsIgnoreCase(reportName)) {
				String fieldsXml = ReportUtils.getFieldsXML(permissionSet,
						ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT, null);
				request.setAttribute("fieldsXml", fieldsXml);
				forward = "datewiseHiringReport";
			}
			if (ReportVersionConstants.REPORT_JOINER.equalsIgnoreCase(reportName)) {
				if (!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions, null, null,
						ReportVersionConstants.REPORT_JOINER)) {
					forward = "authorizationFailure";
					return mapping.findForward(forward);
				}
				forward = "joinerReport";
				String fieldsXml = ReportUtils.getFieldsXML(permissionSet, ReportVersionConstants.REPORT_JOINER, null);
				request.setAttribute("fieldsXml", fieldsXml);
			}
			if (ReportVersionConstants.REPORT_JOINED.equalsIgnoreCase(reportName)) {
				if (!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions, null, null,
						ReportVersionConstants.REPORT_JOINED)) {
					forward = "authorizationFailure";
					return mapping.findForward(forward);
				}
				forward = "joinedReport";
				String fieldsXml = ReportUtils.getFieldsXML(permissionSet, ReportVersionConstants.REPORT_JOINED, null);
				request.setAttribute("fieldsXml", fieldsXml);
			}
			if (ReportVersionConstants.REPORT_CLOSED_POSITION_TAT.equalsIgnoreCase(reportName)) {
				String fieldsXml = ReportUtils.getFieldsXML(permissionSet,
						ReportVersionConstants.REPORT_CLOSED_POSITION_TAT, null);
				request.setAttribute("fieldsXml", fieldsXml);
				forward = "closedPositionTAT";
			}
			// Audit Log Report
			if (ReportVersionConstants.REPORT_AUDIT_LOG.equalsIgnoreCase(reportName)) {
				request.setAttribute("positions", positions);
				request.setAttribute("usersXml",
						reportUtils.getUsersFilterXMLForReports(UserConstants.ROLE_ADMIN + ", " + UserConstants.ROLE_CXO
								+ ", " + UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_REQUISITIONER + ", "
								+ UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_INTERVIEWER));
				forward = "auditLogReport";
			}
			// Audit Log Report
			// BlackListed candidate Report
			if (ReportVersionConstants.REPORT_BLACKLISTED.equalsIgnoreCase(reportName)) {
				if (!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions, null, null,
						ReportVersionConstants.REPORT_BLACKLISTED)) {
					forward = "authorizationFailure";
					return mapping.findForward(forward);
				}
				forward = "blacklistedReport";
				String fieldsXml = ReportUtils.getFieldsXML(permissionSet, ReportVersionConstants.REPORT_JOINED, null);
				request.setAttribute("fieldsXml", fieldsXml);
			}
			if (ReportVersionConstants.ONE_STOP_FILE_REPORT.equalsIgnoreCase(reportName)) {
				if (!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions, null, null,
						ReportVersionConstants.ONE_STOP_FILE_REPORT)) {
					forward = "authorizationFailure";
					return mapping.findForward(forward);
				}
				forward = "oneStopFileReport";
			}
			if (ReportVersionConstants.COST_SHEET_FINANCE_REPORT.equalsIgnoreCase(reportName)) {
				if (!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions, null, null,
						ReportVersionConstants.COST_SHEET_FINANCE_REPORT)) {
					forward = "authorizationFailure";
					return mapping.findForward(forward);
				}
				forward = "financeCostSheetReport";
			}
			if (ReportVersionConstants.JOINER_DATA_FINANCE_REPORT.equalsIgnoreCase(reportName)) {
				if (!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions, null, null,
						ReportVersionConstants.JOINER_DATA_FINANCE_REPORT)) {
					forward = "authorizationFailure";
					return mapping.findForward(forward);
				}
				forward = "joinerDataFinanceReport";
			}
			if (ReportVersionConstants.INDIA_HIRING_REQ_REPORT.equalsIgnoreCase(reportName)) {
				if (!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions, null, null,
						ReportVersionConstants.INDIA_HIRING_REQ_REPORT)) {
					forward = "authorizationFailure";
					return mapping.findForward(forward);
				}
				forward = "indiaHiringReqReport";
			}
			if (ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT.equalsIgnoreCase(reportName)) {
				if (!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions, null, null,
						ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT)) {
					forward = "authorizationFailure";
					return mapping.findForward(forward);
				}
				forward = "indiaHiringSummaryReport";
			}
			// end of blacklisted
			for (CustomizedReportData customizedReportData : customizedReports) {
				if (customizedReportData.getReportName().equals(reportName)) {
					// forward = customizedReportData.getReportJsp();
					return new ActionForward("/reports/customizedReports/" + customizedReportData.getReportJsp());
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	@SuppressWarnings("unchecked")
	private void setHiringActivityDetailsReportData(HttpServletRequest request, String userId,
			PermissionSet permissionSet) {
		PositionManager positionManager = new PositionManager();
		String reportTemplateJsArray = "";
		String usersXml = "";
		String fieldsXml = "";
		try {
			fieldsXml = ReportUtils.getFieldsXML(permissionSet, ReportVersionConstants.REPORT_HIRING_ACTIVITY,
					ReportConstants.REPORT_TYPE_DETAILS);
			List users = positionManager
					.getUsersForRole(UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_RECRUITER);
			usersXml = UserUtils.getXMLforActiveUsers(users);
			reportTemplateJsArray = getReportTemplateJsArray(ReportVersionConstants.REPORT_HIRING_ACTIVITY);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("fieldsXml", fieldsXml);
		request.setAttribute("reportTemplateJsArray", reportTemplateJsArray);
		request.setAttribute("usersXml", usersXml);
	}

	@SuppressWarnings("unchecked")
	private void setHiringActivityReportData(HttpServletRequest request, String userId, PermissionSet permissionSet) {
		PositionManager positionManager = new PositionManager();
		ReportManager reportManager = null;
		String fieldsXml = "";
		String usersXml = "";
		Map<String, String> columMap = null;
		try {
			reportManager = new ReportManager();
			List users = positionManager
					.getUsersForRole(UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_RECRUITER);
			usersXml = UserUtils.getXMLforActiveUsers(users);
			List<SimpleDataObject> positionStepsList = positionManager.getDistinctPositionSteps(null, userId,
					permissionSet);
			columMap = DynamicReportsColumnUtils.getOptionalFields(permissionSet,
					ReportVersionConstants.REPORT_HIRING_ACTIVITY, ReportConstants.REPORT_TYPE_SUMMARY);
			fieldsXml = reportManager.getXMLForHiringActivitySummaryFields(positionStepsList, columMap);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("fieldsXml", fieldsXml);
		request.setAttribute("usersXml", usersXml);
	}

	@SuppressWarnings("unchecked")
	private String getReportTemplateJsArray(String reportId) {
		ReportTemplateManager reportTemplateManager = new ReportTemplateManager();
		ArrayList reportTemplateDataList = (ArrayList) reportTemplateManager.getReportTemplates(reportId);
		String reportTemplateJsArray = CommonUtils.getListJavaScriptArrayWithProperties(reportTemplateDataList,
				"reportTemplateId", "templateName");
		return reportTemplateJsArray;
	}

	public ActionForward getPositions(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				String userId = (String) request.getSession(false).getAttribute("userId");
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				ReportManager reportManager = new ReportManager();
				ArrayList positions = reportManager.getPositions(userId, permissionSet, null);
				xmlFile = reportManager.getXMLForPositions(positions);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting Inbox Settings", e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward hiringStatusSummaryReport(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateHiringStatusSummaryReport(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}
		return mapping.findForward(forward);

	}

	public ActionForward hiringStatusDetailReport(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateHiringStatusDetailReport(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward monthlyJoiningReportSummary(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		if (reportForm.getIsReportFormatCSV().equals(ReportConstants.REPORT_FORMAT_CSV)) {
			return monthlyJoiningReportCSV(mapping, actionForm, request, response);
		}

		if (filterData.getReportType().equals(ReportConstants.REPORT_TYPE_DETAILS)) {
			return monthlyJoiningReportDetails(mapping, actionForm, request, response);
		} else if (filterData.getReportType().equals(ReportConstants.REPORT_TYPE_CHART)) {
			return monthlyJoiningReportChart(mapping, actionForm, request, response);
		}
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateMonthlyJoiningReportSummary(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}

		return mapping.findForward(forward);
	}

	public ActionForward monthlyJoiningReportDetails(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateMonthlyJoiningReportDetails(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward monthlyJoiningReportChart(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {

		String forward = "viewreport";

		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateMonthlyJoiningReportChart(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}

		return mapping.findForward(forward);
	}

	public ActionForward interviewList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateInterviewList(filterData, userId, permissionSet, sessionId,
					clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward interviewListStatus(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateInterviewStatus(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}
		return mapping.findForward(forward);
	}

	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward closedPositionTAT(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		ReportManager reportManager = new ReportManager();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			reportGenerator.setReportsToAndFromDate(filterData);
			ArrayList<CandidateTATData> candidatesList = reportManager.getClosedPositionTATData(filterData, userId,
					permissionSet);
			String outputFileName = ReportVersionConstants
					.getReportTitle(ReportVersionConstants.REPORT_CLOSED_POSITION_TAT) + "-"
					+ String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			ClosedPositionTATReport cpt = new ClosedPositionTATReport(request, outputFileName,
					filterData.getReportFormat(), candidatesList, filterData.getFieldIds(), filterCriteria);
			cpt.generateReport();
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating closed position TAT report", e);
		}

		return mapping.findForward(forward);
	}

	public ActionForward userActivity(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateUserActivity(filterData, userId, permissionSet, sessionId,
					clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the user activity report", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward positionActivity(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generatePositionActivity(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward sourcewiseImport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateSourcewiseImportReport(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}

		return mapping.findForward(forward);
	}

	public ActionForward applicantDetails(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateApplicantDetails(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward hiringEfficiencyReportSummary(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		if (filterData.getReportType().equals(ReportConstants.REPORT_TYPE_DETAILS)) {
			return hiringEfficiencyReportDetails(mapping, actionForm, request, response);
		}
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateHiringEfficiencyReportSummary(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward(forward);
	}

	public ActionForward hiringEfficiencyReportDetails(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateHiringEfficiencyReportDetail(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward(forward);
	}

	public ActionForward callListReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
		ReportForm reportForm = (ReportForm) actionForm;
		String userId = reportForm.getUserId();
		FilterData filterData = reportForm.getFilterData();
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = request.getSession(false).getId() + String.valueOf(System.currentTimeMillis()) + ext;

		try {
			ReportManager reportManager = new ReportManager();
			ArrayList callList = reportManager.getcallListReport(filterData, permissionSet);
			String jrXMLName = "call_list.jrxml";

			HashMap params = new HashMap();

			if (callList.size() == 0) {
				callList.add(new callListReportView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			String filterId = filterData.getFilterId();
			String criteria = TPLabels.getLabel("monthly_hiring_report.lable.criteria_selected_user") + "\""
					+ reportForm.getUserName() + "\"";

			params.put("report_title", "Call List Report");
			params.put("as_of_date_title", TPLabels.getLabel("hiring_status_summary_report.lable.as_of_date"));
			params.put("as_of_date", Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy"));
			params.put("col_applicant_name", TPLabels.getLabel("call_report.lable.applicant_name"));
			params.put("col_dept", TPLabels.getLabel("call_report.lable.dept"));
			params.put("col_position_title", TPLabels.getLabel("common.position_name"));
			params.put("col_applicant_phone", TPLabels.getLabel("call_report.lable.applicant_phone"));
			params.put("col_applicant_mobile", TPLabels.getLabel("call_report.lable.applicant_mobile"));
			params.put("col_applicant_email", TPLabels.getLabel("call_report.lable.applicant_email"));
			params.put("col_applicant_sts_message", TPLabels.getLabel("call_report.lable.applicant_status_message"));
			params.put("col_applicant_step_in", TPLabels.getLabel("call_report.lable.applicant_step_in"));
			params.put("criteria_title", TPLabels.getLabel("hiring_status_summary_report.lable.report_for"));
			params.put("criteria", criteria);

			String clientIpAddr = getClientIpAddr(request);
			reportManager.generateReport(jrXMLName, outputFileName, params, callList, filterData.getReportFormat(),
					userId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);

	}

	public ActionForward importReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "viewreport";

		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateImportReport(filterData, userId, permissionSet, sessionId,
					clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward(forward);
	}

	public ActionForward sourceWiseHringReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "viewreport";

		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateSourceWiseHringReport(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward hiringFunnelSummaryReport(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateHiringFunnelSummaryReport(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward hiringFunnelDetailReport(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportManager reportManager = new ReportManager();
		HiringFunnelDetailReport hiringFunnelDetailReport = null;
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			ArrayList<HiringFunnelDetailView> hiringFunnelDetailListList = reportManager
					.getHiringFunnelDetailReport(filterData, userId, permissionSet);
			String outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_FUNNEL)
					+ "-" + String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			hiringFunnelDetailReport = new HiringFunnelDetailReport(request, outputFileName,
					filterData.getReportFormat(), hiringFunnelDetailListList, filterData.getFieldIds(), filterCriteria);
			hiringFunnelDetailReport.generateReport();
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward offerToJoinedReportSummary(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateOfferToJoinedReportSummary(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward offerToJoinedReportDetailed(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateOfferToJoinedReportDetailed(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward offerToJoinedDetailedReport(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateOfferToJoinedDetailedReport(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward offerCTCReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		PreFormattedDynamicReportGenerator gen = null;
		try {
			ReportManager reportManager = new ReportManager();
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			ReportGenerator reportGenerator = new ReportGenerator();
			reportGenerator.setReportsToAndFromDate(filterData);
			Map<String, List<OfferedCTCView>> offeredCTCView = (Map<String, List<OfferedCTCView>>) reportManager
					.getOfferCTCDetailsReport(filterData);
			String outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_OFFER_CTC) + "-"
					+ String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			List<OfferedCTCReportData> offeredCTCReportData = new ArrayList<OfferedCTCReportData>();
			reportManager.convertMapToList(offeredCTCView, offeredCTCReportData);
			if (ReportConstants.FORMAT_PRE_FORMATTED.equals(filterData.getReportFormat())) {
				gen = new PreFormattedDynamicReportGenerator();
				outputFileName = gen.generateReport(reportForm.getReportTemplateId(), offeredCTCReportData,
						filterCriteria, permissionSet);
			}
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating offered ctc report", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward positionSummaryReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generatePositionSummaryReport(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward candidateComparison(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateCandidateComparisonReport(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward(forward);
	}

	public ActionForward getApplicantsForStep(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "xmlFile";
		ReportForm form = (ReportForm) actionForm;
		ReportManager reportManager = new ReportManager();
		String xmlFile = reportManager.getApplicantForStepsInXml(form.getStepIds());
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward interviewListFilter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "interviewListFilter";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		ReportForm reportForm = (ReportForm) actionForm;
		PositionManager positionManager = new PositionManager();
		List interviewerList = positionManager.getUsersForRole(UserConstants.ROLE_ADMIN + ", " + UserConstants.ROLE_CXO
				+ ", " + UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_REQUISITIONER + ", "
				+ UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_INTERVIEWER);
		reportForm.setInterviewerList(interviewerList);
		return mapping.findForward(forward);
	}

	public ActionForward pendingActions(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			String userRole = (String) request.getSession(false).getAttribute("userRoles");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generatePendingActionsReport(filterData, userRole, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward(forward);
	}

	public ActionForward recruitmentCost(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = null;
		ReportForm form = (ReportForm) actionForm;
		FilterData filterData = form.getFilterData();
		if (filterData.getRecruitmentCostReportType()
				.equalsIgnoreCase(TPLabels.getLabel("report.label.overall_recruitment_cost_report"))) {
			if (filterData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_SUMMARY)) {
				return overallRecruitmentCostSummary(mapping, actionForm, request, response);
			} else if (filterData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_DETAILS)) {
				return overallRecruitmentCostDetail(mapping, actionForm, request, response);
			}
		} else if (filterData.getRecruitmentCostReportType()
				.equalsIgnoreCase(TPLabels.getLabel("report.label.positionwise_recruitment_cost_report"))) {
			if (filterData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_SUMMARY)) {
				return positionwiseRecruitmentCostSummary(mapping, actionForm, request, response);
			} else if (filterData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_DETAILS)) {
				return positionwiseRecruitmentCostDetail(mapping, actionForm, request, response);
			}
		} else if (filterData.getRecruitmentCostReportType()
				.equalsIgnoreCase(TPLabels.getLabel("report.label.sourcewise_recruitment_cost_report"))) {
			if (filterData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_SUMMARY)) {
				return sourcewiseRecruitmentCostSummary(mapping, actionForm, request, response);
			} else if (filterData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_DETAILS)) {
				return sourcewiseRecruitmentCostDetail(mapping, actionForm, request, response);
			}
		} else if (filterData.getRecruitmentCostReportType()
				.equalsIgnoreCase(TPLabels.getLabel("report.label.transaction_report"))) {
			return transactionCost(mapping, actionForm, request, response);
		}
		return mapping.findForward(forward);
	}

	public ActionForward overallRecruitmentCostSummary(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateOverallRecruitmentCostSummary(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward(forward);
	}

	public ActionForward overallRecruitmentCostDetail(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateOverallRecruitmentCostDetail(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward positionwiseRecruitmentCostSummary(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generatePositionwiseRecruitmentCostSummary(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward positionwiseRecruitmentCostDetail(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generatePositionwiseRecruitmentCostDetail(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward sourcewiseRecruitmentCostSummary(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateSourcewiseRecruitmentCostSummary(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward sourcewiseRecruitmentCostDetail(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateSourcewiseRecruitmentCostDetail(filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward transactionCost(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateTransactionCost(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	/*
	 * Start: Candidate Status Report
	 */
	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward reportCandidateStatus(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateReportCandidateStatus(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}
		return mapping.findForward(forward);

	}

	// End: Candidate Status Report

	/*
	 * Start: Customization Reports
	 */
	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cutomizationReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		String reportName = reportForm.getReportName();
		FilterData filterData = reportForm.getFilterData();
		CustomizedReportGenerator reportGenerator = new CustomizedReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateCustomizedReport(reportName, filterData, userId,
					permissionSet, sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the customized report", e);
		}
		return mapping.findForward(forward);

	}
	// End: Customization Reports

	public ActionForward candidateOffersReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String sessionId = request.getSession(false).getId();
			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateCandidateOffersReport(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
			/*
			 * String outputFileExt =
			 * ".ext";//ReportUtils.getReportExtension(filterData.
			 * getReportFormat()); String filePostFix =
			 * Utils.getDateConvertedToString(Calendar.getInstance().getTime(),
			 * "-dd-MMM-yyyy"); String fileNameToDisplay =
			 * "CandidateOffersReport" +filePostFix+outputFileExt;
			 * request.setAttribute("fileNameToDisplay", fileNameToDisplay);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward monthlyJoiningReportCSV(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		try {
			String sessionId = request.getSession(false).getId();
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");

			String clientIpAddr = getClientIpAddr(request);
			String outputFileName = reportGenerator.generateJoinedApplicantCSVReport(filterData, userId, permissionSet,
					sessionId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	public ActionForward customReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "customReport";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_REPORTS;
		if (!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions, null, null, null)) {
			forward = "authorizationFailure";
			return mapping.findForward(forward);
		}

		ReportForm reportForm = (ReportForm) actionForm;
		String reportId = reportForm.getReportId();
		String userId = (String) request.getSession(false).getAttribute("userId");
		String userRole = (String) request.getSession(false).getAttribute("userRoles");
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");

		ReportManager reportManager = new ReportManager();
		ReportDesignManager designManager = new ReportDesignManager();

		try {
			ReportData reportData = designManager.getReportData(reportId);
			ArrayList<PositionData> positions = reportManager.getPositions(userId, permissionSet, null);

			reportForm.setReportType(reportData.getReportType());
			reportForm.setGroupBy(reportData.getGroupBy());
			reportForm.setReportId(reportData.getReportId());
			reportForm.setColumns(reportData.getColumns());

			String filterString = reportData.getFilters();
			if (filterString != null && !filterString.isEmpty()) {
				String[] filterStr = filterString.split(",");
				for (int i = 0; i < filterStr.length; i++) {
					if (filterStr[i].equals(ReportDesignConstants.FILTER_DEPARTMENT)) {
						reportData.setDepartmentFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_POSITION)) {
						reportData.setPositionFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_STAGE)) {
						reportData.setStageFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_SOURCE)) {
						// Changes done to make source filter confidential for
						// bug #776
						String reportType = reportData.getReportType();
						if (reportType.equals(ReportDesignConstants.REPORT_TYPE_CANDIDATES)
								|| reportType.equals(ReportDesignConstants.REPORT_TYPE_CANDIDATES_WITH_POSITION)
								|| reportType.equals(ReportDesignConstants.REPORT_TYPE_CANDIDATES_WITH_ACTIVITY)
								|| reportType.equals(ReportDesignConstants.REPORT_TYPE_POSITIONS_WITH_CANDIDATE)) {
							if (ImportConfigurationManager.isApplicantFieldViewable(
									ImportConfigurationConstants.FIELD_SOURCE,
									permissionSet.isSHOW_CONFIDENTIAL_DATA())) {
								reportData.setSourceFilter(ReportDesignConstants.AVAILABLE);
							}
						} else {
							reportData.setSourceFilter(ReportDesignConstants.AVAILABLE);
						}
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_USER)) {
						reportData.setUserFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_DATE)) {
						reportData.setDateFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_EXPENSE_TYPE)) {
						reportData.setExpenseTypeFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_ACTIVITY_TYPE)) {
						reportData.setActivityTypeFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_APPOINTMENT)) {
						reportData.setAppointmentDateFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_IMPORTED_BY)) {
						reportData.setImportedByFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_ACTIVITY_BY)) {
						reportData.setActivityByFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_AUDIT_ENTITY)) {
						reportData.setAuditEntityFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_BUDGET_DEPARTMENT)) {
						reportData.setBudgetDepartmentFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_BUDGET_GRADE)) {
						reportData.setBudgetGradeFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_BUDGET_BAND)) {
						reportData.setBudgetBandFilter(ReportDesignConstants.AVAILABLE);
					} else if (filterStr[i].equals(ReportDesignConstants.FILTER_BUDGET_STATUS)) {
						reportData.setBudgetStatusFilter(ReportDesignConstants.AVAILABLE);
					}
				}
			}
			request.setAttribute("t", NavigationConstants.T_REPORT);
			request.setAttribute("reportData", reportData);
			request.setAttribute("positions", positions);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward deleteCustomReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		try {
			ReportForm reportForm = (ReportForm) actionForm;
			String reportId = reportForm.getReportId();
			ReportDesignManager designManager = new ReportDesignManager();
			designManager.deleteCustomReport(reportId);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ArrayList errors = new ArrayList();
			errors.add("1");
		}
		return mapping.findForward(forward);
	}

	public ActionForward runReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "runReport";
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			ReportForm reportForm = (ReportForm) actionForm;
			ReportGenerator reportGenerator = new ReportGenerator();
			ReportDesignManager reportDesignManager = new ReportDesignManager();

			String reportType = reportForm.getReportType();
			String groupBy = reportForm.getGroupBy();
			String reportId = reportForm.getReportId();

			FilterData filterData = reportForm.getFilterData();
			reportGenerator.setReportsToAndFromDate(filterData);
			reportGenerator.setReportsToAndFromDateForAppointment(filterData);

			ReportTypes rt = ReportTypeFactory.getInstance().getReportType(reportType);
			ReportData reportData = reportDesignManager.getReportData(reportId);
			FilterCriteria.setFilterCriteriaString(filterData, reportForm, reportData);

			String newFileName = reportDesignManager.getReportResultData(rt, filterData, reportData, reportId,
					reportType, userId, permissionSet);

			String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"),
					TPApplicationProperties.getProperty("ui.dir"));
			String destinationPath = Utils.concatFilePath(basePath, ReportConstants.REPORT_DESTINATION_FOLDER);
			String filePath = Utils.concatFilePath(destinationPath, newFileName);
			request.setAttribute("filePath", filePath);
			String filePostFix = Utils.getDateConvertedToString(Calendar.getInstance().getTime(), "-dd-MMM-yyyy");
			String fileNameToDisplay = reportData.getReportName() + filePostFix + ".xls";
			request.setAttribute("fileNameToDisplay", fileNameToDisplay);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward pendingOffer(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportManager reportManager = null;
		PreFormattedDynamicReportGenerator gen = null;
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			reportManager = new ReportManager();
			List<PendingOffersReportData> pendingOfferList = reportManager.getPendingOffersList(filterData, userId,
					permissionSet);
			String outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_PENDING_OFFER)
					+ "-" + String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			if (ReportConstants.FORMAT_PRE_FORMATTED.equals(filterData.getReportFormat())) {
				gen = new PreFormattedDynamicReportGenerator();
				outputFileName = gen.generateReport(reportForm.getReportTemplateId(), pendingOfferList, filterCriteria,
						permissionSet);
			} else {
				PendingOffersReport edr = new PendingOffersReport(request, outputFileName, filterData.getReportFormat(),
						pendingOfferList, filterData.getFieldIds(), filterCriteria);
				edr.generateReport();
			}
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward rejectedCandidatesReport(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportManager reportManager = null;
		ReportGenerator reportGenerator = null;
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			reportGenerator = new ReportGenerator();
			reportGenerator.setReportsToAndFromDate(filterData);
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			reportManager = new ReportManager();
			List<SimpleDataObject> rejectedCandidatesList = reportManager.getRejectedCandiadatesList(filterData, userId,
					permissionSet);
			String outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_PENDING_OFFER)
					+ "-" + String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			RejectedCandidatesReport rcr = new RejectedCandidatesReport(request, outputFileName,
					filterData.getReportFormat(), rejectedCandidatesList, filterData.getFieldIds(), filterCriteria,
					filterData.getGroupBy());
			rcr.generateReport();
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward allCandidatesReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportManager reportManager = null;
		ReportGenerator reportGenerator = null;
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			reportGenerator = new ReportGenerator();
			reportGenerator.setReportsToAndFromDate(filterData);
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			reportManager = new ReportManager();
			List<SimpleDataObject> allCandidates = reportManager.getAllCandiadatesList(filterData, userId,
					permissionSet);
			String outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_PENDING_OFFER)
					+ "-" + String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			AllCandidatesReport rcr = new AllCandidatesReport(request, outputFileName, filterData.getReportFormat(),
					allCandidates, filterData.getFieldIds(), filterCriteria, filterData.getGroupBy());
			rcr.generateReport();
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward timeToHire(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportManager reportManager = null;
		PreFormattedDynamicReportGenerator gen = null;
		TimeToHireReport edr = null;
		ReportGenerator reportGenerator = null;
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			reportManager = new ReportManager();
			reportGenerator = new ReportGenerator();
			reportGenerator.setReportsToAndFromDate(filterData);
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			List<TimeToHireReportData> timeToHireList = reportManager.getTimeToHire(filterData, userId, permissionSet);
			String outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_TIME_TO_HIRE)
					+ "-" + String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			if (ReportConstants.FORMAT_PRE_FORMATTED.equals(filterData.getReportFormat())) {
				gen = new PreFormattedDynamicReportGenerator();
				outputFileName = gen.generateReport(reportForm.getReportTemplateId(), timeToHireList, filterCriteria,
						permissionSet);
			} else {
				edr = new TimeToHireReport(request, outputFileName, filterData.getReportFormat(), timeToHireList,
						filterData.getFieldIds(), filterCriteria);
				edr.generateReport();
			}
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	@SuppressWarnings("unchecked")
	public ActionForward hiringActivitySummary(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		String userId = (String) request.getSession(false).getAttribute("userId");
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		String filterCriteria = null;
		String outputFileName = null;
		ReportManager reportManager = null;
		ReportGenerator reportGenerator = null;
		Map<String, String> headerMap = null;
		Object[] hiringActivity = null;
		List<HiringActivityReportData> hiringActivityList = null;
		HiringActivityReport hiringActivityReport = null;

		try {
			reportManager = new ReportManager();
			reportGenerator = new ReportGenerator();

			reportGenerator.setReportsToAndFromDate(filterData);

			hiringActivityList = reportManager.getHiringActivitySummary(filterData, userId, permissionSet);
			hiringActivity = reportManager.modifyActivityList(hiringActivityList);
			hiringActivityList = (List<HiringActivityReportData>) hiringActivity[0];
			headerMap = (Map<String, String>) hiringActivity[1];
			outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_ACTIVITY) + "-"
					+ String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			filterCriteria = reportManager.getFilterCriteria(filterData);
			hiringActivityReport = new HiringActivityReport(request, outputFileName, filterData.getReportFormat(),
					hiringActivityList, headerMap, filterData.getFieldIds(), filterData.getStepTitles(),
					filterCriteria);
			hiringActivityReport.generateReport();
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward hiringActivityDetails(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportManager reportManager = null;
		HiringActivityDetailsReport edr = null;
		ReportGenerator reportGenerator = null;
		List<HiringActivityReportData> activityList = null;
		String outputFileName = null;
		PreFormattedDynamicReportGenerator gen = null;
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			reportManager = new ReportManager();
			reportGenerator = new ReportGenerator();
			reportGenerator.setReportsToAndFromDate(filterData);
			activityList = reportManager.getHiringActivityDetails(filterData, userId, permissionSet);
			outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_ACTIVITY) + "-"
					+ String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());

			String filterCriteria = reportManager.getFilterCriteria(filterData);

			if (ReportConstants.FORMAT_PRE_FORMATTED.equals(filterData.getReportFormat())) {
				gen = new PreFormattedDynamicReportGenerator();
				outputFileName = gen.generateReport(reportForm.getReportTemplateId(), activityList, filterCriteria,
						permissionSet);
			} else {
				edr = new HiringActivityDetailsReport(request, outputFileName, filterData.getReportFormat(),
						activityList, filterData.getFieldIds(), filterCriteria);
				edr.generateReport();
			}
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getHiringActivitySummaryFiledsXML(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		PositionManager positionManager = null;
		ReportManager reportManager = null;
		String fieldsXml = "";
		Map<String, String> columMap = null;
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			positionManager = new PositionManager();
			reportManager = new ReportManager();
			ReportForm reportForm = (ReportForm) actionForm;
			List<SimpleDataObject> positionStepsList = positionManager
					.getDistinctPositionSteps(reportForm.getFilterData(), userId, permissionSet);
			columMap = DynamicReportsColumnUtils.getOptionalFields(permissionSet,
					ReportVersionConstants.REPORT_HIRING_ACTIVITY, ReportConstants.REPORT_TYPE_SUMMARY);
			fieldsXml = reportManager.getXMLForHiringActivitySummaryFields(positionStepsList, columMap);
			request.setAttribute("xmlFile", fieldsXml);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getPositionOwnersXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "xmlFile";
		UserManager userManager = null;
		String positionOwnersXml = "";
		try {
			userManager = new UserManager();
			ArrayList<LoginData> positionOwners = userManager
					.getUsersForPermission(PermissionConstants.PERMISSION_CAN_USER_BE_POSITION_OWNER);
			positionOwnersXml = UserUtils.getXMLforActiveUsers(positionOwners);
			request.setAttribute("xmlFile", positionOwnersXml);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getUserFilterXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "xmlFile";
		PositionManager positionManager = new PositionManager();
		List users = positionManager
				.getUsersForRole(UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_RECRUITER);
		String usersXML = UserUtils.getXMLforActiveUsers(users);
		request.setAttribute("xmlFile", usersXML);
		return mapping.findForward(forward);
	}

	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getInterviewerListFilterXML(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		PositionManager positionManager = new PositionManager();
		ArrayList<LoginData> interviewerList = positionManager
				.getUsersForRole(UserConstants.ROLE_ADMIN + ", " + UserConstants.ROLE_CXO + ", "
						+ UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_REQUISITIONER + ", "
						+ UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_INTERVIEWER);
		String interviewerListXML = UserUtils.getXMLforActiveUsers(interviewerList);
		request.setAttribute("xmlFile", interviewerListXML);
		return mapping.findForward(forward);

	}

	public ActionForward getStepsFilterXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "xmlFile";
		ReportUtils reportUtils = new ReportUtils();
		StepManager stepManager = new StepManager();
		List<MasterStepData> steps;
		try {
			steps = stepManager.getAllSteps("false");
			String stepsXML = reportUtils.getStepGridXML(steps);
			request.setAttribute("xmlFile", stepsXML);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			forward = "error";
			e.printStackTrace();
		}
		return mapping.findForward(forward);

	}

	/**
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward XMLActiveSources(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				String userId = (String) request.getSession(false).getAttribute("userId");
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");

				ReportForm reportForm = (ReportForm) actionForm;
				String sourceCategoryIds = reportForm.getSourceCategoryId();

				MastersManager mastersManager = new MastersManager();
				ReportManager reportManager = new ReportManager();
				ReportUtils reportUtils = new ReportUtils();
				ArrayList sources = new ArrayList();
				if (!Utils.isBlankOrNull(sourceCategoryIds)) {
					sources = mastersManager.getSourcesOfSourceType(sourceCategoryIds);
				} else {
					sources = reportManager.getSourceTitles();
				}
				xmlFile = reportUtils.getXMLforActiveSources(sources, userId, permissionSet);
				request.setAttribute("xmlFile", xmlFile);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	/**
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward XMLSourceCategories(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				String userId = (String) request.getSession(false).getAttribute("userId");
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				ReportUtils reportUtils = new ReportUtils();
				MastersManager masterManager = new MastersManager();
				ArrayList<SimpleDataObject> sourceCategories = masterManager.getAllSourceTypes();

				xmlFile = reportUtils.getXMLforSourceCategories(sourceCategories, userId, permissionSet);
				request.setAttribute("xmlFile", xmlFile);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward getDepartmentsXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String forward = "xmlFile";
		String xmlFile = "";
		ReportUtils reportUtils = new ReportUtils();
		MastersManager mastersManager = new MastersManager();
		ArrayList<DepartmentData> departments = null;
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				// String userId = (String)
				// request.getSession(false).getAttribute("userId");
				// PermissionSet permissionSet = (PermissionSet)
				// request.getSession(false).getAttribute("permissionSet");
				departments = mastersManager.getDepartmentsList(0, MastersConstants.DEPARTMENT_LEVEL_1);
				xmlFile = reportUtils.getXMLforDepartments(departments);
				request.setAttribute("xmlFile", xmlFile);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward getPositionsXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		ReportManager reportManager = new ReportManager();
		PositionManager positionManager = new PositionManager();
		ReportUtils reportUtils = new ReportUtils();
		try {
			ReportForm reportForm = (ReportForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String departmentId = reportForm.getDepartmentId();
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				ArrayList positions = reportManager.getPositions(userId, permissionSet, departmentId);
				xmlFile = reportUtils.getPositionGridXML(positions);
			}
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	/**
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward XMLUserRoles(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String forward = "xmlFile";
		String xmlFile = "";
		ReportUtils reportUtils = new ReportUtils();
		UserManager userManager = new UserManager();
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				String userId = (String) request.getSession(false).getAttribute("userId");
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				ArrayList<SimpleDataObject> userRoles = userManager.getRoles();
				xmlFile = reportUtils.getXMLforUserRoles(userRoles, userId, permissionSet);
				request.setAttribute("xmlFile", xmlFile);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	/**
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward XMLRolewiseUsers(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String forward = "xmlFile";
		String xmlFile = "";
		PositionManager positionManager = new PositionManager();
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				ReportForm reportForm = (ReportForm) actionForm;
				// String roleIds = reportForm.getRoleId();

				ArrayList<LoginData> users = new ArrayList<LoginData>();
				/*
				 * if(!Utils.isBlankOrNull(roleIds)){ users =
				 * positionManager.getUsersForRole(roleIds);
				 * 
				 * }else{
				 */
				users = positionManager.getUsersForRole(UserConstants.ROLE_HR_MANAGER + ", "
						+ UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_ADMIN + ", " + UserConstants.ROLE_CXO
						+ ", " + UserConstants.ROLE_REQUISITIONER + ", " + UserConstants.ROLE_INTERVIEWER);
				// }

				xmlFile = UserUtils.getXMLforActiveUsers(users);
				request.setAttribute("xmlFile", xmlFile);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward getStageXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		StepManager stepManager = new StepManager();
		ReportUtils reportUtils = new ReportUtils();
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				List<MasterStepData> stages = stepManager.getAllStages();
				xmlFile = reportUtils.getStageGridXML(stages);
			}
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getStepXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		StepManager stepManager = new StepManager();
		ReportUtils reportUtils = new ReportUtils();
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				List<MasterStepData> steps = stepManager.getAllSteps("false");
				xmlFile = reportUtils.getStepGridXML(steps);
			}
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getPositionStatusXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		ReportUtils reportUtils = new ReportUtils();
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				List<SimpleDataObject> positionStatuses = PositionUtils.getPositionStatuses();
				xmlFile = reportUtils.getStatusesGridXML(positionStatuses);
			}
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	/**
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward datewiseHiringReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportManager reportManager = null;
		DatewiseHiringReport edr = null;
		ReportGenerator reportGenerator = null;
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			reportManager = new ReportManager();
			reportGenerator = new ReportGenerator();
			reportGenerator.setReportsToAndFromDate(filterData);
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			List<DatewiseHiringReportData> datewiseHiringReportList = reportManager.getDatewiseHiringReport(filterData,
					userId, permissionSet);
			String outputFileName = ReportVersionConstants
					.getReportTitle(ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT) + "-"
					+ String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			edr = new DatewiseHiringReport(request, outputFileName, filterData.getReportFormat(),
					datewiseHiringReportList, filterData.getFieldIds(), filterCriteria);
			edr.generateReport();
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return joiner report to get report for candidates yet to join
	 */
	public ActionForward joinerReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportManager reportManager = null;
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			reportManager = new ReportManager();
			reportGenerator.setReportsToAndFromDate(filterData);
			List<JoinerReportData> joinerReportData = reportManager.getJoinerReportData(filterData);
			String outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_JOINER) + "-"
					+ String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			JoinerReport joinerReport = null;
			String fieldsToShow = filterData.getFieldIds();
			String reportTitle = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_JOINER);
			joinerReport = new JoinerReport(request, outputFileName, filterData.getReportFormat(), joinerReportData,
					fieldsToShow, filterCriteria, reportTitle);
			joinerReport.generateReport();
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return joined and joiner report both have same flow to get report for
	 *         candidates already joined
	 */
	public ActionForward joinedReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportManager reportManager = null;
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			reportManager = new ReportManager();
			reportGenerator.setReportsToAndFromDate(filterData);
			List<JoinerReportData> joinedReportData = reportManager.getJoinedReportData(filterData);
			String outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_JOINED) + "-"
					+ String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			JoinerReport joinerReport = null;
			String fieldsToShow = filterData.getFieldIds();
			String reportTitle = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_JOINED);
			joinerReport = new JoinerReport(request, outputFileName, filterData.getReportFormat(), joinedReportData,
					fieldsToShow, filterCriteria, reportTitle);
			joinerReport.generateReport();
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward reportAuditTrail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		ReportManager reportManager = null;
		try {
			reportManager = new ReportManager();
			reportGenerator.setReportsToAndFromDate(filterData);
			/*
			 * String userId = (String)
			 * request.getSession(false).getAttribute("userId"); PermissionSet
			 * permissionSet = (PermissionSet)
			 * request.getSession(false).getAttribute("permissionSet"); String
			 * sessionId = request.getSession(false).getId(); String
			 * clientIpAddr = getClientIpAddr(request); String outputFileName =
			 * reportGenerator.generateReportAuditTrail(filterData, userId,
			 * permissionSet, sessionId, clientIpAddr);
			 * request.setAttribute("fileName", outputFileName);
			 */
			List<AuditTrailView> auditTrailView = reportManager.getAuditTrailReport(filterData);
			// BlackListReportData
			String outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_AUDIT_LOG) + "-"
					+ String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			AuditTrailLogReport auditTrailLogReport = null;
			// BlackListCandidateReport
			String fieldsToShow = filterData.getFieldIds();
			String reportTitle = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_AUDIT_LOG);
			auditTrailLogReport = new AuditTrailLogReport(request, outputFileName, filterData.getReportFormat(),
					auditTrailView, fieldsToShow, filterCriteria, reportTitle);
			auditTrailLogReport.generateReport();
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}
		return mapping.findForward(forward);

	}

	// blacklisted Candidate Report
	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return joined and joiner report both have same flow to get report for
	 *         candidates already joined
	 */
	public ActionForward blacklistedReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportManager reportManager = null;
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			reportManager = new ReportManager();
			reportGenerator.setReportsToAndFromDate(filterData);
			List<BlackListReportData> blackListReportData = reportManager.getBlackListReportData(filterData);
			// BlackListReportData
			String outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_BLACKLISTED)
					+ "-" + String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			BlackListCandidateReport blackListCandidateReport = null;
			// BlackListCandidateReport
			String fieldsToShow = filterData.getFieldIds();
			String reportTitle = ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_BLACKLISTED);
			blackListCandidateReport = new BlackListCandidateReport(request, outputFileName,
					filterData.getReportFormat(), blackListReportData, fieldsToShow, filterCriteria, reportTitle);
			blackListCandidateReport.generateReport();
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward oneStopFileReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportManager reportManager = null;
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			reportManager = new ReportManager();
			reportGenerator.setReportsToAndFromDate(filterData);
			List<CandidateOneStopFileReportData> candidateOneStopFileReportData = reportManager
					.getOneStopFileReportData(filterData);
			String outputFileName = ReportVersionConstants.getReportTitle(ReportVersionConstants.ONE_STOP_FILE_REPORT)
					+ "-" + String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(filterData.getReportFormat());
			String filterCriteria = reportManager.getFilterCriteria(filterData);
			CandidateOneStopFileReport candidateOneStopFileReport = null;
			candidateOneStopFileReport = new CandidateOneStopFileReport(request, outputFileName,
					filterData.getReportFormat(), candidateOneStopFileReportData, filterCriteria);
			candidateOneStopFileReport.generateReport();
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward financeCostSheetReport(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			reportGenerator.setReportsToAndFromDate(filterData);
			String outputFileName = ReportVersionConstants.getReportTitle(
					ReportVersionConstants.COST_SHEET_FINANCE_REPORT) + "-" + String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(ReportConstants.FORMAT_EXCEL);

			String jsonAuthString = ReportUtils.excuteGET(newApplicationURL+newApplicationAuthAPI);
			Gson gson = new Gson();
			OauthResponseObject oauthResponseObject = gson.fromJson(jsonAuthString, OauthResponseObject.class);
			String userId = (String) request.getSession(false).getAttribute("userId");
			boolean showAllPositionPermisssion = UserUtils.getShowPermissionForLoggedInUser(userId);
			String saveFilePath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH,
					outputFileName);
			StringBuilder sb = new StringBuilder("{ \"tenantReportId\" : \"" + TPLabels.getLabel("report.newtalentpool.cost_sheet_finance_report") + "\","
					+ "\"isCustomReport\" : \"" + TPLabels.getLabel("report.newtalentpool.cost_sheet_finance_report_isCustom") + "\"," 
					+ "\"showAllPositionPermisssion\": "+showAllPositionPermisssion+","  
					+ "\"userId\" : \""+ userId + "\","
					+ FilterConverter.getConvertedCriteriaFilter(filterData,ReportVersionConstants.COST_SHEET_FINANCE_REPORT));
			TPLogger.getLogger().info("JSON generated for financeCostSheetReport : " +sb);
			InputStream inputStream = ReportUtils.excutePOST(
					newApplicationURL
							+ newApplicationDownloadAPI,
					oauthResponseObject.getTokenType(), oauthResponseObject.getAccessToken(), saveFilePath, sb.toString());
			TPLogger.getLogger().error("Download request done for financeCostSheetReport :  "+inputStream);
			request.setAttribute("fileResponse", inputStream);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the financeCostSheetReport report", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward joinerDataFinanceReport(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			reportGenerator.setReportsToAndFromDate(filterData);
			String outputFileName = ReportVersionConstants.getReportTitle(
					ReportVersionConstants.JOINER_DATA_FINANCE_REPORT) + "-" + String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(ReportConstants.FORMAT_EXCEL);

			String jsonAuthString = ReportUtils.excuteGET(newApplicationURL+newApplicationAuthAPI);
			Gson gson = new Gson();
			OauthResponseObject oauthResponseObject = gson.fromJson(jsonAuthString, OauthResponseObject.class);
			String userId = (String) request.getSession(false).getAttribute("userId");
			boolean showAllPositionPermisssion = UserUtils.getShowPermissionForLoggedInUser(userId);
			String saveFilePath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH,
					outputFileName);
			StringBuilder sb = new StringBuilder("{ \"tenantReportId\" : \"" + TPLabels.getLabel("report.newtalentpool.joiner_data_finance_report") + "\","
					+ "\"isCustomReport\" : \""+ TPLabels.getLabel("report.newtalentpool.joiner_data_finance_report_isCustom") +"\"," 
					+ "\"showAllPositionPermisssion\": "+showAllPositionPermisssion+"," 
					+ "\"userId\" : \""+ userId + "\","
					+ FilterConverter.getConvertedCriteriaFilter(filterData,ReportVersionConstants.JOINER_DATA_FINANCE_REPORT));
			TPLogger.getLogger().error("JSON generated for joinerDataFinanceReport : " +sb);
			InputStream inputStream = ReportUtils.excutePOST(
					newApplicationURL+ newApplicationDownloadAPI,
					oauthResponseObject.getTokenType(), oauthResponseObject.getAccessToken(), saveFilePath,sb.toString());
			TPLogger.getLogger().error("Download request done for joinerDataFinanceReport :  "+inputStream);
			request.setAttribute("fileResponse", inputStream);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the joinerDataFinanceReport report", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward indiaHiringReqReport(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			reportGenerator.setReportsToAndFromDate(filterData);
			String outputFileName = ReportVersionConstants.getReportTitle(
					ReportVersionConstants.INDIA_HIRING_REQ_REPORT) + "-" + String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(ReportConstants.FORMAT_EXCEL);

			String jsonAuthString = ReportUtils.excuteGET(newApplicationURL+newApplicationAuthAPI);
			Gson gson = new Gson();
			OauthResponseObject oauthResponseObject = gson.fromJson(jsonAuthString, OauthResponseObject.class);
			String userId = (String) request.getSession(false).getAttribute("userId");
			boolean showAllPositionPermisssion = UserUtils.getShowPermissionForLoggedInUser(userId);
			String saveFilePath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH,
					outputFileName);
			StringBuilder sb = new StringBuilder("{ \"tenantReportId\" : \"" + TPLabels.getLabel("report.newtalentpool.india_hiring_req_report") + "\","
					+ "\"isCustomReport\" : \""+ TPLabels.getLabel("report.newtalentpool.india_hiring_req_report_isCustom") + "\"," 
					+ "\"showAllPositionPermisssion\": "+showAllPositionPermisssion+"," 
					+ "\"userId\" : \""+ userId + "\","
					+ FilterConverter.getConvertedCriteriaFilter(filterData,ReportVersionConstants.INDIA_HIRING_REQ_REPORT));
			TPLogger.getLogger().error("JSON generated for indiaHiringReqReport : " +sb);
			InputStream inputStream = ReportUtils.excutePOST(
					newApplicationURL+ newApplicationDownloadAPI,
					oauthResponseObject.getTokenType(), oauthResponseObject.getAccessToken(), saveFilePath, sb.toString());
			TPLogger.getLogger().error("Download request done for indiaHiringReqReport :  "+inputStream);
			request.setAttribute("fileResponse", inputStream);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the indiaHiringReqReport report", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward indiaHiringSummaryReport(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		ReportForm reportForm = (ReportForm) actionForm;
		FilterData filterData = reportForm.getFilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		try {
			reportGenerator.setReportsToAndFromDate(filterData);
			String outputFileName = ReportVersionConstants.getReportTitle(
					ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT) + "-" + String.valueOf(System.currentTimeMillis())
					+ ReportUtils.getReportExtension(ReportConstants.FORMAT_EXCEL);

			String jsonAuthString = ReportUtils.excuteGET(newApplicationURL+newApplicationAuthAPI);
			Gson gson = new Gson();
			OauthResponseObject oauthResponseObject = gson.fromJson(jsonAuthString, OauthResponseObject.class);
			String userId = (String) request.getSession(false).getAttribute("userId");
			boolean showAllPositionPermisssion = UserUtils.getShowPermissionForLoggedInUser(userId);
			
			String saveFilePath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH,
					outputFileName);
			StringBuilder sb = new StringBuilder("{ \"tenantReportId\" : \"" + TPLabels.getLabel("report.newtalentpool.india_hiring_summary_report") + "\","
					+ "\"isCustomReport\" : \""+ TPLabels.getLabel("report.newtalentpool.india_hiring_summary_report_isCustom") + "\"," 
					+ "\"showAllPositionPermisssion\": "+showAllPositionPermisssion+"," 
					+ "\"userId\" : \""+ userId + "\","
					+ FilterConverter.getConvertedCriteriaFilter(filterData,ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT));
			
			TPLogger.getLogger().error("JSON generated for indiaHiringSummaryReport : " +sb);
			
			InputStream inputStream = ReportUtils.excutePOST(
					newApplicationURL+ newApplicationDownloadAPI,
					oauthResponseObject.getTokenType(), oauthResponseObject.getAccessToken(), saveFilePath,sb.toString());
			
			TPLogger.getLogger().error("Download request done for indiaHiringSummaryReport :  "+inputStream);
			request.setAttribute("fileResponse", inputStream);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the indiaHiringSummaryReport report", e);
		}
		return mapping.findForward(forward);
	}
}