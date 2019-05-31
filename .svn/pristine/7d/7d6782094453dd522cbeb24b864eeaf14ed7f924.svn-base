/**
 * 
 */
package com.talentPool.export.manager;

import com.talentPool.export.ExportConstants;

/**
 * @author pallavi
 *
 */
public class ExportManagerFactory {
	public ExportManager getManager(String fieldType) {
		if(ExportConstants.ENTITY_SELECT_STAGE_APPLICANTS.equals(fieldType)) {
			return new ApplicantExportManager();
		} else if(ExportConstants.ENTITY_HIRE_STAGE_APPLICANTS.equals(fieldType)) {
			return new ApplicantExportManager();
		}else if(ExportConstants.ENTITY_POSITION_SUMMARY_APPLICANTS.equals(fieldType)) {
			return new ApplicantExportManager();
		} else if(ExportConstants.ENTITY_SKILLS.equals(fieldType)) {
			return new SkillExportManager();
		} else if(ExportConstants.ENTITY_BRANCHES.equals(fieldType)) {
			return new BranchExportManager();
		} else if(ExportConstants.ENTITY_DEGREES.equals(fieldType)) {
			return new DegreeExportManager();
		} else if(ExportConstants.ENTITY_INSTITUTES.equals(fieldType)) {
			return new InstituteExportManager();
		}else if(ExportConstants.ENTITY_SOURCES.equals(fieldType)) {
			return new SourceExportManager();
		} else if(ExportConstants.ENTITY_LOCATIONS.equals(fieldType)) {
			return new LocationExportManager();
		}else if(ExportConstants.ENTITY_REJECTED_APPLICANTS.equals(fieldType)) {
			return new RejectedApplicantExportManager();
		}else if(ExportConstants.ENTITY_POSITION_DETAILS.equals(fieldType)){
			return new PositionExportManager();
		}
		return null;
	}
}
