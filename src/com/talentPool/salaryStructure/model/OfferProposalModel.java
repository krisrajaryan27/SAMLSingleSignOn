/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.salaryStructure.model;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.talentPool.common.Logger.TPLogger;

/**
 * @author PraveenK
 * @since  Apr 25, 2012
 */
public class OfferProposalModel implements Serializable {

	/**
	 * Auto Generated Serial Version UID
	 */
	private static final long serialVersionUID = 1985904284887854845L;
	
	private String applicantId;
	private String currentCTC;
	private String currentBasic;
	private String currentDesignation;
	private String currentLevel;
	
	private boolean currentCTCEditable;
	
	private String offeredCTC; // same as Target CTC
	private String offeredBasic;
	private String offeredDesignation;
	private String offeredLevel;
	private String offeredInputSalaryVariable;
	
	private boolean offeredCTCEditable; 
	private boolean offeredBasicEditable;
	private boolean offeredDesignationEditable;
	private boolean offeredLevelEditable;
	private boolean offeredInputSalaryVariableEditable;
	
	private String salCatsExistigValJson; // Salary Category existing values json
	
	private int offerProposalAction;
	
	/**
	 * @return the applicantId
	 */
	public String getApplicantId() {
		return applicantId;
	}
	/**
	 * @param applicantId the applicantId to set
	 */
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	/**
	 * @return the currentCTC
	 */
	public String getCurrentCTC() {
		return currentCTC;
	}
	/**
	 * @param currentCTC the currentCTC to set
	 */
	public void setCurrentCTC(String currentCTC) {
		this.currentCTC = currentCTC;
	}
	/**
	 * @return the offeredCTC
	 */
	public String getOfferedCTC() {
		return offeredCTC;
	}
	/**
	 * @param offeredCTC the offeredCTC to set
	 */
	public void setOfferedCTC(String offeredCTC) {
		this.offeredCTC = offeredCTC;
	}
	/**
	 * @return the currentBasic
	 */
	public String getCurrentBasic() {
		return currentBasic;
	}
	/**
	 * @param currentBasic the currentBasic to set
	 */
	public void setCurrentBasic(String currentBasic) {
		this.currentBasic = currentBasic;
	}
	/**
	 * @return the offeredBasic
	 */
	public String getOfferedBasic() {
		return offeredBasic;
	}
	/**
	 * @param offeredBasic the offeredBasic to set
	 */
	public void setOfferedBasic(String offeredBasic) {
		this.offeredBasic = offeredBasic;
	}
	/**
	 * @return the salCatsExistigValJson
	 */
	public String getSalCatsExistigValJson() {
		return salCatsExistigValJson;
	}
	/**
	 * @param salCatsExistigValJson the salCatsExistigValJson to set
	 */
	public void setSalCatsExistigValJson(String salCatsExistigValJson) {
		this.salCatsExistigValJson = salCatsExistigValJson;
	}
	
