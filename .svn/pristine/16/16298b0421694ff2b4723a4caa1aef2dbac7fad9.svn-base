/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.djhelper.wrappers;

import java.util.Date;

import com.talentPool.common.utils.DateUtils;

/**
 * A Data Type which acts as a Wrapper to java.util.Date in Japser reports. 
 * <br> If jasper field data type is set this wrapper then the value displayed will system date format 
 * and in cross tab this wrapper will used to sort based on <code>comaperTo</code> implementation.
 * @author PraveenK
 * @since  Mar 16, 2012    
 */
public class JasperDateWrapper implements Comparable<JasperDateWrapper>, DJComparatorWrapper {
	
	private Date date;
	
	public JasperDateWrapper(Date date){
		this.date=date;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(JasperDateWrapper dateWrapper) {
		if(this.date!=null && dateWrapper.getDate()!=null){
			if(org.apache.commons.lang.time.DateUtils.isSameDay(this.date, dateWrapper.getDate()))
				return 0;
			else if(this.date.after(dateWrapper.getDate()))
				return 1;
			else 
				return -1;			
		}
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return DateUtils.getSystemDateFormat(this.date);
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}
}
