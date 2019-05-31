/**
 * 
 */
package com.talentPool.reports.views;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author pallavi
 *
 */
public class OverallRecruitmentCostSummary extends SimpleDataObject {	
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
	public void setCost(String cost) {
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
}
