/**
 * 
 */
package com.talentPool.employeeservice.dataobject;

import java.util.Date;

/**
 * @author Shantanu
 *
 */
public class EcommunicationData {
	private String communicationText;
	private Date communicationDate;
	/**
	 * @return the communicationDate
	 */
	public Date getCommunicationDate() {
		return communicationDate;
	}
	/**
	 * @param communicationDate the communicationDate to set
	 */
	public void setCommunicationDate(Date communicationDate) {
		this.communicationDate = communicationDate;
	}
	/**
	 * @return the communicationText
	 */
	public String getCommunicationText() {
		return communicationText;
	}
	/**
	 * @param communicationText the communicationText to set
	 */
	public void setCommunicationText(String communicationText) {
		this.communicationText = communicationText;
	}
}
