/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.djhelper.djCustomImpl.totalProvider;

import com.talentPool.common.properties.TPLabels;

import ar.com.fdvs.dj.domain.DJCRosstabMeasurePrecalculatedTotalProvider;


/**
 * Total Provider for few measures where there is a need to display blank total 
 * as actual calculated total does not give a correct information  
 * @author PraveenK
 * @since  Mar 30, 2012
 */
public class BlankTotalProvider implements DJCRosstabMeasurePrecalculatedTotalProvider {
	
	private final String BLANK_TOTAL_DESCRIPTER = TPLabels.getLabel("custom_report.message.blank_total_descripter");

	/* (non-Javadoc)
	 * @see ar.com.fdvs.dj.domain.DJCRosstabMeasurePrecalculatedTotalProvider#getValueFor(java.lang.String[], java.lang.Object[], java.lang.String[], java.lang.Object[])
	 */
	@Override
	public Object getValueFor(String[] colProp, Object[] colValue,
			String[] rowProp, Object[] rowValue) {
		return BLANK_TOTAL_DESCRIPTER;
	}

}
