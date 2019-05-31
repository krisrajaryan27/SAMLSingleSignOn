/**
 * 
 */
package com.talentPool.inbox.dataobject;


import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class AutoImportEducationData extends SimpleDataObject {
	public AutoImportEducationData(String degree, String branch, String institute, String yearOfPassing, String grade) {
		setDegree(degree);
		setBranch(branch);
		setInstitute(institute);
		setYearOfPassing(yearOfPassing);
		setGrade(grade);
	}

	public void setYearOfPassing(String yearOfPassing) {
		setAttribute("yearOfPassing", yearOfPassing);
	}

	public String getYearOfPassing() {
		return getString("yearOfPassing");
	}

	public void setInstitute(String institute) {
		setAttribute("institute", institute);
	}

	public String getInstitute() {
		return getString("institute");
	}

	public void setDegree(String degree) {
		setAttribute("degree", degree);
	}

	public String getDegree() {
		return getString("degree");
	}

	public void setBranch(String branch) {
		setAttribute("branch", branch);
	}

	public String getBranch() {
		return getString("branch");
	}


	public void setGrade(String grade) {
		setAttribute("grade", grade);
	}

	public String getGrade() {
		return getString("grade");
	}

}
