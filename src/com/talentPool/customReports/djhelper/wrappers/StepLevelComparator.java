/**
 * 
 */
package com.talentPool.customReports.djhelper.wrappers;

/**
 * A wrapper to stage which defines the order of stage based on Step Level Id
 * @author PraveenK
 * @since  Dec 28, 2011
 */
public class StepLevelComparator implements Comparable<StepLevelComparator>, DJComparatorWrapper {

	private String stepLevelName;
	private Integer stepLevelId;
	
	public StepLevelComparator(String stepLevelName, Integer stepLevelId) {
		super();
		this.stepLevelName = stepLevelName;
		this.stepLevelId = stepLevelId;
	}
	
	/**
	 * @return the stepId
	 */
	public Integer getStepLevelId() {
		return stepLevelId;
	}
	
	/**
	 * @param stepId the stepId to set
	 */
	public void setStepLevelId(Integer stepLevelId) {
		this.stepLevelId = stepLevelId;
	}
	
	/**
	 * @return the name
	 */
	public String getStepLevelName() {
		return stepLevelName;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setStepLevelName(String stepLevelName) {
		this.stepLevelName = stepLevelName;
	}
	
	@Override
	public int compareTo(StepLevelComparator o) {
		if(this.getStepLevelId()<o.getStepLevelId())
			return -1;
		else if(this.getStepLevelId()>o.getStepLevelId())
			return 1;
		else
			return 0;
	}
	
	@Override
	public String toString() {
		return this.stepLevelName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof StepLevelComparator){
			return this.stepLevelId==((StepLevelComparator)obj).getStepLevelId();			
		}
		else 
			return false;
	}
	
	@Override
	public int hashCode() {
		return this.stepLevelId;
	}

}
