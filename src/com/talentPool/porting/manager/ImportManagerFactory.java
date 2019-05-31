package com.talentPool.porting.manager;

import com.talentPool.export.ExportConstants;

public class ImportManagerFactory {
	public ImportManager getManager(String fieldType) {
		if(ExportConstants.ENTITY_BRANCHES.equals(fieldType)) {
			return new BranchImportManager();
		}else if(ExportConstants.ENTITY_DEGREES.equals(fieldType)){
			return new DegreeImportManager();
		}else if(ExportConstants.ENTITY_INSTITUTES.equals(fieldType)){
			return new InstituteImportManager();
		}else if(ExportConstants.ENTITY_LOCATIONS.equals(fieldType)){
			return new LocationImportManager();
		}else if(ExportConstants.ENTITY_SKILLS.equals(fieldType)){
			return new SkillImportManager();
		}else if(ExportConstants.ENTITY_SOURCES.equals(fieldType)){
			return new SourceImportManager();
		}else if(ExportConstants.ENTITY_USERS.equals(fieldType)){
			return new UserImportManager();
		}
		return null;
	}
}
