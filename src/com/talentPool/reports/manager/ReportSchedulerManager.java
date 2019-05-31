package com.talentPool.reports.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.manager.StepManager;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.dataobject.ReportScheduleData;
import com.talentPool.reports.scheduler.ReportScheduler;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

public class ReportSchedulerManager {

	/* Start: Report Scheduler */

	public ArrayList<ReportScheduleData> getScheduledReports(String userId, String reportName) {
		ArrayList scheduledReportsList = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dReportSchedulerManager_FetchScheduleReportDetails");
			dq.setString(1, userId);
			dq.setString(2, reportName);
			scheduledReportsList = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting Scheduled Report", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return scheduledReportsList;
	}

	public String getXMLForScheduledReports(ArrayList scheduledReports) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < scheduledReports.size(); i++) {
				ReportScheduleData reportScheduleData = (ReportScheduleData) scheduledReports.get(i);
				String scheduleId = "" + reportScheduleData.getScheduleId();
				String frequency = reportScheduleData.getFrequency();
				String emailIds = reportScheduleData.getEmailIds();

				String showCriteria = getReportScheduledCriteria(reportScheduleData);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + scheduleId);
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "delete");
				wr.startElement("", "userdata", "", at);
				wr.characters("delete");
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "showCriteria");
				wr.startElement("", "userdata", "", at);
				wr.characters(showCriteria);
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "emailIds");
				wr.startElement("", "userdata", "", at);
				wr.characters(emailIds);
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<a href=\"#\" onclick=\"onClickDeleteReportSchedule(" + scheduleId + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>");
				wr.endElement("cell");

				if (ReportConstants.REPORT_SCHEDULER_DAILY.equalsIgnoreCase(frequency)) {
					frequency = "Daily";
				} else if (ReportConstants.REPORT_SCHEDULER_WEEKLY.equalsIgnoreCase(frequency)) {
					frequency = "Weekly";
				} else if (ReportConstants.REPORT_SCHEDULER_MONTHLY.equalsIgnoreCase(frequency)) {
					frequency = "Monthly";
				}

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "frequency");
				wr.startElement("", "userdata", "", at);
				wr.characters(frequency);
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(frequency) + "^javascript:onClickReportScheduler(" + scheduleId + ");^_self");
				wr.endElement("cell");

				showCriteria = (showCriteria.length() > 40) ? showCriteria.substring(0, 40) + "..." : showCriteria;
				wr.startElement("cell");
				wr.characters(showCriteria);
				wr.endElement("cell");

				emailIds = (emailIds.length() > 25) ? emailIds.substring(0, 25) + "..." : emailIds;
				wr.startElement("cell");
				wr.characters(emailIds);
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("error while creating xml file for Reminder", e);
		}
		return sWr.getBuffer().toString();
	}

	public FilterData generateFilterData(ReportScheduleData reportScheduleData) {
		FilterData filterData = new FilterData();
		filterData.setActionId(reportScheduleData.getActionId());
		filterData.setApplicants(reportScheduleData.getApplicants());
		filterData.setDateRange(reportScheduleData.getDateRange());
		filterData.setDegreeIds(reportScheduleData.getDegreeIds());
		filterData.setDepartmentId(reportScheduleData.getDepartmentId());
		filterData.setFilterId(reportScheduleData.getFilterId());
		filterData.setFromDate(reportScheduleData.getFromDate());
		filterData.setFromMonth(reportScheduleData.getFromMonth());
		filterData.setFromYear(reportScheduleData.getFromYear());
		filterData.setInterviewers(reportScheduleData.getInterviewers());
		filterData.setMaxExp(reportScheduleData.getMaxExp());
		filterData.setMinExp(reportScheduleData.getMinExp());
		filterData.setOrderBy(reportScheduleData.getOrderBy());
		filterData.setPositionId(reportScheduleData.getPositionId());
		filterData.setRecruitmentCostReportType(reportScheduleData.getCostReportType());
		filterData.setReportFormat(reportScheduleData.getReportFormat());
		filterData.setReportName(reportScheduleData.getReportId());
		filterData.setReportType(reportScheduleData.getReportType());
		filterData.setSourceId(reportScheduleData.getSourceId());
		filterData.setSourceCategoryId(reportScheduleData.getSourceCategoryId());
		filterData.setStages(reportScheduleData.getStages());
		filterData.setStepIds(reportScheduleData.getStepIds());
		filterData.setToDate(reportScheduleData.getToDate());
		filterData.setToMonth(reportScheduleData.getToMonth());
		filterData.setToYear(reportScheduleData.getToYear());
		filterData.setUserId(reportScheduleData.getUserId());
		filterData.setUsers(reportScheduleData.getUsers());
		filterData.setNumberRange(reportScheduleData.getNumberRange());
		filterData.setActivities(reportScheduleData.getActivities());
		filterData.setPositionFilter(reportScheduleData.getPositionFilter());
		filterData.setDepartmentFilter(reportScheduleData.getDepartmentFilter());
		filterData.setReportTemplateId(reportScheduleData.getTemplateId());
		return filterData;
	}

	public ReportScheduleData generateReportScheduleData(FilterData filterData) {
		ReportScheduleData reportScheduleData = new ReportScheduleData();
		reportScheduleData.setActionId(filterData.getActionId());
		reportScheduleData.setApplicants(filterData.getApplicants());
		reportScheduleData.setDateRange(filterData.getDateRange());
		reportScheduleData.setDegreeIds(filterData.getDegreeIds());
		reportScheduleData.setDepartmentId(filterData.getDepartmentId());
		reportScheduleData.setSubDepartmentId(filterData.getSubDepartmentId());
		reportScheduleData.setSubSubDepartmentId(filterData.getSubSubDepartmentId());
		reportScheduleData.setFilterId(filterData.getFilterId());
		reportScheduleData.setFromDate(filterData.getFromDate());
		reportScheduleData.setFromMonth(filterData.getFromMonth());
		reportScheduleData.setFromYear(filterData.getFromYear());
		reportScheduleData.setInterviewers(filterData.getInterviewers());
		reportScheduleData.setMaxExp(filterData.getMaxExp());
		reportScheduleData.setMinExp(filterData.getMinExp());
		reportScheduleData.setOrderBy(filterData.getOrderBy());
		reportScheduleData.setPositionId(filterData.getPositionId());
		reportScheduleData.setCostReportType(filterData.getRecruitmentCostReportType());
		reportScheduleData.setReportFormat(filterData.getReportFormat());
		reportScheduleData.setReportId(filterData.getReportName());
		reportScheduleData.setReportType(filterData.getReportType());
		reportScheduleData.setSourceId(filterData.getSourceId());
		reportScheduleData.setSourceCategoryId(filterData.getSourceCategoryId());
		reportScheduleData.setStages(filterData.getStages());
		reportScheduleData.setStepIds(filterData.getStepIds());
		reportScheduleData.setToDate(filterData.getToDate());
		reportScheduleData.setToMonth(filterData.getToMonth());
		reportScheduleData.setToYear(filterData.getToYear());
		reportScheduleData.setUserId(filterData.getUserId());
		reportScheduleData.setUsers(filterData.getUsers());
		reportScheduleData.setNumberRange(filterData.getNumberRange());
		reportScheduleData.setActivites(filterData.getActivities());
		reportScheduleData.setPositionFilter(filterData.getPositionFilter());
		reportScheduleData.setDepartmentFilter(filterData.getDepartmentFilter());
		reportScheduleData.setTemplateId(filterData.getReportTemplateId());
		return reportScheduleData;
	}

	public String getReportScheduledCriteria(ReportScheduleData reportScheduleData) {
		ReportManager reportManager = new ReportManager();
		String showCriteria = null;
		String reportType = "";
		String reportFormat = "";
		String minExp = "";
		String maxExp = "";
		String dateRange = "";
		String[] sourceIds = null;
		String[] sourceCategoryIds = null;
		String sourceName = "";
		String sourceCategory = "";
		String departmentName = "";
		String positionName = "";
		String costReportTypeLabel = "";
		String fromMonth = "";
		String toMonth = "";
		String fromYear = "";
		String toYear = "";
		String stepName = "";
		String reportTemplateId = "";

		LoginManager loginManager = new LoginManager();
		String[] userIds = null;
		String userNames = "";
		String[] interviewerIds = null;
		String interviewerNames = "";
		LoginData loginData = new LoginData();

		ApplicantManager applicantManager = new ApplicantManager();
		String[] applicantIds = null;
		String applicantNames = "";
		ApplicantData applicantData = new ApplicantData();

		String[] degreeIds = null;
		String degreeNames = "";

		String orderByLabel = "";

		String activityLabel = "";
		String stagesLabel = "";

		String[] stagesIds = null;
		String numberRange = reportScheduleData.getNumberRange();

		try {

			//
			if (!Utils.isBlankOrNull(reportScheduleData.getFromMonth())) {
				fromMonth = "From " + reportScheduleData.getFromMonth();
			}
			if (!Utils.isBlankOrNull(reportScheduleData.getFromYear())) {
				fromYear = reportScheduleData.getFromYear() + " to ";
			}
			if (!Utils.isBlankOrNull(reportScheduleData.getToMonth())) {
				toMonth = "to " + reportScheduleData.getToMonth();
			}
			if (!Utils.isBlankOrNull(reportScheduleData.getToYear())) {
				toYear = reportScheduleData.getToYear() + " to ";
			}

			if (!Utils.isBlankOrNull(reportScheduleData.getMinExp())) {
				minExp = "Exp " + reportScheduleData.getMinExp() + " to ";
			}

			if (!Utils.isBlankOrNull(reportScheduleData.getMaxExp())) {
				maxExp = reportScheduleData.getMaxExp() + " yrs | ";
			}
			// Criteria for Date Range
			if (!Utils.isBlankOrNull(reportScheduleData.getDateRange())) {				
				if( !Utils.isBlankOrNull(numberRange) && 
						(reportScheduleData.getDateRange().equals(ReportConstants.NEXT_N_DAYS)||
						reportScheduleData.getDateRange().equals(ReportConstants.NEXT_N_WEEKS)) ){					
					dateRange = "Date Range:-" + ReportConstants.mapDateRangeConstant.get(reportScheduleData.getDateRange()).replace("N ",numberRange+" " ) + " | ";
				}else{
					dateRange = "Date Range:-" + ReportConstants.mapDateRangeConstant.get(reportScheduleData.getDateRange()) + " | ";
				}
			}

			// Criteria for Source Name
			if (!Utils.isBlankOrNull(reportScheduleData.getSourceId()) && !reportScheduleData.getSourceId().equals("-1")) {
				sourceName = "Source:-";
				sourceIds = reportScheduleData.getSourceId().split(",");
				for(String sourceId : sourceIds) {
					sourceName += CommonUtils.getSourceName(sourceId) + ", ";
				}
				sourceName = sourceName.substring(0, sourceName.length()-2) + " | ";				
			}
			// Criteria for Source Category
			if (!Utils.isBlankOrNull(reportScheduleData.getSourceCategoryId()) 
					&& !reportScheduleData.getSourceCategoryId().equals("-1")) {
				sourceCategory = "Source Category:-";
				sourceCategoryIds = reportScheduleData.getSourceCategoryId().split(",");
				for(String sourceCategoryId : sourceCategoryIds) {
					sourceCategory += CommonUtils.getSourceTypeName(sourceCategoryId) + ", ";
				}
				sourceCategory = sourceCategory.substring(0, sourceCategory.length()-2) + " | ";				
			}
			// Criteria for Department Name
			if(Utils.isBlankOrNull(reportScheduleData.getDepartmentFilter())) {
				if (!Utils.isBlankOrNull(reportScheduleData.getDepartmentId())) {
					StringBuffer sb =  new StringBuffer();
					PositionManager positionManager = new PositionManager();
					sb.append(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) + ":-");
					String[] departmentIds = reportScheduleData.getDepartmentId().split(",");
					for(String departmentId : departmentIds) {
						sb.append(positionManager.getDeptName(departmentId));
						sb.append(",");
					}
					sb.deleteCharAt(sb.length()-1) ;
					sb.append(" | ");
					departmentName=sb.toString();
				}
				if (!Utils.isBlankOrNull(reportScheduleData.getSubDepartmentId())) {
					PositionManager positionManager = new PositionManager();
					departmentName += GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2) + ":-" + positionManager.getDeptName(reportScheduleData.getSubDepartmentId()) + " | ";
				}
				if (!Utils.isBlankOrNull(reportScheduleData.getSubSubDepartmentId())) {
					PositionManager positionManager = new PositionManager();
					departmentName += GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3) + ":-" + positionManager.getDeptName(reportScheduleData.getSubSubDepartmentId()) + " | ";
				}
			} else {
				departmentName = "Department:-";
				StringBuffer departmentFilterText = new StringBuffer();
				PositionManager positionManager = new PositionManager();
				if (ReportConstants.FILTER_ALL_DEPARTMENTS.equalsIgnoreCase(reportScheduleData.getDepartmentFilter())) {
					departmentFilterText.append("All Departments");
				} else if (ReportConstants.FILTER_SPECIFIC_DEPARTMENT.equalsIgnoreCase(reportScheduleData.getDepartmentFilter())) {
					String[] departmentIds = reportScheduleData.getDepartmentId().split(",");
					for(String departmentId : departmentIds) {
						if(!Utils.isBlankOrNull(departmentFilterText.toString())) {
							departmentFilterText.append(",");
						}				
						departmentFilterText.append(positionManager.getDeptName(departmentId));
					}			
				}				
				departmentName += departmentFilterText.toString() + " | ";
			}

			// Criteria for Postion Name
			if(Utils.isBlankOrNull(reportScheduleData.getPositionFilter())) {
				if (!Utils.isBlankOrNull(reportScheduleData.getPositionId())) {
					StringBuffer sb =  new StringBuffer();
					PositionManager positionManager = new PositionManager();
					sb.append("Position:-");
					String[] positionIds = reportScheduleData.getPositionId().split(",");
					for(String positionId : positionIds) {
						sb.append(positionManager.getPositionName(positionId));
						sb.append(",");
					}
					sb.deleteCharAt(sb.length()-1) ;
					sb.append(" | ");
					positionName=sb.toString();
				} else {
					if (Utils.isBlankOrNull(reportScheduleData.getFilterId()) || reportScheduleData.getFilterId().equalsIgnoreCase("-1")) {
						positionName = "";
					} else if (reportScheduleData.getFilterId().equalsIgnoreCase(ReportConstants.FILTER_ALL_POSITIONS)) {
						positionName = "All Positions | ";
					} else if (reportScheduleData.getFilterId().equalsIgnoreCase(ReportConstants.FILTER_OPEN_POSITIONS)) {
						positionName = "Open Positions | ";
					}
				}
			} else {
				positionName = "Position:-";
				StringBuffer positionFilterText = new StringBuffer();
				PositionManager positionManager = new PositionManager();
				if (ReportConstants.FILTER_ALL_POSITIONS.equalsIgnoreCase(reportScheduleData.getPositionFilter())) {
					positionFilterText.append("All Positions");
				} else if (ReportConstants.FILTER_OPEN_POSITIONS.equalsIgnoreCase(reportScheduleData.getPositionFilter())) {
					positionFilterText.append("Open Positions");
				} else if (ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS.equalsIgnoreCase(reportScheduleData.getPositionFilter())) {
					positionFilterText.append("Open and Onhold Positions");
				} else if (ReportConstants.FILTER_SPECIFIC_POSITION.equalsIgnoreCase(reportScheduleData.getPositionFilter())) {
					String[] positionIds = reportScheduleData.getPositionId().split(",");
					for(String positionId : positionIds) {
						if(!Utils.isBlankOrNull(positionFilterText.toString())) {
							positionFilterText.append(",");
						}
						positionFilterText.append(positionManager.getPositionName(positionId));
					}			
				}
				positionName += positionFilterText.toString() + " | ";
			}
			// Criteria for Action Taken By Name
			if (!Utils.isBlankOrNull(reportScheduleData.getActionId())) {
				if (reportScheduleData.getActionId().equals(ReportConstants.FILTER_IMPORTED_BY)) {
					userNames = "Imported By:-";
				} else if (reportScheduleData.getActionId().equals(ReportConstants.FILTER_MOVED_BY)) {
					userNames = "Moved By:-";
				} else if (reportScheduleData.getActionId().equals(ReportConstants.FILTER_REJECTED_BY)) {
					userNames = "Rejected By:-";
				}
			}
			
			if (!Utils.isBlankOrNull(reportScheduleData.getUsers())) {
				if(reportScheduleData.getReportId().equals("27") || reportScheduleData.getReportId().equals("28")){
					userNames ="Recruiters:- ";
				}else{
					userNames = "Users:- ";
				}
				userIds = reportScheduleData.getUsers().split(",");
				for (int i = 0; i < userIds.length; i++) {
					loginData = loginManager.getUser(userIds[i]);
					if (i == (userIds.length - 1)) {
						userNames += loginData.getName();
					} else {
						userNames += loginData.getName() + ", ";
					}
				}
				userNames += " | ";
			}else if(reportScheduleData.getReportId().equals("27") || reportScheduleData.getReportId().equals("28")){
				userNames +=" Recruiters:- All Recruiters | ";
			}

			if (!Utils.isBlankOrNull(reportScheduleData.getApplicants())) {
				applicantNames = "Applicants:- ";
				applicantIds = reportScheduleData.getApplicants().split(",");
				for (int j = 0; j < applicantIds.length; j++) {
					applicantData = applicantManager.getApplicantData(applicantIds[j]);
					if (j == (applicantIds.length - 1)) {
						applicantNames += applicantData.getApplicantName();
					} else {
						applicantNames += applicantData.getApplicantName() + ", ";
					}
				}
			}

			if (!Utils.isBlankOrNull(reportScheduleData.getInterviewers())) {
				interviewerNames = "Interviewers:- ";
				interviewerIds = reportScheduleData.getInterviewers().split(",");
				for (int k = 0; k < interviewerIds.length; k++) {
					loginData = loginManager.getUser(interviewerIds[k]);
					if (k == (interviewerIds.length - 1)) {
						interviewerNames += loginData.getName();
					} else {
						interviewerNames += loginData.getName() + ", ";
					}
				}
				interviewerNames += " | ";
			}

			if (!Utils.isBlankOrNull(reportScheduleData.getDegreeIds())) {
				degreeNames = "Degree:- ";
				degreeIds = reportScheduleData.getDegreeIds().split(",");
				for (int l = 0; l < degreeIds.length; l++) {
					if (l == (degreeIds.length - 1)) {
						degreeNames += CommonUtils.getDegreeName(degreeIds[l]);
					} else {
						degreeNames += CommonUtils.getDegreeName(degreeIds[l]) + ",";
					}
				}
				degreeNames += "|";
			}

			// Criteria for Recrutitment Cost Report Type
			if (!Utils.isBlankOrNull(reportScheduleData.getCostReportType())) {
				costReportTypeLabel = "Display:-" + reportScheduleData.getCostReportType() + " | ";
			} else {
				costReportTypeLabel = "";
			}
			// Criteria for Order By
			if (!Utils.isBlankOrNull(reportScheduleData.getOrderBy())) {
				orderByLabel = "Order By:-" + ReportConstants.mapSortByConstant.get(reportScheduleData.getOrderBy()) + " | ";
			} else {
				orderByLabel = "";
			}

			// Criteria for Stages
			if (reportScheduleData.getReportId().equals("16") || reportScheduleData.getReportId().equals("17")) {
				if (!Utils.isBlankOrNull(reportScheduleData.getStages())) {
					stagesIds = reportScheduleData.getStages().split(",");
					stagesLabel = "Stages:-";
					for (int m = 0; m < stagesIds.length; m++) {
						if (stagesIds[m].trim().equals("1")) {
							stagesLabel += "Select, ";
						} else if (stagesIds[m].trim().equals("2")) {
							stagesLabel += "Hire, ";
						}
					}
					stagesLabel = stagesLabel.subSequence(0, stagesLabel.length()-2) + " | ";
				} else {
					stagesLabel = "Stages:-All |";
				}
			}
			
			//Criteria for Steps
			if (reportScheduleData.getReportId().equals("27")){
				if(!Utils.isBlankOrNull(reportScheduleData.getStepIds())){
					String[] steps =reportScheduleData.getStepIds().split(",");
					StepManager stepManager = new StepManager();
					stepName+="Steps:-";
					for(String step: steps){
						if(!stepName.equals("Steps:-")){
							stepName+=", ";
						}
						stepName += stepManager.getStepNameForStepId(step);
					}
					stepName+=" | ";
				} else {
					stepName=" Steps:- All Steps |";
				}	
			}
			
			// Criteria for Activities
			if (reportScheduleData.getReportId().equals("16")) {
				if (!Utils.isBlankOrNull(reportScheduleData.getActivities())) {
					String[] activityIds = reportScheduleData.getActivities().split(",");
					activityLabel = "Activities:-";
					for (String activityId : activityIds) {
						String activityText = "";
						if(activityId.equalsIgnoreCase("4")) {
							activityText = "Feedback";
						} else {
							activityText = SelectionProcessConstants.INTERACTION_TYPES.get(activityId);
						}
						activityLabel += activityText + ", ";
					}
					activityLabel = activityLabel.substring(0, activityLabel.length()-2) + " | ";
				} else {
					activityLabel = "Activities:-All |";
				}
			}
			// Criteria for Report Type
			if (!Utils.isBlankOrNull(reportScheduleData.getReportType())) {
				reportType = ReportConstants.mapReportTypeConstant.get(reportScheduleData.getReportType()) + " Report | ";
			}

			// Criteria for Report Format
			if (!Utils.isBlankOrNull(reportScheduleData.getReportFormat())) {
				reportFormat = ReportConstants.mapReportFormatConstant.get(reportScheduleData.getReportFormat()) + " format ";
			}
			

			showCriteria = stepName + stagesLabel + activityLabel + orderByLabel + degreeNames + interviewerNames + applicantNames 
					+ userNames + positionName + sourceCategory + sourceName + departmentName + minExp 
					+ maxExp + dateRange + costReportTypeLabel + reportType + reportFormat;

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting Schedule Report Criteria info", e);
		}
		return showCriteria;
	}

	public ReportScheduleData getReportSchedule(String scheduleId) {
		DBPreparedQuery dq = null;
		ReportScheduleData reportScheduleData = null;
		try {
			dq = new DBPreparedQuery("dReportSchedulerManager_ViewScheduledReport");
			dq.setId(1, scheduleId);
			reportScheduleData = (ReportScheduleData) dq.getSingleObjectResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while add/modify getting Schedule Report info", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		
		return reportScheduleData;
	}

	public String getCommaSeparatedEmailIds(String scheduleId) {
		DBPreparedQuery dq = null;
		String commaSeparatedemailIds = null;
		try {
			dq = new DBPreparedQuery("dReportSchedulerManager_FetchEmailIdForScheduledToUser");
			dq.setId(1, scheduleId);
			commaSeparatedemailIds = dq.getStringResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting Scheduled To Email Id", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return commaSeparatedemailIds;
	}

	public String createReportSchedule(ReportScheduleData reportScheduleData, String userId, FilterData filterData, ArrayList<String> toUserIds) throws SQLException {		
		DBPreparedQuery dq = null;
		String scheduleId = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dReportSchedulerManager_AddNewScheduleReport", tran);
			dq.setString(1, reportScheduleData.getReportId());
			dq.setString(2, reportScheduleData.getFrequency());
			dq.setTimestamp(3, new Timestamp(Utils.convertToDate(reportScheduleData.getStringStartDate(), "dd/MM/yyyy hh:mm a").getTime()));
			if (Utils.isBlankOrNull(reportScheduleData.getEveryday())) {
				dq.setString(4, null);
			} else {
				dq.setString(4, reportScheduleData.getEveryday());
			}
			if (Utils.isBlankOrNull(reportScheduleData.getWeekdays())) {
				dq.setString(5, null);
			} else {
				dq.setString(5, reportScheduleData.getWeekdays());
			}
			if (Utils.isBlankOrNull(reportScheduleData.getDayOfWeek())) {
				dq.setString(6, null);
			} else {
				dq.setString(6, reportScheduleData.getDayOfWeek());
			}
			if (Utils.isBlankOrNull(reportScheduleData.getDayOfMonth())) {
				dq.setString(7, null);
			} else {
				dq.setString(7, reportScheduleData.getDayOfMonth());
			}
			dq.setTimestamp(8, reportScheduleData.getScheduleTime());
			dq.setString(9, reportScheduleData.getScheduleStatus());
			dq.setString(10, reportScheduleData.getEmailIds());
			dq.setInt(11, Integer.parseInt(userId));

			if (Utils.isBlankOrNull(filterData.getDepartmentId())) {
				dq.setString(12, null);
			} else {
				dq.setString(12, filterData.getDepartmentId());
			}
			if (Utils.isBlankOrNull(filterData.getPositionId())) {
				dq.setString(13, null);
			} else {
				dq.setString(13, filterData.getPositionId());
			}
			if (Utils.isBlankOrNull(filterData.getFilterId())) {
				dq.setId(14, null);
			} else {
				dq.setId(14, filterData.getFilterId());
			}
			if (Utils.isBlankOrNull(filterData.getSourceId())) {
				dq.setString(15, null);
			} else {
				dq.setString(15, filterData.getSourceId());
			}
			
			if (filterData.getActionId()==null) {
				dq.setString(16, null);
			} else {
				dq.setString(16, filterData.getActionId());
			}
			
			if (Utils.isBlankOrNull(filterData.getDegreeIds())) {
				dq.setString(17, null);
			} else {
				dq.setString(17, filterData.getDegreeIds());
			}
			if (Utils.isBlankOrNull(filterData.getStepIds())) {
				dq.setString(18, null);
			} else {
				dq.setString(18, filterData.getStepIds());
			}
			if (Utils.isBlankOrNull(filterData.getReportType())) {
				dq.setString(19, null);
			} else {
				dq.setString(19, filterData.getReportType());
			}
			if (Utils.isBlankOrNull(filterData.getReportFormat())) {
				dq.setString(20, null);
			} else {
				dq.setString(20, filterData.getReportFormat());
			}
			if (Utils.isBlankOrNull(filterData.getApplicants())) {
				dq.setString(21, null);
			} else {
				dq.setString(21, filterData.getApplicants());
			}
			if (Utils.isBlankOrNull(filterData.getUsers())) {
				dq.setString(22, null);
			} else {
				dq.setString(22, filterData.getUsers());
			}
			if (Utils.isBlankOrNull(filterData.getInterviewers())) {
				dq.setString(23, null);
			} else {
				dq.setString(23, filterData.getInterviewers());
			}
			if (Utils.isBlankOrNull(filterData.getFromDate())) {
				dq.setString(24, null);
			} else {
				//dq.setDate(24, Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat));
				dq.setString(24, filterData.getFromDate());
			}
			if (Utils.isBlankOrNull(filterData.getToDate())) {
				dq.setString(25, null);
			} else {
				//dq.setDate(25, Utils.convertToSQLDate(filterData.getToDate(), Utils.regEUDateFormat));
				dq.setString(25,filterData.getToDate());
			}
			if (Utils.isBlankOrNull(filterData.getFromMonth())) {
				dq.setString(26, null);
			} else {
				dq.setString(26, filterData.getFromMonth());
			}
			if (Utils.isBlankOrNull(filterData.getToMonth())) {
				dq.setString(27, null);
			} else {
				dq.setString(27, filterData.getToMonth());
			}
			if (Utils.isBlankOrNull(filterData.getFromYear())) {
				dq.setString(28, null);
			} else {
				dq.setString(28, filterData.getFromYear());
			}
			if (Utils.isBlankOrNull(filterData.getToYear())) {
				dq.setString(29, null);
			} else {
				dq.setString(29, filterData.getToYear());
			}
			if (Utils.isBlankOrNull(filterData.getDateRange())) {
				dq.setString(30, null);
			} else {
				dq.setString(30, filterData.getDateRange());
			}
			if (Utils.isBlankOrNull(filterData.getMaxExp())) {
				dq.setString(31, null);
			} else {
				dq.setString(31, filterData.getMaxExp());
			}
			if (Utils.isBlankOrNull(filterData.getMinExp())) {
				dq.setString(32, null);
			} else {
				dq.setString(32, filterData.getMinExp());
			}
			if (Utils.isBlankOrNull(filterData.getStages())) {
				dq.setString(33, null);
			} else {
				dq.setString(33, filterData.getStages());
			}
			if (Utils.isBlankOrNull(filterData.getOrderBy())) {
				dq.setString(34, null);
			} else {
				dq.setString(34, filterData.getOrderBy());
			}
			if (Utils.isBlankOrNull(filterData.getRecruitmentCostReportType())) {
				dq.setString(35, null);
			} else {
				dq.setString(35, filterData.getRecruitmentCostReportType());
			}
			if (Utils.isBlankOrNull(reportScheduleData.getMailSubject())) {
				dq.setString(36, null);
			} else {
				dq.setString(36, reportScheduleData.getMailSubject());
			}
			String mailBodyFormat = null;
			if (Utils.isBlankOrNull(reportScheduleData.getMailBody())) {
				dq.setString(37, null);
			} else {
				mailBodyFormat = reportScheduleData.getMailBody();
				dq.setString(37, mailBodyFormat);
			}
			if (Utils.isBlankOrNull(filterData.getNumberRange())) {
				dq.setString(38, null);
			} else {				
				dq.setString(38, filterData.getNumberRange());
			}
			if (Utils.isBlankOrNull(filterData.getSubDepartmentId())) {
				dq.setId(39, null);
			} else {
				dq.setId(39, filterData.getSubDepartmentId());
			}
			if (Utils.isBlankOrNull(filterData.getSubSubDepartmentId())) {
				dq.setId(40, null);
			} else {
				dq.setId(40, filterData.getSubSubDepartmentId());
			}
			if (Utils.isBlankOrNull(filterData.getSourceCategoryId())) {
				dq.setString(41, null);
			} else {
				dq.setString(41, filterData.getSourceCategoryId());
			}
			if (Utils.isBlankOrNull(filterData.getActivities())) {
				dq.setString(42, null);
			} else {
				dq.setString(42, filterData.getActivities());
			}
			if (Utils.isBlankOrNull(filterData.getPositionFilter())) {
				dq.setString(43, null);
			} else {
				dq.setString(43, filterData.getPositionFilter());
			}
			if (Utils.isBlankOrNull(filterData.getDepartmentFilter())) {
				dq.setString(44, null);
			} else {
				dq.setString(44, filterData.getDepartmentFilter());
			}
			if (Utils.isBlankOrNull(filterData.getReportTemplateId())) {
				dq.setString(45, null);
			} else {
				dq.setString(45, filterData.getReportTemplateId());
			}
			
			dq.execute();
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			scheduleId = dq.getIdResult();

			for (int i = 0; i < toUserIds.size(); i++) {
				dq = new DBPreparedQuery("dReportSchedulerManager_AddNewScheduleReportUsers", tran);
				dq.setString(1, scheduleId);
				dq.setString(2, toUserIds.get(i));
				dq.execute();
			}
			tran.commit();
			reportScheduleData.setScheduleId(Integer.parseInt(scheduleId));
			ReportScheduler.addTrigger(reportScheduleData);

		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Scheduling Reports", e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return scheduleId;
	}

	public void updateReportSchedule(ReportScheduleData reportScheduleData, String userId, String scheduleId, ArrayList<String> toUserIds) throws SQLException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dReportSchedulerManager_UpdateScheduleReportDetails", tran);
			dq.setString(1, reportScheduleData.getReportId());
			dq.setString(2, reportScheduleData.getFrequency());
			dq.setTimestamp(3, new Timestamp(Utils.convertToDate(reportScheduleData.getStringStartDate(), "dd/MM/yyyy hh:mm a").getTime()));
			if (Utils.isBlankOrNull(reportScheduleData.getEveryday())) {
				dq.setString(4, null);
			} else {
				dq.setString(4, reportScheduleData.getEveryday());
			}
			if (Utils.isBlankOrNull(reportScheduleData.getWeekdays())) {
				dq.setString(5, null);
			} else {
				dq.setString(5, reportScheduleData.getWeekdays());
			}
			if (Utils.isBlankOrNull(reportScheduleData.getDayOfWeek())) {
				dq.setString(6, null);
			} else {
				dq.setString(6, reportScheduleData.getDayOfWeek());
			}
			if (Utils.isBlankOrNull(reportScheduleData.getDayOfMonth())) {
				dq.setString(7, null);
			} else {
				dq.setString(7, reportScheduleData.getDayOfMonth());
			}
			dq.setTimestamp(8, reportScheduleData.getScheduleTime());
			dq.setString(9, reportScheduleData.getScheduleStatus());
			dq.setString(10, reportScheduleData.getEmailIds());
			if (Utils.isBlankOrNull(reportScheduleData.getMailSubject())) {
				dq.setString(11, null);
			} else {
				dq.setString(11, reportScheduleData.getMailSubject());
			}
			String mailBodyFormat = null;
			if (Utils.isBlankOrNull(reportScheduleData.getMailBody())) {
				dq.setString(12, null);
			} else {
				mailBodyFormat = reportScheduleData.getMailBody();
				dq.setString(12, mailBodyFormat);
			}
			if(reportScheduleData.getTemplateId() != null){
				dq.setString(13,reportScheduleData.getTemplateId());	
			}else{
				dq.setString(13,null);
			}
			
			dq.setInt(14, Integer.parseInt(scheduleId));
			dq.execute();

			dq = new DBPreparedQuery("dReportSchedulerManager_DeleteScheduledReportUsers", tran);
			dq.setId(1, scheduleId);
			dq.execute();

			for (int i = 0; i < toUserIds.size(); i++) {
				dq = new DBPreparedQuery("dReportSchedulerManager_AddNewScheduleReportUsers", tran);
				dq.setString(1, scheduleId);
				dq.setString(2, toUserIds.get(i));
				dq.execute();
			}
			tran.commit();

			ReportScheduler.updateTrigger(reportScheduleData, scheduleId);

		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Updating Schedule Reports", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public void removeScheduledReport(String scheduleId) throws SQLException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dReportSchedulerManager_DeleteScheduledReport", tran);
			dq.setId(1, scheduleId);
			dq.execute();
			dq = new DBPreparedQuery("dReportSchedulerManager_DeleteScheduledReportUsers", tran);
			dq.setId(1, scheduleId);
			dq.execute();
			tran.commit();
			ReportScheduler.deleteTrigger(scheduleId);

		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Deleting Scheduled Report", e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	public ArrayList<SimpleDataObject> getConflicts(ArrayList<String> receivers, ArrayList<String> positions) throws SQLException{
		ArrayList<SimpleDataObject> conflicts = null;
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		
		try {			
			String[] dynParams = new String[2];
			dynParams[0] = "";
			dynParams[1] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			for (int i = 0; i < positions.size(); i++) {
				dynParams[0] += (i > 0) ? ",?" : "?";
				dynamicContent.add(positions.get(i));
			}
			if(!Utils.isBlankOrNull(dynParams[0])){
				dynParams[0] = " AND tpsu.position_id in (" + dynParams[0] + ") ";
			}
			
			for (int i = 0; i < receivers.size(); i++) {
				dynParams[1] += (i > 0) ? ",?" : "?";
				dynamicContent.add(receivers.get(i));
			}
			dq = new DBPreparedQuery("dReportSchedulerManager_GetPositionConflicts", dynParams );
			
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setInt(cnt, positions.size());		
			conflicts = dq.getResult();						
			for (int i = 0; conflicts!=null && i < conflicts.size(); i++) {
				String[] dynParams1 = new String[1];
				dynParams1[0] = "";
				ArrayList<String> dynamicContent1 = new ArrayList<String>();
				for (int k = 0; k < positions.size(); k++) {
					dynParams1[0] += (k > 0) ? ",?" : "?";
					dynamicContent1.add(positions.get(k));
				}
				dq = new DBPreparedQuery("dReportSchedulerManager_GetPositionWithNoRightsToUser", dynParams1);
				
				int cnt1 = 1;
				for (int j = 0; j < dynamicContent1.size(); j++) {
					dq.setString(cnt1++, dynamicContent1.get(j));
				}
				dq.setId(cnt1, conflicts.get(i).getId("userId"));
				dq.setId(cnt1+1, conflicts.get(i).getId("userId"));
				ArrayList<String> pos = dq.getResult();				
				conflicts.get(i).setAttribute("positions", pos);				
			}
			//tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error", e);
			//tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				//dq.releaseTransaction(tran);
				dq.releaseConnection();
			}
		}
		return conflicts;
	}
}
