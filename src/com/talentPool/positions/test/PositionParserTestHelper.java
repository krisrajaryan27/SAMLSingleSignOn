/**
 * 
 */
package com.talentPool.positions.test;

import com.talentPool.positions.PositionParserConstants;

/**
 * @author pallavi
 *
 */
public class PositionParserTestHelper {
	public String getPosition() {
		StringBuffer position = new StringBuffer();
		position.append(PositionParserConstants.POSITION_NAME + ":" + System.getProperty("line.separator"));
		position.append("QA Engineer" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.POSITION_CODE + ":" + System.getProperty("line.separator"));
		position.append("QA Engineer" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.POSITION_OWNER + ":" + System.getProperty("line.separator"));
		position.append("Nick" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.REQUESTED_BY + ":" + System.getProperty("line.separator"));
		position.append("Nick" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.LOCATION + ":" + System.getProperty("line.separator"));
		position.append("Pune" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.VACANCIES + ":" + System.getProperty("line.separator"));
		position.append("2" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.HIRE_BY_DATE + ":" + System.getProperty("line.separator"));
		position.append("11/05/2010" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.POSITION_LEVEL + ":" + System.getProperty("line.separator"));
		position.append("Management" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.REFERRAL_FEES + ":" + System.getProperty("line.separator"));
		position.append("Rs. 50000" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.NOTE + ":" + System.getProperty("line.separator"));
		position.append("Hire ASAP" + System.getProperty("line.separator") + "Before due date" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.JOB_RESPONSIBILITIES + ":" + System.getProperty("line.separator"));
		position.append("QA Engineer" + System.getProperty("line.separator") + "QA Engineer" + System.getProperty("line.separator") + "QA Engineer" + System.getProperty("line.separator") + "QA Engineer" + System.getProperty("line.separator") + "\tQA Engineer" + System.getProperty("line.separator") + "\t\t\t");
		position.append(PositionParserConstants.PRIMARY_SKILLS + ":" + System.getProperty("line.separator"));
		position.append("Java" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.SECONDARY_SKILLS + ":" + System.getProperty("line.separator"));
		position.append("JUnit" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.EDUCATION + ":" + System.getProperty("line.separator"));
		position.append("B.E." + System.getProperty("line.separator"));
		position.append(PositionParserConstants.BRANCH + ":" + System.getProperty("line.separator"));
		position.append("Computer Engineering" + System.getProperty("line.separator"));
		position.append(System.getProperty("line.separator") + "\t#COMMENT\t Test Comment " + System.getProperty("line.separator") + System.getProperty("line.separator") + System.getProperty("line.separator"));
		position.append(PositionParserConstants.MINIMUM_EXPERIENCE + ":" + System.getProperty("line.separator"));
		position.append("2" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.MAXIMUM_EXPERIENCE + ":" + System.getProperty("line.separator"));
		position.append("6" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.JOB_REQUIREMENTS + ":" + System.getProperty("line.separator"));
		position.append("QA Engineer" + System.getProperty("line.separator") + "QA Engineer" + System.getProperty("line.separator") + "QA Engineer" + System.getProperty("line.separator") + "QA Engineer" + System.getProperty("line.separator") + "QA Engineer" + System.getProperty("line.separator") + "\t\t\t");
		position.append(PositionParserConstants.DEPT_HIERARCHY_LEVEL_1 + ":" + System.getProperty("line.separator"));
		position.append("Level 1" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.DEPT_HIERARCHY_LEVEL_2 + ":" + System.getProperty("line.separator"));
		position.append("Level 2" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.DEPT_HIERARCHY_LEVEL_3 + ":" + System.getProperty("line.separator"));
		position.append("Level 3" + System.getProperty("line.separator"));		
		position.append(PositionParserConstants.APPROVAL_COMMENT + ":" + System.getProperty("line.separator"));
		position.append("Test Comment" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.NOTIFY_USERS + ":" + System.getProperty("line.separator"));
		position.append("Pallavi, Ajeet" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.KEEP_ON_HOLD + ":" + System.getProperty("line.separator"));
		position.append("Yes" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.NEXT_APPROVER + ":" + System.getProperty("line.separator"));
		position.append("Pallavi" + System.getProperty("line.separator"));
		position.append(PositionParserConstants.REQUISITION_APPROVAL_TEMPLATE + ":" + System.getProperty("line.separator"));
		position.append("My Template" + System.getProperty("line.separator"));
		return position.toString();
	}
	
	public String getMultiplePositions(int count) {
		StringBuffer position = new StringBuffer();
		for(int i = 0; i < count; i++) {
			if(position.length() > 0) {
				position.append(System.getProperty("line.separator"));
				position.append(PositionParserConstants.POSITION_DEFINITION_SEPARATOR);
				position.append(System.getProperty("line.separator"));
			}
			position.append(getPosition());
		}
		return position.toString();
	}
}
