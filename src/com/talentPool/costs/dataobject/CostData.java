/**
 * 
 */
package com.talentPool.costs.dataobject;

import java.util.Date;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class CostData extends SimpleDataObject {
	public String getCostId() {
		return getId("costId");
	}

	public void setCostId(String costId) {
		setAttribute("costId", costId);
	}

	public String getCostTypeId() {
		return getId("costTypeId");
	}

	public void setCostTypeId(String costTypeId) {
		setAttribute("costTypeId", costTypeId);
	}

	public String getCostTypeName() {
		return getString("costTypeName");
	}

	public void setCostTypeName(String costTypeName) {
		setAttribute("costTypeName", costTypeName);
	}

	public String getCostAmount() {
		return getString("costAmount");
	}

	public void setCostAmount(String costAmount) {
		setAttribute("costAmount", costAmount);
	}

	public void setCostPaidDate(Date costPaidDate) {
		setAttribute("costPaidDate", costPaidDate);
	}

	public Date getCostPaidDate() {
		try {
			return getDate("costPaidDate");
		} catch (Exception e) {
			// no need to check
		}
		return null;
	}

	public String getPositionIds() {
		return getId("positionIds");
	}

	public void setPositionIds(String positionIds) {
		setAttribute("positionIds", positionIds);
	}

	public String getSourceId() {
		return getId("sourceId");
	}

	public void setSourceId(String sourceId) {
		setAttribute("sourceId", sourceId);
	}

	public String getCostRemark() {
		return getString("costRemark");
	}

	public void setCostRemark(String costRemark) {
		setAttribute("costRemark", costRemark);
	}

	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}

	public String getUserId() {
		return getString("userId");
	}

	public void setDateCreated(Date dateCreated) {
		setAttribute("dateCreated", dateCreated);
	}

	public Date getDateCreated() {
		return getDate("dateCreated");
	}

	public void setOwnerName(String ownerName) {
		setAttribute("ownerName", ownerName);
	}

	public String getOwnerName() {
		return getString("ownerName");
	}

	public void setCostDateFrom(Date costDateFrom) {
		setAttribute("costDateFrom", costDateFrom);
	}

	public Date getCostDateFrom() {
		try {
			return getDate("costDateFrom");
		} catch (Exception e) {
			// no need to check
		}
		return null;
	}

	public void setCostDateTo(Date costDateTo) {
		setAttribute("costDateTo", costDateTo);
	}

	public Date getCostDateTo() {
		try {
			return getDate("costDateTo");
		} catch (Exception e) {
			// no need to check
		}
		return null;
	}
}
