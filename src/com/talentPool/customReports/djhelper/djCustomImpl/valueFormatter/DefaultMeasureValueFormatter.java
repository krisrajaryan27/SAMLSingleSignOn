/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.djhelper.djCustomImpl.valueFormatter;

import java.util.Map;

import ar.com.fdvs.dj.domain.DJValueFormatter;

/**
 * @author PraveenK
 * @since  Mar 30, 2012
 */
public class DefaultMeasureValueFormatter implements DJValueFormatter {

	/* (non-Javadoc)
	 * @see ar.com.fdvs.dj.domain.DJValueFormatter#evaluate(java.lang.Object, java.util.Map, java.util.Map, java.util.Map)
	 */
	@Override
	public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
		if(value!=null){
			if(value instanceof Integer){
				Integer val = (Integer) value;
				if(val==0){
					return null;
				}
			}else if(value instanceof String){
				if(((String)value).equals("0")){
					return null;
				}
			}			
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see ar.com.fdvs.dj.domain.DJValueFormatter#getClassName()
	 */
	@Override
	public String getClassName() {
		return Integer.class.getName();
	}

}
