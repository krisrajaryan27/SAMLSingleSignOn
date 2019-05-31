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
public class TransactionCostView extends SimpleDataObject {
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return getDouble("amount");
	}
	/**
	 * @param amount 
	 * 			the amount to set
	 */
	public void setAmount(Double amount) {
		setAttribute("amount", amount);
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		try {
			return DateUtils.getSystemDateFormat(getDate("date"));			
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
		}
		return null;
	}
	/**
	 * @param date 
	 * 			the date to set
	 */
	public void setDate(String date) {
		setAttribute("date", date);
	}
	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return getString("purpose");
	}
	/**
	 * @param purpose 
	 * 			the purpose to set
	 */
	public void setPurpose(String purpose) {
		setAttribute("purpose", purpose);
	}
	/**
	 * @return the purposeId
	 */
	public String getPurposeId() {
		return getString("purposeId");
	}
	/**
	 * @param purposeId 
	 * 			the purposeId to set
	 */
	public void setPurposeId(String purposeId) {
		setAttribute("purposeId", purposeId);
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return getString("remark");
	}
	/**
	 * @param remark 
	 * 			the remark to set
	 */
	public void setRemark(String remark) {
		setAttribute("remark", remark);
	}
	/**
	 * @return the positionTitle
	 */
	public String getPositionTitle() {
		return getString("positionTitle");
	}
	/**
	 * @param positionTitle 
	 * 			the positionTitle to set
	 */
	public void setPositionTitle(String positionTitle) {
		setAttribute("positionTitle", positionTitle);
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
	
}
