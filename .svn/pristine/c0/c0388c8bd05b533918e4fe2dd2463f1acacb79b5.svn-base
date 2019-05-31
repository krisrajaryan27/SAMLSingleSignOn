/**
 * 
 */
package com.talentPool.dashboard.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.calendar.CalendarConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.dashboard.constants.DashboardConstants;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.utils.PositionUtils;
import com.talentPool.positions.utils.PositionWithRightsClause;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.todo.constants.ToDoConstants;
import com.talentPool.user.MessageConstants;
import com.talentPool.user.UserConstants;
import com.talentPool.user.constants.DataViewConstants;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.utils.DataViewUtils;

/**
 * @author shivprasad
 * 
 */
@Component
public class DashboardManager {
	public int getTotalSentMessagesForUser(String userId, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		int totalMsgs = 0;
		try {
			String[] dynParams = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParams[0] = " ";
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParams[0] += " AND ta.is_confidential = ? ";
				dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			dq = new DBPreparedQuery("dDashboardManager_GetTotalSentMessagesForUser",dynParams);
			dq.setString(1, userId);
			dq.setInt(2, MessageConstants.MESSAGE_RECEIVED_AS_TO);
			dq.setString(3, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_MESSAGES));
			int cnt = 4;
			for (int k = 0; k < dynamicContent.size(); k++) {
				dq.setString(cnt++, dynamicContent.get(k));
			}
			totalMsgs = dq.getIntResult();
		} catch (Exception e) {
			totalMsgs = 0;
			TPLogger.getLogger().error("Error while querying sent messages", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return totalMsgs;
	}

	public String getXmlForMessages(ArrayList Messages) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (Messages != null) {
				for (int i = 0; i < Messages.size(); i++) {
					SimpleDataObject sDo = (SimpleDataObject) Messages.get(i);

					String messageId = sDo.getString("messageId");
					String applicantId = sDo.getString("applicantId");
					// String originalResumePath =
					// sDo.getString("originalResumePath");
					String messageFrom = sDo.getString("messageFrom");
					String applicantName = sDo.getString("applicantName");
					String messageText = sDo.getString("messageText");

					messageFrom = (messageFrom.length() > 15) ? messageFrom.substring(0, 15) + "..." : messageFrom;
					messageText = (messageText.length() > 35) ? messageText.substring(0, 35) + "..." : messageText;

					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", messageId);
					wr.startElement("", "row", "", at);

					// at = new AttributesImpl();
					// at.addAttribute("", "name", "", "", "resumePath");
					// wr.startElement("", "userdata", "", at);
					// wr.characters(originalResumePath);
					// wr.endElement("userdata");

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "applicantName");
					wr.startElement("", "userdata", "", at);
					wr.characters(applicantName);
					wr.endElement("userdata");

					String date = "";
					String fullDate = "";
					if (sDo.getDate("messageDate") != null) {
						date = DateUtils.getSystemDateFormat(sDo.getDate("messageDate"));
						date = Utils.isBlankOrNull(date)?"UNKNOWN":date;
						fullDate = DateUtils.getDateFormatForGridSorting(sDo.getDate("messageDate"));
					}
					wr.startElement("cell");
					wr.characters("<a href=\"#\" onclick=\"onClickDeleteMessage(" + messageId + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>");
					wr.endElement("cell");
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(messageFrom));
					wr.endElement("cell");
					wr.startElement("cell");
					wr.characters(date);
					wr.endElement("cell");
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(messageText) + "^javascript:onClickMessage(" + messageId + ");^_self");
					// wr.characters("<a href=\"#\" onclick=\"onClickMessage(" +
					// messageId + ")\">" +wr.doubleEscape(messageText) +
					// "</a>");
					wr.endElement("cell");
					applicantName = (applicantName.length() > 20) ? applicantName.substring(0, 20) + "..." : applicantName;
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(applicantName) + "^javascript:onClickApplicant(" + applicantId + ");^_self");
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(fullDate);
					wr.endElement("cell");

