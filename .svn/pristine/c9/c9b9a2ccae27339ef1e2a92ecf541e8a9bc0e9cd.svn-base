/**
 * 
 */
package com.talentPool.user.dataobject;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;

/**
 * @author pallavi
 * @date Jan 25, 2007
 */
public class LastViewedEntity extends SimpleDataObject {
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return getString("description");
	}
	
	public String getDescriptionTrimmed() {
		String desc= getString("description");
		if(!Utils.isBlankOrNull(desc)){
			if(desc.length()>20){
				return desc.substring(0,20)+ "...";
			}
		}
		return desc;
	}
	/**
	 * @param description 
	 * 			The description to set.
	 */
	public void setDescription(String description) {
		setAttribute("description", description);
	}
	
	/**
	 * @return Returns the entityId.
	 */
	public String getEntityId() {
		return getString("entityId");
	}
	
	/**
	 * @param entityId 
	 * 			The entityId to set.
	 */
	public void setEntityId(String entityId) {
		setAttribute("entityId", entityId);
	}
	
	/**
	 * @return Returns the entityType.
	 */
	public String getEntityType() {
		return getString("entityType");
	}
	
	/**
	 * @param entityType 
	 * 			The entityType to set.
	 */
	public void setEntityType(String entityType) {
		setAttribute("entityType", entityType);
	}
	
	/**
	 * @return Returns the lastViewed.
	 */
	public String getLastViewed() {
		return getString("lastViewed");
	}
	
	/**
	 * @param lastViewed 
	 * 			The lastViewed to set.
	 */
	public void setLastViewed(String lastViewed) {
		setAttribute("lastViewed", lastViewed);
	}
	
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return getString("title");
	}
	
	/**
	 * @param title 
	 * 			The title to set.
	 */
	public void setTitle(String title) {
		setAttribute("title", title);
	}
	
}
