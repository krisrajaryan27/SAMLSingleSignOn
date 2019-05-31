/**
 * 
 */
package com.talentPool.vendorservice.dataobject;

/**
 * @author pallavi
 *
 */
public class VcandidateData {
	private String name;
	private String departmentPosition;
	private String dateUpdated;
	private String status;
	/**
	 * @return the dateUpdated
	 */
	public String getDateUpdated() {
		return dateUpdated;
	}
	/**
	 * @param dateUpdated 
	 * 			the dateUpdated to set
	 */
	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	/**
	 * @return the departmentPosition
	 */
	public String getDepartmentPosition() {
		return departmentPosition;
	}
	/**
	 * @param departmentPosition 
	 * 			the departmentPosition to set
	 */
	public void setDepartmentPosition(String departmentPosition) {
		this.departmentPosition = departmentPosition;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name 
	 * 			the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status 
	 * 			the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}	
}