	/**
	 * Converts salCatsExistigValJson to Map with salCatId as key and existing value for corresponding sal cat  
	 * @return salCatsExistigValMap
	 */
	public Map<String,String> getSalCatsExistingValMap(){
		Map<String,String> salCatsExistigValMap = null;
		if(getSalCatsExistigValJson()!=null){
			try {
				Gson gson = new Gson();
				Type salCatsExistigValType = new TypeToken<Map<String,String>>(){}.getType();
				salCatsExistigValMap = gson.fromJson(getSalCatsExistigValJson(), salCatsExistigValType);
			} catch (JsonSyntaxException jse) {
				TPLogger.getLogger().error("salCatsJSON: "+getSalCatsExistigValJson(), jse);
			} catch (JsonParseException jpe) {
				TPLogger.getLogger().error("salCatsJSON: "+getSalCatsExistigValJson(), jpe);
			}			
		}
		return salCatsExistigValMap;
	}
	/**
	 * @return the currentDesignation
	 */
	public String getCurrentDesignation() {
		return currentDesignation;
	}
	/**
	 * @param currentDesignation the currentDesignation to set
	 */
	public void setCurrentDesignation(String currentDesignation) {
		this.currentDesignation = currentDesignation;
	}
	/**
	 * @return the currentLevel
	 */
	public String getCurrentLevel() {
		return currentLevel;
	}
	/**
	 * @param currentLevel the currentLevel to set
	 */
	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}
	/**
	 * @return the offeredDesignation
	 */
	public String getOfferedDesignation() {
		return offeredDesignation;
	}
	/**
	 * @param offeredDesignation the offeredDesignation to set
	 */
	public void setOfferedDesignation(String offeredDesignation) {
		this.offeredDesignation = offeredDesignation;
	}
	/**
	 * @return the offeredLevel
	 */
	public String getOfferedLevel() {
		return offeredLevel;
	}
	/**
	 * @param offeredLevel the offeredLevel to set
	 */
	public void setOfferedLevel(String offeredLevel) {
		this.offeredLevel = offeredLevel;
	}
	/**
	 * @return the offeredCTCEditable
	 */
	public boolean isOfferedCTCEditable() {
		return offeredCTCEditable;
	}
	/**
	 * @param offeredCTCEditable the offeredCTCEditable to set
	 */
	public void setOfferedCTCEditable(boolean offeredCTCEditable) {
		this.offeredCTCEditable = offeredCTCEditable;
	}
	/**
	 * @return the offeredBasicEditable
	 */
	public boolean isOfferedBasicEditable() {
		return offeredBasicEditable;
	}
	/**
	 * @param offeredBasicEditable the offeredBasicEditable to set
	 */
	public void setOfferedBasicEditable(boolean offeredBasicEditable) {
		this.offeredBasicEditable = offeredBasicEditable;
	}
	/**
	 * @return the offeredDesignationEditable
	 */
	public boolean isOfferedDesignationEditable() {
		return offeredDesignationEditable;
	}
	/**
	 * @param offeredDesignationEditable the offeredDesignationEditable to set
	 */
	public void setOfferedDesignationEditable(boolean offeredDesignationEditable) {
		this.offeredDesignationEditable = offeredDesignationEditable;
	}
	/**
	 * @return the offeredLevelEditable
	 */
	public boolean isOfferedLevelEditable() {
		return offeredLevelEditable;
	}
	/**
	 * @param offeredLevelEditable the offeredLevelEditable to set
	 */
	public void setOfferedLevelEditable(boolean offeredLevelEditable) {
		this.offeredLevelEditable = offeredLevelEditable;
	}
	
	/**
	 * All offered details are can be edited 
	 */
	public void setOfferedDetailsAsEditable(){
		this.offeredCTCEditable					= true; 
		this.offeredBasicEditable				= true;
		this.offeredDesignationEditable			= true;
		this.offeredLevelEditable				= true;
		this.offeredInputSalaryVariableEditable = true;
	}
	
	/**
	 * All offered details are cannot be edited 
	 */
	public void setOfferedDetailsAsNonEditable(){
		this.offeredCTCEditable					= false; 
		this.offeredBasicEditable				= false;
		this.offeredDesignationEditable			= false;
		this.offeredLevelEditable				= false;
		this.offeredInputSalaryVariableEditable = false;
	}
	/**
	 * @return the currentCTCEditable
	 */
	public boolean isCurrentCTCEditable() {
		return currentCTCEditable;
	}
	/**
	 * @param currentCTCEditable the currentCTCEditable to set
	 */
	public void setCurrentCTCEditable(boolean currentCTCEditable) {
		this.currentCTCEditable = currentCTCEditable;
	}
	/**
	 * @return the offerProposalAction
	 */
	public int getOfferProposalAction() {
		return offerProposalAction;
	}
	/**
	 * @param offerProposalAction the offerProposalAction to set
	 */
	public void setOfferProposalAction(int offerProposalAction) {
		this.offerProposalAction = offerProposalAction;
	}
	/**
	 * @return the offeredInputSalaryVariable
	 */
	public String getOfferedInputSalaryVariable() {
		return offeredInputSalaryVariable;
	}
	/**
	 * @param offeredInputSalaryVariable the offeredInputSalaryVariable to set
	 */
	public void setOfferedInputSalaryVariable(String offeredInputSalaryVariable) {
		this.offeredInputSalaryVariable = offeredInputSalaryVariable;
	}
	/**
	 * @return the offeredInputSalaryVariableEditable
	 */
	public boolean isOfferedInputSalaryVariableEditable() {
		return offeredInputSalaryVariableEditable;
	}
	/**
	 * @param offeredInputSalaryVariableEditable the offeredInputSalaryVariableEditable to set
	 */
	public void setOfferedInputSalaryVariableEditable(
			boolean offeredInputSalaryVariableEditable) {
		this.offeredInputSalaryVariableEditable = offeredInputSalaryVariableEditable;
	}
}
