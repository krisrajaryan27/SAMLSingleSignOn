/*
 * Created on Jul 6, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.talentPool.calendar.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author pallavi
 * @date Jul 6, 2006
 */
public class TimeData extends SimpleDataObject {
	public TimeData() {
		super();
	}
	
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return getString("description");
	}
	
	/**
	 * @param description 
	 * 				The description to set.
	 */
	public void setDescription(String description) {
		setAttribute("description", description);
	}
	
	/**
	 * @return Returns the numberOfMinutes.
	 */
	public int getNumberOfMinutes() {
		return getInt("numberOfMinutes");
	}
	
	/**
	 * @param numberOfMinutes 
	 * 					The numberOfMinutes to set.
	 */
	public void setNumberOfMinutes(int numberOfMinutes) {
		setAttribute("numberOfMinutes", new Integer(numberOfMinutes));
	}
}
