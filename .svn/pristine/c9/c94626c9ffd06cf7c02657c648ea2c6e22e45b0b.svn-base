package com.talentPool.positions.dataobject;

import java.sql.Date;
import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;

public class PositionData extends SimpleDataObject {

	private static final long serialVersionUID = 1L;

	public PositionData() {

	}

	public String getPositionId() {
		return getId("positionId");
	}

	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}

	public String getPositionReferenceCode() {
		return getString("positionReferenceCode");
	}

	public void setPositionReferenceCode(String positionReferenceCode) {
		setAttribute("positionReferenceCode", positionReferenceCode);
	}
	
	public String getPositionCode() {
		return getString("positionCode");
	}

	public void setPositionCode(String positionCode) {
		setAttribute("positionCode", positionCode);
	}

	public String getPositionTitle() {
		return getString("positionTitle");
	}

	public void setPositionTitle(String positionTitle) {
		setAttribute("positionTitle", positionTitle);
	}

	public int getNoOfPositions() {
		return getInt("noOfPositions");
	}

	public void setNoOfPositions(int noOfPositions) {
		setAttribute("noOfPositions", new Integer(noOfPositions));
	}

	public Date getPositionExpiryDate() {
		try {
			return getDate("positionExpiryDate");	
		} catch (ClassCastException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return null;
	}
	
	public String getPositionExpiryDateToDisplay() {
		return DateUtils.getSystemDateFormat(getPositionExpiryDate());
	}

	public void setPositionExpiryDate(Date positionExpiryDate) {
		setAttribute("positionExpiryDate", positionExpiryDate);
	}

	public String getPositionNote() {
		return getString("positionNote");
	}

	public void setPositionNote(String positionNote) {
		setAttribute("positionNote", positionNote);
	}

	public float getMinExperience() {
		return getFloat("minExperience");
	}

	public void setMinExperience(float minExperience) {
		setAttribute("minExperience", new Float(minExperience));
	}

	public float getMaxExperience() {
		return getFloat("maxExperience");
	}

	public void setMaxExperience(float maxExperience) {
		setAttribute("maxExperience", new Float(maxExperience));
	}

	public int getCurrentNoOfPositions() {
		return getInt("currentNoOfPositions");
	}

	public void setCurrentNoOfPositions(int currentNoOfPositions) {
		setAttribute("currentNoOfPositions", new Integer(currentNoOfPositions));
	}

	public String getPositionStatus() {
		return getId("positionStatus");
	}

	public void setPositionStatus(String positionStatus) {
		setAttribute("positionStatus", positionStatus);
	}

	public String getDeptName() {
		return getString("deptName");
	}

	public void setDeptName(String deptName) {
		setAttribute("deptName", deptName);
	}

	public int getDepartmentId() {
		return getInt("departmentId");
	}

	public void setDepartmentId(int departmentId) {
		setAttribute("departmentId", new Integer(departmentId));
	}

	public int getDegreeId() {
		return getInt("degreeId");
	}

	public void setDegreeId(int degreeId) {
		setAttribute("degreeId", new Integer(degreeId));
	}

	public String getDegreeName() {
		return getString("degreeName");
	}

	public void setDegreeName(String degreeName) {
		setAttribute("degreeName", degreeName);
	}
	
	public ArrayList getPrimarySkills() {
		return (ArrayList) getAttribute("primarySkills");
	}

	public void setPrimarySkills(ArrayList primarySkills) {
		setAttribute("primarySkills", primarySkills);
	}

	public ArrayList getSecondarySkills() {
		return (ArrayList) getAttribute("secondarySkills");
	}

	public void setSecondarySkills(ArrayList secondarySkills) {
		setAttribute("secondarySkills", secondarySkills);
	}

	public String getRequirements() {
		return getString("requirements");
	}

	public void setRequirements(String requirements) {
		setAttribute("requirements", requirements);
	}

	public String getResponsibilities() {
		return getString("responsibilities");
	}

	public void setResponsibilities(String responsibilities) {
		setAttribute("responsibilities", responsibilities);
	}

	public ArrayList getSteps() {
		return (ArrayList) getAttribute("steps");
	}

	public void setSteps(ArrayList steps) {
		setAttribute("steps", steps);
	}

	public ArrayList getRequisitioners() {
		return (ArrayList) getAttribute("requisitioners");
	}

	public void setRequisitioners(ArrayList requisitioners) {
		setAttribute("requisitioners", requisitioners);
	}

	public ArrayList getRecruiters() {
		return (ArrayList) getAttribute("recruiters");
	}

	public void setRecruiters(ArrayList recruiters) {
		setAttribute("recruiters", recruiters);
	}

	public ArrayList getInterviewers() {
		return (ArrayList) getAttribute("interviewers");
	}

	public void setInterviewers(ArrayList interviewers) {
		setAttribute("interviewers", interviewers);
	}

	public int getNoOfOffers() {
		return getInt("noOfOffers");
	}

	public void setNoOfOffers(int noOfOffers) {
		setAttribute("noOfOffers", new Integer(noOfOffers));
	}
	
	public int getJoined(){
		return getInt("joined");
	}
	
	public void setJoined(int joined){
		setAttribute("joined", new Integer(joined));
		
	}
	public int getBranchId() {
		return getInt("branchId");
	}

	public void setBranchId(int branchId) {
		setAttribute("branchId", new Integer(branchId));
	}

	public String getBranchName() {
		return getString("branchName");
	}

	public void setBranchName(String branchName) {
		setAttribute("branchName", branchName);
	}

	public int getNoInProcess() {
		return getInt("noInProcess");
	}

	public void setNoInProcess(int noInProcess) {
		setAttribute("noInProcess", new Integer(noInProcess));
	}
	
	/**
	 * @return Returns the positionOwnerId.
	 */
	public String getPositionOwnerId() {
		return getString("positionOwnerId");
	}

	/**
	 * @param positionOwnerId 
	 * 			The positionOwnerId to set.
	 */
	public void setPositionOwnerId(String positionOwnerId) {
		setAttribute("positionOwnerId", positionOwnerId);
	}

	/**
	 * @return Returns the positionOwnerName.
	 */
	public String getPositionOwnerName() {
		return getString("positionOwnerName");
	}

	/**
	 * @param positionOwnerName 
	 * 			The positionOwnerName to set.
	 */
	public void setPositionOwnerName(String positionOwnerName) {
		setAttribute("positionOwnerName", positionOwnerName);
	}	
	
	/**
	 * @return Returns the requestedById.
	 */
	public String getRequestedById() {
		return getString("requestedById");
	}

	/**
	 * @param requestedById 
	 * 			The requestedById to set.
	 */
	public void setRequestedById(String requestedById) {
		setAttribute("requestedById", requestedById);
	}

	/**
	 * @return Returns the requestedByName.
	 */
	public String getRequestedByName() {
		return getString("requestedByName");
	}

	/**
	 * @param requestedByName 
	 * 			The requestedByName to set.
	 */
	public void setRequestedByName(String requestedByName) {
		setAttribute("requestedByName", requestedByName);
	}	

	/**
	 * @return Returns the positionCreationDate.
	 */
	public Date getPositionCreationDate() {
		try {
			return getDate("positionCreationDate");			
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
		}
		return null;
	}
	
	/**
	 * @return Returns the positionCreationDate To Display in system date format.
	 */
	public String getPositionCreationDateToDisplay() {
		return DateUtils.getSystemDateFormat(getPositionCreationDate());
	}
	
	

	/**
	 * @param positionCreationDate 
	 * 			The positionCreationDate to set.
	 */
	public void setPositionCreationDate(String positionCreationDate) {
		setAttribute("positionCreationDate", positionCreationDate);
	}
	
	/**
	 * @return Returns the positionPriority.
	 */
	public String getPositionPriority() {
		return getString("positionPriority");
	}

	/**
	 * @param positionPriority 
	 * 			The positionPriority to set.
	 */
	public void setPositionPriority(String positionPriority) {
		setAttribute("positionPriority", positionPriority);
	}	
	/**
	 * @return the publishedForWalkIn
	 */
	public String getPublishedForWalkIn() {
		return getString("publishedForWalkIn");
	}

	/**
	 * @param publishedForWalkIn the publishedForWalkIn to set
	 */
	public void setPublishedForWalkIn(String publishedForWalkIn) {
		setAttribute("publishedForWalkIn", publishedForWalkIn);
	}
	
	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return getString("locationName");
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		setAttribute("locationName", locationName);
	}
	
	/**
	 * @return the publishedEmployeePortal
	 */
	public String getPublishedEmployeePortal() {
		return getString("publishedEmployeePortal");
	}

	/**
	 * @param publishedEmployeePortal the publishedEmployeePortal to set
	 */
	public void setPublishedEmployeePortal(String publishedEmployeePortal) {
		setAttribute("publishedEmployeePortal", publishedEmployeePortal);
	}	
	
	public String getPublishToSite() {
		return getString("publishToSite");
	}

	public void setPublishToSite(String publishToSite) {
		setAttribute("publishToSite", publishToSite);
	}	
	/**
	 * @return the publishedToVendors
	 */
	public String getPublishedToVendors() {
		return getString("publishedToVendors");
	}

	/**
	 * @param publishedToVendors the publishedToVendors to set
	 */
	public void setPublishedToVendors(String publishedToVendors) {
		setAttribute("publishedToVendors", publishedToVendors);
	}
	
	/**
	 * @return the positionVendors
	 */
	public String getPositionVendors() {
		if(Utils.isBlankOrNull(getString("positionVendors"))) {
			return "";
		} else {
			return getString("positionVendors");
		}
	}

	/**
	 * @param positionVendors the positionVendors to set
	 */
	public void setPositionVendors(String positionVendors) {
		setAttribute("positionVendors", positionVendors);
	}
	
	public String getRuleText() {
		return getString("ruleText");
	}

	public void setRuleText(String ruleText) {
		setAttribute("ruleText", ruleText);
	}	
	
	
	public Date getVendorPublishToDate() {
		return getDate("vendorPublishToDate");
	}
	
	public void setVendorPublishToDate(Date vendorPublishToDate) {
		setAttribute("vendorPublishToDate",vendorPublishToDate);
	}
	
	public Date getVendorPublishFromDate() {
		return getDate("vendorPublishFromDate");
	}
	
	public void setVendorPublishFromDate(Date vendorPublishFromDate) {
		setAttribute("vendorPublishFromDate",vendorPublishFromDate);
	}
	
	public Date getEmployeePublishToDate() {
		return getDate("employeePublishToDate");
	}
	
	public void setEmployeePublishToDate(Date employeePublishToDate) {
		setAttribute("employeePublishToDate",employeePublishToDate);
	}
	
	public Date getEmployeePublishFromDate() {
		return getDate("employeePublishFromDate");
	}
	
	public void setEmployeePublishFromDate(Date employeePublishFromDate) {
		setAttribute("employeePublishFromDate",employeePublishFromDate);
	}
	
	public Date getWalkInPublishToDate() {
		return getDate("walkInPublishToDate");
	}
	
	public void setWalkInPublishToDate(Date walkInPublishToDate) {
		setAttribute("walkInPublishToDate",walkInPublishToDate);
	}
	
	public Date getWalkInPublishFromDate() {
		return getDate("walkInPublishFromDate");
	}
	
	public void setWalkInPublishFromDate(Date walkInPublishFromDate) {
		setAttribute("walkInPublishFromDate",walkInPublishFromDate);
	}
	
	public Date getWebSitePublishToDate() {
		return getDate("webSitePublishToDate");
	}
	
	public void setWebSitePublishToDate(Date webSitePublishToDate) {
		setAttribute("webSitePublishToDate",webSitePublishToDate);
	}
	
	public Date getWebSitePublishFromDate() {
		return getDate("webSitePublishFromDate");
	}
	
	public String geStringWebSitePublishToDate() {
		return getString("stringWebSitePublishToDate");
	}
	
	public void setStringWebSitePublishToDate(String stringWebSitePublishToDate) {
		setAttribute("stringWebSitePublishToDate",stringWebSitePublishToDate);
	}
	
	public void setWebSitePublishFromDate(Date webSitePublishFromDate) {
		setAttribute("webSitePublishFromDate",webSitePublishFromDate);
	}
	
	public String getReferalFees() {
		return getString("referalFees");
	}

	public String getStringWebSitePublishFromDate() {
		return getString("stringWebSitePublishFromDate");
	}
	
	public void setStringWebSitePublishFromDate(String stringWebSitePublishFromDate) {
		setAttribute("stringWebSitePublishFromDate",stringWebSitePublishFromDate);
	}
	
	public void setReferalFees(String referalFees) {
		setAttribute("referalFees", referalFees);
	}
	
	public String getExperience() {
		return getString("experience");
	}

	public void setExperience(String experience) {
		setAttribute("experience", experience);
	}
	
	public String getEducation() {
		return getString("education");
	}

	public void setEducation(String education) {
		setAttribute("education", education);
	}
	
	public String getPrimarySkillsAsString() {
		return getString("primarySkillsAsString");
	}

	public void setPrimarySkillsAsString(String primarySkillsAsString) {
		setAttribute("primarySkillsAsString", primarySkillsAsString);
	}

	public String getSecondarySkillsAsString() {
		return  getString("secondarySkillsAsString");
	}

	public void setSecondarySkillsAsString(String secondarySkillsAsString) {
		setAttribute("secondarySkillsAsString", secondarySkillsAsString);
	}
	
	public String getDepartmentName() {
		return  getString("departmentName");
	}

	public void setDepartmentName(String departmentName) {
		setAttribute("departmentName", departmentName);
	}
	
	public String getSubDeptName() {
		return  getString("subDeptName");
	}

	public void setSubDeptName(String subDeptName) {
		setAttribute("subDeptName", subDeptName);
	}
	
	public String getGroupName() {
		return  getString("groupName");
	}

	public void setGroupName(String groupName) {
		setAttribute("groupName", groupName);
	}
	
	public String getSub3DeptName() {
		return  getString("sub3DeptName");
	}

	public void setSub3DeptName(String sub3DeptName) {
		setAttribute("sub3DeptName", sub3DeptName);
	}
	
	public String getSub4DeptName() {
		return  getString("sub4DeptName");
	}

	public void setSub4DeptName(String sub4DeptName) {
		setAttribute("sub4DeptName", sub4DeptName);
	}
	
	public String getPositionLevel() {
		return  getString("positionLevel");
	}

	public void setPositionLevel(String positionLevel) {
		setAttribute("positionLevel", positionLevel);
	}
	
	public String getApprovedBy() {
		return  getString("approvedBy");
	}

	public void setApprovedBy(String approvedBy) {
		setAttribute("approvedBy", approvedBy);
	}
	
	public String getRequestedBy() {
		return  getString("requestedBy");
	}

	public void setRequestedBy(String requestedBy) {
		setAttribute("requestedBy", requestedBy);
	}
	
	public String getBudgetItemName() {
		return  getString("budgetItemName");
	}

	public void setBudgetItemName(String budgetItemName) {
		setAttribute("budgetItem", budgetItemName);
	}
	public String getBandName() {
		return  getString("bandName");
	}

	public void setBandName(String bandName) {
		setAttribute("bandName", bandName);
	}
	public String getGradeName() {
		return  getString("gradeName");
	}

	public void setGradeName(String gradeName) {
		setAttribute("gradeName", gradeName);
	}
	
		
	public String getEmployeeApplyRefer() {
		return  getString("employeeApplyRefer");
	}

	public void setEmployeeApplyRefer(String employeeApplyRefer) {
		setAttribute("employeeApplyRefer", employeeApplyRefer);
	}
	
	public String getEmployeeCanEmail() {
		return  getString("employeeCanEmail");
	}

	public void setEmployeeCanEmail(String employeeCanEmail) {
		setAttribute("employeeCanEmail", employeeCanEmail);
	}
	
	public String getPositionClone() {
		return  getString("positionClone");
	}

	public void setPositionClone(String positionClone) {
		setAttribute("positionClone", positionClone);
	}
	
	public String getLastPostedSocialMediaType() {
		return  getString("lastPostedSocialMediaType");
	}

	public void setLastPostedSocialMediaType(String lastPostedSocialMediaType) {
		setAttribute("lastPostedSocialMediaType", lastPostedSocialMediaType);
	}
	
	public Date getLastPostedSocialMediaDate() {
		return getDate("lastPostedSocialMediaDate");
	}
	
	public void setLastPostedSocialMediaDate(Date lastPostedSocialMediaDate) {
		setAttribute("lastPostedSocialMediaDate", lastPostedSocialMediaDate);
	}	
	
	public String getIsPublishedToNaukri() {
		return getString("isPublishedToNaukri");
	}
	
	public void setIsPublishedToNaukri(String isPublishedToNaukri) {
		setAttribute("isPublishedToNaukri", isPublishedToNaukri);
	}	
	
	public String getDocResumePath(){
		return getString("docResumePath");
	}
	
	public void setDocResumePath(String docResumePath){
		setAttribute("docResumePath", docResumePath);
	}
	
	public String getHTMLResumePath(){
		return getString("htmlResumePath");
	}
	
	public void setHTMLResumePath(String htmlResumePath){
		setAttribute("htmlResumePath", htmlResumePath);
	}
	
	public String getFeedBackformTitle(){
		return getString("feedBackformTitle");
	}
	
	public void setFeedBackformTitle(String feedBackformTitle){
		setAttribute("feedBackformTitle", feedBackformTitle);
	}
}