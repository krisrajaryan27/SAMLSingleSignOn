/**
 * 
 */
package com.talentPool.reports.views;

import java.math.BigDecimal;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author pallavi
 *
 */
public class OverallRecruitmentCostDetail extends SimpleDataObject {	
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
	public void setCost(BigDecimal cost) {
		setAttribute("cost", cost);
	}
	/**
	 * @return the costType
	 */
	public String getCostType() {
		return getString("costType");
	}
	/**
	 * @param costType 
	 * 			the costType to set
	 */
	public void setCostType(String costType) {
		setAttribute("costType", costType);
	}
	/**
	 * @return the costTypeId
	 */
	public String getCostTypeId() {
		return getString("costTypeId");
	}
	/**
	 * @param costTypeId 
	 * 			the costTypeId to set
	 */
	public void setCostTypeId(String costTypeId) {
		setAttribute("costTypeId", costTypeId);
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
