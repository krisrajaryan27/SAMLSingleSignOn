/**
 * 
 */
package com.talentPool.positions.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author pallavi
 *
 */
public class PositionVendorData extends SimpleDataObject {
	/**
	 * @return the positionOpenToVendor
	 */
	public String getPositionOpenToVendor() {
		return getString("positionOpenToVendor");
	}
	/**
	 * @param positionOpenToVendor 
	 * 			the positionOpenToVendor to set
	 */
	public void setPositionOpenToVendor(String positionOpenToVendor) {
		setAttribute("positionOpenToVendor", positionOpenToVendor);
	}
	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return getString("sourceId");
	}
	/**
	 * @param sourceId 
	 * 			the sourceId to set
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
	 * 			the sourceTitle to set
	 */
	public void setSourceTitle(String sourceTitle) {
		setAttribute("sourceTitle", sourceTitle);
	}
	/**
	 * @return the sourceType
	 */
	public String getSourceType() {
		return getString("sourceType");
	}
	/**
	 * @param sourceType 
	 * 			the sourceType to set
	 */
	public void setSourceType(String sourceType) {
		setAttribute("sourceType", sourceType);
	}
	/**
	 * @return the sourceTypeId
	 */
	public String getSourceTypeId() {
		return getString("sourceTypeId");
	}
	/**
	 * @param sourceTypeId 
	 * 			the sourceTypeId to set
	 */
	public void setSourceTypeId(String sourceTypeId) {
		setAttribute("sourceTypeId", sourceTypeId);
	}	
}
