package com.talentPool.notifier.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.budget.dataobject.BudgetItem;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.HTMLUtils.HTMLUtils;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.utils.PositionUtils;
import com.talentPool.requisition.dataobject.RequisitionApprovalStepData;
import com.talentPool.requisition.dataobject.RequisitionFeedbackData;
import com.talentPool.user.dataobject.LoginData;

public class TemplateUtils {

	public static String getConvertedGlobalVars(InboxData data) {
		StringBuffer sb = new StringBuffer();
		if (data != null) {
			sb.append(getConstructedToken("COMPANY_NAME", data.getInboxDisplayName()));
		}
		return sb.toString();
	}

	public static String getConvertedApplicantData(ApplicantData data, String lastFeedback) {
		StringBuffer sb = new StringBuffer();
		if (data != null) {
			sb.append(getConstructedToken("CANDIDATE_ID", data.getApplicantId()));
			sb.append(getIfNotNull("CANDIDATE_NAME", data.getApplicantName()));
			sb.append(getIfNotNull("LINK_TO_CANDIDATE_PROFILE", getInternalLinkToCandidateProfile(data.getApplicantId(),data.getApplicantName())));
			sb.append(getIfNotNull("EXTERNAL_LINK_TO_CANDIDATE_PROFILE", getExternalLinkToCandidateProfile(data.getApplicantId(),data.getApplicantName())));
			sb.append(getIfNotNull("CANDIDATE_CURRENT_LOCATION", data.getApplicantCity()));
			sb.append(getIfNotNull("CANDIDATE_CELL_PHONE", data.getApplicantCellPhone()));
			sb.append(getIfNotNull("CANDIDATE_PHONE_1", data.getApplicantHomePhone()));
			sb.append(getIfNotNull("CANDIDATE_PHONE_2", data.getApplicantWorkPhone()));
			sb.append(getIfNotNull("CANDIDATE_EMAIL_1", data.getApplicantEmail1()));
			sb.append(getIfNotNull("CANDIDATE_EMAIL_2", data.getApplicantEmail2()));
			sb.append(getIfNotNull("CANDIDATE_CURRENT_EMPLOYER", data.getApplicantCurrentEmployer()));			
			sb.append(getIfNotNull("CANDIDATE_INTERVIEW_FEEDBACK", lastFeedback));
			sb.append(getIfNotNull("CANDIDATE_EMPLOYMENT_INFO", data.getFullEmploymentHistoryDetailsFormatted()));		
			
		}
		return sb.toString();
	}

	public static String getConvertedUserData(LoginData data) {
		return getConvertedUserInfo(data, "USER_");
	}

	public static String getConvertedContactData(LoginData data) {
		return getConvertedUserInfo(data, "CONTACT_PERSON_");
	}

	private static String getConvertedUserInfo(LoginData data, String prefix) {
		StringBuffer sb = new StringBuffer();
		if (data != null) {
			sb.append(getConstructedToken(prefix + "FNAME", data.getFirstName()));
			sb.append(getIfNotNull(prefix + "LNAME", data.getLastName()));
			sb.append(getIfNotNull(prefix + "CELL_PHONE", data.getCellPhone()));
			sb.append(getIfNotNull(prefix + "HOME_PHONE", data.getHomePhone()));
			sb.append(getIfNotNull(prefix + "EMAIL", data.getEmail()));
			sb.append(getIfNotNull(prefix + "NAME", data.getUserName()));
			sb.append(getIfNotNull(prefix + "PASSWORD", data.getPassword()));
		}
		return sb.toString();

	}

	public static String getConvertedAppointmentData(Date fromDate, Date toDate, String dtFormat) {
		StringBuffer sb = new StringBuffer();
		if (fromDate != null && toDate != null) {
			String date = Utils.getDateConvertedToString(fromDate, dtFormat);
			String timeFrom = Utils.getDateConvertedToString(fromDate, "hh:mm a zzz");
			String timeTo = Utils.getDateConvertedToString(toDate, "hh:mm a zzz");
			sb.append(getConstructedToken("APPOINTMENT_DATE", date));
			sb.append(getIfNotNull("APPOINTMENT_TIME_FROM", timeFrom));
			sb.append(getIfNotNull("APPOINTMENT_TIME_TO", timeTo));
		}
		return sb.toString();

	}
	
