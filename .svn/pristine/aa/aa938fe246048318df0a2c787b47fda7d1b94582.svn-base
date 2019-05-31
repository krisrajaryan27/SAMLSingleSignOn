package com.talentPool.porting.manager;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.dataobject.SkillCategoryData;
import com.talentPool.masters.exception.AliasExistException;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.porting.dataobject.FailedStatusObject;

public class SkillImportManager extends ImportManager{
	public List<FailedStatusObject> saveData(List<SimpleDataObject> entities){
		MastersManager manager = new MastersManager();
		List<FailedStatusObject> failedObjects = new ArrayList<FailedStatusObject>();
		int counter = 2;
		for(   SimpleDataObject simpleDataObject : entities) {
			String[] aliases = new String[3];
			aliases[0]=simpleDataObject.getString("fld2");
			aliases[1]=simpleDataObject.getString("fld3");
			aliases[2]=simpleDataObject.getString("fld4");
			Boolean errorSplChr = false; 
			if(!Utils.isBlankOrNull(simpleDataObject.getString("fld0")) && !Utils.isBlankOrNull(simpleDataObject.getString("fld1"))){
				try{
					try{
						if(!Utils.hasValidCharacter(simpleDataObject.getString("fld0"))){
							errorSplChr=true;
							throw new MasterExistException("invalid special char in input");
						}else if(!Utils.hasValidCharacter(aliases[0])){
							errorSplChr=true;
							throw new AliasExistException("invalid special char in alias {" + aliases[0] + "} input");
						}else if(!Utils.hasValidCharacter(aliases[1])){
							errorSplChr=true;
							throw new AliasExistException("invalid special char in alias {" +aliases[1]+ "} input");
						}else if(!Utils.hasValidCharacter(aliases[2])){
							errorSplChr=true;
							throw new AliasExistException("invalid special char in alias {" + aliases[2] + "} input");
						}
					}catch (MasterExistException e) {
						failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, "invalid special char in skill name input"));
					}catch (AliasExistException e) {
						failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, e.getLocalizedMessage()));	
					}
					String skillCategoryId = null;
					try{
						skillCategoryId = manager.addSkillCategorytoDB(simpleDataObject.getString("fld1"));						
					}catch(MasterExistException e){
						SkillCategoryData skillCategory = manager.getSkillCategoryByName(simpleDataObject.getString("fld1"));
						skillCategoryId = skillCategory.getItemId()+"";
					}
					if(!errorSplChr){
						manager.addSkilltoSkillCategory(simpleDataObject.getString("fld0"), skillCategoryId, aliases);
					}
				}catch (MasterExistException e) {
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("admin_master_skill.error.duplicate")));					
				} catch (AliasExistException e) {
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("admin_master_skill_alias.error.duplicate")));	
				} catch (Exception e) {
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("admin_master.errors.cannot_add_skill")));
				}
			}else{
				failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, "Skill name of category not specified."));			
			}
			counter++;
		}
		return failedObjects;		
	}	
}
