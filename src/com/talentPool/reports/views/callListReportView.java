package com.talentPool.reports.views;

import java.util.ArrayList;

import com.talentPool.common.db.SimpleDataObject;

public class callListReportView extends SimpleDataObject {
	
	public String commaSeptSkills ;

	public callListReportView(){
		super();
	}
	
	public String getApplicantId() {
		return getString("applicantId");
	}
	
	public String getApplicantName(){
		
		return getString("applicantName");
	}
	
	public String getPositionTitle() {
		return getString("positionTitle");
	}
	
	public String getApplicantCellPhone() {
		return getString("applicantCellPhone");
	}
	
	public String getApplicantHomePhone() {
		return getString("applicantHomePhone");
	}
	
	public String getApplicantWorkPhone() {
		return getString("applicantWorkPhone");
	}
	
	public String getApplicantEmail1() {
		return getString("applicantEmail1");
	}
	
	public String getApplicantEmail2() {
		return getString("applicantEmail2");
	}
	
	public String getDeptId(){
		return getString("deptId");
	}
	
	public String getDeptName(){
		return getString("deptName");
	}
	
	public String getStatusMessage(){
		
		return getString("statusMessage");
	}
	
	public String getStepIn(){
		
		return getString("stepIn");
	}
	
	public int getYearsOfExp(){
		return getInt("yearsOfExp");
	}
	
	public void setSkills(ArrayList skills){
		StringBuffer skillList = new StringBuffer();
		if(skills!=null){
			for (int i = 0; i < skills.size(); i++){
				SimpleDataObject sDO = (SimpleDataObject) skills.get(i);
				skillList.append(sDO.getString("skill"));
				if (i < skills.size() - 1) {
					skillList.append(", ");
				}
			}
		}
		commaSeptSkills = skillList.toString();
	}
	
	public String getCommaSeptSkills(){
		return commaSeptSkills;
	}
	
	public String getDegree(){
		return getString("degree");
	}
	
	public String getInstitute(){
		return getString("institute");
	}
	
	public String getYearOfPassing(){
		
		return getString("yearOfPassing");
	}
	
	public String getApplicantPositionId(){
		return getString("applicantPositionId");
	}
	
	public String getSkill(){
		return getString("skill");
	}
	public String getUserName(){
		return getString("userName");
	}
}
