/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.djhelper.wrappers;

/**
 * A wrapper to month name which is used in Crosstab reports as sorting order is decided by month sequence and not by month name 
 * @author PraveenK
 * @since  Dec 28, 2011
 */
public class JasperMonthWrapper implements Comparable<JasperMonthWrapper>, DJComparatorWrapper {

	/**
	 * Name of the month like January , February , March etc..
	 */
	private String monthName;
	
	/**
	 * Month Sequence that represent the month and starts from 1 to 12 , like 1-January , 2-February , 3-March
	 */
	private Integer monthSequence;
	
	public JasperMonthWrapper(String monthName, Integer monthSequence) {
		this.monthName = monthName;
		this.monthSequence = monthSequence;
	}
	/**
	 * @return the stepId
	 */
	public Integer getMonthSequence() {
		return monthSequence;
	}
	/**
	 * @param stepRank the stepId to set
	 */
	public void setMonthSequence(Integer monthSequence) {
		this.monthSequence = monthSequence;
	}
	/**
	 * @return the name
	 */
	public String getMonthName() {
		return monthName;
	}
	/**
	 * @param name the name to set
	 */
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	@Override
	public int compareTo(JasperMonthWrapper o) {
		if(this.getMonthSequence()<o.getMonthSequence())
			return -1;
		else if(this.getMonthSequence()>o.getMonthSequence())
			return 1;
		else
			return 0;
	}
	
	@Override
	public String toString() {
		return this.monthName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof JasperMonthWrapper){
			return this.monthSequence==((JasperMonthWrapper)obj).getMonthSequence();			
		}
		else 
			return false;
	}
	
	@Override
	public int hashCode() {
		return this.monthSequence;
	}
}
