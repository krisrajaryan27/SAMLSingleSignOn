/**
 * 
 */
package com.talentPool.reports.views;

import java.sql.Timestamp;
import java.util.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;

/**
 * @author Ajeet
 *
 */
public class AuditTrailView extends SimpleDataObject {

	/*public String getDateCreated() {
		return getString("dateCreated");
	}*/
	/**
	 * @return dateCreated
	 */
	public String getDateCreated() {
		return getString("dateCreated");
	}

	/**
	 * @param dateCreated
	 */
	public void setDateCreated(Date dateCreated) {
		setAttribute("dateCreated", dateCreated);
	}
	/**
	 * @return the sourceId
	 */
	public String getAuditId() {
		return getString("auditId");
	}
	public void setAuditId(String auditId) {
		setAttribute("auditId", auditId);
	}
	/**
	 * @return the sourceTitle
	 */
	public String getAuditDesc() {
		return getString("auditDesc");
	}
	public void setAuditDesc(String auditDesc) {
		setAttribute("auditDesc", auditDesc);
	}
	/**
	 * @return the auditType
	 */
	public String getAuditType() {
		return getString("auditType");
	}

	/**
	 * @param auditType
	 *            the auditType to set
	 */
	public void setAuditType(String auditType) {
		setAttribute("auditType", auditType);
	}
	/**
	 * @return the auditType
	 */
	public String getEntityField() {
		return getString("entityField");
	}

	/**
	 * @param auditType
	 *            the auditType to set
	 */
	public void setEntityField(String entityField) {
		setAttribute("entityField", entityField);
	}
	/**
	 * @return the userId
	 */
	public String getUserName() {
		return getString("userName");
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserName(String userName) {
		setAttribute("userName", userName);
	}
}
