/**
 * 
 */
package com.talentPool.reports.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.gson.Gson;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.ZipFileUtil;
import com.talentPool.dynamicReports.data.OfferedCTCReportData;
import com.talentPool.dynamicReports.generator.PreFormattedDynamicReportGenerator;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.ReportUtils;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.reports.action.CustomizedReportGenerator;
import com.talentPool.reports.action.ReportGenerator;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.dataobject.OauthResponseObject;
import com.talentPool.reports.dataobject.ReportScheduleData;
import com.talentPool.reports.manager.CustomizedReportManager;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.reports.manager.ReportSchedulerManager;
import com.talentPool.reports.utils.FilterConverter;
import com.talentPool.reports.views.OfferedCTCView;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.utils.UserUtils;

/**
 * @author Shantanu
 * 
 */
public class SendScheduledReportJob implements Job {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	private static Logger log = TPLogger.getLogger();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			log.debug("Sending Scheduled Report");
			String scheduleId = context.getTrigger().getJobDataMap().getString("scheduleId");
			log.debug("Scheduled "+scheduleId);
			sendReportForScheduler(scheduleId);

		} catch (Exception e) {
			log.error("Error in execute", e);
		}
	}

	private void sendReportForScheduler(String scheduleId) throws Exception {
		// GET Report Schedule DATA
		ReportManager reportManager = new ReportManager();
		ReportSchedulerManager reportSchedulerManager = new ReportSchedulerManager();
		
		ReportScheduleData reportScheduleData = reportSchedulerManager.getReportSchedule(scheduleId);		

		//LoginManager loginManager = new LoginManager();
		//LoginData loginData = loginManager.getUser(reportScheduleData.getUserId());			
		 
		FilterData filterData =reportSchedulerManager.generateFilterData(reportScheduleData);
		
		//BitSet permissions= loginManager.getUserPermissionsBitSet(loginData.getUserId());
		//BitSet reportBitSet = loginManager.getUserReportsBitSet(loginData.getUserId());
		//PermissionSet permissionSet = new PermissionSet(permissions);
		PermissionSet permissionSet =	reportManager.getUserPermission(reportScheduleData.getUserId());
		
		String reportFileName="";			
			
		reportFileName= generateReportFileName(filterData,reportScheduleData,permissionSet);
		scheduleReportMailSender(reportFileName,reportScheduleData,scheduleId);
		
	}
	
	public void scheduleReportMailSender(String reportFileName, ReportScheduleData reportScheduleData, String scheduleId) throws Exception{
		ReportSchedulerManager reportSchedulerManager =new ReportSchedulerManager();
			
			// Construct token data
		InboxManager inboxManager = new InboxManager();
		InboxData inboxData = inboxManager.getCurrentInboxSettings();
			
		LoginManager loginManager = new LoginManager();
		LoginData loginData = loginManager.getUser(reportScheduleData.getUserId());
		
		//	 send email
		MessageData messageData = new MessageData();
		messageData.setFrom(loginData.getName() + " <" + inboxData.getInboxEmail() + ">");
		messageData.setReplyTo(loginData.getName() + " <" + loginData.getEmail() + ">");
		messageData.setHtmlBody("<pre>"+Utils.getBlankIfNull(reportScheduleData.getMailBody())+"</pre>");
		messageData.setSubject(reportScheduleData.getMailSubject());
		messageData.setTo(reportSchedulerManager.getCommaSeparatedEmailIds(scheduleId));
			
		String[] fileNames=null; 
		String outputFileName=null; 
		String outputFilePath=null;		
		String htmlFileName=null;
		try{	
			//if(reportScheduleData.getReportFormat().equals("1")){
			if(reportScheduleData.getReportFormat().equals(ReportConstants.FORMAT_HTML)){
				htmlFileName=reportFileName+"_files";	
				fileNames=new String[] {htmlFileName,reportFileName};
				outputFilePath=ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH;
				outputFileName=reportFileName;
				reportFileName=ZipFileUtil.convertToZipFile(fileNames,outputFileName,outputFilePath);
			}		
			
			
			AttachmentData attachmentData = new AttachmentData();		
			String attachmentFilePath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH,reportFileName);
			attachmentData.setAttachmentFilePath(attachmentFilePath);				
			String fileName = ReportVersionConstants.getReportTitle(reportScheduleData.getReportId());
			if(!ReportVersionConstants.mapReportIdName.containsKey(reportScheduleData.getReportId())) {
				fileName = new CustomizedReportManager().getCustomizedReportDataForReport(reportScheduleData.getReportId()).getReportLabel();
			}
	
			Calendar cal = Calendar.getInstance();
			if(reportScheduleData.getReportFormat().equals(ReportConstants.FORMAT_HTML))
				fileName = fileName + " [" + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE)+"]"+".zip";
			else if(reportScheduleData.getReportFormat().equals(ReportConstants.FORMAT_PDF))
				fileName = fileName + " [" + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE)+"]"+".pdf";
			else if(reportScheduleData.getReportFormat().equals(ReportConstants.FORMAT_EXCEL))
				fileName = fileName + " [" + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE)+"]"+".xls";
						
			attachmentData.setOriginalFileName(fileName);
			
			ArrayList<AttachmentData> attachments = new ArrayList<AttachmentData>();
			attachments.add(attachmentData);		
			messageData.setAttachments(attachments);
					
			TPMailSender sender = new TPMailSender();
			sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
		}catch(Exception e){
			TPLogger.getLogger().error("Caught Exception", e);			 
		}
		
	}
	
	public String generateReportFileName(FilterData filterData, ReportScheduleData reportScheduleData, PermissionSet permissionSet){
		String reportFileName=null;
		String sessionId=Utils.getRandomString(20, 0, 0, true, true, null, new Random());
		ReportGenerator reportGenerator=new ReportGenerator();
		String clientIpAddr = null;
		String reportId = reportScheduleData.getReportId();
		int iReportId = 0;
		try {
			iReportId = Integer.parseInt(reportId);
		} catch (NumberFormatException e) {
			log.debug("Cannot parse reportId as Integer, it must be a customized report", e);			
			reportFileName = new CustomizedReportGenerator().generateCustomizedReport(reportId, filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
			return reportFileName;
		}
		try{
			
			switch (iReportId) {
			case 1:
				if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_SUMMARY)){
					reportFileName = reportGenerator.generateHiringStatusSummaryReport(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
					break;
				}else if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_DETAILS)){
					reportFileName = reportGenerator.generateHiringStatusDetailReport(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
					break;
				}			
			case 2:			
				if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_SUMMARY)){
					reportFileName = reportGenerator.generateHiringFunnelSummaryReport(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
					break;
				}else if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_DETAILS)){
					reportFileName = reportGenerator.generateHiringFunnelDetailReport(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
					break;
				}				
			case 3:
				reportFileName = reportGenerator.generatePositionSummaryReport(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;
			case 4:
				LoginManager loginManager = new LoginManager();
				LoginData data = loginManager.getUser(reportScheduleData.getUserId());
				reportFileName = reportGenerator.generatePendingActionsReport(filterData, data.getRoleId(), reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;
			case 5:			
				if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_SUMMARY)){
					reportFileName = reportGenerator.generateHiringEfficiencyReportSummary(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
					break;
				}else if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_DETAILS)){
					reportFileName = reportGenerator.generateHiringEfficiencyReportDetail(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr );
					break;
				}	
				
			case 6:
				reportFileName = reportGenerator.generateSourceWiseHringReport(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;
			case 7:
				if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_SUMMARY)){
					reportFileName = reportGenerator.generateMonthlyJoiningReportSummary(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
					break;
				}else if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_DETAILS)){
					reportFileName = reportGenerator.generateMonthlyJoiningReportDetails(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
					break;
				}else if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_CHART)){
					reportFileName = reportGenerator.generateMonthlyJoiningReportChart(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
					break;
				}
				
			case 8:
				reportFileName = reportGenerator.generateSourcewiseImportReport(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;
			case 9:
				if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_SUMMARY)){
					reportFileName = reportGenerator.generateOfferToJoinedReportSummary(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
					break;
				}else if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_DETAILS)){
					reportFileName = reportGenerator.generateOfferToJoinedReportDetailed(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
					break;
				}
			case 10:
				reportFileName = reportGenerator.generateCandidateComparisonReport(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;
			case 11:
				if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_SUMMARY) && !reportScheduleData.getCostReportType().equalsIgnoreCase("Transaction Report")){
					if(reportScheduleData.getCostReportType().equalsIgnoreCase("Overall Recruitment Cost Report")){
						reportFileName = reportGenerator.generateOverallRecruitmentCostSummary(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
						break;
					}else if(reportScheduleData.getCostReportType().equalsIgnoreCase("Positionwise Recruitment Cost Report")){
						reportFileName = reportGenerator.generatePositionwiseRecruitmentCostSummary(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
						break;
					}else if(reportScheduleData.getCostReportType().equalsIgnoreCase("Sourcewise Recruitment Cost Report")){
						reportFileName = reportGenerator.generateSourcewiseRecruitmentCostSummary(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
						break;
					}							
				}else if(reportScheduleData.getReportType().equalsIgnoreCase(ReportConstants.REPORT_TYPE_DETAILS)&& !reportScheduleData.getCostReportType().equalsIgnoreCase("Transaction Report")){
					if(reportScheduleData.getCostReportType().equalsIgnoreCase("Overall Recruitment Cost Report")){
						reportFileName = reportGenerator.generateOverallRecruitmentCostDetail(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
						break;
					}else if(reportScheduleData.getCostReportType().equalsIgnoreCase("Positionwise Recruitment Cost Report")){
						reportFileName = reportGenerator.generatePositionwiseRecruitmentCostDetail(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
						break;
					}else if(reportScheduleData.getCostReportType().equalsIgnoreCase("Sourcewise Recruitment Cost Report")){
						reportFileName = reportGenerator.generateSourcewiseRecruitmentCostDetail(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
						break;
					}
				}else if(reportScheduleData.getCostReportType().equalsIgnoreCase("Transaction Report")){
					reportFileName = reportGenerator.generateTransactionCost(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
					break;				
				}		
			case 13:
				reportFileName =reportGenerator.generateInterviewList(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr );
				break;
			case 14:
				reportFileName = reportGenerator.generateApplicantDetails(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;
			case 15:
				reportFileName =reportGenerator.generateImportReport(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;
			case 16:
				reportFileName =reportGenerator.generateUserActivity(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;	
			case 17:
				reportFileName =reportGenerator.generatePositionActivity(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;
			case 18:				
				reportFileName = reportGenerator.generateReportCandidateStatus(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;
			case 27:
				reportFileName= reportGenerator.generateInterviewStatus(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;
			case 28:
				reportFileName= reportGenerator.generateOfferToJoinedDetailedReport(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;
			case 46:
				ReportManager reportManager = new ReportManager();
				Map<String,List<OfferedCTCView>> offeredCTCView = (Map<String,List<OfferedCTCView>>)reportManager.getOfferCTCDetailsReport(filterData);
				List<OfferedCTCReportData> offeredCTCReportData= new ArrayList<OfferedCTCReportData>();
				reportManager.convertMapToList(offeredCTCView,offeredCTCReportData);
				String filterCriteria = reportManager.getFilterCriteria(filterData);
				PreFormattedDynamicReportGenerator gen = new PreFormattedDynamicReportGenerator();
				reportFileName = gen.generateReport(reportScheduleData.getTemplateId(), offeredCTCReportData, filterCriteria, permissionSet);
				    
				//reportFileName= reportGenerator.generateOfferToJoinedDetailedReport(filterData, reportScheduleData.getUserId(), permissionSet, sessionId, clientIpAddr);
				break;
			case 48:
				reportFileName = sendScheduledReport(reportScheduleData,ReportVersionConstants.COST_SHEET_FINANCE_REPORT,sessionId,TPLabels.getLabel("report.newtalentpool.cost_sheet_finance_report"),TPLabels.getLabel("report.newtalentpool.cost_sheet_finance_report_isCustom"));
				break;
			case 49:
				reportFileName = sendScheduledReport(reportScheduleData,ReportVersionConstants.JOINER_DATA_FINANCE_REPORT,sessionId,TPLabels.getLabel("report.newtalentpool.joiner_data_finance_report"),TPLabels.getLabel("report.newtalentpool.joiner_data_finance_report_isCustom"));
				break;
			case 50:
				reportFileName = sendScheduledReport(reportScheduleData,ReportVersionConstants.INDIA_HIRING_REQ_REPORT,sessionId,TPLabels.getLabel("report.newtalentpool.india_hiring_req_report"),TPLabels.getLabel("report.newtalentpool.india_hiring_req_report_isCustom"));
				break;
			case 51:
				reportFileName = sendScheduledReport(reportScheduleData,ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT,sessionId,TPLabels.getLabel("report.newtalentpool.india_hiring_summary_report"),TPLabels.getLabel("report.newtalentpool.india_hiring_summary_report_isCustom"));
				break;
			}					
		}catch (Exception e) {
			log.error("Error while Generating Report File Name ", e);
		}		
		return reportFileName;
	}

	private String sendScheduledReport(ReportScheduleData reportScheduleData,String reportName, String sessionId, String reportId, String isCustomReport){
		String outputFileName = reportName+sessionId + String.valueOf(System.currentTimeMillis()) + ReportConstants.FORMAT_EXCEL;

		String newApplicationURL= TPApplicationProperties.getProperty("common.newtalentpool.application_url");
		String newApplicationAuthAPI= TPApplicationProperties.getProperty("report.newtalentpool.get_oauthtoken_api");
		String newApplicationDownloadAPI = TPApplicationProperties.getProperty("report.newtalentpool.post_XLSreport_api");
		
		String jsonAuthString = newApplicationURL+newApplicationAuthAPI;
		Gson gson = new Gson();
		OauthResponseObject oauthResponseObject = gson.fromJson(jsonAuthString, OauthResponseObject.class);
		String saveFilePath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH,
				outputFileName);
		boolean showAllPositionPermisssion = UserUtils.getShowPermissionForLoggedInUser(reportScheduleData.getUserId());
		StringBuilder sb = new StringBuilder("{ \"tenantReportId\" : \"" + reportId + "\","
				+ "\"isCustomReport\" : \""+isCustomReport+"\"," 
				+ "\"showAllPositionPermisssion\": "+showAllPositionPermisssion+"," 
				+ "\"userId\" : \""+ reportScheduleData.getUsers() + "\","
				+ FilterConverter.getConvertedCriteriaFilterForScedulableReports(reportScheduleData,reportName));
		ReportUtils.excutePOST(newApplicationURL+ newApplicationDownloadAPI,
				oauthResponseObject.getTokenType(), oauthResponseObject.getAccessToken(), saveFilePath,sb.toString());
		
		return outputFileName;
		
	}
}
