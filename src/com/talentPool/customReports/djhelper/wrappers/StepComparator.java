/**
 * 
 */
package com.talentPool.customReports.djhelper.wrappers;

/**
 * A wrapper to step name to decide the sorting based on step rank instead of step name 
 * @author PraveenK
 * @since  Dec 28, 2011
 */
public class StepComparator implements Comparable<StepComparator>, DJComparatorWrapper {

	private String stepName;
	private Integer stepRank;
	
	public StepComparator(String stepLevelName, Integer stepRank) {
		super();
		this.stepName = stepLevelName;
		this.stepRank = stepRank;
	}
	
	/**
	 * @return the stepId
	 */
	public Integer getStepRank() {
		return stepRank;
	}
	
	/**
	 * @param stepRank the stepId to set
	 */
	public void setStepRank(Integer stepRank) {
		this.stepRank = stepRank;
	}
	
	/**
	 * @return the name
	 */
	public String getStepName() {
		return stepName;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setStepName(String stepLevelName) {
		this.stepName = stepLevelName;
	}
	
	@Override
	public int compareTo(StepComparator o) {
		if(this.getStepRank()<o.getStepRank())
			return -1;
		else if(this.getStepRank()>o.getStepRank())
			return 1;
		else
			return 0;
	}
	
	@Override
	public String toString() {
		return this.stepName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof StepComparator){
			return this.stepRank==((StepComparator)obj).getStepRank();			
		}
		else 
			return false;
	}
	
	@Override
	public int hashCode() {
		return this.stepRank;
	}
	

}
