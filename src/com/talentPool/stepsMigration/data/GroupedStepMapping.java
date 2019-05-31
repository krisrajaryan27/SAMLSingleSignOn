/**
 * 
 */
package com.talentPool.stepsMigration.data;

/**
 * @author PraveenK
 * @since  Jan 5, 2012
 */
public class GroupedStepMapping {

	private int groupedStepId;
	private String stepIds;
	private String stepName;
	private String stepMappingId;
	private String isScheduled;
	
	/**
	 * @return the groupedStepId
	 */
	public int getGroupedStepId() {
		return groupedStepId;
	}
	/**
	 * @param groupedStepId the groupedStepId to set
	 */
	public void setGroupedStepId(int groupedStepId) {
		this.groupedStepId = groupedStepId;
	}
	/**
	 * @return the stepIds
	 */
	public String getStepIds() {
		return stepIds;
	}
	/**
	 * @param stepIds the stepIds to set
	 */
	public void setStepIds(String stepIds) {
		this.stepIds = stepIds;
	}
	/**
	 * @return the stepName
	 */
	public String getStepName() {
		return stepName;
	}
	/**
	 * @param stepName the stepName to set
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	/**
	 * @return the stepMappingId
	 */
	public String getStepMappingId() {
		return stepMappingId;
	}
	/**
	 * @param stepMappingId the stepMappingId to set
	 */
	public void setStepMappingId(String stepMappingId) {
		this.stepMappingId = stepMappingId;
	}
	/**
	 * @return the isScheduled
	 */
	public String getIsScheduled() {
		return isScheduled;
	}
	/**
	 * @param isScheduled the isScheduled to set
	 */
	public void setIsScheduled(String isScheduled) {
		this.isScheduled = isScheduled;
	}
	
}
