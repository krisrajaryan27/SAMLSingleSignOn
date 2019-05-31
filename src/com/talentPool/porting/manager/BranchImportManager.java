package com.talentPool.porting.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.exception.AliasExistException;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.porting.dataobject.FailedStatusObject;

public class BranchImportManager extends ImportManager{
	public List<FailedStatusObject> saveData(List<SimpleDataObject> entities){
		MastersManager manager = new MastersManager();
		List<FailedStatusObject> failedObjects = new ArrayList<FailedStatusObject>();
		int counter = 2;
		
		for( SimpleDataObject simpleDataObject : entities) {
			String[] aliases = new String[3];
			aliases[0]=simpleDataObject.getString("fld1");
			aliases[1]=simpleDataObject.getString("fld2");
			aliases[2]=simpleDataObject.getString("fld3");
			if(!Utils.isBlankOrNull(simpleDataObject.getString("fld0"))){
				try{
					manager.addBranchToDB(simpleDataObject.getString("fld0"), aliases);
				} catch (MasterExistException e) {
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("admin_master_branch_cat.error")));					
				} catch (AliasExistException e) {
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("admin_master_alias_cat.error")));	
				} catch (Exception e) {
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("admin_master.errors.cannot_add_branch")));
				}
			}else{
				failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, "Branch name not specified"));
			}
			counter++;
		}
		return failedObjects;		
	}	
}
