/**
 * 
 */
package com.talentPool.masters.dataobject;

import java.util.ArrayList;

/**
 * @author shivprasad
 * 
 */
public class DepartmentData extends MasterData {

	public DepartmentData() {

	}

	public void setDepartmentLevel(String departmentLevel) {
		setAttribute("departmentLevel", departmentLevel);
	}

	public String getDepartmentLevel() {
		return getString("departmentLevel");
	}

	public void setChildrens(ArrayList<DepartmentData> childrens) {
		setAttribute("childrens", childrens);
	}

	public ArrayList<DepartmentData> getChildrens() {
		return (ArrayList<DepartmentData>) getAttribute("childrens");
	}
	
	public String getJobCode(){
		return getString("jobCode");
	}
	
	public void setItemName(String jobCode){
		setAttribute("jobCode",jobCode);
	}
	
	public String getItemExternalCode() {
		return getString("itemExternalCode");
	}

	public void setItemExternalCode(String itemExternalCode) {
		setAttribute("itemExternalCode", itemExternalCode);
	}
	
	
}