	public static String getConvertedRequisitionApprovalStepData(RequisitionApprovalStepData data) {
		StringBuffer sb = new StringBuffer();
		sb.append(getConstructedToken("REQUISITION_APPROVAL_STEP_NAME", data.getRequisitionApprovalStepName()));
		sb.append(getIfNotNull("REQUISITION_APPROVAL_STEP_USER", data.getString("name")));
		return sb.toString();
	}

	public static String getConvertedRequisitionFeedbackData(RequisitionFeedbackData data) {
		StringBuffer sb = new StringBuffer();
		sb.append(getConstructedToken("REQUISITION_TITLE", data.getPositionTitle()));
		sb.append(getIfNotNull("REQUISITION_STEP_TITLE", data.getToStepName()));
		return sb.toString();
	}
	
	public static String getConvertedBudgetItemData(BudgetItem data) {
		StringBuffer sb = new StringBuffer();
		if (data != null) {
			sb.append(getIfNotNull("BUDGET_ITEM_NAME", data.getBudgetItemName()));
			sb.append(getIfNotNull("BUDGET_OWNER_NAME", data.getOwnerName()));
			sb.append(getIfNotNull("BUDGET_DEPT_NAME", data.getDeptName()));
			sb.append(getIfNotNull("BUDGET_SUB_DEPT_NAME", data.getSubDeptName()));
			sb.append(getIfNotNull("BUDGET_SUB_SUB_DEPT_NAME", data.getSubSubDeptName()));
			sb.append(getIfNotNull("BUDGET_GRADE", data.getGradeName()));
			sb.append(getIfNotNull("BUDGET_BAND", data.getBandName()));
			sb.append(getIfNotNull("BUDGET_HEAD_COUNT", data.getAvailableHeadCount()+""));		
			sb.append(getIfNotNull("BUDGET_START_DATE", data.getStartTimeToDisplay()));
			sb.append(getIfNotNull("BUDGET_END_DATE", data.getEndTimeToDisplay()));			
		}
		return sb.toString();
	}
	
