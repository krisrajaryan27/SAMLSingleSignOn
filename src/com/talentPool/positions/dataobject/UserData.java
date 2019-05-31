/*
 * Created on Jul 6, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.talentPool.positions.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * 
 * @author pallavi
 * @date Dec 11, 2006
 */
public class UserData extends SimpleDataObject {
	/**
	 * @return Returns the userId.
	 */
	public int getUserId() {
		return getInt("userId");
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserId(int userId) {
		setAttribute("userId", new Integer(userId));
	}
	
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return getString("userName");
	}
	
	/**
	 * @param userName
	 * 				The userName to set.
	 */
	public void setUserName(String userName) {
		setAttribute("userName", userName);
	}
	
	public String getUserEmail() {
		return getString("userEmail");
	}
	
	public void setUserEmail(String userEmail) {
		setAttribute("userEmail", userEmail);
	}
}
