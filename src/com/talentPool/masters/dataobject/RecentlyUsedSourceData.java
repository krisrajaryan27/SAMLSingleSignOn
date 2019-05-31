/**
 * 
 */
package com.talentPool.masters.dataobject;



import java.sql.Date;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class RecentlyUsedSourceData extends SimpleDataObject {

	public RecentlyUsedSourceData(){}
	
	public RecentlyUsedSourceData(String sourceId, String sourceTitle, Date lastUsedDate) {
		setSourceId(sourceId);
		setSourceTitle(sourceTitle);
		setLastUsedDate(lastUsedDate);
	}

	/**
	 * @return the lastUsedDate
	 */
	public Date getLastUsedDate() {
		return getDate("lastUsedDate");
	}

	/**
	 * @param lastUsedDate
	 *            the lastUsedDate to set
	 */
	public void setLastUsedDate(Date lastUsedDate) {
		setAttribute("lastUsedDate", lastUsedDate);
	}

	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return getString("sourceId");
	}

	/**
	 * @param sourceId
	 *            the sourceId to set
	 */
	public void setSourceId(String sourceId) {
		setAttribute("sourceId", sourceId);
	}

	/**
	 * @return the sourceTitle
	 */
	public String getSourceTitle() {
		return getString("sourceTitle");
	}

	/**
	 * @param sourceTitle
	 *            the sourceTitle to set
	 */
	public void setSourceTitle(String sourceTitle) {
		setAttribute("sourceTitle", sourceTitle);
	}

}
