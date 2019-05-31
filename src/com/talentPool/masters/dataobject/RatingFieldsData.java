/**
 * 
 */
package com.talentPool.masters.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class RatingFieldsData extends SimpleDataObject {
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
	 * @return the ratingFieldDesc
	 */
	public String getRatingFieldDesc() {
		return getString("ratingFieldDesc");
	}

	/**
	 * @param ratingFieldDesc
	 *            the ratingFieldDesc to set
	 */
	public void setRatingFieldDesc(String ratingFieldDesc) {
		setAttribute("ratingFieldDesc", ratingFieldDesc);
	}

	/**
	 * @return the ratingFieldId
	 */
	public String getRatingFieldId() {
		return getString("ratingFieldId");
	}

	/**
	 * @param ratingFieldId
	 *            the ratingFieldId to set
	 */
	public void setRatingFieldId(String ratingFieldId) {
		setAttribute("ratingFieldId", ratingFieldId);
	}

	/**
	 * @return the ratingFieldRank
	 */
	public int getRatingFieldRank() {
		return getInt("ratingFieldRank");
	}

	/**
	 * @param ratingFieldRank
	 *            the ratingFieldRank to set
	 */
	public void setRatingFieldRank(int ratingFieldRank) {
		setAttribute("ratingFieldRank", ratingFieldRank);
	}

	public String getRatingFieldStatus() {
		return getString("ratingFieldStatus");
	}

	public void setRatingFieldStatus(String ratingFieldStatus) {
		setAttribute("ratingFieldStatus", ratingFieldStatus);
	}

}
