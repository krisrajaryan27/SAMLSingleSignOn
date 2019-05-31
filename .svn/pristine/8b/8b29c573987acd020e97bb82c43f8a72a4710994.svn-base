package com.talentPool.porting.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.dataobject.SourceTypeData;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.porting.dataobject.FailedStatusObject;
import com.talentPool.user.exception.EmployeeCodeExistException;
import com.talentPool.user.exception.SourceExistException;

public class SourceImportManager extends ImportManager{
	public List<FailedStatusObject> saveData(List<SimpleDataObject> entities){
		MastersManager manager = new MastersManager();
		List<FailedStatusObject> failedObjects = new ArrayList<FailedStatusObject>();
		int counter = 2;
		for(   SimpleDataObject simpleDataObject : entities) {			

			if(!Utils.isBlankOrNull(simpleDataObject.getString("fld0")) && !Utils.isBlankOrNull(simpleDataObject.getString("fld1"))){
				try{
					String sourceCategoryId = null;
					try{
						sourceCategoryId = manager.addSourceCategorytoDB(simpleDataObject.getString("fld1"));						
					}catch(MasterExistException e){
						SourceTypeData sourceType = manager.getSourceTypeByName(simpleDataObject.getString("fld1"));
						sourceCategoryId = sourceType.getItemId()+"";
					}			
					SourceData data = new SourceData();
					
					data.setSourceTypeId(sourceCategoryId);
					data.setSourceTitle(simpleDataObject.getString("fld0"));
					data.setSourceEmail(simpleDataObject.getString("fld2"));
					data.setSourcePhone(simpleDataObject.getString("fld3"));
					data.setSourceMobile(simpleDataObject.getString("fld4"));
					data.setSendEmailToSource(simpleDataObject.getString("fld6").equals("Yes")?"1":"0");
					data.setSendSMSToSource(simpleDataObject.getString("fld7").equals("Yes")?"1":"0");					
					data.setLockInPeriodOnImport(simpleDataObject.getString("fld8").replace(" mos", ""));					
					data.setEmployeeCode(Utils.isBlankOrNull(simpleDataObject.getString("fld5"))?null:simpleDataObject.getString("fld5"));
					
					if(manager.employeeCodeExists(data.getEmployeeCode())){
						throw new EmployeeCodeExistException();
					}
					
					manager.addSourcetoSourceType(data, null) ; 
				}catch(EmployeeCodeExistException e) {
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("admin_master_source.error.duplicate_employee_code")));
				}catch (SourceExistException e) {
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("admin_master_source.error.duplicate")));
				}catch(Exception e){
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("admin_master_source.errors.cannot_add_source")));
				}
			}else{
				failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, "Souce name of category is not specified."));
			}
			counter++;
		}
		return failedObjects;		
	}	
}
