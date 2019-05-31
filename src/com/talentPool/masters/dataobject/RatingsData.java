/**
 * 
 */
package com.talentPool.masters.dataobject;

import java.util.ArrayList;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class RatingsData extends SimpleDataObject {
	ArrayList<RatingFieldsData> ratingFields = null;
	/**
	 * @return the ratingId
	 */
	public String getRatingId() {
		return getString("ratingId");
	}

	/**
	 * @param ratingId
	 *            the ratingId to set
	 */
	public void setRatingId(String ratingId) {
		setAttribute("ratingId", ratingId);
	}

	/**
	 * @return the ratingStatus
	 */
	public String getRatingStatus() {
		return getString("ratingStatus");
	}

	/**
	 * @param ratingStatus
	 *            the ratingStatus to set
	 */
	public void setRatingStatus(String ratingStatus) {
		setAttribute("ratingStatus", ratingStatus);
	}

	/**
	 * @return the ratingTitle
	 */
	public String getRatingTitle() {
		return getString("ratingTitle");
	}

	/**
	 * @param ratingTitle
	 *            the ratingTitle to set
	 */
	public void setRatingTitle(String ratingTitle) {
		setAttribute("ratingTitle", ratingTitle);
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return getString("userId");
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}
	
	
	/**
	 * @return the ratingFields
	 */
	public ArrayList<RatingFieldsData> getRatingFields() {
		return ratingFields;
	}

	/**
	 * @param ratingFields the ratingFields to set
	 */
	public void setRatingFields(ArrayList<RatingFieldsData> ratingFields) {
		this.ratingFields = ratingFields;
	}
	
}