					wr.endElement("row");
				}
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Failed to get XML for Messages", e);
		}
		return sWr.getBuffer().toString();
	}

	public String getXmlForSentMessages(ArrayList Messages) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (Messages != null) {
				for (int i = 0; i < Messages.size(); i++) {
					SimpleDataObject sDo = (SimpleDataObject) Messages.get(i);

					String messageId = sDo.getString("messageId");
					String applicantId = sDo.getString("applicantId");
					// String originalResumePath =
					// sDo.getString("originalResumePath");
					String messageTo = sDo.getString("messageTo");
					String applicantName = sDo.getString("applicantName");
					String messageText = sDo.getString("messageText");

					messageTo = (messageTo.length() > 15) ? messageTo.substring(0, 15) + "..." : messageTo;
					messageText = (messageText.length() > 35) ? messageText.substring(0, 35) + "..." : messageText;

					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", messageId);
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "applicantName");
					wr.startElement("", "userdata", "", at);
					wr.characters(applicantName);
					wr.endElement("userdata");

					String date = "";
					String fullDate = "";
					if (sDo.getDate("messageDate") != null) {
						date = DateUtils.getSystemDateFormat(sDo.getDate("messageDate"));
						date = Utils.isBlankOrNull(date)?"UNKNOWN":date;
						fullDate = DateUtils.getDateFormatForGridSorting(sDo.getDate("messageDate"));
					}
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(messageTo));
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(date);
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(messageText) + "^javascript:onClickSentMessage(" + messageId + ");^_self");
					wr.endElement("cell");

					applicantName = (applicantName.length() > 20) ? applicantName.substring(0, 20) + "..." : applicantName;
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(applicantName) + "^javascript:onClickApplicant(" + applicantId + ");^_self");
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(fullDate);
					wr.endElement("cell");

					wr.endElement("row");
				}
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Failed to get XML for Messages", e);
		}
		return sWr.getBuffer().toString();
	}

	public ArrayList getUpcomingEvents(String userId, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		ArrayList upcomingEvents = new ArrayList();
		String[] dynParams = new String[2];
		List<String> dynamicContents = new ArrayList<String>();
		try {
			dynParams[0] = " ";
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParams[0] += " AND ta.is_confidential = ? ";
				dynamicContents.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			if (GlobalConstants.ENABLED.equalsIgnoreCase(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_REMINDER))) {
				dynParams[1] = " union " + " ( select ta.applicant_id, ta.applicant_name, ta.applicant_original_resume_path, '' as position_id, '' as position_title, "
						+ " '' as position_step_id, tpr.reminder_desc as position_step_title, tpr.reminder_date as appointment_from_date, tpr.reminder_id as appointment_id, "
						+ " '' as status_message, ? as type " + " from tp_reminder tpr left join tp_applicants ta on (tpr.applicant_id = ta.applicant_id) " + " where tpr.user_id = ? " + " )";

				dynamicContents.add(CalendarConstants.CALENDAR_ITEM_REMINDER);
				dynamicContents.add(userId);
			} else {
				dynParams[1] = "";
			}
			dq = new DBPreparedQuery("dDashboardManager_GetUpcomingEvents", dynParams);
			dq.setString(1, CalendarConstants.CALENDAR_ITEM_APPOINTMENT);
			dq.setId(2, PositionConstants.STEP_ACTIVE);
			dq.setId(3, PositionConstants.POSITION_STATUS_OPENED);
			dq.setId(4, ApplicantConstants.APPLICANT_NOT_JOINED);
			dq.setInt(5, PositionConstants.NOT_RESPONSIBLE_FOR_SCHEDULING);
			/*
			 * removed the authorized condition because Decision Maker can also be an interviewer
			 * */
//			dq.setInt(6, PositionConstants.NOT_AUTHORIZED_TO_MOVE);
			dq.setId(6, userId);
			dq.setId(7, userId);
			dq.setInt(8, CalendarConstants.APPOINTMENT_STATUS_NOSHOW);
			int cnt = 9;
			for (int i = 0; i < dynamicContents.size(); i++) {
				dq.setString(cnt++, dynamicContents.get(i));
			}
			upcomingEvents = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("failed to get upcoming events", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return upcomingEvents;
	}

	public String getXMLForUpcomingEvents(ArrayList upcomingEvents) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");

			for (int i = 0; i < upcomingEvents.size(); i++) {
				SimpleDataObject sdo = (SimpleDataObject) upcomingEvents.get(i);

				String applicantId = sdo.getString("applicantId");
				if (Utils.isBlankOrNull(applicantId)) {
					applicantId = "0";
				}
				String applicantName = sdo.getString("applicantName");
				if (Utils.isBlankOrNull(applicantName)) {
					applicantName = "NA";
				}
				String positionTitle = sdo.getString("positionTitle");
				String stepTitle = sdo.getString("stepTitle");
				String status = sdo.getString("applicantStatus");
				if (Utils.isBlankOrNull(status)) {
					status = "NA";
				}
				String appointmentId = sdo.getString("appointmentId");
				String type = sdo.getString("type");

				Date pendingDate = sdo.getDate("appointmentDate");

				String date = "";
				String fullDate = "";
				if (pendingDate != null) {
					date = DateUtils.getSystemDateTimeFormat(pendingDate);
					date = Utils.isBlankOrNull(date)?"UNKNOWN":date;
					fullDate = DateUtils.getDateFormatForGridSorting(pendingDate);
				}

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + type + "_" + appointmentId);
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "applicantName");
				wr.startElement("", "userdata", "", at);
				wr.characters(applicantName);
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "appId");
				wr.startElement("", "userdata", "", at);
				wr.characters(appointmentId);
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "type");
				wr.startElement("", "userdata", "", at);
				wr.characters(type);
				wr.endElement("userdata");

				wr.startElement("cell");
				if (CalendarConstants.CALENDAR_ITEM_REMINDER.equalsIgnoreCase(type)) {
					wr.characters("<a href=\"#\" onclick=\"onClickDeleteReminder(" + appointmentId + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>");
				} else {
					wr.characters("");
				}
				wr.endElement("cell");
				String applicantLink = "<a href=\"#\" onclick=\"onClickApplicant('" +  applicantId + "');\" onmouseover=\"showAjaxTip(event,'" + applicantId + "')\" onmouseout=\"hideToolTip()\" >" + wr.doubleEscape(applicantName) + "</a>";
				applicantName = (applicantName.length() > 15) ? applicantName.substring(0, 15) + "..." : applicantName;

				wr.startElement("cell");
				if ("0".equalsIgnoreCase(applicantId)) {
					wr.characters(wr.doubleEscape(applicantName));
				} else {
					wr.characters(applicantLink);
				}
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(status));
				wr.endElement("cell");

				if (CalendarConstants.CALENDAR_ITEM_APPOINTMENT.equalsIgnoreCase(type)) {
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(stepTitle + " for " + positionTitle));
					wr.endElement("cell");
				} else {
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(stepTitle));
					wr.endElement("cell");
				}

				wr.startElement("cell");
				if (CalendarConstants.CALENDAR_ITEM_APPOINTMENT.equalsIgnoreCase(type)) {
					wr.characters(date + "^javascript:onClickAppointment(" + appointmentId + "," + applicantId + ");^_self");
				} else {
					wr.characters(date + "^javascript:onClickReminder(" + appointmentId + "," + applicantId + ");^_self");
				}
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(fullDate);
				wr.endElement("cell");

				wr.endElement("row");

			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("error while creating xml file for emails", e);
		}
		return sWr.getBuffer().toString();
	}

	/**
	 * @param positions
	 * @param permissionSet
	 * @param userId
	 * @return
	 */
	public String getXmlForPositionSummary(ArrayList<SimpleDataObject> positions, PermissionSet permissionSet,String userId) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			for (SimpleDataObject sdo : positions) {
				String positionId = sdo.getString("positionId");
				String positionCode = sdo.getString("positionCode");
				String positionTitle = sdo.getString("positionTitle");
				String noOfOpenings = sdo.getString("noOfPositions");
				int shortlistCandidates = sdo.getInt("shortlistCandidates");
				int selectCandidates = sdo.getInt("selectCandidates");
				//String candidates = sdo.getString("candidates");
				String candidates = ""+(shortlistCandidates+selectCandidates);
				String offered = sdo.getString("offered");
				String joined = sdo.getString("joined");
				String rejected = sdo.getString("rejected");
				String positionStatus = sdo.getString("positionStatus");
				String hireByDate = (sdo.getString("expiryDate") == null) ? "" : sdo.getString("expiryDate");
				
				HashMap<String, String> dataMap = PositionUtils.setPositionDynamicColumns(sdo);
				String dynamicColumn2 = Utils.getBlankIfNull(dataMap.get(DataViewUtils.getColumnMapping(DataViewConstants.KEY_COLUMN2, userId, UserConstants.DATA_VIEW_TYPE_DASHBOARD_POSITION_CONFIG)));
				String dynamicColumn1 = Utils.getBlankIfNull(dataMap.get(DataViewUtils.getColumnMapping(DataViewConstants.KEY_COLUMN1, userId, UserConstants.DATA_VIEW_TYPE_DASHBOARD_POSITION_CONFIG)));
		
				String positionPriority = sdo.getString("positionPriority");
				String ImgPositionPriority = "";
				String priorityLevel = "";
				String editPriority = "";
				if (!Utils.isBlankOrNull(positionPriority)) {
					if (positionPriority.equals(PositionConstants.POSITION_LEVEL_HIGH)) {
						ImgPositionPriority = "<img src=\"images/ico_priority_high.jpg\" border=0>";
						priorityLevel = "HIGH";
					} else if (positionPriority.equals(PositionConstants.POSITION_LEVEL_MEDIUM)) {
						ImgPositionPriority = "<img src=\"images/ico_priority_medium.jpg\" border=0>";
						priorityLevel = "MEDIUM";
					} else if (positionPriority.equals(PositionConstants.POSITION_LEVEL_LOW)) {
						ImgPositionPriority = "<img src=\"images/ico_priority_low.jpg\" border=0>";
						priorityLevel = "LOW";
					}
					if (permissionSet.isPERMISSION_POSITION_PRIORITY()) {
						editPriority = "<a href=\"#\" onclick=\"javascript:onClickPositionPriority(" + positionId + ");\" title=\"Position Priority\">" + ImgPositionPriority + "</a>";
					} else {
						editPriority = ImgPositionPriority;
					}
				} else {
					editPriority = "";
				}

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + positionId);
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "priorityLevel");
				wr.startElement("", "userdata", "", at);
				wr.characters(wr.doubleEscape(priorityLevel));
				wr.endElement("userdata");

				String position = positionTitle;
				if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
					position = positionCode;
				}
				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "positionTitle");
				wr.startElement("", "userdata", "", at);
				wr.characters(positionTitle+" "+TPLabels.getLabel("common.openingSquareBracket")+positionCode+TPLabels.getLabel("common.closingSquareBracket"));
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "dynamicColumn2");
				wr.startElement("", "userdata", "", at);
				wr.characters(wr.doubleEscape(dynamicColumn2));
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "dynamicColumn1");
				wr.startElement("", "userdata", "", at);
				wr.characters(wr.doubleEscape(dynamicColumn1));
				wr.endElement("userdata");
				
				wr.startElement("cell");
				wr.characters(editPriority);
				wr.endElement("cell");

				dynamicColumn1 = (dynamicColumn1.length() > 13) ? dynamicColumn1.substring(0, 11) + "..." : dynamicColumn1;				
				position = (position.length() > 23) ? position.substring(0, 21) + "..." : position;
				wr.startElement("cell");
				if (permissionSet.isPERMISSION_POSITION_DETAILS()) {
					wr.characters(wr.doubleEscape(position) + "^javascript:onClickPosition(" + positionId + ");^_self");
				} else {
					wr.characters(wr.doubleEscape(position));
				}
				wr.endElement("cell");
				
				wr.startElement("cell");
				wr.characters(wr.doubleEscape(dynamicColumn1));
				wr.endElement("cell");

				wr.startElement("cell");
				if (!Utils.isBlankOrNull(dynamicColumn2)) {
					dynamicColumn2 = (dynamicColumn2.length() > 17) ? dynamicColumn2.substring(0, 15) + "..." : dynamicColumn2;
					wr.characters(dynamicColumn2);
				} else {
					wr.characters(" ");
				}
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(noOfOpenings);
				wr.endElement("cell");

				wr.startElement("cell");
				if (candidates.equals("0") || !permissionSet.isPERMISSION_SELECT()) {
					wr.characters(candidates);
				} else {
					wr.characters("<a href=\"#\" onclick=\"javascript:onClickCandidates(" + positionId + ");\">" + candidates + "</a>");
				}
				wr.endElement("cell");

				wr.startElement("cell");
				if (offered.equals("0") || !permissionSet.isPERMISSION_HIRE()) {
					wr.characters(offered);
				} else {
					wr.characters("<a href=\"#\" onclick=\"javascript:onClickHired(" + positionId + ");\">" + offered + "</a>");
				}
				wr.endElement("cell");

				wr.startElement("cell");
				if (joined.equals("0") || !permissionSet.isPERMISSION_HIRE() || !permissionSet.isPERMISSION_SHOW_JOINED_CANDIDATES()) {
					wr.characters(joined);
				} else {
					wr.characters("<a href=\"#\" onclick=\"javascript:onClickJoined(" + positionId + ");\">" + joined + "</a>");
				}
				wr.endElement("cell");
				
				wr.startElement("cell");
				if (rejected.equals("0") || !permissionSet.isPERMISSION_VIEW_REJECTED_CANDIDATES()) {
					wr.characters(rejected);
				} else {
					wr.characters("<a href=\"#\" onclick=\"javascript:onClickRejected(" + positionId + ");\">" + rejected + "</a>");
				}
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(positionPriority);
				wr.endElement("cell");
				
				if(!Utils.isBlankOrNull(hireByDate) && PositionConstants.POSITION_STATUS_OPENED.equals(positionStatus)){
					Date dt = Utils.convertToDate(hireByDate, DateConstants.DB_DATE_TIME_PATTERN);
					if(dt.before(Calendar.getInstance().getTime())){
						positionStatus = PositionConstants.POSITION_STATUS_OVERDUE;
					}
				}
				wr.startElement("cell");
				wr.characters(positionStatus);
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}

	public ArrayList getMessagesForUser(String userId, int messageReadStatus, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		ArrayList msgs = new ArrayList();
		try {
			String[] dynParams = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParams[0] = " ";
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParams[0] += " AND ta.is_confidential = ? ";
				dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			dq = new DBPreparedQuery("dDashboardManager_GetMessagesForUser",dynParams);
			dq.setString(1, userId);
			dq.setInt(2, messageReadStatus);
			int cnt = 3;
			for (int k = 0; k < dynamicContent.size(); k++) {
				dq.setString(cnt++, dynamicContent.get(k));
			}
			msgs = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return msgs;
	}

	public ArrayList getSentMessagesForUser(String userId, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		ArrayList msgs = new ArrayList();
		try {
			String[] dynParams = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParams[0] = " ";
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParams[0] += " AND ta.is_confidential = ? ";
				dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			dq = new DBPreparedQuery("dDashboardManager_GetSentMessagesForUser",dynParams);
			dq.setString(1, userId);
			dq.setInt(2, MessageConstants.MESSAGE_RECEIVED_AS_TO);
			dq.setString(3, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_MESSAGES));
			int cnt = 4;
			for (int k = 0; k < dynamicContent.size(); k++) {
				dq.setString(cnt++, dynamicContent.get(k));
			}
			msgs = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return msgs;
	}

	/**
	 * @param userId
	 * @param positionGrouping
	 * @param positionGroupItemId
	 * @param permissionSet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SimpleDataObject> getPositionSummary(String userId,String positionGrouping,String positionGroupItemId,PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> positions = new ArrayList<SimpleDataObject>();
		try {
			String qMarks = null;
			String[] dynParams = new String[2];
			dynParams[1] = "";
			
			ArrayList<String> dynamicContent0 = new ArrayList<String>();
			ArrayList<String> dynamicContent1 = new ArrayList<String>();
			
			dynParams[0] =   PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent0, permissionSet);

			if(!Utils.isBlankOrNull(positionGroupItemId) && !DashboardConstants.POSITION_GRID_TYPE_LIST.equals(positionGrouping)){
				if(DashboardConstants.POSITION_GRID_TYPE_DEPARTMENT.equals(positionGrouping)){
					qMarks = Utils.setDynamicParamsAndReturnQmarks(positionGroupItemId, dynamicContent1);
					dynParams[1] = " and tp.dept_id in ("+qMarks+") ";
				}else if(DashboardConstants.POSITION_GRID_TYPE_SUB_DEPARTMENT.equals(positionGrouping)){
					qMarks = Utils.setDynamicParamsAndReturnQmarks(positionGroupItemId, dynamicContent1);
					dynParams[1] = " and tp.sub_dept_id in ("+qMarks+")";
				}else if(DashboardConstants.POSITION_GRID_TYPE_SUB_SUB_DEPARTMENT.equals(positionGrouping)){
					qMarks = Utils.setDynamicParamsAndReturnQmarks(positionGroupItemId, dynamicContent1);
					dynParams[1] = " and tp.sub_sub_dept_id in ("+qMarks+") ";
				}else if(DashboardConstants.POSITION_GRID_TYPE_SUB3_DEPARTMENT.equals(positionGrouping)){
					qMarks = Utils.setDynamicParamsAndReturnQmarks(positionGroupItemId, dynamicContent1);
					dynParams[1] = " and tp.sub3_dept_id in ("+qMarks+") ";
				}else if(DashboardConstants.POSITION_GRID_TYPE_SUB4_DEPARTMENT.equals(positionGrouping)){
					qMarks = Utils.setDynamicParamsAndReturnQmarks(positionGroupItemId, dynamicContent1);
					dynParams[1] = " and tp.sub4_dept_id in ("+qMarks+") ";
				}else if(DashboardConstants.POSITION_GRID_TYPE_POSITION_OWNER.equals(positionGrouping)){
					if(DashboardConstants.GROUPED_ITEM_NOT_ASSIGNED_ID.equals(positionGroupItemId)){
						dynParams[1] = " and tp.position_owner_id IS NULL ";
					}else {
						qMarks = Utils.setDynamicParamsAndReturnQmarks(positionGroupItemId, dynamicContent1);
						dynParams[1] = " and tp.position_owner_id in ("+qMarks+") ";
					}
				}else if(DashboardConstants.POSITION_GRID_TYPE_REQUESTED.equals(positionGrouping)){
					qMarks = Utils.setDynamicParamsAndReturnQmarks(positionGroupItemId, dynamicContent1);
					dynParams[1] = " and tp.position_requested_by in ("+qMarks+") ";
				}else if(DashboardConstants.POSITION_GRID_TYPE_LOCATION.equals(positionGrouping)){
					dynParams[1] = " and tpl.location_id=? ";
					dynamicContent1.add(positionGroupItemId);
				}else if(DashboardConstants.POSITION_GRID_TYPE_POSITION_TYPE.equals(positionGrouping)){					
					dynParams[1] = " and tp.position_type_ext_int =? ";
					dynamicContent1.add(positionGroupItemId);
				}
			}
			
			dq = new DBPreparedQuery("dDashboardManager_GetPositionSummaryDashBoard", dynParams);
			int cnt = 1;
			dq.setId(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setId(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setId(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			
			dq.setInt(cnt++, UserConstants.ROLE_RECRUITER);
			dq.setId(cnt++, PositionConstants.STEP_ACTIVE);
			dq.setInt(cnt++, UserConstants.ROLE_RECRUITER);

			dq.setId(cnt++, PositionConstants.POSITION_STATUS_OPENED);
				
			for (int i = 0; i < dynamicContent0.size(); i++) {
				dq.setId(cnt++, dynamicContent0.get(i));
			}

			for (int i = 0; i < dynamicContent1.size(); i++) {
				dq.setString(cnt++, dynamicContent1.get(i));
			}
			positions = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positions;
	}
	
	/**
	 * @param userId
	 * @param positionGrouping
	 * @param permissionSet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SimpleDataObject> getPositionSummaryGrouped(String userId,String positionGrouping, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> positions = new ArrayList<SimpleDataObject>();
		try {
			String[] dynParams = new String[4];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParams[0] = "dept_id";
			dynParams[2] = "";
			dynParams[3] = "";
			
			dynParams[1] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);
			
			if(DashboardConstants.POSITION_GRID_TYPE_DEPARTMENT.equals(positionGrouping)){
				dynParams[0] = "dept_id";
				dynParams[2] = " group by dept_name ";
				dynParams[3] = " order by dept_name ";
			}else if(DashboardConstants.POSITION_GRID_TYPE_SUB_DEPARTMENT.equals(positionGrouping)){
				dynParams[0] = "sub_dept_id";
				dynParams[2] = " group by dept_name ";
				dynParams[3] = " order by dept_name ";
			}else if(DashboardConstants.POSITION_GRID_TYPE_SUB_SUB_DEPARTMENT.equals(positionGrouping)){
				dynParams[0] = "sub_sub_dept_id";
				dynParams[2] = " group by dept_name ";
				dynParams[3] = " order by dept_name ";
			}else if(DashboardConstants.POSITION_GRID_TYPE_SUB3_DEPARTMENT.equals(positionGrouping)){
				dynParams[0] = "sub3_dept_id";
				dynParams[2] = " group by dept_name ";
				dynParams[3] = " order by dept_name ";
			}else if(DashboardConstants.POSITION_GRID_TYPE_SUB4_DEPARTMENT.equals(positionGrouping)){
				dynParams[0] = "sub4_dept_id";
				dynParams[2] = " group by dept_name ";
				dynParams[3] = " order by dept_name ";
			}else if(DashboardConstants.POSITION_GRID_TYPE_REQUESTED.equals(positionGrouping)){
				dynParams[2] = " group by requested_by_name ";
				dynParams[3] = " order by requested_by_name ";
			}else if(DashboardConstants.POSITION_GRID_TYPE_POSITION_OWNER.equals(positionGrouping)){
				dynParams[2] = " group by position_owner_name ";
				dynParams[3] = " ORDER  BY ISNULL(position_owner_name), position_owner_name ASC ";
				// dynParams[1]+= " AND tp.position_owner_id IS NOT NULL ";
			}else if(DashboardConstants.POSITION_GRID_TYPE_LOCATION.equals(positionGrouping)){
				dynParams[2] = " group by location_name ";
				dynParams[3] = " order by location_name ";
			}else if(DashboardConstants.POSITION_GRID_TYPE_POSITION_TYPE.equals(positionGrouping)){
				dynParams[2] = " group by position_type_ext_int ";
				dynParams[3] = " order by position_type_ext_int ";
			}
			dq = new DBPreparedQuery("dDashboardManager_GetPositionSummaryDetailsDashBoard", dynParams);
			int cnt =1;
			dq.setId(cnt++, DashboardConstants.GROUPED_ITEM_NOT_ASSIGNED_ID);
			dq.setString(cnt++, DashboardConstants.GROUPED_ITEM_NOT_ASSIGNED_LABEL);
			dq.setId(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setId(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setId(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			
			dq.setId(cnt++, PositionConstants.POSITION_STATUS_OPENED);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setId(cnt++, dynamicContent.get(i));
			}
			positions = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positions;
	}
	
	/**
	 * @param userId
	 * @param positionGrouping
	 * @param permissionSet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SimpleDataObject> getPositionSummaryLocationGrouped(String userId,String positionGrouping, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> positions = new ArrayList<SimpleDataObject>();
		try {
			String[] dynParams = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			
			dynParams[0] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tp.position_id", dynamicContent, permissionSet);			
			
			dq = new DBPreparedQuery("dDashboardManager_GetPositionSummaryDetailsLocationGrouped", dynParams);
			int cnt =1;
			dq.setId(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setId(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setId(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			
			dq.setId(cnt++, PositionConstants.POSITION_STATUS_OPENED);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setId(cnt++, dynamicContent.get(i));
			}
			positions = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positions;
	}

	public void markMessagesRead(String messageIds, String userId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];
			dynParams[0] = messageIds;
			dq = new DBPreparedQuery("dDashboardManager_MarkMessagesRead", dynParams);
			dq.setInt(1, MessageConstants.MESSAGE_READ);
			dq.setId(2, userId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while marking messages as read", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public String getXmlForReminderList(ArrayList reminderList, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < reminderList.size(); i++) {
				SimpleDataObject sdo = (SimpleDataObject) reminderList.get(i);

				String reminderId = sdo.getString("reminderId");
				String applicantId = sdo.getString("applicantId");
				String reminderDate = sdo.getString("reminderDate");
				String reminderDesc = sdo.getString("reminderDesc");
				String applicantName = sdo.getString("applicantName");
				if (Utils.isBlankOrNull(applicantName)) {
					applicantName = "NA";
				}

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + reminderId);
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "applicantId");
				wr.startElement("", "userdata", "", at);
				wr.characters(applicantId);
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "applicantName");
				wr.startElement("", "userdata", "", at);
				wr.characters(applicantName);
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "reminderDesc");
				wr.startElement("", "userdata", "", at);
				wr.characters(reminderDesc);
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<a href=\"#\" onclick=\"onClickDeleteReminder(" + reminderId + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>");
				wr.endElement("cell");

				reminderDesc = (reminderDesc.length() > 40) ? reminderDesc.substring(0, 40) + "..." : reminderDesc;
				wr.startElement("cell");
				// wr.characters(reminderDesc);
				wr.characters(wr.doubleEscape(reminderDesc) + "^javascript:onClickReminder(" + reminderId + "," + applicantId + ");^_self");
				wr.endElement("cell");

				applicantName = (applicantName.length() > 25) ? applicantName.substring(0, 25) + "..." : applicantName;
				wr.startElement("cell");
				if ("NA".equalsIgnoreCase(applicantName)) {
					wr.characters(wr.doubleEscape(applicantName));
				} else {
					wr.characters(wr.doubleEscape(applicantName) + "^javascript:onClickApplicant(" + applicantId + ");^_self");
				}

				wr.endElement("cell");

				String dateReminder = "";
				if (reminderDate != null) {
					dateReminder = DateUtils.getSystemDateTimeFormatForDbDate(dateReminder);
					dateReminder = Utils.isBlankOrNull(dateReminder)?"UNKNOWN":dateReminder;
				}

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "dateReminder");
				wr.startElement("", "userdata", "", at);
				wr.characters(dateReminder);
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters(dateReminder);
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

	public ArrayList getReminderList(String userId) {
		ArrayList reminderList = new ArrayList();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dDashboardManager_GetReminderListDashBoard");
			dq.setString(1, userId);
			reminderList = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting batched", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return reminderList;
	}

	public void removeReminder(String reminderId) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dDashboardManager_DeleteReminder");
			dq.setId(1, reminderId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While deleting Reminder", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	

	public ArrayList<SimpleDataObject> getToDoList(String userId, PermissionSet permissionSet, 
			String listType, String groupItemId, String sortOrder, String sortByColumn, String userRole) {
		ArrayList<SimpleDataObject> toDoList = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[6];
			dynParams[0] = " 1 as count ";
			dynParams[1] = "";
			dynParams[2] = "";
			dynParams[3] = "";
			dynParams[4] = "";
			dynParams[5] = " ORDER BY due_date DESC";			
			ArrayList<String> dynamicContent1 = new ArrayList<String>();
			ArrayList<String> dynamicContent2 = new ArrayList<String>();
			ArrayList<String> dynamicContent3 = new ArrayList<String>();
	
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParams[1] += " AND ta.is_confidential = ?";
				dynamicContent1.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			if(permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
				dynParams[1] += " AND tpos.position_id in (select su.position_id from tp_position_step_users su, tp_position_steps ps where su.position_step_id = ps.position_step_id and ps.position_step_status = ? and su.user_id = ? "
					+ " UNION SELECT position_id from tp_positions where position_requested_by = ? " + " UNION SELECT distinct traf.position_id FROM tp_requisition_approval_feedback traf WHERE traf.by_user_id=? OR traf.to_user_id=? ) ";
				dynamicContent1.add(PositionConstants.STEP_ACTIVE);
				dynamicContent1.add(userId);
				dynamicContent1.add(userId);
				dynamicContent1.add(userId);
				dynamicContent1.add(userId);				
			} 
			
			if(permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
				dynParams[2] += " AND tie.position_id in (select su.position_id from tp_position_step_users su, tp_position_steps ps where su.position_step_id = ps.position_step_id and ps.position_step_status = ? and su.user_id = ? "
					+ " UNION SELECT position_id from tp_positions where position_requested_by = ? " + " UNION SELECT distinct traf.position_id FROM tp_requisition_approval_feedback traf WHERE traf.by_user_id=? OR traf.to_user_id=? ) ";
				dynamicContent2.add(PositionConstants.STEP_ACTIVE);
				dynamicContent2.add(userId);
				dynamicContent2.add(userId);
				dynamicContent2.add(userId);
				dynamicContent2.add(userId);				
			} 
			
			dynParams[2] += " AND ? ";
			if((UserConstants.ROLE_HR_MANAGER == Integer.parseInt(userRole) 
					|| UserConstants.ROLE_RECRUITER == Integer.parseInt(userRole))) {				
				dynamicContent2.add("1");		
			} else {
				dynamicContent2.add("0");
			}
	
			if (listType.equals(DashboardConstants.TODO_LIST_TYPE_ACTION)) {
				if(Utils.isBlankOrNull(groupItemId)){
					dynParams[0] = " count(*) as count ";
					dynParams[4] = " GROUP BY action_type ";	
				}else{
					dynParams[3] = " WHERE action_type= ? " ;
					dynamicContent3.add(groupItemId);
				}
			}else if (listType.equals(DashboardConstants.TODO_LIST_TYPE_STEP)) {
				if(Utils.isBlankOrNull(groupItemId)){
					dynParams[0] = " count(*) as count ";
					dynParams[4] = " GROUP BY step_name ";
				}else{
					dynParams[3] = " WHERE step_name= ? " ;
					dynamicContent3.add(groupItemId);
				}				
			}else if (listType.equals(DashboardConstants.TODO_LIST_TYPE_POSITION)) {
				if(Utils.isBlankOrNull(groupItemId)){
					dynParams[0] = " count(*) as count ";
					dynParams[4] = " GROUP BY position_title ";	
				}else{
					dynParams[3] = " WHERE position_id= ? " ;
					dynamicContent3.add(groupItemId);
				}
			}

			if(!Utils.isBlankOrNull(sortOrder) && !Utils.isBlankOrNull(sortByColumn)){
				if(sortByColumn.equals(DashboardConstants.SORT_BY_CANDIDATE)){
					dynParams[5] = " ORDER BY applicant_name " + sortOrder; 
				}else if(sortByColumn.equals(DashboardConstants.SORT_BY_POSITION)){
					dynParams[5] = " ORDER BY position_title " + sortOrder; 
				}else if(sortByColumn.equals(DashboardConstants.SORT_BY_TODO)){
					dynParams[5] = " ORDER BY action_type " + sortOrder; 
				}else if(sortByColumn.equals(DashboardConstants.SORT_BY_DUE_DATE)){
					dynParams[5] = " ORDER BY due_date " + sortOrder; 
				}
			}
			
			dq = new DBPreparedQuery("dDashboardManager_GetToDoList", dynParams);
			int cnt = 1;		
			dq.setInt(cnt++, PositionConstants.STEP_SCHEDULED);
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_SCHEDULE);
			dq.setInt(cnt++, CalendarConstants.APPOINTMENT_STATUS_NOSHOW);
			dq.setInt(cnt++, CalendarConstants.APPOINTMENT_STATUS_HAPPENED);
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_CONFIRM_ATTENDANCE);
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_FEEDBACK);
			dq.setInt(cnt++, DashboardConstants.ACTION_ON_CONDUCT);
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_FEEDBACK);
			
			dq.setInt(cnt++, PositionConstants.STEP_SCHEDULED);
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_SCHEDULING);
			
			dq.setInt(cnt++, CalendarConstants.APPOINTMENT_STATUS_NOSHOW);
			dq.setInt(cnt++, CalendarConstants.APPOINTMENT_STATUS_HAPPENED);
			dq.setInt(cnt++, PositionConstants.STEP_INTERVIEWER_CAN_CONFIRM);
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_INTERVIEW);
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_SCHEDULING);
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_SCHEDULING);
			
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_INTERVIEW);
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_DECISION);	
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_INTERVIEW);
			
			dq.setString(cnt++, ToDoConstants.TODO_TYPE_SELECTION);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(cnt++, userId);
			for (int i = 0; i < dynamicContent1.size(); i++) {
				dq.setString(cnt++, dynamicContent1.get(i));
			}
			dq.setInt(cnt++, DashboardConstants.ACTION_ON_CONDUCT);
			
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_REQUISITION_APPROVAL);
			dq.setString(cnt++, ToDoConstants.TODO_TYPE_POSITION_APPROVAL);
			dq.setString(cnt++, userId);
			
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_BUDGET_APPROVAL);
			dq.setString(cnt++, ToDoConstants.TODO_TYPE_BUDGET_APPROVAL);
			dq.setString(cnt++, userId);
			
			dq.setString(cnt++, ToDoConstants.TODO_TYPE_CLEAR_DRAFT);
			dq.setInt(cnt++, DashboardConstants.ACTION_CLEAR_DRAFT);
			dq.setString(cnt++, MastersConstants.DRAFT);
	
			
			for (int i = 0; i < dynamicContent2.size(); i++) {
				dq.setString(cnt++, dynamicContent2.get(i));
			}
			for (int i = 0; i < dynamicContent3.size(); i++) {
				dq.setString(cnt++, dynamicContent3.get(i));
			}
						
			toDoList = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return toDoList;
	}
}
