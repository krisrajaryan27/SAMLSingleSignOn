/**
 * 
 */
package com.talentPool.notifier.dataobject;

import java.util.Date;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class OutBoundData extends SimpleDataObject {

	/**
	 * @return the entityId
	 */
	public String getEntityId() {
		return getString("entityId");
	}

	/**
	 * @param entityId
	 *            the entityId to set
	 */
	public void setEntityId(String entityId) {
		setAttribute("entityId", entityId);
	}

	/**
	 * @return the entityType
	 */
	public String getEntityType() {
		return getString("entityType");
	}

	/**
	 * @param entityType
	 *            the entityType to set
	 */
	public void setEntityType(String entityType) {
		setAttribute("entityType", entityType);
	}

	/**
	 * @return the outboundId
	 */
	public int getOutboundId() {
		return getInt("outboundId");
	}

	/**
	 * @param outboundId
	 *            the outboundId to set
	 */
	public void setOutboundId(int outboundId) {
		setAttribute("outboundId", new Integer(outboundId));
	}

	/**
	 * @return the outboundMode
	 */
	public String getOutboundMode() {
		return getString("outboundMode");
	}

	/**
	 * @param outboundMode
	 *            the outboundMode to set
	 */
	public void setOutboundMode(String outboundMode) {
		setAttribute("outboundMode", outboundMode);
	}

	/**
	 * @return the outboundType
	 */
	public String getOutboundType() {
		return getString("outboundType");
	}

	/**
	 * @param outboundType
	 *            the outboundType to set
	 */
	public void setOutboundType(String outboundType) {
		setAttribute("outboundType", outboundType);
	}

	/**
	 * @return the sendDate
	 */
	public Date getSendDate() {
		return getDate("sendDate");
	}

	/**
	 * @param sendDate
	 *            the sendDate to set
	 */
	public void setSendDate(Date sendDate) {
		setAttribute("sendDate", sendDate);
	}

	/**
	 * @return the sendFrom
	 */
	public String getSendFrom() {
		return getString("sendFrom");
	}

	/**
	 * @param sendFrom
	 *            the sendFrom to set
	 */
	public void setSendFrom(String sendFrom) {
		setAttribute("sendFrom", sendFrom);
	}

	/**
	 * @return the sendTo
	 */
	public String getSendTo() {
		return getString("sendTo");
	}

	/**
	 * @param sendTo
	 *            the sendTo to set
	 */
	public void setSendTo(String sendTo) {
		setAttribute("sendTo", sendTo);
	}

	/**
	 * @return the sentDate
	 */
	public Date getSentDate() {
		return getDate("sentDate");
	}

	/**
	 * @param sentDate
	 *            the sentDate to set
	 */
	public void setSentDate(Date sentDate) {
		setAttribute("sentDate", sentDate);
	}

	/**
	 * @return the sentMessageId
	 */
	public String getSentMessageId() {
		return getString("sentMessageId");
	}

	/**
	 * @param sentMessageId
	 *            the sentMessageId to set
	 */
	public void setSentMessageId(String sentMessageId) {
		setAttribute("sentMessageId", sentMessageId);
	}

	/**
	 * @return the sentStatus
	 */
	public String getSentStatus() {
		return getString("sentStatus");
	}

	/**
	 * @param sentStatus
	 *            the sentStatus to set
	 */
	public void setSentStatus(String sentStatus) {
		setAttribute("sentStatus", sentStatus);
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

}
