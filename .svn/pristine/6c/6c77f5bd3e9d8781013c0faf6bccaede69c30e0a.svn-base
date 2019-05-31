/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.queryBuilder.utils;

import java.util.List;

import com.talentPool.customReports.constants.CRColumnValueConstants;
import com.talentPool.customReports.jaxb.Field;

/**
 * Utils class for QueryBuilder
 * @author PraveenK
 * @since  Dec 20, 2011
 */
public class QueryBuilderUtils {
	
	/**
	 * Checks whether any grouping fields (SUM, FIRST or LAST) available in the given list of fields 
	 * @param fields
	 * @return true if grouping fields available
	 * false if no grouping fields available
	 */
	public static boolean haveGroupingFields(List<Field> fields){
		for (Field field : fields) {
			if(CRColumnValueConstants.COLUMN_VALUE_TYPE_SUM.equals(field.getFieldValueType()))
				return true;
			else if(CRColumnValueConstants.COLUMN_VALUE_TYPE_FIRST.equals(field.getFieldValueType()))
				return true;
			else if(CRColumnValueConstants.COLUMN_VALUE_TYPE_LAST.equals(field.getFieldValueType()))
				return true;
		}
		return false;
	}
}
