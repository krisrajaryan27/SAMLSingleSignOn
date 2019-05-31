/**
 * 
 */
package com.talentPool.reports.views;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;

/**
 * @author pallavi
 *
 */
public class SourcewiseImportView extends SimpleDataObject {
	/**
	 * @return the count
	 */
	public Integer getCount() {
		return new Integer(getInt("count"));
	}
	/**
	 * @return the importDate
	 */
	public String getImportDate() {
		try {
			return DateUtils.getSystemDateFormat(getDate("importDate"));			
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
		}
		return null;
	}
	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return getString("sourceId");
	}
	/**
	 * @return the sourceTitle
	 */
	public String getSourceTitle() {
		return getString("sourceTitle");
	}
	
	/**
	 * @param sourceCategory the sourceCategory to set
	 */
	public void setSourceCategory(String sourceCategory) {
		setAttribute("sourceCategory", sourceCategory);
	}
	/**
	 * @return the sourceCategory
	 */
	public String getSourceCategory() {
		return getString("sourceCategory");
	}
		
	/**
	 * @param countCategory the countCategory to set
	 */
	public void setCountCategory(String countCategory) {
		setAttribute("countCategory", countCategory);
	}
	/**
	 * @return the countCategory
	 */
	public String getCountCategory() {
		return getString("countCategory");
	}
	
}
