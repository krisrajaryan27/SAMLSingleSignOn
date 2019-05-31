/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.selectionProcess.dataobject;

import java.sql.Date;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.DateUtils;

/**
 * @author PraveenK
 * @since  May 21, 2012
 */
public class OfferDetailsModifiedInteractionData extends SimpleDataObject {
	
	private static final long serialVersionUID = -1510623356880367847L;
	
	public String getInteractionId(){
		return getString("interactionId");
	}
	public void setInteractionId(String interactionId) {
		setAttribute("interactionId", interactionId);
	}
	public String getApplicantId(){
		return getString("applicantId");
	}
	public void setApplicantId(String applicantId) {
		setAttribute("applicantId", applicantId);
	}
	public String getApplicantName(){
		return getString("applicantName");
	}
	public void setApplicantName(String applicantName) {
		setAttribute("applicantName", applicantName);
	}
	public String getInteractionType(){
		return getString("interactionType");
	}
	public void setInteractionType(String interactionType) {
		setAttribute("interactionType", interactionType);
	}
	public String getInteractionTitle(){
		return getString("interactionTitle");
	}
	public void setInteractionTitle(String interactionTitle) {
		setAttribute("interactionTitle", interactionTitle);
	}
	public String getPreviousCtc(){
		return getString("previousCtc");
	}
	public void setPreviousCtc(String previousCtc) {
		setAttribute("previousCtc", previousCtc);
	}
	public String getChangedCtc(){
		return getString("changedCtc");
	}
	public void setChangedCtc(String changedCtc) {
		setAttribute("changedCtc", changedCtc);
	}
	public String getPreviousBasic(){
		return getString("previousBasic");
	}
	public void setPreviousBasic(String previousBasic) {
		setAttribute("previousBasic", previousBasic);
	}
	public String getChangedBasic(){
		return getString("changedBasic");
	}
	public void setChangedBasic(String changedBasic) {
		setAttribute("changedBasic", changedBasic);
	}
	public String getPreviousDesignation(){
		return getString("previousDesignation");
	}
	public void setPreviousDesignation(String previousDesignation) {
		setAttribute("previousDesignation", previousDesignation);
	}
	public String getChangedDesignation(){
		return getString("changedDesignation");
	}
	public void setChangedDesignation(String changedDesignation) {
		setAttribute("changedDesignation", changedDesignation);
	}
	public String getPreviousLevel(){
		return getString("previousLevel");
	}
	public void setPreviousLevel(String previousLevel) {
		setAttribute("previousLevel", previousLevel);
	}
	
	public String getChangedLevel(){
		return getString("changedLevel");
	}
	public void setChangedLevel(String changedLevel) {
		setAttribute("changedLevel", changedLevel);
	}
	
	public String getPreviousInputSalaryVariable(){
		return getString("previousInputSalaryVariable");
	}
	public void setPreviousInputSalaryVariable(String previousInputSalaryVariable) {
		setAttribute("previousInputSalaryVariable", previousInputSalaryVariable);
	}
	
	public String getChangedInputSalaryVariable(){
		return getString("changedInputSalaryVariable");
	}
	public void setChangedInputSalaryVariable(String changedInputSalaryVariable) {
		setAttribute("changedInputSalaryVariable", changedInputSalaryVariable);
	}
	
	public String getUserId(){
		return getString("userId");
	}
	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}
	public String getUserName(){
		return getString("userName");
	}
	public void setUserName(String userName) {
		setAttribute("userName", userName);
	}
	public Date getDateCreated(){
		return getDate("dateCreated");
	}
	public String getDateCreatedToDisplay(){
		return DateUtils.getSystemDateTimeFormat(getDateCreated());
	}
	public void setDateCreated(Date dateCreated) {
		setAttribute("dateCreated", dateCreated);
	}
	public String getOfferCode(){
		return getString("offerCode");
	}
	public void setOfferCode(String offerCode) {
		setAttribute("offerCode", offerCode);
	}
	public String getOfferSheetName(){
		return getString("offerSheetName");
	}
	public void setOfferSheetName(String offerSheetName) {
		setAttribute("offerSheetName", offerSheetName);
	}
}
