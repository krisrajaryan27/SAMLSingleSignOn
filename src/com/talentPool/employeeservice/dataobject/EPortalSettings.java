package com.talentPool.employeeservice.dataobject;

import java.util.Map;

public class EPortalSettings {
	private Map<String,String> ePortalSettingsMap;

	/**
	 * @return the ePortalSettingsMap
	 */
	public Map<String, String> getEPortalSettingsMap() {
		return ePortalSettingsMap;
	}

	/**
	 * @param ePortalSettingsMap the ePortalSettingsMap to set
	 */
	public void setEPortalSettingsMap(Map<String, String> ePortalSettingsMap) {
		this.ePortalSettingsMap = ePortalSettingsMap;
	} 
}