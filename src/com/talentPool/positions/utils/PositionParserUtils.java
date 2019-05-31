/**
 * 
 */
package com.talentPool.positions.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.positions.PositionParserConstants;
/**
 * @author pallavi
 *
 */
public class PositionParserUtils {
	public Map<String, String> getPositionAttributesToBeImported(List<CustomFieldData> customFields) {
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put(PositionParserConstants.POSITION_NAME, "");
		attributes.put(PositionParserConstants.POSITION_CODE, "");
		attributes.put(PositionParserConstants.POSITION_OWNER, "");
		attributes.put(PositionParserConstants.REQUESTED_BY, "");
		attributes.put(PositionParserConstants.LOCATION, "");
		attributes.put(PositionParserConstants.DEPT_HIERARCHY_LEVEL_1, "");
		attributes.put(PositionParserConstants.DEPT_HIERARCHY_LEVEL_2, "");
		attributes.put(PositionParserConstants.DEPT_HIERARCHY_LEVEL_3, "");
		attributes.put(PositionParserConstants.DEPT_HIERARCHY_LEVEL_4, "");
		attributes.put(PositionParserConstants.DEPT_HIERARCHY_LEVEL_5, "");
		attributes.put(PositionParserConstants.VACANCIES, "");
		attributes.put(PositionParserConstants.HIRE_BY_DATE, "");
		attributes.put(PositionParserConstants.POSITION_LEVEL, "");
		attributes.put(PositionParserConstants.REFERRAL_FEES, "");
		attributes.put(PositionParserConstants.NOTE, "");
		attributes.put(PositionParserConstants.JOB_RESPONSIBILITIES, "");
		attributes.put(PositionParserConstants.PRIMARY_SKILLS, "");
		attributes.put(PositionParserConstants.SECONDARY_SKILLS, "");
		attributes.put(PositionParserConstants.EDUCATION, "");
		attributes.put(PositionParserConstants.BRANCH, "");
		attributes.put(PositionParserConstants.MINIMUM_EXPERIENCE, "");
		attributes.put(PositionParserConstants.MAXIMUM_EXPERIENCE, "");
		attributes.put(PositionParserConstants.JOB_REQUIREMENTS, "");		
		attributes.put(PositionParserConstants.APPROVAL_COMMENT, "");
		attributes.put(PositionParserConstants.NOTIFY_USERS, "");
		attributes.put(PositionParserConstants.KEEP_ON_HOLD, "");
		attributes.put(PositionParserConstants.NEXT_APPROVER, "");
		attributes.put(PositionParserConstants.REQUISITION_APPROVAL_TEMPLATE, "");
		attributes.put(PositionParserConstants.BUDGET_ITEM, "");
		attributes.put(PositionParserConstants.GRADE, "");
		attributes.put(PositionParserConstants.BAND, "");
		attributes.put(PositionParserConstants.BUSINESS_UNIT, "");
		attributes.put(PositionParserConstants.COST_CENTER, "");
		if(customFields != null && customFields.size() > 0) {
			for(int i = 0; i < customFields.size(); i++) {
				CustomFieldData customFieldData = customFields.get(i);
				attributes.put(customFieldData.getFieldDisplayName(), "");
			}
		}
		
		//naukri attribs

		attributes.put(PositionParserConstants.CONTACT_PERSON_NAME, "");
		attributes.put(PositionParserConstants.JOB_INDUSTRY_CODE, "");
		attributes.put(PositionParserConstants.JOB_FUNCTION_CODE, "");
		attributes.put(PositionParserConstants.JOB_ROLE_CODE, "");
		attributes.put(PositionParserConstants.JOB_KEY_WORDS, "");
		attributes.put(PositionParserConstants.COUNTRY, "");
		attributes.put(PositionParserConstants.MINIMUM_SALARY, "");
		attributes.put(PositionParserConstants.MAXIMUM_SALARY, "");
		attributes.put(PositionParserConstants.BENEFITS_DESCRIPTION, "");
		attributes.put(PositionParserConstants.DISPLAY_SALARY, "");
		attributes.put(PositionParserConstants.DESIRED_CANDIDATE_SUMMARY_TEXT, "");
		attributes.put(PositionParserConstants.CONTACT_PERSON_EMAIL, "");
		attributes.put(PositionParserConstants.APPLY_BY_WEB_URL, "");
		attributes.put(PositionParserConstants.JOBFEED_RESPONSE_EMAIL, "");
		attributes.put(PositionParserConstants.SALARY_CURRENCY, "");
		return attributes;
	}
	
	public String getFormattedPositionText(Map<String, String> map) {
		StringBuffer positionDef = new StringBuffer();
		if(map != null) {
			Iterator<String> itr = map.keySet().iterator();
			while(itr.hasNext()) {
				String key = itr.next();
				positionDef.append(key);
				positionDef.append(PositionParserConstants.POSITION_ATTRIBUTE_INDICATOR);
				positionDef.append(System.getProperty("line.separator"));
				positionDef.append(map.get(key));
				positionDef.append(System.getProperty("line.separator"));
				positionDef.append(System.getProperty("line.separator"));
			}
		}
		return positionDef.toString().trim();
	}
}