	public static String getConvertedPositionDescriptionData(PositionData data) {
		StringBuffer sb = new StringBuffer();
		if (data != null) {
			sb.append(getIfNotNull("POSITION_TITLE", data.getPositionTitle()));
			sb.append(getIfNotNull("POSITION_CODE", data.getPositionReferenceCode()));
			sb.append(getIfNotNull("POSITION_CREATED_ON", Utils.getDateConvertedToString(data.getPositionCreationDate(),Utils.redDDMMYYYYFormat)));
			sb.append(getIfNotNull("POSITION_GRADE", data.getGradeName()));
			sb.append(getIfNotNull("POSITION_BAND", data.getBandName()));
			sb.append(getIfNotNull("POSITION_LOCATION", data.getLocationName()));
			sb.append(getIfNotNull("POSITION_BUDGET_ITEM_NAME", data.getBudgetItemName()));
			sb.append(getIfNotNull("POSITION_DEPARTMENT", data.getDeptName()));
			sb.append(getIfNotNull("POSITION_REFERAL_FEES", data.getReferalFees()));
			sb.append(getIfNotNull("POSITION_VACANCIES", ""+data.getNoOfPositions()));
			sb.append(getIfNotNull("POSITION_HIRE_BY_DATE", Utils.getDateConvertedToString(data.getPositionExpiryDate(),Utils.regDDMMMYYYYFormat)));		
			sb.append(getIfNotNull("POSITION_REQUESTED_BY", data.getRequestedByName()));
			sb.append(getIfNotNull("POSITION_EXPERIENCE",PositionUtils.getPositionExperienceConstructed(data.getMinExperience(), data.getMaxExperience())));
			sb.append(getIfNotNull("COMPANY_CAREERS_PAGE_LINK",GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_CAREERS_PAGE_URL)));
		}
		return sb.toString();
	}
	
	public static String getConvertedRequisitionData(PositionData data) {
		StringBuffer sb = new StringBuffer();
		sb.append(getConstructedToken("REQUISITION_TITLE", data.getPositionTitle()));
		String positionStatus = null;
		if(data.getPositionStatus().equalsIgnoreCase(PositionConstants.POSITION_STATUS_OPENED)) {
			positionStatus = TPLabels.getLabel("position.approval.label.open");
		} else if(data.getPositionStatus().equalsIgnoreCase(PositionConstants.POSITION_STATUS_INPROCESS)) {
			positionStatus = TPLabels.getLabel("position.approval.label.inprocess");
		} else if(data.getPositionStatus().equalsIgnoreCase(PositionConstants.POSITION_STATUS_HOLD)) {
			positionStatus = TPLabels.getLabel("position.approval.label.onhold");
		} else if(data.getPositionStatus().equalsIgnoreCase(PositionConstants.POSITION_STATUS_REJECTED)) {
			positionStatus = TPLabels.getLabel("position.approval.label.rejected");
		}
		sb.append(getIfNotNull("REQUISITION_STATUS", positionStatus));
		return sb.toString();
	}

	public static String getIfNotNull(String var, String content) {
		if (!Utils.isBlankOrNull(content)) {
			return "||" + getConstructedToken(var, content);
		}
		return "";
	}

	public static String appndToToken(String tokenStr1, String tokenStr2) {
		if (!Utils.isBlankOrNull(tokenStr1) || !Utils.isBlankOrNull(tokenStr2)) {
			if (Utils.isBlankOrNull(tokenStr1)) {
				return tokenStr2;
			} else if (Utils.isBlankOrNull(tokenStr2)) {
				return tokenStr1;
			} else {
				return tokenStr1 + "||" + tokenStr2;
			}
		}
		return "";
	}

	public static String getConstructedToken(String key, String val) {
		return key + "==" + val;
	}

	public static String getBlobDataValue(String blobData, String blobKey) {
		String[] st = blobData.split("\\|\\|");
		if (st != null) {
			for (int i = 0; i < st.length; i++) {
				String token = st[i];
				String st1[] = token.split("==");
				if (st1 != null) {
					for (int k = 0; k < st1.length; k++) {
						if (st1[0].equalsIgnoreCase(blobKey)) {
							return st1[1];
						}
					}
				}
			}
		}
		return "";
	}

	/**
	 * @param keyMap
	 *            comma separated string of all possible strings. for example key1,key2,key3
	 * @param contentStr
	 *            as key and value pair string separated by separator. In our case it should be like
	 *            key1==val1||key2==val2||key3==val3
	 * @return
	 */
	public static HashMap<String, String> getKeyValueMap(String keyMap, String contentStr) {
		HashMap<String, String> keyValMap = new HashMap<String, String>();
		List<String> keys = Utils.getKeys(keyMap, ",");
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			key = key.trim();
			String value = getBlobDataValue(contentStr, key);
			keyValMap.put(key, value);
		}
		return keyValMap;
	}

	public static String getNewTemplateCode() {
		Calendar cal = new GregorianCalendar();
		String templateCode = cal.get(Calendar.YEAR) + "" + cal.get(Calendar.MONTH) + "" + cal.get(Calendar.DAY_OF_MONTH) + "" + cal.get(Calendar.HOUR) + "" + cal.get(Calendar.MINUTE) + ""
				+ cal.get(Calendar.SECOND) + "" + cal.get(Calendar.MILLISECOND);
		return templateCode;
	}

	public static boolean isTypeExist(String typeIds, String type) {
		if (!Utils.isBlankOrNull(typeIds)) {
			String[] ids = typeIds.split(",");
			if (ids != null) {
				for (int i = 0; i < ids.length; i++) {
					if (ids[i].equals(type)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static String getConvertedEmployeeData(SourceData srcData){
		return getConvertedEmployeeData(srcData, "EMPLOYEE");
	}
	
	private static String getConvertedEmployeeData(SourceData data, String prefix) {
		StringBuffer sb = new StringBuffer();
		if (data != null) {
			sb.append(getIfNotNull(prefix + "_NAME", data.getSourceTitle()));
			sb.append(getIfNotNull(prefix + "_EMAIL", data.getSourceEmail()));
			sb.append(getIfNotNull(prefix + "_PHONE", data.getSourcePhone()));
			sb.append(getIfNotNull(prefix + "_MOBILE", data.getSourceMobile()));
			sb.append(getIfNotNull(prefix + "_CODE", data.getEmployeeCode()));
		}
		return sb.toString();

	}
	
	public static String getConvertedPositionVendorPublishData(PositionData data) {
		StringBuffer sb = new StringBuffer();
		if (data != null) {
			sb.append(getIfNotNull("VENDOR_PUBLISH_FROM_DATE", Utils.getDateConvertedToString(data.getVendorPublishFromDate(),Utils.regDDMMMYYYYFormat)));
			sb.append(getIfNotNull("VENDOR_PUBLISH_TO_DATE", Utils.getDateConvertedToString(data.getVendorPublishToDate(),Utils.regDDMMMYYYYFormat)));
		}
		return sb.toString();
	}
	
	public static String getConvertedPositionEmployeesPublishData(PositionData data) {
		StringBuffer sb = new StringBuffer();
		if (data != null) {
			sb.append(getIfNotNull("EMPLOYEE_PUBLISH_FROM_DATE", Utils.getDateConvertedToString(data.getEmployeePublishFromDate(),Utils.regDDMMMYYYYFormat)));
			sb.append(getIfNotNull("EMPLOYEE_PUBLISH_TO_DATE", Utils.getDateConvertedToString(data.getEmployeePublishToDate(),Utils.regDDMMMYYYYFormat)));
		}
		return sb.toString();
	}
	
	public static String getConvertedCustomFieldData(List<CustomFieldData> custFieldList){
		StringBuffer sb = new StringBuffer();
		CustomFieldData cData = null; 
		if(custFieldList!=null && custFieldList.size()>0){
			for (int i = 0; i < custFieldList.size(); i++) {
				cData = custFieldList.get(i);
				sb.append(getIfNotNull(cData.getFieldName(),cData.getDisplayValue()));
			}	
		}
		return sb.toString();
	}
	
	public static String isContainRepeat(String text){
		String isRepeat = TemplateConstants.TEXT_NOT_HAVING_REPEAT_TAG;
		if(!Utils.isBlankOrNull(text)){
			if(text.indexOf(TemplateConstants.REPEAT)!=-1 && text.indexOf(TemplateConstants.END_REPEAT)!=-1){
				isRepeat = TemplateConstants.TEXT_HAVING_REPEAT_TAG;
			}
		}
		return isRepeat;
	}
	
	/**
	 * Candidate Name with internal link to candidate profile is returned.
	 * Eg: If applicant name is <code>Praveen Kumar</code> and id is 888 then, link generated will look like 
	 * <p>
	 * 		<code>&lt;a href="http://hostname:port/alias/selectionProcess.do?mode=viewOriginalResume&applicantId=888"&gt;Praveen Kumar&lt;/a&gt;</code>
	 * </p>
	 * 
	 * Where 
	 * <p> hostname: system.name provided in talentpool.properties </p> 
	 * <p> port: application.port provided in talentpool.properties </p>
	 * <p> alias: application.alias </p>
	 * 
	 * @param applicantId
	 * @param applicantName
	 * @return internal link to candidate profile
	 */
	public static String getInternalLinkToCandidateProfile(String applicantId, String applicantName) {
		try {
			String queryString = "selectionProcess.do?mode=viewOriginalResume&applicantId="+applicantId;
			String linkName = Utils.isBlankOrNull(applicantName)?TPLabels.getLabel("email_templates.label.link_to_candidate"):applicantName;
			String path= Utils.buildTalentPoolURL(queryString);
			return HTMLUtils.writeAnchorTag(path, linkName, null);	
		} catch (Exception e) {
			TPLogger.getLogger().error("applicantId:"+applicantId+" applicantName:"+applicantName,e);
			return HTMLUtils.BLANK_SPACE;
		}
	}
	/**
	 * Candidate Name with external link to candidate profile is returned.
	 * Eg: If applicant name is <code>Praveen Kumar</code> and id is 888 then, link generated will look like 
	 * <p>
	 * 		<code>&lt;a href="http://hostname:port/alias/selectionProcess.do?mode=viewOriginalResume&applicantId=888"&gt;Praveen Kumar&lt;/a&gt;</code>
	 * </p>
	 * 
	 * Where 
	 * <p> hostname: application.external_IP provided in talentpool.properties </p> 
	 * <p> port: application.port provided in talentpool.properties </p>
	 * <p> alias: application.alias </p>
	 * 
	 * @param applicantId
	 * @param applicantName
	 * @return external link to candidate profile
	 */
	public static String getExternalLinkToCandidateProfile(String applicantId, String applicantName) {
		try {
			String queryString 	= "selectionProcess.do?mode=viewOriginalResume&applicantId="+applicantId;
			String linkName 	= Utils.isBlankOrNull(applicantName)?TPLabels.getLabel("email_templates.label.link_to_candidate"):applicantName;
			String path			= Utils.buildExternalTalentPoolURL(queryString);
			return HTMLUtils.writeAnchorTag(path, linkName, null);	
		} catch (Exception e) {
			TPLogger.getLogger().error("applicantId:"+applicantId+" applicantName:"+applicantName,e);
			return HTMLUtils.BLANK_SPACE;
		}
	}
	
	/**
	 * @param uuid
	 * @return confirmation link
	 */
	public static String getConfirmationLink(String uuid, int product,String requestUri) {
		try {
			String queryString 	= "";
			String path = "";
			switch (product) {
			case CommonConstants.PRODUCT_TALENTPOOL:
				queryString = "user.do?mode=changePasswordForLink&forgotPassword=1&id="+uuid;
				break;
			case CommonConstants.PRODUCT_TEMPLOYEE:
				queryString = "myAccount.do?mode=changePasswordForLink&forgotPassword=1&id="+uuid;
				break;
			case CommonConstants.PRODUCT_TVENDOR:
				queryString = "user.do?mode=changePassword&forgotPassword=1&id="+uuid;
				break;
			case CommonConstants.PRODUCT_TWEBSITE:
				break;
			case CommonConstants.PRODUCT_CANDIDATE_PORTAL:
				queryString = uuid;
				break;
			default:
				break;
			}
			StringBuilder builder = new StringBuilder();
			builder.append(requestUri);
			builder.append("/");
//			builder.append(TPApplicationProperties.getProperty("application.alias"));
//			builder.append("/");
			builder.append(queryString);
			path = builder.toString();
			return HTMLUtils.writeAnchorTag(path, path, null);	
		} catch (Exception e) {
			TPLogger.getLogger().error("error while generation confirmation link",e);
			return HTMLUtils.BLANK_SPACE;
		}
	}

	/**
	 * @param fromDate
	 * @param toDate
	 * @param regeeeedmmmmyyyyformat
	 * @param timezone
	 * @return
	 * 
	 * to be called for timezone related reminders and notification emails
	 */
	public static String getConvertedAppointmentData(Date fromDate,Date toDate, String dtFormat, String timezone) {
		StringBuffer sb = new StringBuffer();
		if (fromDate != null && toDate != null) {
			String date = Utils.getDateConvertedToStringWithTimezone(fromDate, dtFormat,timezone);
			String timeFrom = Utils.getDateConvertedToStringWithTimezone(fromDate, "hh:mm a zzz",timezone);
			String timeTo = Utils.getDateConvertedToStringWithTimezone(toDate, "hh:mm a zzz",timezone);
			sb.append(getConstructedToken("APPOINTMENT_DATE", date));
			sb.append(getIfNotNull("APPOINTMENT_TIME_FROM", timeFrom));
			sb.append(getIfNotNull("APPOINTMENT_TIME_TO", timeTo));
		}
		return sb.toString();
	}
	
}