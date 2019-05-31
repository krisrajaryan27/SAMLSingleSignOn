package com.talentPool.inbox.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.RegexUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.AutoImportData;
import com.talentPool.inbox.dataobject.AutoImportEducationData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.parser.converter.WordToHtmlConverter;

public class InboxUtils {
	public static final String autoImportExp = "(?mids)(Name:.*)(Source:.*)(Current Location:.*)(Email:.*)(Cell Phone:.*)(Phone1:.*)(Phone2:.*)(Working Since:.*)(Current employer:.*?)([0-9]+\\)Degree:.*?Year of passing:.*?Institute/university:.*?Branch:.*?Grade/Class:.*?\n)*([0-9]+\\)Employment From Date:.*?Employment To Date:.*?Employment Employer Id:.*?Employment Designation Id:.*?Employment Experience:.*?\n)*(CTC:.*)(E-CTC:.*)(Skills:.*?)\n(.*)";
	public static final String part1 = "(?mids)(Name:.*)(Source:.*)(Current Location:.*)(Email:.*)(Cell Phone:.*)(Phone1:.*)(Phone2:.*)(Working Since:.*)(Current employer:.*?)\n";
	public static final String edupart2 = "(?mids)([0-9]+\\)Degree:.*?Year of passing:.*?Institute/university:.*?Branch:.*?Grade/Class:.*?\n)";
	public static final String edupart22 = "(?mids)(Degree:.*?)(Year of passing:.*?)(Institute/university:.*?)(Branch:.*?)(Grade/Class:.*)";
	public static final String empHistroypart10 = "(?mids)([0-9]+\\)Employment From Date:.*?Employment To Date:.*?Employment Employer Id:.*?Employment Designation Id:.*?Employment Experience:.*?\n)";
	public static final String empHistroypart102 = "(?mids)(Employment From Date:.*?)(Employment To Date:.*?)(Employment Employer Id:.*?)(Employment Designation Id:.*?)(Employment Experience:.*)";
	public static final String part3 = "(?mids)(CTC:.*?)(E-CTC:.*?)(Notice Period:.*?)(Skills:.*?)\n";
	public static final String part4 = "(?mids)(Position code:.*?)(Position title:.*?)\n";
	public static final String part5 = "(?mids)(CTC:.*)(E-CTC:.*)(Skills:.*?)\n";
	public static final String part6 = "(?mids)(User ID:.*?)\n";
	public static final String part7 = "(?mids)(Note:.*)";
	public static final String part8 = "(?mids)(Employee Id:.*)";
	public static final String part9 = "(?mids)(Date of Birth:.*)(Passport Number:.*)(Resume Type:.*?)\n";
	public static String uuidPart = "(?mids)(UUID:.*)";
	
