package com.talentPool.porting.manager;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.dataobject.LocationData;
import com.talentPool.masters.manager.LocationManager;
import com.talentPool.porting.dataobject.FailedStatusObject;

public class LocationImportManager extends ImportManager{
	public List<FailedStatusObject> saveData(List<SimpleDataObject> entities){
		LocationManager manager = new LocationManager();
		List<FailedStatusObject> failedObjects = new ArrayList<FailedStatusObject>();
		int counter = 2;
		for(SimpleDataObject simpleDataObject : entities) {
			if(!Utils.isBlankOrNull(simpleDataObject.getString("fld0"))){
				try{
					LocationData location = manager.getLocationByName(simpleDataObject.getString("fld0"));
					if(location != null)
						failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("master_locations.error.location_name")));
					else					
						manager.addLocation(simpleDataObject.getString("fld0"));
				}catch( Exception e){
					failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, TPLabels.getLabel("master_locations.error.save_location")));
				}
			}else{
				failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld0"),counter, "Location name not specified."));
			}
			counter++;
		}
		return failedObjects;		
	}	
}
