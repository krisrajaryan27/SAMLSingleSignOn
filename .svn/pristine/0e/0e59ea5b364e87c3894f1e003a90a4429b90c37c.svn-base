/**
 * 
 */
package com.talentPool.export.bo;

import com.talentPool.export.ExportConstants;

/**
 * @author pallavi
 *
 */
public class FieldsFactory {
	public Fields getFields(String fieldType) {
		if(ExportConstants.ENTITY_SELECT_STAGE_APPLICANTS.equals(fieldType)) {
			return new ApplicantFields();
		} else if(ExportConstants.ENTITY_HIRE_STAGE_APPLICANTS.equals(fieldType)) {
			return new ApplicantFields();
		}else if(ExportConstants.ENTITY_POSITION_SUMMARY_APPLICANTS.equals(fieldType)) {
			return new ApplicantFields();
		}else if(ExportConstants.ENTITY_SKILLS.equals(fieldType)) {
			return new SkillFields();
		}else if(ExportConstants.ENTITY_BRANCHES.equals(fieldType)) {
			return new BranchFields();
		}else if(ExportConstants.ENTITY_DEGREES.equals(fieldType)) {
			return new DegreeFields();
		}else if(ExportConstants.ENTITY_INSTITUTES.equals(fieldType)) {
			return new InstituteFields();
		}else if(ExportConstants.ENTITY_SOURCES.equals(fieldType)) {
			return new SourceFields();
		}else if(ExportConstants.ENTITY_LOCATIONS.equals(fieldType)) {
			return new LocationFields();
		}else if(ExportConstants.ENTITY_USERS.equals(fieldType)) {
			return new UserFields();
		}else if(ExportConstants.ENTITY_REJECTED_APPLICANTS.equals(fieldType)) {
			return new RejectedApplicantFields();
		}else if(ExportConstants.ENTITY_POSITION_DETAILS.equals(fieldType)){
			return new PositionFields();
		}
		return null;
	}
}
