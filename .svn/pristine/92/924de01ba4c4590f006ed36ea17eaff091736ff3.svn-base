package com.talentPool.porting.manager;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.exception.AliasExistException;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.porting.dataobject.FailedStatusObject;

public class InstituteImportManager extends ImportManager{
	public List<FailedStatusObject> saveData(List<SimpleDataObject> entities){
		MastersManager manager = new MastersManager();
		List<FailedStatusObject> failedObjects = new ArrayList<FailedStatusObject>();
		int counter = 2;
		for(   SimpleDataObject simpleDataObject : entities) {
			String[] aliases = new String[3];
			aliases[0]=simpleDataObject.getString("fld1");
			aliases[1]=simpleDataObject.getString("fld2");
			aliases[2]=simpleDataObject.getString("fld3");
			if(!Utils.isBlankOrNull(simpleDataObject.getString("fld0"))){
				try{
					manager.addInstituteToDB(simpleDataObject.getString("fld0"), aliases);
				} catch (MasterExistException e) {
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("admin_master_institute_exists.error")));					
				} catch (AliasExistException e) {
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("admin_master_institute_alias_exists.error")));	
				} catch (Exception e) {
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, "Can not add this institute"));
				}
			}else{
				failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, "Institute name not specified"));
			}
			counter++;
		}
		return failedObjects;		
	}	
}
