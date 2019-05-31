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
public class PositionwiseRecruitmentCostSummaryView extends SimpleDataObject {	
	/**
	 * @return the cost
	 */
	public Double getCost() {
		return getDouble("cost");
	}
	/**
	 * @param cost 
	 * 			the cost to set
	 */
	public void setCost(Double cost) {
		setAttribute("cost", cost);
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
