package com.talentPool.dynamicReports.data;

import com.talentPool.common.db.SimpleDataObject;

public class BlackListReportData extends SimpleDataObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return srNo
	 */
	public String getSrNo(){
		return getString("srNo");
	}
	
	/**
	 * @param srNo
	 */
	public void setSrNo(String srNo){
		setAttribute("srNo", srNo);
	}
	
	/**
	 * @return blacklistDate
	 */
	public String getBlacklistDate(){
		return getString("blacklistDate");
	}
	
	/**
	 * @param blacklistDate
	 */
	public void setBlacklistDate(String blacklistDate){
		setAttribute("blacklistDate",blacklistDate);
	}
	
	/**
	 * @return name
	 */
	public String getName(){
		return getString("name");
	}
	
	/**
	 * @param name
	 */
	public void setName(String name){
		setAttribute("name", name);
	}
	
	/**
	 * @return blacklistReason
	 */
	public String getBlacklistReason(){
		return getString("blacklistReason");
	}
	
	/**
	 * @param blacklistReason
	 */
	public void setBlacklistReason(String blacklistReason){
		setAttribute("blacklistReason", blacklistReason);
	}
	
	/**
	 * @return blacklistedBy
	 */
	public String getBlacklistedBy(){
		return getString("blacklistedBy");
	}
	
	/**
	 * @param blacklistedBy
	 */
	public void setBlacklistedBy(String blacklistedBy){
		setAttribute("blacklistedBy", blacklistedBy);
	}
	
	/**
	 * @return email
	 */
	public String getEmail(){
		return getString("email");
	}
	
	/**
	 * @param email
	 */
	public void setEmail(String email){
		setAttribute("email", email);
	}
	
	/**
	 * @return mobile
	 */
	public String getMobile(){
		return getString("mobile");
	}
	
	/**
	 * @param mobile
	 */
	public void setMobile(String mobile){
		setAttribute("mobile", mobile);
	}
	/**
	 * @return currentEmployer
	 */
	public String getCurrentEmployer(){
		return getString("currentEmployer");
	}
	
	/**
	 * @param currentEmployer
	 */
	public void setCurrentEmployer(String currentEmployer){
		setAttribute("currentEmployer", currentEmployer);
	}
	
	/**
	 * @return currentCtc
	 */
	public String getCurrentCtc(){
		return getString("currentCtc");
	}
	
	/**
	 * @param currentCtc
	 */
	public void setCurrentCtc(String currentCtc){
		setAttribute("currentCtc", currentCtc);
	}
	/**
	 * @return source
	 */
	public String getSource(){
		return getString("source");
	}
	
	/**
	 * @param source
	 */
	public void setSource(String source){
		setAttribute("source", source);
	}
	/**
	 * @return applicantId
	 */
	public String getApplicantId(){
		return getString("applicantId");
	}
	
	/**
	 * @param applicantId
	 */
	public void setApplicantId(String applicantId){
		setAttribute("applicantId", applicantId);
	}
	/**
	 * @return position
	 */
	public String getPosition(){
		return getString("position");
	}
	
	/**
	 * @param position
	 */
	public void setPosition(String position){
		setAttribute("position", position);
	}
	/**
	 * @return step
	 */
	public String getStep(){
		return getString("step");
	}
	
	/**
	 * @param step
	 */
	public void setStep(String step){
		setAttribute("step", step);
	}
}
