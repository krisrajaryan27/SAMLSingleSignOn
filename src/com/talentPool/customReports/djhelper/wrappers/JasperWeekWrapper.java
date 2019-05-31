/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.djhelper.wrappers;

/**
 * A Data Type which acts as a Wrapper to java.util.Date in Japser reports. 
 * <br> If jasper field data type is set this wrapper then the value displayed will be week for given date 
 * and in cross tab this wrapper will used to sort based on <code>comaperTo</code> implementation.
 * @author PraveenK
 * @since  Mar 19, 2012
 * 
 */
public class JasperWeekWrapper implements Comparable<JasperWeekWrapper>, DJComparatorWrapper { 

	/**
	 * Week Display string 
	 */
	private String weekDispalyString;
	
	/**
	 * Week rank
	 */
	private Integer weekRank;
	
	public JasperWeekWrapper(String weekDispalyString, Integer weekRank) {
		this.weekDispalyString = weekDispalyString;
		this.weekRank = weekRank;
	}
	/**
	 * @return the weekRank
	 */
	public Integer getWeekRank() {
		return weekRank;
	}
	/**
	 * @param weekRank the weekRank to set
	 */
	public void setWeekRank(Integer weekRank) {
		this.weekRank = weekRank;
	}
	/**
	 * @return the weekDispalyString
	 */
	public String getWeekDispalyString() {
		return weekDispalyString;
	}
	/**
	 * @param weekDispalyString the weekDispalyString to set
	 */
	public void setWeekDispalyString(String monthName) {
		this.weekDispalyString = monthName;
	}
	@Override
	public int compareTo(JasperWeekWrapper jww) {
		if(this.getWeekRank()<jww.getWeekRank())
			return -1;
		else if(this.getWeekRank()>jww.getWeekRank())
			return 1;
		else
			return 0;
	}
	
	@Override
	public String toString() {
		return this.weekDispalyString;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof JasperMonthWrapper){
			return this.weekRank==((JasperMonthWrapper)obj).getMonthSequence();			
		}
		else 
			return false;
	}
	
	@Override
	public int hashCode() {
		return this.weekRank;
	}
}
