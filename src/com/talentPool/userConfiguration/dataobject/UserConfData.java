/**
 * 
 */
package com.talentPool.userConfiguration.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author Ajeet
 *
 */
public class UserConfData extends SimpleDataObject{

	public String getUserId() {
		return getString("userId");
	}

	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}
	
	public String getConfId() {
		return getString("confId");
	}

	public void setConfId(String confId) {
		setAttribute("confId", confId);
	}
	
	public String getConfValue() {
		return getString("confValue");
	}

	public void setConfValue(String confValue) {
		setAttribute("confValue", confValue);
	}

}
