/**
 * 
 */
package com.talentPool.masters.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author pallavi
 *
 */
public class InboxFolderData extends SimpleDataObject {
	/**
	 * @return the folderId
	 */
	public String getFolderId() {
		return getString("folderId");
	}
	/**
	 * @param folderId the folderId to set
	 */
	public void setFolderId(String folderId) {
		setAttribute("folderId", folderId);
	}
	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return getString("folderName");
	}
	/**
	 * @param folderName the folderName to set
	 */
	public void setFolderName(String folderName) {
		setAttribute("folderName", folderName);
	}	
	/**
	 * @return the systemDefined
	 */
	public String getSystemDefined() {
		return getString("systemDefined");
	}
	/**
	 * @param systemDefined the systemDefined to set
	 */
	public void setSystemDefined(String systemDefined) {
		setAttribute("systemDefined", systemDefined);
	}
	/**
	 * @return the emailCount
	 */
	public String getEmailCount() {
		return getString("emailCount");
	}
	/**
	 * @param emailCount the emailCount to set
	 */
	public void setEmailCount(String emailCount) {
		setAttribute("emailCount", emailCount);
	}
}
