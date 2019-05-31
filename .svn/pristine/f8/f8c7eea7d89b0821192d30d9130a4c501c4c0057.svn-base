/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.djhelper.constants;

import java.util.HashMap;
import java.util.Map;

import com.talentPool.customReports.djhelper.wrappers.JasperDateWrapper;
import com.talentPool.customReports.djhelper.wrappers.JasperMonthWrapper;
import com.talentPool.customReports.djhelper.wrappers.JasperWeekWrapper;
import com.talentPool.customReports.djhelper.wrappers.StepLevelComparator;
import com.talentPool.customReports.djhelper.wrappers.StepComparator;


/**
 * @author PraveenK
 * @since  Dec 28, 2011
 */
public class DJHelperConstants {
	
	private DJHelperConstants(){}

	/**
	 * DJ Comparator Map which maps each Wrapper comparator to parameter name that will be used in JRXML 
	 */
	public static final Map<String,String> DJ_COMPARATORS_MAP;
	
	/**
	 * A String constant used to fetch the order by field. 
	 * For example if the field name in POS and if its ordering is defined by any other
	 * field then data is fetched with name POS_ORDER_BY   
	 */
	public static final String ORDER_BY_FIELD = "_ORDER_BY";
	
	
	/**
	 * A Constant used by to find any cross tab parameters with this suffix present jasper reports
	 * So that we can add Total provider objects for these parameters
	 */
	public static final String TOTAL_PROVIDER_PARAM_NAME_SUFFIX = "_totalProvider".intern();
	
	/**
	 * A Constant used by to find any cross tab parameters with this suffix present jasper reports
	 * So that we can add value formatter objects for these parameters
	 */
	public static final String VALUE_FORMATTER_PARAM_NAME_SUFFIX = "_vf".intern();
	
	static{
		DJ_COMPARATORS_MAP = new HashMap<String, String>();
		DJ_COMPARATORS_MAP.put(StepLevelComparator.class.getName(), "OVERRIDE_COMPARATOR_StepLevelWrapper");
		DJ_COMPARATORS_MAP.put(StepComparator.class.getName(), "OVERRIDE_COMPARATOR_StepWrapper");
		DJ_COMPARATORS_MAP.put(JasperMonthWrapper.class.getName(), "OVERRIDE_COMPARATOR_JasperMonthWrapper");
		DJ_COMPARATORS_MAP.put(JasperDateWrapper.class.getName(), "OVERRIDE_COMPARATOR_JasperDateWrapper");
		DJ_COMPARATORS_MAP.put(JasperWeekWrapper.class.getName(), "OVERRIDE_COMPARATOR_JasperWeekWrapper");
	}
	
	/**
	 * Retrieves value from static {@link #DJ_COMPARATORS_MAP} for the given key
	 * @param key
	 * @return
	 */
	public static String getComparatorParamValue(String key){
		return DJ_COMPARATORS_MAP.get(key);
	} 
}
