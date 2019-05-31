/**
 * 
 */
package com.talentPool.reports.views;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;

/**
 * @author pallavi
 *
 */
public class PositionwiseRecruitmentCostDetailView extends SimpleDataObject {
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
	 * @return the positionId
	 */
	public String getPositionId() {
		return getString("positionId");
	}
	/**
	 * @param positionId 
	 * 			the positionId to set
	 */
	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}
	/**
	 * @return the positionTitle
	 */
	public String getPositionTitle() {
		if (Utils.isBlankOrNull(getDepartmentTitle())) {
			return getString("positionTitle");
		} else {
			return getDepartmentTitle().concat("-").concat(getString("positionTitle"));
		}
	}
	/**
	 * @param positionTitle 
	 * 			the positionTitle to set
	 */
	public void setPositionTitle(String positionTitle) {
		setAttribute("positionTitle", positionTitle);
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
	 * @return the source
	 */
	public String getSource() {
		return getString("source");
	}
	/**
	 * @param source 
	 * 			the source to set
	 */
	public void setSource(String source) {
		setAttribute("source", source);
	}	
	/**
	 * @return the departmentTitle
	 */
	public String getDepartmentTitle() {
		return getString("departmentTitle");
	}
	/**
	 * @param departmentTitle 
	 * 			the departmentTitle to set
	 */
	public void setDepartmentTitle(String departmentTitle) {
		setAttribute("departmentTitle", departmentTitle);
	}
}