	public boolean isAutoImportFormat(String content) {
		try {
			Pattern p = Pattern.compile(autoImportExp);
			boolean theEnd = false;
			if (!Utils.isBlankOrNull(content)) {
				Matcher m = p.matcher(content);
				if (m != null) {
					while (!theEnd) {
						theEnd = !m.find();
						if (!theEnd) {
							TPLogger.getLogger().debug("Auto format found");
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return false;
	}

	public ArrayList<String> getAutoImportFields(String content, String exp) {
		ArrayList<String> groups = new ArrayList<String>();
		try {
			Pattern p = Pattern.compile(exp);
			Matcher m = p.matcher(content);
			int groupCount = m.groupCount();
			if (groupCount > 1) {
				m.find();
				for (int i = 1; i <= groupCount; i++) {
					String grp = m.group(i);
					grp = grp.substring(grp.indexOf(":") + 1).trim();
					groups.add(grp);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return groups;
	}

	public String getSingleValue(String content, String exp) {
		String result = "";
		try {
			Matcher m = RegexUtils.getMatcher(content, exp);
			if (m != null && m.find()) {
				result = content.substring(m.start(), m.end());
				result = result.substring(result.indexOf(":") + 1).trim();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return result;
	}

	public AutoImportData getAutoImportData(String content) {
		AutoImportData data = new AutoImportData();
		try {
			ArrayList<String> groups = getAutoImportFields(content, part1);
			data.setName(groups.get(0));
			data.setSource(groups.get(1));
			data.setLocation(groups.get(2));
			String emails = groups.get(3);
			if (!Utils.isBlankOrNull(emails)) {
				String[] emailIds = emails.split(",");
				if (emailIds != null) {
					data.setEmail1((emailIds.length > 0) ? emailIds[0].trim() : "");
					data.setEmail2((emailIds.length > 1) ? emailIds[1].trim() : "");
				}
			}
			data.setCellPhone(groups.get(4));
			data.setPhone1(groups.get(5));
			data.setPhone2(groups.get(6));

			data.setWorkingSince(groups.get(7));
			data.setCurrentEmployer(groups.get(8));

			ArrayList<String> matches = RegexUtils.getMatches(content, edupart2);
			for (int i = 0; matches != null && i < matches.size(); i++) {
				groups = getAutoImportFields(matches.get(i), edupart22);
				AutoImportEducationData eData = new AutoImportEducationData(groups.get(0), groups.get(3), groups.get(2), groups.get(1), groups.get(4));
				if (data.getRawEducationDetails() == null) {
					data.setRawEducationDetails(new ArrayList<AutoImportEducationData>());
				}
				data.getRawEducationDetails().add(eData);
			}
			
			TPLogger.getLogger().debug("=========Strat==========");
			TPLogger.getLogger().debug("CONTENT  = "+content);
			TPLogger.getLogger().debug("empHistory = "+empHistroypart10);
			ArrayList<String> matchesEmpHistory = RegexUtils.getMatches(content, empHistroypart10);
			TPLogger.getLogger().debug(matchesEmpHistory);
			for (int i = 0; matchesEmpHistory != null && i < matchesEmpHistory.size(); i++) {
				groups = getAutoImportFields(matchesEmpHistory.get(i), empHistroypart102);
				TPLogger.getLogger().debug("SHANTANU NEED == "+groups.get(0)+"||"+groups.get(1)+"||"+ groups.get(2)+"||"+ groups.get(3) +"||"+ groups.get(4));
				EmploymentHistoryData empHistoryData = new EmploymentHistoryData(groups.get(0), groups.get(1), groups.get(2), groups.get(3), groups.get(4));
				if (data.getEmploymentHistoryDetails() == null) {
					data.setEmploymentHistoryDetails(new ArrayList<EmploymentHistoryData>());
				}
				data.getEmploymentHistoryDetails().add(empHistoryData);
				TPLogger.getLogger().debug("SIKDAR NEED == "+empHistoryData.getDesignationName()+empHistoryData.getEmployerName());
				TPLogger.getLogger().debug("SIKDAR NEED == "+empHistoryData.getDesignationId()+empHistoryData.getEmployerId());
			}
			if(!Utils.isListEmptyOrNull(data.getEmploymentHistoryDetails())){
				try{
					Collections.sort(data.getEmploymentHistoryDetails(),EmploymentHistoryData.REVERSE_CHRONOLOGICAL );
				}catch (Exception e) {
					TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
				}
				data.setCurrentEmployer(data.getEmploymentHistoryDetails().get(0).getEmployerName());
			}
			TPLogger.getLogger().debug("=========End==========");
			
			groups = getAutoImportFields(content, part3);
			data.setCTC(groups.get(0));
			data.setECTC(groups.get(1));
			data.setNoticePeriod(groups.get(2));
			data.setSkills(groups.get(3));

			groups = getAutoImportFields(content, part4);
			if (groups.size() > 0) {
				data.setPositionCode(groups.get(0));
				data.setPositionTitle(groups.get(1));
			}
			
			data.setUserName(getSingleValue(content,part6));
			
			String customString = "";
			Matcher m = RegexUtils.getMatcher(content, part5);
			if (m != null && m.find()) {
				int endIndex = m.end();
				customString = content.substring(endIndex);
			}
			ArrayList<CustomFieldData> customFields = getCustomFieldsFromContent(customString);
			data.setCustomFields(customFields);

			String note = getSingleValue(customString, part7);
			data.setNote(note);
			
			String employeeId = getSingleValue(customString, part8);
			data.setEmployeeCode(employeeId);

			groups = getAutoImportFields(content, part9);
			if (groups.size() > 0) {
				data.setDateOfBirth(groups.get(0));
				data.setPassport(groups.get(1));
				data.setResumeTypeId(groups.get(2));
			}
			
			String uuid = getSingleValue(content, uuidPart);
			data.setAttribute("uuid", uuid);
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return data;
	}

	private ArrayList<CustomFieldData> getCustomFieldsFromContent(String content) {
		CustomFieldManager customFieldManager = new CustomFieldManager();
		ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsFor(CustomFieldConstants.ENTITY_TYPE_APPLICANT, true);
		for (int i = 0; customFields != null && i < customFields.size(); i++) {
			CustomFieldData cData = customFields.get(i);
			String rawVals = getSingleValue(content, "(?mids)" + cData.getFieldDisplayName() + ":.*?\n");
			if (!Utils.isBlankOrNull(rawVals)) {
				String[] vals = new String[1];
				if (cData.isMultipleValuesAllowed()) {
					vals = rawVals.trim().split(",");
				} else {
					vals[0] = rawVals.trim();
				}
				cData.setFieldValues(vals);
			}else{
				cData.setFieldValues(null);
			}
		}
		return customFields;
	}

	/**
	 * converts doc attachments to html format
	 * 
	 * @param msg
	 * @param pathToDocFolder
	 */
	public void convertDocAttachments(MessageData msg, String pathToDocFolder) {
		try {
			ArrayList attachments = msg.getAttachments();
			if (attachments != null) {
				WordToHtmlConverter converter = new WordToHtmlConverter();
				for (int i = 0; i < attachments.size(); i++) {
					AttachmentData aData = (AttachmentData) attachments.get(i);
					String srcFilePath = Utils.concatFilePath(pathToDocFolder, aData.getAttachmentFilePath());
					converter.convertToHtml(srcFilePath);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}

}
