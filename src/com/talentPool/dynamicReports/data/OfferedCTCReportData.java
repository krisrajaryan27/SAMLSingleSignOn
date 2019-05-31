package com.talentPool.dynamicReports.data;

import com.talentPool.common.db.SimpleDataObject;

public class OfferedCTCReportData extends SimpleDataObject {
	/**
	 * @return the cost
	 */
	public String getApplicantId() {
		return getString("applicantId");
	}
	/**
	 * @param cost 
	 * 			the cost to set
	 */
	public void setApplicantId(String applicantId) {
		setAttribute("applicantId", applicantId);
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
	 * @return the positionId
	 */
	public String getJoiningBonus() {
		return getString("joiningBonus");
	}
	/**
	 * @param offerCode 
	 * 			the offerCode to set
	 */
	public void setJoiningBonus(String joiningBonus) {
		setAttribute("joiningBonus", joiningBonus);
	}
	/**
	 * @return the positionId
	 */
	public String getTemplateId() {
		return getString("templateId");
	}
	/**
	 * @param offerCode 
	 * 			the offerCode to set
	 */
	public void setTemplateId(String templateId) {
		setAttribute("templateId", templateId);
	}
	/**
	 * @return the dateCreated
	 */
	public String getDateCreated() {
		return getString("dateCreated");
	}
	/**
	 * @param dateCreated 
	 * 			the dateCreated to set
	 */
	public void setDateCreated(String dateCreated) {
		setAttribute("dateCreated", dateCreated);
	}
	/**
	 * @return the offerCode
	 */
	public String getOfferCode() {
		return getString("offerCode");
	}
	/**
	 * @param offerCode 
	 * 			the offerCode to set
	 */
	public void setOfferCode(String offerCode) {
		setAttribute("offerCode", offerCode);
	}
	
	public String getRequisitionerNumber() {
		return getString("requisitionerNumber");
	}
	public void setRequisitionerNumber(String requisitionerNumber) {
		setAttribute("requisitionerNumber", requisitionerNumber);
	}
	
	
	public String getName() {
		return getString("name");
	}
	public void setName(String name) {
		setAttribute("name", name);
	}
	
	
	public String getDoj() {
		return getString("doj");
	}
	public void setDoj(String doj) {
		setAttribute("doj", doj);
	}
	
	
	public String getDesignation() {
		return getString("designation");
	}
	public void setDesignation(String designation) {
		setAttribute("designation", designation);
	}
	
	
	public String getBand() {
		return getString("band");
	}
	public void setBand(String band) {
		setAttribute("band", band);
	}
	
	
	public String getLevel() {
		return getString("level");
	}
	public void setLevel(String level) {
		setAttribute("level", level);
	}
	
	
	public String getBandLevel() {
		return getString("bandLevel");
	}
	public void setBandLevel(String bandLevel) {
		setAttribute("bandLevel", bandLevel);
	}
	
	public String getAddressLine1() {
		return getString("addressLine1");
	}
	public void setAddressLine1(String addressLine1) {
		setAttribute("addressLine1", addressLine1);
	}
	
	public String getAddressLine2() {
		return getString("addressLine2");
	}
	public void setAddressLine2(String addressLine2) {
		setAttribute("addressLine2", addressLine2);
	}
	
	public String getAddressLine3() {
		return getString("addressLine3");
	}
	public void setAddressLine3(String addressLine3) {
		setAttribute("addressLine3", addressLine3);
	}
	
	public String getTargetCTC() {
		return getString("targetCTC");
	}
	public void setTargetCTC(String targetCTC) {
		setAttribute("targetCTC", targetCTC);
	}
}
